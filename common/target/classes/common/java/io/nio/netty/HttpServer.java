package common.java.io.nio.netty;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

public class HttpServer
{
    
    public void start(int port)
        throws Exception
    {
        // EventLoopGroup实际就是一个EventLoop线程组，负责管理EventLoop的申请和释放
        // EventLoopGroup管理的线程数可以通过构造函数设置，如果没有设置，默认取-Dio.netty.eventLoopThreads，
        // 如果该系统参数也没有指定，则为可用的CPU内核数 × 2。
        
        // bossGroup线程组实际就是Acceptor线程池，负责处理客户端的TCP连接请求，
        // 如果系统只有一个服务端端口需要监听，则建议bossGroup线程组线程数设置为1
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        // workerGroup是真正负责I/O读写操作的线程组
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try
        {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>()
                {
                    @Override
                    public void initChannel(SocketChannel ch)
                        throws Exception
                    {
                        // server端发送的是httpResponse，所以要使用HttpResponseEncoder进行编码
                        ch.pipeline().addLast(new HttpResponseEncoder());
                        // server端接收到的是httpRequest，所以要使用HttpRequestDecoder进行解码
                        ch.pipeline().addLast(new HttpRequestDecoder());
                        ch.pipeline().addLast(new HttpServerInboundHandler());
                    }
                })
                // 服务器满负荷时最多可以积压128
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
            // 绑定端口同步等待成功
            ChannelFuture f = b.bind(port).sync();
            // 等待服务端监听端口关闭,主要靠此方法将main线程阻塞住，等待服务端链路关闭之后才退出
            f.channel().closeFuture().sync();
        }
        finally
        {
            // 服务关闭时，释放线程池资源
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args)
        throws Exception
    {
        HttpServer server = new HttpServer();
        server.start(8844);
    }
}
package common.java.io.nio.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

public class HttpClient
{
    public void connect(String host, int port)
        throws Exception
    {
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        
        try
        {
            Bootstrap b = new Bootstrap();
            b.group(workerGroup);
            b.channel(NioSocketChannel.class);
            b.option(ChannelOption.SO_KEEPALIVE, true);
            b.handler(new ChannelInitializer<SocketChannel>()
            {
                @Override
                public void initChannel(SocketChannel ch)
                    throws Exception
                {
                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                    ch.pipeline().addLast(new HttpResponseDecoder());
                    // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码
                    ch.pipeline().addLast(new HttpRequestEncoder());
                    ch.pipeline().addLast(new HttpClientInboundHandler());
                }
            });
            
            // 连接上之后再handler里面发送请求
            ChannelFuture f = b.connect(host, port).sync();
            
            // 此方法将main阻塞住
            f.channel().closeFuture().sync();
        }
        finally
        {
            workerGroup.shutdownGracefully();
        }
        
    }

    public static void main(String[] args)
        throws Exception
    {
        HttpClient client = new HttpClient();
        client.connect("127.0.0.1", 8844);
    }
}
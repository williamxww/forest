package common.java.nio.netty.halfpackage;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;

/**
 * 为了模拟tcp 粘包/拆包 一个数据包分为多次才收到
 *
 * @author acer
 * @version C10 2016年4月10日
 */
public class HalfPackageServer {
    public void start() throws Exception {
        EventLoopGroup parentGroup = new NioEventLoopGroup();
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(parentGroup, childGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 删除server and client此代码，可以看到粘包/拆包造成的影响
                            // 一条数据被拆分成好多个包发送到了服务端
                            // 设置定长解码器,这样就按照固定长度拆包
                            ch.pipeline().addLast(new FixedLengthFrameDecoder(30));
                            ch.pipeline().addLast(new HalfPackageServerHandler());
                        }

                    });
            ChannelFuture future = bootstrap.bind(9001).sync();
            future.channel().closeFuture().sync();

        } finally {
            parentGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) {
        HalfPackageServer server = new HalfPackageServer();
        try {
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

package common.java.nio.netty.halfpackage;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;

/**
 *
 * @author acer
 * @version C10 2016年4月10日
 */
public class HalfPackageClient {
    public void start() throws Exception {
        EventLoopGroup childGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(childGroup).channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            // 设置定长解码器,删除server and client此代码，可以看到粘包/拆包造成的影响
                            ch.pipeline().addLast(new FixedLengthFrameDecoder(30));
                            ch.pipeline().addLast(new HalfPackageClientHandler());
                        }

                    });
            ChannelFuture future = bootstrap.connect("127.0.0.1", 9001).sync();
            future.channel().closeFuture().sync();
        } finally {
            childGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        HalfPackageClient client = new HalfPackageClient();
        try {
            client.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

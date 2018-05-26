package common.java.io.nio.netty.halfpackage;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * @author acer
 * @version C10 2016年4月10日
 */
public class HalfPackageServerHandler extends ChannelInboundHandlerAdapter {
    private static final String MESSAGE = "what you send had been received ,and nice to meet you";

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("---------RECEIVED--------");
        ByteBuf msgBuf = (ByteBuf) msg;
        byte[] byteArray = new byte[msgBuf.readableBytes()];
        msgBuf.readBytes(byteArray);
        System.out.println(new String(byteArray));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}

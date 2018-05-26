package common.java.io.nio.netty.halfpackage;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 *
 * @author acer
 * @version C10 2016年4月10日
 */
public class HalfPackageClientHandler extends ChannelInboundHandlerAdapter
{
    private static final String req = "*********0";
    
    @Override
    public void channelActive(ChannelHandlerContext ctx)
        throws Exception
    {
        ByteBuf msgBuf = null;
        for (int i = 0; i < 200; i++)
        {
            msgBuf = Unpooled.buffer(req.length());
            msgBuf.writeBytes(req.getBytes());
            ctx.writeAndFlush(msgBuf);
        }
    }

}

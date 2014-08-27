package com.furtune.server.socket.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

import org.apache.log4j.Logger;

/**
 * The <code>ContentLengthDecoder</code> 是一个解码器，对消息进行解析.
 * <p>
 *  为以后的解密数据和扩展有很大的帮助。
 * 
 * @author Jimmy
 * 
 * @since <i>Trading v0.0.2(May 16, 2014)</i>
 */
public class ContentLengthDecoder extends ByteToMessageDecoder {
	private final Logger L = Logger.getLogger(getClass());
	
	private int msglen = -1;
	/* (non-JAVADOC)
	 * @see io.netty.handler.codec.ByteToMessageDecoder#decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List)
	 */
	@Override
	protected void decode(final ChannelHandlerContext ctx, ByteBuf buf, List<Object> v) throws Exception {
		L.debug("Decoding content length");
		if (msglen == -1){
			if (buf.readableBytes() >= 4){
				msglen = buf.readInt();
			}
			return;
		}
		if (buf.readableBytes() < msglen){			
			return;
		}
		ByteBuf oBuffer = ctx.alloc().buffer(msglen);
		buf.readBytes(oBuffer);
		v.add(oBuffer);
		msglen = -1;
	}	
}

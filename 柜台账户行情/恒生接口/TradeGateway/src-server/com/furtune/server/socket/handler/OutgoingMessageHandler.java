package com.furtune.server.socket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;

import org.apache.log4j.Logger;
/**
 * The <code>OutgoingMessageHandler</code> 处理从<code>ClientSession</code>中过的消息，返回给客户端.
 * 
 * @author Colin, Jimmy
 * @since <i>Trading v0.0.2(May 16, 2014)</i>
 */
public class OutgoingMessageHandler extends ChannelOutboundHandlerAdapter{
	private final Logger L = Logger.getLogger(getClass());
	@Override
	public void write(ChannelHandlerContext ctx, Object msg,
			ChannelPromise promise) throws Exception {
		L.debug("Outgoing message : " + msg);
		super.write(ctx, msg, promise);
	}
}

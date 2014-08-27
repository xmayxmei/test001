package com.furtune.server.socket.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.furtune.server.main.IGatewayServer;
import com.furtune.server.session.RequestHolder;

/**
 * The <code>CCBABusinessLogicInboundHandler</code> 处理客户端发来的请求。
 * 
 * @author Colin, Jimmy
 * @since <i>Trading v0.0.2(May 16, 2014)</i>
 */
public class IncomingMessageHandler extends SimpleChannelInboundHandler<RequestHolder> {
	private IGatewayServer m_oGateway = null;
	
	public IncomingMessageHandler(IGatewayServer oGateway) {
		m_oGateway = oGateway;
	}
	/* (non-JAVADOC)
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelReadComplete(io.netty.channel.ChannelHandlerContext)
	 */
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
	}
	/* (non-JAVADOC)
	 * @see io.netty.channel.ChannelHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)
	 */
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		ctx.close();
	}
	/* (non-JAVADOC)
	 * @see io.netty.channel.SimpleChannelInboundHandler#channelRead0(io.netty.channel.ChannelHandlerContext, java.lang.Object)
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, RequestHolder oHolder) throws Exception {
		m_oGateway.processTradeRequest(oHolder);
	}
}

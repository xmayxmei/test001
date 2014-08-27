package com.furtune.server.socket.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.net.SocketAddress;
import java.util.List;

import org.apache.log4j.Logger;

import com.furtune.server.main.GatewayServer;
import com.furtune.server.session.ClientSessionManager;
import com.furtune.server.util.ServerConstants;
/**
 * The <code>HandshakingDecoder</code> 主要是与客户端的握手过程，可以防止非法的连接.
 * <p>
 * @author Colin, Jimmy
 * @since Trading v0.0.2 (May 16, 2014)
 */
public class HandshakingDecoder extends ByteToMessageDecoder {
	private final Logger L = Logger.getLogger(getClass());
	/**
	 * The current state of the decoder
	 */
	private volatile int m_iState = 0;
	/**
	 * The CCBAGateway of this Decoder
	 */
	private GatewayServer m_oGateway;
	
	public HandshakingDecoder(GatewayServer oGateway) {
		m_oGateway = oGateway;
	}
	/* (non-JAVADOC)
	 * @see io.netty.channel.ChannelHandlerAdapter#exceptionCaught(io.netty.channel.ChannelHandlerContext, java.lang.Throwable)
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)	throws Exception {
		ctx.close();
	}		
	/* (non-JAVADOC)
	 * @see io.netty.channel.ChannelInboundHandlerAdapter#channelActive(io.netty.channel.ChannelHandlerContext)
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
	}
	
	/* (non-JAVADOC)
	 * @see io.netty.handler.codec.ByteToMessageDecoder#decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List)
	 */
	@Override
	protected void decode(final ChannelHandlerContext ctx, ByteBuf buf, List<Object> v) throws Exception {
		SocketAddress skAddr = ctx.channel().remoteAddress();
		L.info(skAddr + " handShaking start trying decode");
		final Channel ch = ctx.channel();
		try {
			if (m_iState == 0){
				// Get the handshaking message length
				int iLen = buf.getInt(0);
				if (buf.readableBytes() + 4 >= iLen){
					buf.readerIndex(4);
					byte[] ab = new byte[iLen];
					buf.readBytes(ab);
					String sData = new String(ab, ServerConstants.encoding);
					if (!"Hello".equals(sData)) {
						throw new Exception("Illeagal handshake.");
					}
					final String sSessionID = ClientSessionManager.generateSessionID();
					ctx.channel().attr(ClientSessionManager.SESSIONID_KEY).set(sSessionID);
					m_oGateway.register(sSessionID, ctx.channel());
					
					byte[] asRsp = "Hello".getBytes(ServerConstants.encoding);
					int iRespLen = asRsp.length;
					ByteBuf bb = ch.alloc().buffer(4 + iRespLen);
					bb.writeInt(iRespLen);
					bb.writeBytes(asRsp);
					ch.writeAndFlush(bb);
					m_iState = 1;
					ch.pipeline().remove(this);
					return;
				} else {
					ch.close();
				}
			}  
			v.add(buf.readBytes(buf.readableBytes()));
		} catch (Exception e) {
			L.info(skAddr + " exception in handshaking:" + e.getMessage());
			ch.close();
		}
		
	}
}
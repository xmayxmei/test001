package com.furtune.server.socket.handler;

import com.furtune.server.session.ResponseHolder;
import com.furtune.server.util.ServerConstants;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * <code>ResponseToByteEncoder</code> 将<code>ResponseHolder</code>相关的属性转换成字节,发送客户端。
 *
 * @author Colin, Jimmy
 * @since <i>Trading v0.0.2(May 16, 2014)</i>
 */
public class ResponseToByteEncoder extends MessageToByteEncoder<ResponseHolder>{

	@Override
	protected void encode(ChannelHandlerContext ctx, ResponseHolder msg, 
			ByteBuf out) throws Exception {
		byte[] abResp = getFullRespContent(msg);
		out.writeInt(abResp.length);
		out.writeBytes(abResp);
	}
	
	private byte[] getFullRespContent(ResponseHolder oHolder) {
		String sRespContent = oHolder.seqNum() + "|" + oHolder.resp();
		return sRespContent.getBytes(ServerConstants.encoding);
	}
}

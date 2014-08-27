package com.furtune.server.socket.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.furtune.server.session.ClientSessionManager;
import com.furtune.server.session.RequestHolder;
import com.furtune.server.util.U;
/**
 * The <code>ByteToRequestDecoder</code> 是一个解码器，把Buffer里面的数据转换成<code>RequestHolder</code>
 * 
 * @author Colin, Jimmy
 * 
 * @since <i>Trading v0.0.2(May 16, 2014)</i>
 */
public class ByteToRequestHolderDecoder extends ByteToMessageDecoder {
	private final Logger L = Logger.getLogger(getClass());
	/* (non-Javadoc)
	 * @see io.netty.handler.codec.ByteToMessageDecoder#decode(io.netty.channel.ChannelHandlerContext, io.netty.buffer.ByteBuf, java.util.List)
	 */
	public void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if (in.readableBytes() <= 0){
			return;
		}
		byte[] abData = new byte[in.readableBytes()];
		in.readBytes(abData);
		String sContent = new String(abData, "UTF-8");
		Map<String, String> hData = U.STR.convert2Map(sContent, "&", "=");
		String sSeqNum = hData.remove("seqNum");
		L.debug("Receive incoming message:" + sContent);
		if (U.STR.isEmpty(sSeqNum)) {
			L.debug("This message will be ingore because of missing seqNum");
			return ;
		}
		String sSessionId = ctx.channel().attr(ClientSessionManager.SESSIONID_KEY).get();
		RequestHolder oHolder = new RequestHolder(sSessionId, sSeqNum, U.STR.convert2Map(sContent, "&", "="));
		out.add(oHolder);
	}
}

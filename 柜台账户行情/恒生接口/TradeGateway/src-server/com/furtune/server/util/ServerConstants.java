package com.furtune.server.util;

import java.nio.charset.Charset;

/**
 * <code>ServerConstants</code>
 *
 * @author Colin, Jimmy
 * @since Trading v0.0.1 (May 16, 2014)
 */
public class ServerConstants {
	/** 编码方式 */
	public static Charset encoding = Charset.forName("UTF-8");
	/** Socket服务器的端口 */
	public static int serverScoketPort = 8989;
	/** 限制Socket服务器最大连接 */
	public static int serverSocketMaxConnections = 100;
	
}

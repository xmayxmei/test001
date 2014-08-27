package com.furtune.server.main;

import java.util.Properties;

import com.furtune.server.session.RequestHolder;

import io.netty.channel.Channel;

/**
 * <code>IGatewayServer</code>
 *
 * @author Colin, Jimmy
 * @since Trading - v.0.0.1(May 16, 2014)
 * 
 */
public interface IGatewayServer {
	/**
	 * @param oReq
	 */
	public void processTradeRequest(RequestHolder oReq);
	/**
	 * @param sSessionID
	 * @param ch
	 */
	public void register(String sSessionID, Channel ch);
	/**
	 * @param sSession
	 */
	public void unregister(String sSession);
	/**
	 *  Load server properties and related trading properties.
	 *  @return 
	 */
	public void bootstrap(Properties oProp);
	/**
	 * Start gateway server
	 */
	public void start();
	/**
	 * Start gateway server
	 */
	public void ayncStart();
	/**
	 * Stop gateway server
	 */
	public void stop();
}

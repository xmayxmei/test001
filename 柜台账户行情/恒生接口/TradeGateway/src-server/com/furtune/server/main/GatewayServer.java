package com.furtune.server.main;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;

import com.furtune.server.monitor.HeartBeatMonitor;
import com.furtune.server.session.ClientSession;
import com.furtune.server.session.ClientSessionManager;
import com.furtune.server.session.RequestHolder;
import com.furtune.server.socket.handler.ByteToRequestHolderDecoder;
import com.furtune.server.socket.handler.ContentLengthDecoder;
import com.furtune.server.socket.handler.HandshakingDecoder;
import com.furtune.server.socket.handler.IncomingMessageHandler;
import com.furtune.server.socket.handler.OutgoingMessageHandler;
import com.furtune.server.socket.handler.ResponseToByteEncoder;
import com.service.ABOSSServiceImpl;
import com.service.HSServiceImpl;
import com.service.ITradeService;
import com.util.SysConstants;

/**
 * <code>GatewayServer</code>
 *
 * @author Colin, Jimmy
 * @since Trading - v.0.0.1(May 16, 2014)
 * 
 */
public class GatewayServer implements IGatewayServer{
	private final Logger L = Logger.getLogger(getClass());
	
	private int m_iPort;
	
	private volatile int m_iMaxConnections;
	
	private final AtomicInteger m_iConnectionCount = new AtomicInteger(0);
	
	private boolean m_bRunning;
	
	private Channel m_chServer;
	
	private volatile ClientSessionManager m_sessionManager;
	
	/* (non-Javadoc)
	 * @see com.furtune.server.IGatewayServer#bootstrap()
	 */
	public void bootstrap(Properties oProps) {
		L.info("Gateway bootstrap");
		initSysParams(oProps);
	}
	/**
	 * 
	 */
	private void initSysParams(Properties props) {
		try {
			m_iPort = Integer.parseInt(props.getProperty("gatewayPort", "8989"));
			m_iMaxConnections = Integer.parseInt(props.getProperty("maxConnections", "-1"));
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	/* (non-Javadoc)
	 * @see com.furtune.server.IGatewayServer#start()
	 */
	public void start() {
		EventLoopGroup bossGroup = new NioEventLoopGroup(3);
        EventLoopGroup workerGroup = new NioEventLoopGroup(100);
        try {
            final ServerBootstrap sb = new ServerBootstrap();
            sb.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(final SocketChannel ch) throws Exception {
                	ch.closeFuture().addListener(new ChannelFutureListener() {
						@Override
						public void operationComplete(ChannelFuture chFuture)
								throws Exception {
							m_iConnectionCount.decrementAndGet();
						}
                	});
                	int iCurrConn = m_iConnectionCount.incrementAndGet();
                	if (iCurrConn == m_iMaxConnections && m_iMaxConnections > 0) {
                		ch.close();
                		return ;
                	}
                    ch.pipeline().addLast(
                    	new HandshakingDecoder(GatewayServer.this),
                    	new ContentLengthDecoder(),
                    	new ByteToRequestHolderDecoder(),
                    	new ResponseToByteEncoder(),
                    	new IncomingMessageHandler(GatewayServer.this),
                    	new OutgoingMessageHandler()
                    );
                }
            });

            m_chServer = sb.bind(m_iPort).sync().channel();
            L.info("Gateway server started at port " + m_iPort);
            m_bRunning = true;
            
            ITradeService oService = null;
            if ("abs".equals(SysConstants.brokerId)) {
            	oService = new ABOSSServiceImpl();
            } else if ("handsun".equals(SysConstants.brokerId)){
            	oService = new HSServiceImpl();
            } else {
            	throw new Exception("brokerId错误,启动GatewayServer失败!");
            }
            m_sessionManager = new ClientSessionManager(oService);
            m_sessionManager.startRequestMonitor();
            HeartBeatMonitor oHeartBeat = new HeartBeatMonitor(m_sessionManager);
            oHeartBeat.start();
            m_chServer.closeFuture().sync();
        } catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			
		} finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
            stop();
        }
	}
	@Override
	public void ayncStart() {
		Thread oTh = new Thread(new Runnable() {
			@Override
			public void run() {
				start();
			}
		});
		oTh.setDaemon(true);
		oTh.start();
	}
	/* (non-Javadoc)
	 * @see com.furtune.server.main.IGatewayServer#register(java.lang.String, io.netty.channel.Channel)
	 */
	public void register(String sSessionID, Channel ch) {
		ClientSession oClientSession = m_sessionManager.getClientSession(sSessionID);
		if (oClientSession == null) {
			oClientSession = new ClientSession(sSessionID, ch);
		}
		m_sessionManager.registerSession(sSessionID, oClientSession);
	}
	/* (non-Javadoc)
	 * @see com.furtune.server.main.IGatewayServer#unregister(java.lang.String)
	 */
	public void unregister(String sSessionID) {
		m_sessionManager.unregisterSession(sSessionID);
	}
	/* (non-Javadoc)
	 * @see com.furtune.server.main.IGatewayServer#processTradeRequest(com.furtune.server.session.RequestHolder)
	 */
	public void processTradeRequest(RequestHolder oReq) {
		m_sessionManager.processTradeRequest(oReq);
	}
	/* (non-Javadoc)
	 * @see com.furtune.server.IGatewayServer#stop()
	 */
	public void stop() {
		m_bRunning = false;
		m_sessionManager.stopRequestMonitor();
		m_chServer.close();
	}
	/**
	 * @return
	 */
	public boolean isRunning() {
		return m_bRunning;
	}
}

package com.core.abs;

import static com.util.SysConstants.ABS.ConnTimeOut;
import static com.util.SysConstants.ABS.ipAddr;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.util.SysConstants;

/**
 * <code>ConnManager</code> 是一个连接池，里面的所有的连接与顶点柜台保持着长连接。
 * <p>
 * <b>Note</b> 每次用完连接，都要放回到连接池。
 * 
 * @since Trading
 *
 */
public class ConnManager {
	protected Logger log = Logger.getLogger(getClass());
	
	private final ReentrantLock lock = new ReentrantLock();
	
	private static ConnManager instance;
	
	private volatile int hasInitCount = 0;
	/** 获取连接的等待时间 单位:毫秒*/
	private final int WAIT_TIMEOUT = 5000;
	
	private volatile LinkedBlockingQueue<Integer> connPool;
	
	private ConnManager() {
	}
	/**
	 *  仅仅一个实例
	 */
	public static synchronized ConnManager getInstance() {
		if (instance == null) {
			instance = new ConnManager();
		}
		return instance;
	}
	
	public void initPool() {
		log.info("init connPool....");
		connPool = new LinkedBlockingQueue<Integer>(SysConstants.ABS.maxPoolSize);
		for (int i = 0; i < SysConstants.ABS.initPoolSize; i++) {
			int iIndex = i + 1;
			int iConnId = ABOSS2Inf.createConnection(ipAddr, "", "", ConnTimeOut);
			if(iConnId != 0){
				try {
					connPool.put(iConnId);
					hasInitCount ++;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				log.info("create "+iIndex+" connection success...");
			}else{
				log.info("create connection faile....");
			}
		}
		
	}
	/**
	 * @return
	 */
	public int getConnection() {
		try{
			lock.lock();
			if (connPool.isEmpty() && hasInitCount < SysConstants.ABS.maxPoolSize) {
				int connId = ABOSS2Inf.createConnection(ipAddr, "", "", ConnTimeOut);
				if(connId != 0){
					try {
						connPool.put(connId);
						hasInitCount ++;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}finally {
			lock.unlock();
		}
		Integer iConnID = 0;
		try {
			iConnID = connPool.poll(WAIT_TIMEOUT, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (iConnID == null) {
			return 0;
		}
		return iConnID;
	}
	/**
	 * 把id为<code>iConnID</code>的连接放回到连接池.
	 * @param iConnID
	 */
	public void freeConnection(int iConnID) {
		if (iConnID == 0) {
			return ;
		}
		try {
			connPool.put(iConnID);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 
	 * @param iConnId
	 * @return
	 */
	public boolean closeConnection(Integer iConnId) {
		try{
			lock.lock();
			if (connPool.remove(iConnId)) {
				hasInitCount--;
				return true;
			}
		}finally {
			lock.unlock();
		}
		return false;
	}

}

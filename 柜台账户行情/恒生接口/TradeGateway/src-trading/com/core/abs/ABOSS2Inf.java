package com.core.abs;

import org.apache.log4j.Logger;
import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.pointers.Pointer;

import com.util.SysConstants;

/**
 * <code>ABOSS2Inf</code> 是 Aboss底层调用API。 
 * <p>
 * <b>Note</b> Native中的所有的dispose操作(也就是释放内存)都在finally语句中进行，
 * 否则有可能出现内存溢出。
 * 
 * @author Colin
 *  
 *
 */
public class ABOSS2Inf {
	protected static Logger log = Logger.getLogger(ABOSS2Inf.class);
	/**
	 * 与顶点柜台建立连接
	 * 
	 * @param sIpAddr
	 * @param sUsername
	 * @param sPwd
	 * @param iConnTimeout
	 * @return
	 */
	public static int createConnection(String sIpAddr, String sUsername, String sPwd, int iConnTimeout) {
		JNative n = null;
		try {
			n = new JNative("FixApi", "Fix_Connect");
			n.setParameter(0, Type.STRING, sIpAddr);
			n.setParameter(1, Type.STRING, sUsername);
			n.setParameter(2, Type.STRING, sPwd);
			n.setParameter(3, iConnTimeout);
			n.setRetVal(Type.INT);
			n.invoke();
			return n.getRetValAsInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 初始化API
	 * <p>
	 * <b>Note</b> 只能在程序启动的时候调用一次。
	 * 
	 * @return
	 * @throws NativeException
	 * @throws IllegalAccessException
	 */
	public static int initialize() throws NativeException, IllegalAccessException {
		JNative n = new JNative("FixApi", "Fix_Initialize");
		n.setRetVal(Type.INT);
		n.invoke();
		return n.getRetValAsInt();
	}
	/**
	 * @param sessionId
	 * @param funId
	 * @return
	 */
	public static int createHead(int sessionId, int funId) {
		JNative n = null;
		try {
			n = new JNative("FixApi", "Fix_CreateReq");
			n.setParameter(0, sessionId);
			n.setParameter(1, funId);
			n.setRetVal(Type.INT);
			n.invoke();
			return n.getRetValAsInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 获得获得数据总数
	 * 
	 * @param sessId
	 * @return
	 */
	public static int getCount(int sessId) {
		JNative n = null;
		try {
			n = new JNative("FixApi", "Fix_GetCount");
			n.setParameter(0, sessId);
			n.setRetVal(Type.LONG);
			n.invoke();
			return Integer.parseInt(n.getRetVal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	/**
	 * 获得错误信息
	 * 
	 * @param sessId
	 * @return
	 */
	public static String getErrMsg(int sessId) {
		JNative n = null;
		String sErr = "";
		try {
			final Pointer pointer = Pointer.createPointer(128);
		    try {
				n = new JNative("FixApi", "Fix_GetErrMsg");
				n.setParameter(0, sessId);
				n.setParameter(1, pointer);
				n.setParameter(2, 128);
				n.setRetVal(Type.VOID);
				n.invoke();
				sErr = pointer.getAsString();
		    } finally {
		    	pointer.dispose();
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sErr;
	}
	/**
	 * @param sessId
	 * @param fid
	 * @param size
	 * @param rowIndex
	 * @return
	 */
	public static String getItemForString(int sessId, int fid, int size, int rowIndex) {
		JNative n = null;
		String result = "";
		try {
			final Pointer pointer = Pointer.createPointer(size);
			try {
				n = new JNative("FixApi", "Fix_GetItem");
				n.setParameter(0, sessId);
				n.setParameter(1, fid);
				n.setParameter(2, pointer);
				n.setParameter(3, size);
				n.setParameter(4, rowIndex);
				n.setRetVal(Type.VOID);
				n.invoke();
				byte[] by = pointer.getMemory();
				result = new String(by, "UTF-8").trim();
			} finally {
				pointer.dispose();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/*public static String getItemForString(int sessId, int fid, int size) {
		JNative n = null;
		String result = "";
		try {
			// Pointer pointer = new
			// Pointer(MemoryBlockFactory.createMemoryBlock(size));
			Pointer pointer = Pointer.createPointer(size);
			n = new JNative("FixApi", "Fix_GetItem");
			n.setParameter(0, sessId);
			n.setParameter(1, fid);
			n.setParameter(2, pointer);
			n.setParameter(3, size);
			n.setRetVal(Type.VOID);
			n.invoke();
			// pointer.
			byte[] by;
			by = pointer.getMemory();
			String str = new String(by, "UTF-8").trim();
			// pointer.zeroMemory();

			result = str;
			pointer.dispose();
			// pointer.dispose();
			// JNative.freeMemory(pointer.getPointer());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}*/
	/*public static String getItemToString(int sessId, int fid, int size) {
		JNative n = null;
		String result = "";
		try {
			// Pointer pointer = new
			// Pointer(MemoryBlockFactory.createMemoryBlock(size));
			Pointer pointer = Pointer.createPointer(size);
			n = new JNative("FixApi", "Fix_GetItem");
			n.setParameter(0, sessId);
			n.setParameter(1, fid);
			n.setParameter(2, pointer);
			n.setParameter(3, size);
			n.setRetVal(Type.VOID);
			n.invoke();
			// pointer.
			byte[] by;
			String str = pointer.getAsString();
			// pointer.zeroMemory();

			result = str;
			pointer.dispose();
			// pointer.dispose();
			// JNative.freeMemory(pointer.getPointer());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}*/
	/**
	 * @param sessId
	 * @param fid
	 * @param size
	 * @param rowIndex
	 * @return
	 */
	public static String getItem(int sessId, int fid, int size, int rowIndex) {
		JNative n = null;
		String result = "";
		try {
			final Pointer pointer = Pointer.createPointer(size);
			try {
				n = new JNative("FixApi", "Fix_GetItem");
				n.setParameter(0, sessId);
				n.setParameter(1, fid);
				n.setParameter(2, pointer);
				n.setParameter(3, size);
				n.setParameter(4, rowIndex);
				n.setRetVal(Type.VOID);
				n.invoke();
//				result = pointer.getAsString();
				byte[] by = pointer.getMemory();
				result = new String(by, SysConstants.ABS.encoding).trim();
			} finally {
				pointer.dispose();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * @param sessId
	 * @param fid
	 * @param size
	 * @return
	 */
	public static String getItem(int sessId, int fid, int size) {
		JNative n = null;
		String result = "";
		try {
			final Pointer pointer = Pointer.createPointer(size);
			try {
				n = new JNative("FixApi", "Fix_GetItem");
				n.setParameter(0, sessId);
				n.setParameter(1, fid);
				n.setParameter(2, pointer);
				n.setParameter(3, size);
				n.setRetVal(Type.STRING);
				n.invoke();
				result = pointer.getAsString();
			} finally {
				pointer.dispose();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getLong(int sessId, int fid, int size) {
		JNative n = null;
		String result = "";
		try {
			Pointer pointer = Pointer.createPointer(size);
			n = new JNative("FixApi", "Fix_GetLong");
			n.setParameter(0, sessId);
			n.setParameter(1, fid);
			n.setParameter(2, pointer);
			n.setParameter(3, size);
			n.setRetVal(Type.LONG);
			n.invoke();
			result = pointer.getAsString();
			pointer.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static int setSessionTimeOut(int connId,int timeOut) {
		JNative n = null;
		try {
			n = new JNative("FixApi", "Fix_SetTimeOut");
			n.setParameter(0, connId);
			n.setParameter(1, timeOut);
			n.setRetVal(Type.INT);
			n.invoke();
			return n.getRetValAsInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int initAllocateSession(int connId) {
		JNative n = null;
		try {
			n = new JNative("FixApi", "Fix_AllocateSession");
			n.setParameter(0, connId);
			n.setRetVal(Type.LONG);
			try {
				n.invoke();
			} catch (NativeException e) {
				log.error(e);
				e.printStackTrace();
			}
			return n.getRetValAsInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	public static int releaseSession(int connId) {
		JNative n = null;
		try {
			n = new JNative("FixApi", "Fix_ReleaseSession");
			n.setParameter(0, connId);
			n.setRetVal(Type.LONG);
			try {
				n.invoke();
			} catch (NativeException e) {
				// TODO: handle exception
			}
			
			return n.getRetValAsInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int closeConn(int connId) {
		JNative n = null;
		try {
			n = new JNative("FixApi", "Fix_Close");
			n.setParameter(0, connId);
			n.setRetVal(Type.INT);
			n.invoke();
			return n.getRetValAsInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	public static String encode(String password){
		String result="";
		if(password!=null){
			JNative n = null;
			try {
				Pointer pointer = Pointer.createPointer(512);
				//byte strByte[]=password.getBytes();
				pointer.setMemory(password);
				n = new JNative("FixApi", "Fix_Encode");
				n.setParameter(0, pointer);
				n.setRetVal(Type.STRING);
				n.invoke();
				String str = pointer.getAsString();
				// pointer.zeroMemory();
				result = str;
				pointer.dispose();
				//result= n.getRetVal();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			log.error("password is null");
		}
		return result;
	}
	

	
	//设置营业部代码
	public static long setFBDM(int sessId, String val){
		JNative n = null;
		try {
			n = new JNative("FixApi", "Fix_SetFBDM");
			n.setParameter(0, sessId);
			n.setParameter(1, val);
			n.setRetVal(Type.LONG);
			n.invoke();
			return Integer.parseInt(n.getRetVal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	//设置营业部代码
		public static long setDestFBDM (int sessId, String val){
			JNative n = null;
			try {
				n = new JNative("FixApi", "Fix_SetDestFBDM");
				n.setParameter(0, sessId);
				n.setParameter(1, val);
				n.setRetVal(Type.LONG);
				n.invoke();
				return Integer.parseInt(n.getRetVal());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return 0;
		}
		//设置营业部代码
	public static long setNode (int sessId, String val){
		JNative n = null;
		try {
			n = new JNative("FixApi", "Fix_SetNode");
			n.setParameter(0, sessId);
			n.setParameter(1, val);
			n.setRetVal(Type.LONG);
			n.invoke();
			return Integer.parseInt(n.getRetVal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
			
	//设置委托方式
	public static long setWTFS(int sessId,String val){
		JNative n = null;
		try {
			n = new JNative("FixApi", "Fix_SetWTFS");
			n.setParameter(0, sessId);
			n.setParameter(1, val);
			n.setRetVal(Type.LONG);
			n.invoke();
			return Integer.parseInt(n.getRetVal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	//设置柜员代码
	public static long setGYDM(int sessId, String val){
		JNative n = null;
		try {
			n = new JNative("FixApi", "Fix_SetGYDM");
			n.setParameter(0, sessId);
			n.setParameter(1, val);
			n.setRetVal(Type.LONG);
			n.invoke();
			return Integer.parseInt(n.getRetVal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	

	// 设置字符类型请求数据
	public static long setItem(int sessId, int tag, String val) {
		JNative n = null;
		try {
			n = new JNative("FixApi", "Fix_SetItem");
			n.setParameter(0, sessId);
			n.setParameter(1, tag);
			n.setParameter(2, val);
			n.setRetVal(Type.STRING);
			n.invoke();
			return Integer.parseInt(n.getRetVal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}


	// 设置字符类型请求数据
	public static void setString(int sessId, int tag, String val) {
		if(val!=null){
			JNative n = null;
			try {
				log.debug("setString end: Fid:"+tag+"----"+"val:"+val);
				n = new JNative("FixApi", "Fix_SetString");
				n.setParameter(0, sessId);
				n.setParameter(1, tag);
				n.setParameter(2, val);
				n.setRetVal(Type.LONG);
				n.invoke();
				int result= n.getRetValAsInt();
				log.debug("setString end:"+result);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}

	public static long setLong(int sessId, int tag, String val) {
		JNative n = null;
		try {
			n = new JNative("FixApi", "Fix_SetLong");
			n.setParameter(0, sessId);
			n.setParameter(1, tag);
			n.setParameter(2, val);
			n.setRetVal(Type.LONG);
			n.invoke();
			return Integer.parseInt(n.getRetVal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static int setDouble(int sessId, int tag, String val) {
		JNative n = null;
		try {
			n = new JNative("FixApi", "Fix_SetDouble");
			n.setParameter(0, sessId);
			n.setParameter(1, tag);
			n.setParameter(2, val);
			n.setRetVal(Type.LONG);
			n.invoke();
			return Integer.parseInt(n.getRetVal());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}


	// 执行方法
	public static int dllRun(int sessId) {
		JNative n = null;
		try {
			n = new JNative("FixApi", "Fix_Run");
			n.setParameter(0, sessId);
			n.setRetVal(Type.INT);
			n.invoke();	
			return n.getRetValAsInt();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}

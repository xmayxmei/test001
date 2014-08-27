package org.xvolks.jnative.com;
import java.util.logging.Logger;

import org.xvolks.jnative.Native;
import org.xvolks.jnative.com.interfaces.IDispatch;


public abstract class EventProxy {
	private int eventCookie;
	protected final Logger logger;
	private final String sinkIID;

	private boolean disposed = false;
	private IDispatch iDispatch;
	
	private native EventProxy comNewInstance(int jpIUnknown, String jsSinkIID) throws Exception;
	private native void comDispose() throws Exception;
	
	/* appelé et utilisé par le natif... */
	@Native
	protected void setEventCookie(int eventCookie) {
		EventProxy.this.eventCookie = eventCookie;
	}
	@Native
	protected int getEventCookie() {
		return EventProxy.this.eventCookie;
	}
	
	@Native
	protected String getSinkIID() {
		return sinkIID;
	}

	@Native
	protected int getPIUnknown() {
		return iDispatch.getPIUnknown();
	}

	protected Logger getLogger() {
		return logger;
	}
	
	private abstract class ComTasks implements Runnable {
		protected String sinkIID;
		protected IDispatch iDispatch;
		
		public ComTasks(IDispatch iDispatch, String sinkIID) {
			this.iDispatch = iDispatch;
			this.sinkIID = sinkIID;
		}
		
		public abstract void run();
	}
	
	private class ComNewInstanceTask extends ComTasks {
		public ComNewInstanceTask(IDispatch iDispatch, String sinkIID) {
			super(iDispatch, sinkIID);
		}

		@Override
		public void run() {
			try {
				comNewInstance(iDispatch.getPIUnknown(), sinkIID);
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
	}
	
	private class ComDisposeTask extends ComTasks {
		public ComDisposeTask(IDispatch iDispatch, String sinkIID) {
			super(iDispatch, sinkIID);
		}

		@Override
		public void run() {
			try {
				comDispose();
			} catch (Throwable t) {
				throw new RuntimeException(t);
			}
		}
	}
	
	public EventProxy(IDispatch iDispatch, String sinkIID) throws Exception {
		this.iDispatch = iDispatch;
		this.eventCookie = 0;	/* valeur NULL par défaut */
		this.logger = Logger.getLogger(this.getClass().getSimpleName());
		this.sinkIID = sinkIID; //"{6DA738E2-4619-11D5-99F2-00018001017C}";
		
		iDispatch.syncExec(new ComNewInstanceTask(iDispatch, sinkIID));
	}
	
	public synchronized void dispose() {
		if (!disposed) {
			try {
				iDispatch.syncExec(new ComDisposeTask(iDispatch, sinkIID));
			} catch (Exception e) {
				e.printStackTrace();
			}
			disposed = true;
		}
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		
		dispose();
	}	
}

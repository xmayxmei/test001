package org.xvolks.jnative.com;

import org.xvolks.jnative.com.interfaces.IDispatch;
import org.xvolks.jnative.com.utils.Logger;


public abstract class AbstractEventsProxy implements IEventsListener {
	private int eventCookie;
	private Logger logger;
	private String sinkIID;

	private boolean disposed = false;
	private IDispatch iDispatch;
	
	private native AbstractEventsProxy comNewInstance(int jpIUnknown, String jsSinkIID) throws Exception;
	private native void comDispose() throws Exception;
	
	/* called and used by native side... */
	protected void setEventCookie(int eventCookie) {
		AbstractEventsProxy.this.eventCookie = eventCookie;
	}
	
	protected int getEventCookie() {
		return AbstractEventsProxy.this.eventCookie;
	}
	
	protected String getSinkIID() {
		return sinkIID;
	}

	protected int getPIUnknown() {
		return iDispatch.getPIUnknown();
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
	
	public AbstractEventsProxy(IDispatch iDispatch, String sinkIID) throws Exception {
		this.iDispatch = iDispatch;
		this.eventCookie = 0;	/* valeur NULL par défaut */
		this.logger = Logger.getInstance(this.getClass().getSimpleName());
		this.sinkIID = sinkIID; //"{6DA738E2-4619-11D5-99F2-00018001017C}";
		
		iDispatch.syncExec(new ComNewInstanceTask(iDispatch, this.sinkIID));
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
	
	public Logger getLogger() {
    	return logger;
    }
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		
		dispose();
	}
}

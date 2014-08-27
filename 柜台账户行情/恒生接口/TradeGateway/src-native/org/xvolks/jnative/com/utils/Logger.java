package org.xvolks.jnative.com.utils;

import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import org.xvolks.jnative.logging.AbstractLogger;
import org.xvolks.jnative.logging.JNativeLogger;

// TODO : replace with jnative's logger ?
public class Logger extends AbstractLogger {

	private static Map<String, Logger> instances = new HashMap<String, Logger>();
	
	private final static int FATAL = 0;
	private final static int ERROR = 1;
	private final static int WARN = 2;
	private final static int INFO = 3;
	private final static int DEBUG = 4;
	private final static int TRACE = 5;
	
	private String identifier;

	private Logger(String identifier, int level) {
		super(level);
		this.identifier = identifier;
	}
	
	public void fatal(Object message) {
		log(FATAL, message, null);
	}

	public void error(Object message) {
		log(ERROR, message, null);
	}

	public void error(Object message, Throwable t) {
		log(ERROR, message, t);
	}
	
	public void warn(Object message) {
		log(WARN, message, null);
	}
	
	public void warn(Object message, Throwable t) {
		log(WARN, message, t);
	}
	
	public void info(Object message) {
		log(INFO, message, null);
	}
	
	public void info(Object message, Throwable t) {
		log(INFO, message, t);
	}
	
	public void debug(Object message) {
		log(DEBUG, message, null);
	}
	
	public void trace(Object message) {
		log(TRACE, message, null);
	}
	
	private void log(int msgLevel, Object message, Throwable t) {
		PrintStream pw;
		
		if (msgLevel > getLoggerLevel().ordinal()) return;
		
		if (msgLevel <= WARN) {
			pw = System.err;
		} else {
			pw = System.out;
		}
		
		synchronized (pw) {
			pw.println("["+identifier+"] - ["+Thread.currentThread().getName()+"] - " + message);
			if (t!=null) t.printStackTrace(pw);
		}
	}
	
	public synchronized static Logger getInstance(String identifier) {
		Logger instance = null;
		
		if ((instance = instances.get(identifier)) == null) {
			if (System.getProperty(JNativeLogger.LOG_LEVEL)!=null) {
				instance = new Logger(identifier, getSystemLogLevel());
			} else {
				instance = new Logger(identifier, WARN);
			}
			instances.put(identifier, instance);
		}
		return instance;
	}

	public void log(SEVERITY severity, Object message) {
		// TODO Auto-generated method stub
		
	}

	public void log(Object message) {
		// TODO Auto-generated method stub
		
	}

}

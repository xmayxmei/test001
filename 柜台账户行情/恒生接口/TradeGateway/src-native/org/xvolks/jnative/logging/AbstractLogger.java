/*
 * AbstractLogger.java
 *
 * Created on 25. April 2007, 12:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.logging;

import java.util.HashMap;

/**
 *
 * @author Thubby
 */
public abstract class AbstractLogger implements JNativeLogger
{
	
	protected static HashMap<String, JNativeLogger> loggers = new HashMap<String, JNativeLogger>();
	protected String loggerName = null;
	private final SEVERITY loggerLevel;
	
	protected AbstractLogger(Integer level) {
		if(level == null) {
			loggerLevel = SEVERITY.DEBUG;
		} else {
			loggerLevel = SEVERITY.values()[level];
		}
	}
	
	public String getName()
	{
		return loggerName;
	}
	public void setName(String name)
	{
		loggerName = name;
	}

	public SEVERITY getLoggerLevel() {
		return loggerLevel;
	}
	protected static Integer getSystemLogLevel() {
		String property = System.getProperty(JNativeLogger.LOG_LEVEL);
		Integer v = property == null ? null : new Integer(property);
		return v;
	}

}

/*
 * SimpleFileLogger.java
 *
 * Created on 25. April 2007, 12:48
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.logging;

import java.util.logging.*;
import org.xvolks.jnative.JNative;

/**
 *
 * @author Thubby
 */
public class SimpleFileLogger extends AbstractLogger
{
	
	private Logger logger = null;
	
	private class FileLogger extends Logger
	{
		private FileHandler fh = null;
		
		public FileLogger(String name, String resourceBundleName)
		{
			super(name, resourceBundleName);
			try
			{
				fh = new FileHandler(getName()+".log", true);
				fh.setFormatter(new SimpleFormatter());
				addHandler(fh);
				setLevel(Level.ALL);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/** Creates a new instance of SimpleFileLogger */
	private SimpleFileLogger(Integer logLevel)
	{
		super(logLevel);
		logger = new FileLogger(null,null);
	}
	private SimpleFileLogger(String name, Integer logLevel)
	{
		super(logLevel);
		logger = new FileLogger(name,null);
	}
	
	public static JNativeLogger getInstance(Class<?> clazz)
	{
		return getInstance(clazz.getSimpleName());
	}
	public static JNativeLogger getInstance(String name)
	{
		JNativeLogger logger = null;
		if(!loggers.containsKey(name))
		{
			logger = new SimpleFileLogger(name, getSystemLogLevel());
			loggers.put(name, logger);
		}
		else
		{
			logger = (JNativeLogger)loggers.get(name);
		}
		return logger;
	}
	
	public void log(Object message)
	{
		if(!JNative.isLogginEnabled())
			return;
		
		if(message != null)
			logger.log(Level.ALL, message.toString());
	}
	
	public void log(SEVERITY severity, Object message)
	{
		log(message);
	}
}

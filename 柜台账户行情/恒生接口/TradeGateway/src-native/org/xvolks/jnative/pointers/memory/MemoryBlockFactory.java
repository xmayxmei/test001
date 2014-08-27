package org.xvolks.jnative.pointers.memory;
import java.lang.reflect.*;
import org.xvolks.jnative.JNative;
import org.xvolks.jnative.exceptions.*;
import org.xvolks.jnative.logging.JNativeLogger.SEVERITY;

/**
 * $Id: MemoryBlockFactory.java,v 1.7 2008/08/31 15:28:21 thubby Exp $
 *
 * <p>This factory permits to reserve a block of memory of the default type</p>
 * <p><b>HeapMemoryBlock</b> : is currently the default type.</p>
 * <p>You should allways call <code>setPreferredMemoryType</code> before, default type is subject to change !!</p>
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class MemoryBlockFactory
{
	private MemoryBlockFactory()
	{}
	
	private static Constructor<? extends MemoryBlock> preferredConstructor;
	
	public static void setPreferredMemoryType(Class<? extends MemoryBlock> type) throws NoSuchMethodException
	{
		try
		{
			preferredConstructor = type.getDeclaredConstructor(int.class);
			MemoryBlock mem = preferredConstructor.newInstance(1);
			mem.dispose();
			JNative.getLogger().log(SEVERITY.DEBUG,"Using "+type.getName()+" memory reservation strategy");
		}
		catch(Exception e)
		{
			try
			{
				e.printStackTrace();
				throw new NoSuchMethodException(type.toString() + " not found");
			}
			catch(NullPointerException ex)
			{
				throw new NoSuchMethodException("Class not found");
			}
		}
	}
	
	public static MemoryBlock createMemoryBlock(int size) throws NativeException
	{
		try
		{
			if(preferredConstructor == null)
			{
				setPreferredMemoryType(HeapMemoryBlock.class);
			}
			return preferredConstructor.newInstance(size);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			throw new NativeException(e.toString());
		}
	}
}

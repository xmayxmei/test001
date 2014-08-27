package org.xvolks.jnative.misc;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.AbstractBasicData;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.pointers.NullPointer;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.GlobalMemoryBlock;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;


public class WNDCLASSEX extends AbstractBasicData<WNDCLASSEX>
{
	private LONG style = new LONG(0);
	private LONG lpfnWndProc = new LONG(0);
	private int cbClsExtra = 0;
	private int cbWndExtra = 0;
	private LONG hInstance = new LONG(0);
	private LONG hIcon = new LONG(0);
	private LONG hCursor = new LONG(0);
	private LONG hbrBackground = new LONG(0);
	private String lpszMenuName = null;
	private String lpszClassName = null;
	private LONG hIconSm = new LONG(0);
	
	private final Pointer lpszMenuNamePointer;
	private final Pointer lpszClassNamePointer;
	
	public WNDCLASSEX() throws NativeException
	{
		this((WNDCLASSEX)null);
	}
	protected WNDCLASSEX(WNDCLASSEX lValue) throws NativeException
	{
		super(lValue);
		lpszMenuNamePointer = new Pointer(MemoryBlockFactory.createMemoryBlock(256));
		lpszClassNamePointer = new Pointer(MemoryBlockFactory.createMemoryBlock(256));
	}
	
	private void toPointer() throws NativeException
	{
		offset = 0;
		offset += pointer.setIntAt(offset, getSizeOf());
		offset += pointer.setIntAt(offset, style.getValue());
		offset += pointer.setIntAt(offset, lpfnWndProc.getValue());
		offset += pointer.setIntAt(offset, cbClsExtra);
		offset += pointer.setIntAt(offset, cbWndExtra);
		offset += pointer.setIntAt(offset, hInstance.getValue());
		offset += pointer.setIntAt(offset, hIcon.getValue());
		offset += pointer.setIntAt(offset, hCursor.getValue());
		offset += pointer.setIntAt(offset, hbrBackground.getValue());
		lpszClassNamePointer.zeroMemory();
		if(lpszClassName != null)
		{
			lpszClassNamePointer.setStringAt(0, lpszClassName);
		}
		lpszMenuNamePointer.zeroMemory();
		if(lpszMenuName != null)
		{
			lpszMenuNamePointer.setStringAt(0, lpszMenuName);
		}
		offset += pointer.setIntAt(offset, lpszMenuName == null ? NullPointer.NULL.getPointer() : lpszMenuNamePointer.getPointer());
		offset += pointer.setIntAt(offset, lpszClassName == null ? NullPointer.NULL.getPointer() : lpszClassNamePointer.getPointer());
		pointer.setIntAt(offset, hIconSm.getValue());
		offset = 0;
	}
	private void fromPointer() throws NativeException
	{
		getNextInt();
		style = new LONG(getNextInt());
		lpfnWndProc = new LONG(getNextInt());
		cbClsExtra = getNextInt();
		cbWndExtra = getNextInt();
		hInstance = new LONG(getNextInt());
		hIcon = new LONG(getNextInt());
		hCursor = new LONG(getNextInt());
		hbrBackground = new LONG(getNextInt());
		
		int nextInt = getNextInt();
		if(nextInt != 0)
		{
			lpszMenuName = JNative.getMemoryAsString(nextInt, 256);
		}
		
		nextInt = getNextInt();
		if(nextInt != 0)
		{
			lpszClassName = JNative.getMemoryAsString(nextInt, 256);
		}
		hIconSm = new LONG(getNextInt());
	}
	
	public String toString()
	{
		String s =  "style: "+style+"\n";
		s += "lpfnWndProc: "+lpfnWndProc+"\n";
		s += "cbClsExtra: "+cbClsExtra+"\n";
		s += "cbWndExtra: "+cbWndExtra+"\n";
		s += "hInstance: "+hInstance+"\n";
		s += "hIcon: "+hIcon+"\n";
		s += "hCursor: "+hCursor+"\n";
		s += "hbrBackground: "+hbrBackground+"\n";
		s += "lpszMenuName: "+lpszMenuName+"\n";
		s += "lpszClassName: "+lpszClassName+"\n";
		s += "hIconSm: "+hIconSm+"\n";
		return s;
	}
	
	public Pointer createPointer() throws NativeException
	{
		if(pointer == null)
		{
			pointer = new Pointer(new GlobalMemoryBlock(getSizeOf()));
		}
		toPointer();
		return pointer;
	}
	
	public int getSizeOf()
	{
		return 48;
	}
	
	public WNDCLASSEX getValueFromPointer() throws NativeException
	{
		fromPointer();
		return this;
	}
	
	public int getCbClsExtra()
	{
		return cbClsExtra;
	}
	
	public void setCbClsExtra(int cbClsExtra)
	{
		this.cbClsExtra = cbClsExtra;
	}
	
	public int getCbWndExtra()
	{
		return cbWndExtra;
	}
	
	public void setCbWndExtra(int cbWndExtra)
	{
		this.cbWndExtra = cbWndExtra;
	}
	
	public LONG getHbrBackground()
	{
		return hbrBackground;
	}
	
	public void setHbrBackground(LONG hbrBackground)
	{
		this.hbrBackground = hbrBackground;
	}
	
	public LONG getHCursor()
	{
		return hCursor;
	}
	
	public void setHCursor(LONG cursor)
	{
		hCursor = cursor;
	}
	
	public LONG getHIcon()
	{
		return hIcon;
	}
	
	public void setHIcon(LONG icon)
	{
		hIcon = icon;
	}
	
	public LONG getHInstance()
	{
		return hInstance;
	}
	
	public void setHInstance(LONG instance)
	{
		hInstance = instance;
	}
	
	public LONG getLpfnWndProc()
	{
		return lpfnWndProc;
	}
	
	public void setLpfnWndProc(LONG lpfnWndProc)
	{
		this.lpfnWndProc = lpfnWndProc;
	}
	
	public String getLpszClassName()
	{
		return lpszClassName;
	}
	
	public void setLpszClassName(String lpszClassName)
	{
		this.lpszClassName = lpszClassName;
	}
	
	public String getLpszMenuName()
	{
		return lpszMenuName;
	}
	
	public void setLpszMenuName(String lpszMenuName)
	{
		this.lpszMenuName = lpszMenuName;
	}
	
	public LONG getStyle()
	{
		return style;
	}
	
	public void setStyle(LONG style)
	{
		this.style = style;
	}
	
}

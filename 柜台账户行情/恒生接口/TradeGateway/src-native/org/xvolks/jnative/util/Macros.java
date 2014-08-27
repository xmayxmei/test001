/*
 * Macros.java
 *
 * Created on 20. Februar 2008, 21:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.util;

/**
 *
 * @author __USER__
 */
public class Macros
{
    
    /** Creates a new instance of Macros */
    public Macros()
    {
    }
	public static final String MAKEINTRESOURCE(int lID)
	{
		return "#"+LOWORD(lID);
	}	
    public static final int HIWORD(int i)
    {
        return i  >> 16;
    }
    public static final int LOWORD(int i)
    {
        return i & 0xFFFF;
    }    
    public static final short LOBYTE(short i)
    {
        return (short)(i & 0xFF);
    }
    public static final short HIBYTE(short i)
    {
        return (short)(i  >> 8);
    }
    // #define RGB(r,g,b)          ((COLORREF)(((BYTE)(r)|((WORD)((BYTE)(g))<<8))|(((DWORD)(BYTE)(b))<<16)))
    public static final int RGB(int r, int g, int b)
    {
        return ((short)r | (((short)g) << 8) | (((short)b)<<16));
    }    
    //#define GetRValue(rgb)      ((BYTE)(rgb))
    public static final int GetRValue(int rgb)
    {
        return rgb & 0xFF;
    }
    //#define GetGValue(rgb)      ((BYTE)(((WORD)(rgb)) >> 8))
    public static final int GetGValue(int rgb)
    {
        return rgb >> 8 & 0xFF;
    }
    //#define GetBValue(rgb)      ((BYTE)((rgb)>>16))
    public static final int GetBValue(int rgb)
    {
        return rgb >> 16 & 0xFF;
    }
    
    public static void main(String args[])
    {
        System.out.println(GetBValue(RGB(254, 253, 252)));
    }
}

/**
 *
 */
package org.xvolks.jnative.util;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.POINT;
import org.xvolks.jnative.misc.BITMAP;
import org.xvolks.jnative.misc.BITMAPINFOHEADER;
import org.xvolks.jnative.misc.basicStructures.DC;
import org.xvolks.jnative.misc.basicStructures.DWORD;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.pointers.NullPointer;
import org.xvolks.jnative.pointers.Pointer;


/**
 * @author Marc DENTY (mdt) - 14 nov. 06
 * $Id: Gdi32.java,v 1.19 2008/07/25 09:33:42 thubby Exp $
 *
 */
public class Gdi32
{	
	public static final String DLL_NAME = "Gdi32.dll";
	
	public static final int DIB_RGB_COLORS = 0;
	public static final int SRCCOPY = 0xCC0020;
    
    public static final int PS_SOLID = 	0;
    public static final int PS_DASH =	1;
    public static final int PS_DOT =	2;
    public static final int PS_DASHDOT =	3;
    public static final int PS_DASHDOTDOT =	4;
    public static final int PS_NULL =	5;
    public static final int PS_USERSTYLE =	7;
    public static final int PS_INSIDEFRAME =	6;
	
	/**
	 * <pre>
	 * GetStockObject
	 
	 The GetStockObject function retrieves a handle to one of the stock pens, brushes, fonts, or palettes.
	 
	 HGDIOBJ GetStockObject(
	 int fnObject   // stock object type
	 );
	 * Parameters
	 
	 fnObject
	 [in] Specifies the type of stock object. This parameter can be one of the following values.
	 Value 	Meaning
	 BLACK_BRUSH 	Black brush.
	 DKGRAY_BRUSH 	Dark gray brush.
	 DC_BRUSH 	Windows 2000/XP: Solid color brush. The default color is white. The color can be changed by using the SetDCBrushColor function. For more information, see the Remarks section.
	 GRAY_BRUSH 	Gray brush.
	 HOLLOW_BRUSH 	Hollow brush (equivalent to NULL_BRUSH).
	 LTGRAY_BRUSH 	Light gray brush.
	 NULL_BRUSH 	Null brush (equivalent to HOLLOW_BRUSH).
	 WHITE_BRUSH 	White brush.
	 BLACK_PEN 	Black pen.
	 DC_PEN 	Windows 2000/XP: Solid pen color. The default color is white. The color can be changed by using the SetDCPenColor function. For more information, see the Remarks section.
	 WHITE_PEN 	White pen.
	 ANSI_FIXED_FONT 	Windows fixed-pitch (monospace) system font.
	 ANSI_VAR_FONT 	Windows variable-pitch (proportional space) system font.
	 DEVICE_DEFAULT_FONT 	Windows NT/2000/XP: Device-dependent font.
	 DEFAULT_GUI_FONT 	Default font for user interface objects such as menus and dialog boxes. This is MS Sans Serif. Compare this with SYSTEM_FONT.
	 OEM_FIXED_FONT 	Original equipment manufacturer (OEM) dependent fixed-pitch (monospace) font.
	 SYSTEM_FONT 	System font. By default, the system uses the system font to draw menus, dialog box controls, and text.
	 
	 Windows 95/98 and Windows NT: The system font is MS Sans Serif.
	 
	 Windows 2000/XP: The system font is Tahoma
	 SYSTEM_FIXED_FONT 	Fixed-pitch (monospace) system font. This stock object is provided only for compatibility with 16-bit Windows versions earlier than 3.0.
	 DEFAULT_PALETTE 	Default palette. This palette consists of the static colors in the system palette.
	 
	 Return Values
	 
	 If the function succeeds, the return value is a handle to the requested logical object.
	 
	 If the function fails, the return value is NULL.
	 
	 Windows NT/2000/XP: To get extended error information, call GetLastError.</pre>
	 * @throws IllegalAccessException
	 * @throws NativeException
	 */
	public static LONG GetStockObject(int fnObject) throws NativeException, IllegalAccessException
	{
		JNative getStockObject = new JNative(DLL_NAME, "GetStockObject");
		getStockObject.setRetVal(Type.INT);
		
		getStockObject.setParameter(0, fnObject);
		getStockObject.invoke();
		
		return new LONG(getStockObject.getRetValAsInt());
	}
   /*
		The GetObject function retrieves information for the specified graphics object.
	
		int GetObject(
		  HGDIOBJ hgdiobj,  // handle to graphics object
		  int cbBuffer,     // size of buffer for object information
		  LPVOID lpvObject  // buffer for object information
		);
	**/
	public static int GetObject(int handle, int size, BITMAP info) throws NativeException, IllegalAccessException
	{
		
		JNative GetObject = new JNative(DLL_NAME, "GetObjectA");
		try
		{
			GetObject.setRetVal(Type.INT);
			
			GetObject.setParameter(0, handle);
			GetObject.setParameter(1, size);
			GetObject.setParameter(2, info.getPointer());
			GetObject.invoke();
			return GetObject.getRetValAsInt();
		}
		finally
		{
			if(GetObject != null)
				GetObject.dispose();
		}
	}
	
	/*
	BOOL DeleteDC(
	  HDC hdc   // handle to DC
	);
	 */
	public static boolean DeleteDC(DC hdc)
	throws NativeException, IllegalAccessException
	{
		JNative DeleteDC = new JNative(DLL_NAME, "DeleteDC");
		try
		{
			DeleteDC.setRetVal(Type.INT);
			DeleteDC.setParameter(0, hdc.getValue());
			DeleteDC.invoke();
			return (DeleteDC.getRetValAsInt() != 0);
		}
		finally
		{
			if(DeleteDC != null)
				DeleteDC.dispose();
		}
	}
	
	 /*
	HDC CreateCompatibleDC(
		HDC hdc   // handle to DC
	);
	  */
	public static DC CreateCompatibleDC(DC hdc)
	throws NativeException, IllegalAccessException
	{
		
		JNative CreateCompatibleDC = new JNative(DLL_NAME, "CreateCompatibleDC");
		try
		{
			CreateCompatibleDC.setRetVal(Type.INT);
			
			CreateCompatibleDC.setParameter(0, hdc.getValue());
			CreateCompatibleDC.invoke();
			return new DC(CreateCompatibleDC.getRetValAsInt());
		}
		finally
		{
			if(CreateCompatibleDC != null)
				CreateCompatibleDC.dispose();
		}
		
	}
	
	/*
	int GetDIBits(
	  HDC hdc,           // handle to DC
	  HBITMAP hbmp,      // handle to bitmap
	  UINT uStartScan,   // first scan line to set
	  UINT cScanLines,   // number of scan lines to copy
	  LPVOID lpvBits,    // array for bitmap bits
	  LPBITMAPINFO lpbi, // bitmap data buffer
	  UINT uUsage        // RGB or palette index
	);
	 */
	public static int GetDIBits(DC hdc,
			int hbmp,
			int uStartScan,
			int cScanLines,
			Pointer lpvBits,
			BITMAPINFOHEADER lpbi,
			int uUsage)
			throws NativeException, IllegalAccessException
	{
		
		JNative GetDIBits = new JNative(DLL_NAME, "GetDIBits");
		try
		{
			GetDIBits.setRetVal(Type.INT);
			int i = 0;
			
			GetDIBits.setParameter(i++, hdc.getValue());
			GetDIBits.setParameter(i++, hbmp);
			GetDIBits.setParameter(i++, uStartScan);
			GetDIBits.setParameter(i++, cScanLines);
			GetDIBits.setParameter(i++, lpvBits);
			GetDIBits.setParameter(i++, lpbi.getPointer());
			GetDIBits.setParameter(i++, uUsage);
			GetDIBits.invoke();
			return GetDIBits.getRetValAsInt();
		}
		finally
		{
			if(GetDIBits != null)
				GetDIBits.dispose();
		}
	}
	
	/*
	BOOL BitBlt(
	  HDC hdcDest, // handle to destination DC
	  int nXDest,  // x-coord of destination upper-left corner
	  int nYDest,  // y-coord of destination upper-left corner
	  int nWidth,  // width of destination rectangle
	  int nHeight, // height of destination rectangle
	  HDC hdcSrc,  // handle to source DC
	  int nXSrc,   // x-coordinate of source upper-left corner
	  int nYSrc,   // y-coordinate of source upper-left corner
	  DWORD dwRop  // raster operation code
	);
	 */
	public static boolean BitBlt(DC hdcDest,
			int nXDest,
			int nYDest,
			int nWidth,
			int nHeight,
			DC hdcSrc,
			int nXSrc,
			int nYSrc,
			int dwRop)
			throws NativeException, IllegalAccessException
	{
		
		JNative BitBlt = new JNative(DLL_NAME, "BitBlt");
		try
		{
			BitBlt.setRetVal(Type.INT);
			int i = 0;
			
			BitBlt.setParameter(i++, hdcDest.getValue());
			BitBlt.setParameter(i++, nXDest);
			BitBlt.setParameter(i++, nYDest);
			BitBlt.setParameter(i++, nWidth);
			BitBlt.setParameter(i++, nHeight);
			BitBlt.setParameter(i++, hdcSrc.getValue());
			BitBlt.setParameter(i++, nXSrc);
			BitBlt.setParameter(i++, nYSrc);
			BitBlt.setParameter(i++, dwRop);
			
			BitBlt.invoke();
			return (BitBlt.getRetValAsInt() != 0);
		}
		finally
		{
			if(BitBlt != null)
				BitBlt.dispose();
		}
		
	}
	
	
	/*
	HGDIOBJ SelectObject(
		HDC hdc,          // handle to DC
		HGDIOBJ hgdiobj   // handle to object
	);
	 **/
	public static int SelectObject(DC hdc,
			int hgdiobj)
			throws NativeException, IllegalAccessException
	{
		JNative SelectObject = new JNative(DLL_NAME, "SelectObject");
		try
		{
			SelectObject.setRetVal(Type.INT);
			
			int i = 0;
			
			SelectObject.setParameter(i++, hdc.getValue());
			SelectObject.setParameter(i++, hgdiobj);
			SelectObject.invoke();
			return SelectObject.getRetValAsInt();
		}
		finally
		{
			if(SelectObject != null)
				SelectObject.dispose();
		}
	}
	
	/*
	BOOL DeleteObject(
	  HGDIOBJ hObject   // handle to graphic object
	);
	 */
	public static boolean DeleteObject(int hObject) throws NativeException, IllegalAccessException
	{
		JNative DeleteObject = new JNative(DLL_NAME, "DeleteObject");
		try
		{
			DeleteObject.setRetVal(Type.INT);
			
			DeleteObject.setParameter(0, hObject);
			DeleteObject.invoke();
			return (DeleteObject.getRetValAsInt() != 0);
		}
		finally
		{
			if(DeleteObject != null)
				DeleteObject.dispose();
		}
	}
	
	
	/*
	HBITMAP CreateCompatibleBitmap(
		HDC hdc,        // handle to DC
		int nWidth,     // width of bitmap, in pixels
		int nHeight     // height of bitmap, in pixels
	);
	 */
	public static int CreateCompatibleBitmap(DC hdc,
			int nWidth, int nHeight)
			throws NativeException, IllegalAccessException
	{
		JNative CreateCompatibleBitmap = new JNative(DLL_NAME, "CreateCompatibleBitmap");
		try
		{
			CreateCompatibleBitmap.setRetVal(Type.INT);
			
			int i = 0;
			
			CreateCompatibleBitmap.setParameter(i++, hdc.getValue());
			CreateCompatibleBitmap.setParameter(i++, nWidth);
			CreateCompatibleBitmap.setParameter(i++, nHeight);
			CreateCompatibleBitmap.invoke();
			return CreateCompatibleBitmap.getRetValAsInt();
		}
		finally
		{
			if(CreateCompatibleBitmap != null)
				CreateCompatibleBitmap.dispose();
		}
	}
	/*
	LONG GetBitmapBits(
	  HBITMAP hbmp,      // handle to bitmap
	  LONG cbBuffer,     // number of bytes to copy
	  LPVOID lpvBits     // buffer to receive bits
	);
	 */
	public static int GetBitmapBits(int hbmp,
			int cbBuffer, Pointer lpvBits)
			throws NativeException, IllegalAccessException
	{
		JNative GetBitmapBits = new JNative(DLL_NAME, "GetBitmapBits");
		try
		{
			GetBitmapBits.setRetVal(Type.INT);
			int i = 0;
			
			GetBitmapBits.setParameter(i++, hbmp);
			GetBitmapBits.setParameter(i++, cbBuffer);
			GetBitmapBits.setParameter(i++, lpvBits);
			GetBitmapBits.invoke();
			return GetBitmapBits.getRetValAsInt();
		}
		finally
		{
			if(GetBitmapBits != null)
				GetBitmapBits.dispose();
		}
	}
	
	/*
	int SetBkMode(
	  HDC hdc,      // handle to DC
	  int iBkMode   // background mode
	);
	 */
	public static int SetBkMode(DC hdc, int iBkMode) throws NativeException, IllegalAccessException
	{
		JNative SetBkMode = new JNative(DLL_NAME, "SetBkMode");
		try
		{
			SetBkMode.setRetVal(Type.INT);
			int i = 0;
			
			SetBkMode.setParameter(i++, hdc.getValue());
			SetBkMode.setParameter(i++, iBkMode);
			
			SetBkMode.invoke();
			return SetBkMode.getRetValAsInt();
		}
		finally
		{
			if(SetBkMode != null)
				SetBkMode.dispose();
		}
	}
	
	/*
	BOOL TextOut(
	  HDC hdc,           // handle to DC
	  int nXStart,       // x-coordinate of starting position
	  int nYStart,       // y-coordinate of starting position
	  LPCTSTR lpString,  // character string
	  int cbString       // number of characters
	);
	 */
	public static boolean TextOut(DC hdc, int nXStart, int nYStart, String lpString, int cbString) throws NativeException, IllegalAccessException
	{
		JNative TextOut = new JNative(DLL_NAME, "TextOutA");
		try
		{
			TextOut.setRetVal(Type.INT);
			int i = 0;
			
			TextOut.setParameter(i++, hdc.getValue());
			TextOut.setParameter(i++, nXStart);
			TextOut.setParameter(i++, nYStart);
			TextOut.setParameter(i++, lpString);
			TextOut.setParameter(i++, cbString);
			
			TextOut.invoke();
			return (TextOut.getRetValAsInt() != 0);
		}
		finally
		{
			if(TextOut != null)
				TextOut.dispose();
		}
	}
    
    /*
    BOOL Rectangle(
      HDC hdc,         // handle to DC
      int nLeftRect,   // x-coord of upper-left corner of rectangle
      int nTopRect,    // y-coord of upper-left corner of rectangle
      int nRightRect,  // x-coord of lower-right corner of rectangle
      int nBottomRect  // y-coord of lower-right corner of rectangle
    );
     */
	public static boolean Rectangle(DC hdc, int nLeftRect, int nTopRect, int nRightRect, int nBottomRect) throws NativeException, IllegalAccessException
	{
		JNative Rectangle = new JNative(DLL_NAME, "Rectangle");
		try
		{
			Rectangle.setRetVal(Type.INT);
			int i = 0;
			
			Rectangle.setParameter(i++, hdc.getValue());
			Rectangle.setParameter(i++, nLeftRect);
			Rectangle.setParameter(i++, nTopRect);
			Rectangle.setParameter(i++, nRightRect);
			Rectangle.setParameter(i++, nBottomRect);
			
			Rectangle.invoke();
			return (Rectangle.getRetValAsInt() != 0);
		}
		finally
		{
			if(Rectangle != null)
				Rectangle.dispose();
		}
	}
    /*
    COLORREF SetDCBrushColor(
      HDC hdc,          // handle to DC
      COLORREF crColor  // new brush color
    );
     */
    public static DWORD SetDCBrushColor(DC hdc, DWORD crColor) throws NativeException, IllegalAccessException
	{
		JNative SetDCBrushColor = new JNative(DLL_NAME, "SetDCBrushColor");
		try
		{
			SetDCBrushColor.setRetVal(Type.INT);
			int i = 0;
			
			SetDCBrushColor.setParameter(i++, hdc.getValue());
			SetDCBrushColor.setParameter(i++, crColor.getValue());
			
			SetDCBrushColor.invoke();
			return new DWORD(SetDCBrushColor.getRetValAsInt());
		}
		finally
		{
			if(SetDCBrushColor != null)
				SetDCBrushColor.dispose();
		}
	}
     /*
    BOOL EndPath(
      HDC hdc   // handle to DC
    );
     */
    public static boolean EndPath(DC hdc) throws NativeException, IllegalAccessException
	{
		JNative EndPath = new JNative(DLL_NAME, "EndPath");
		try
		{
			
			EndPath.setRetVal(Type.INT);
			EndPath.setParameter(0, hdc.getValue());
			EndPath.invoke();
            
            return (EndPath.getRetValAsInt() != 0);
		}
		finally
		{
			if(EndPath != null)
				EndPath.dispose();
		}
    }
    /*
    BOOL BeginPath(
      HDC hdc   // handle to DC
    );
     */
    public static boolean BeginPath(DC hdc) throws NativeException, IllegalAccessException
	{
		JNative BeginPath = new JNative(DLL_NAME, "BeginPath");
		try
		{
			
			BeginPath.setRetVal(Type.INT);
			BeginPath.setParameter(0, hdc.getValue());
			BeginPath.invoke();
            
            return (BeginPath.getRetValAsInt() != 0);
		}
		finally
		{
			if(BeginPath != null)
				BeginPath.dispose();
		}
    }
    /*
    int SaveDC(
      HDC hdc   // handle to DC
    );
     */
	public static int SaveDC(DC hdc) throws NativeException, IllegalAccessException
	{
		JNative SaveDC = new JNative(DLL_NAME, "SaveDC");
		try
		{
			
			SaveDC.setRetVal(Type.INT);
			SaveDC.setParameter(0, hdc.getValue());
			SaveDC.invoke();
            
            return SaveDC.getRetValAsInt();
		}
		finally
		{
			if(SaveDC != null)
				SaveDC.dispose();
		}
    }
    /*
    BOOL RestoreDC(
      HDC hdc,       // handle to DC
      int nSavedDC   // restore state
    );
     */
    public static boolean RestoreDC(DC hdc, int savedDC) throws NativeException, IllegalAccessException
	{
		JNative RestoreDC = new JNative(DLL_NAME, "RestoreDC");
		try
		{
			
			RestoreDC.setRetVal(Type.INT);
			RestoreDC.setParameter(0, hdc.getValue());
            RestoreDC.setParameter(1, savedDC);
			RestoreDC.invoke();
            
            return (RestoreDC.getRetValAsInt() != 0);
		}
		finally
		{
			if(RestoreDC != null)
				RestoreDC.dispose();
		}
    }
	public static DWORD SetTextColor(DC hdc, DWORD color) throws NativeException,
			IllegalAccessException
	{
		JNative TextOut = new JNative(DLL_NAME, "SetTextColor");
		try
		{
			TextOut.setRetVal(Type.INT);
			int i = 0;
			
			TextOut.setParameter(i++, hdc.getValue());
			TextOut.setParameter(i++, color.getValue());
			
			TextOut.invoke();
			return new DWORD(TextOut.getRetValAsInt());
		}
		finally
		{
			if (TextOut != null)
				TextOut.dispose();
		}
	}
    
    /*
     HPEN CreatePen(
      int fnPenStyle,    // pen style
      int nWidth,        // pen width
      COLORREF crColor   // pen color
    );
     */
    
    public static int CreatePen( int fnPenStyle,    // pen style
                                 int nWidth,        // pen width
                                 int crColor) throws NativeException,
			IllegalAccessException
	{
		JNative CreatePen = new JNative(DLL_NAME, "CreatePen");
		try
		{
			CreatePen.setRetVal(Type.INT);
			int i = 0;
			
			CreatePen.setParameter(i++, fnPenStyle);
			CreatePen.setParameter(i++, nWidth);
            CreatePen.setParameter(i++, crColor);
			
			CreatePen.invoke();
			return CreatePen.getRetValAsInt();
		}
		finally
		{
			if (CreatePen!= null)
				CreatePen.dispose();
		}
	}
    /*
     BOOL MoveToEx(
      HDC hdc,          // handle to device context
      int X,            // x-coordinate of new current position
      int Y,            // y-coordinate of new current position
      LPPOINT lpPoint   // old current position
    );
     */
    public static boolean MoveToEx( DC hdc,    // pen style
                                 int X,        // pen width
                                 int Y,
                                 POINT lpPointer) throws NativeException,
			IllegalAccessException
	{
		JNative MoveToEx = new JNative(DLL_NAME, "MoveToEx");
		try
		{
			MoveToEx.setRetVal(Type.INT);
			int i = 0;
			
			MoveToEx.setParameter(i++, hdc.getValue());
			MoveToEx.setParameter(i++, X);
            MoveToEx.setParameter(i++, Y);
            MoveToEx.setParameter(i++, lpPointer == null ? NullPointer.NULL.getPointer() : lpPointer.getPointer().getPointer());
			
			MoveToEx.invoke();
			return (MoveToEx.getRetValAsInt() != 0);
		}
		finally
		{
			if (MoveToEx != null)
				MoveToEx.dispose();
		}
	}
    
    /*
     BOOL LineTo(
      HDC hdc,    // device context handle
      int nXEnd,  // x-coordinate of ending point
      int nYEnd   // y-coordinate of ending point
    );
     */
    public static boolean LineTo( DC hdc,    // pen style
                                 int nXEnd,        // pen width
                                 int nYEnd) throws NativeException,
			IllegalAccessException
	{
		JNative LineTo = new JNative(DLL_NAME, "LineTo");
		try
		{
			LineTo.setRetVal(Type.INT);
			int i = 0;
			
			LineTo.setParameter(i++, hdc.getValue());
			LineTo.setParameter(i++, nXEnd);
            LineTo.setParameter(i++, nYEnd);
			
			LineTo.invoke();
			return (LineTo.getRetValAsInt() != 0);
		}
		finally
		{
			if (LineTo != null)
				LineTo.dispose();
		}
	}
}

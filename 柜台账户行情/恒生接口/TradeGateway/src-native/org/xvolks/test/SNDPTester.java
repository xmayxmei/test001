package org.xvolks.test;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.misc.basicStructures.UINT;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.GlobalMemoryBlock;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;
import org.xvolks.jnative.util.StructConverter;
/*
 JNative will be both Java library and C++ DLL.
 The C++ DLL provide access to malloc and free functions of the system and can map a Java byte array to memory regions. It also provides a way to call any function in any library (call is done in assembler).
 
 The Java side is mostly composed of utils classes to manipulate native pointers and fuctions.
 
 Some libraries of predefied functions of well known DLL are planned
 
*/

/**
 * This class demonstrates the JNative by driving a scanner
 * 
* $Id: SNDPTester.java,v 1.5 2006/06/09 20:44:05 mdenty Exp $
* 
* This software is released under the LGPL.
* @author Created by Marc DENTY - (c) 2006 JNative project
*/
public class SNDPTester {
	public final static int INIT_SUCCESS = 0;
	public final static int INIT_VERSION_ERROR = 1;
	public final static int INIT_SOCKET_FAILURE = 2;
	public final static int INIT_INI_NOT_FOUND = 3;
	public final static int INIT_MANAGER_START_FAILURE = 4;
	public final static int INIT_HEARTBEAT_FAILURE = 6;
	public final static int INIT_INVALID_PORTNUMBER=0x0B;
	public final static int INIT_SHUTTDOWN_IN_PROGRESS = 0x0C;
	public final static int INIT_ALREADY_INITIALIZED = 0xD;
	
	public static final int SNDP_DISCONNECTED = 0;
	public static final int SNDP_EXCEPTION = 1;
	public static final int SNDP_NOT_INITIALIZED = 2;
	public static final int SNDP_APP_ERROR = 3;
	public static final int SNDP_INVALID_DOC_ID = 4;
	public static final int SNDP_DLL_ERROR = 5;
	public static final int SNDP_CONNECTED = 6;
	public static final int SNDP_READYING = 7;
	public static final int SNDP_READY = 8;
	public static final int SNDP_FLOWING = 9;
	public static final int SNDP_FEEDER_EMPTY = 10;
	public static final int SNDP_SHUTTING_DOWN = 11;
	public static final int SNDP_SHUTDOWN = 12;
	public static final int SNDP_CODELINES_AVAILABLE = 13;
	public static final int SNDP_FRONT_IMAGES_AVAILABLE = 14;
	public static final int SNDP_REAR_IMAGES_AVAILABLE = 15;
	public static final int SNDP_DOC_COMPLETE_AVAILABLE = 16;
	
	private static String cmc7;
	
	
	public static void main(String[] args) throws NativeException, IllegalAccessException, InterruptedException, NoSuchMethodException {
		MemoryBlockFactory.setPreferredMemoryType(GlobalMemoryBlock.class);
//		MemoryBlockFactory.setPreferredMemoryType(HeapMemoryBlock.class);
		getUnitStatus();
		shutDown();
		while(getUnitStatus() != 2) {
			Thread.sleep(1);
		}
		JNative initialize = new JNative("SNDP.dll", "Initialize");
		initialize.setRetVal(Type.INT);
		initialize.setParameter(0, Type.STRING, "C:\\SourceNDP\\Samples\\QualApp\\SNDP_DLL.INI");
		initialize.setParameter(1, Type.STRING, "C:\\SourceNDP");
		initialize.invoke();
		switch(Integer.parseInt(initialize.getRetVal())) {
			case INIT_SUCCESS:
				System.err.println("Succes");
				handleEvents();
				break;
			case INIT_VERSION_ERROR:
				System.err.println("Version Error");
				break;
			case INIT_SOCKET_FAILURE:
				System.err.println("Socket Error");
				break;
			case INIT_INI_NOT_FOUND:
				System.err.println("INI not found");
				break;
			case INIT_MANAGER_START_FAILURE:
				System.err.println("Manager Error");
				break;
			case INIT_HEARTBEAT_FAILURE:
				System.err.println("Heartbeat Error");
				break;
			case INIT_INVALID_PORTNUMBER:
				System.err.println("Invalid port number");
				break;
			case INIT_SHUTTDOWN_IN_PROGRESS:
				System.err.println("Shutdown in progress");
				break;
			case INIT_ALREADY_INITIALIZED:
				System.err.println("Already initialized");
				break;
		}
	}
	
	private static void handleEvents() throws NativeException, NumberFormatException, IllegalAccessException, InterruptedException {
		new File("Images").mkdirs();
		while(true) {
			getUnitStatus();
			if(false) break;
		}
		while(true) {
			switch(getUnitStatus()) {
				case SNDP_DISCONNECTED:
					Thread.sleep(1000);
					break;
				case SNDP_EXCEPTION:
					System.err.println("SNDP_EXCEPTION");
					break;
				case SNDP_NOT_INITIALIZED:
				case SNDP_APP_ERROR:
				case SNDP_INVALID_DOC_ID:
				case SNDP_DLL_ERROR:
					Thread.sleep(500);
					break;
				case SNDP_CONNECTED:
					System.err.println("CONNECTED");
					Thread.sleep(500);
					break;
				case SNDP_READYING:
					System.err.println("SNDP_READYING");
					Thread.sleep(500);
					break;
				case SNDP_READY:
					saveDDValueNVM(150);
					System.err.println(loadDDValueFromNVM());
					startFlow();
					break;
				case SNDP_FLOWING:
					Thread.sleep(1);
					System.err.println("flowing");
					break;
				case SNDP_FEEDER_EMPTY:
					System.err.println("Feeder empty");
					break;
				case SNDP_SHUTTING_DOWN:
				case SNDP_SHUTDOWN:
					Thread.sleep(1000);
					break;
				case SNDP_CODELINES_AVAILABLE:
					cmc7 = getCodeLines();
					break;
				case SNDP_FRONT_IMAGES_AVAILABLE:
					try {
						FileOutputStream os = new FileOutputStream("Images/FTest" + docId + ".tif");
						os.write(getFrontImages());
						os.close();
						setPocketAndEndorsement();
					}
					catch(IOException e) {
						e.printStackTrace();
					}
					break;
				case SNDP_REAR_IMAGES_AVAILABLE:
					try {
						FileOutputStream os = new FileOutputStream("Images/RTest" + docId + ".tif");
						os.write(getRearImages());
						os.close();
					}
					catch(IOException e) {
						e.printStackTrace();
					}
//					break;
				case SNDP_DOC_COMPLETE_AVAILABLE:
					System.err.println("GetdocComplete : " + getDocComplete());
					break;
				default:
					Thread.sleep(1000);
			}
		}
	}
	static JNative getCodeLines ;
	static Pointer lpCodeLines ;
	static LONG docId;
	/*
	 struct {
	 uint32 DocId;
	 uint16 DocLength;
	 uint32 DocStatus;
	 UCHAR Codeline1[96];
	 int16 CantReadCnt1;
	 UCHAR Codeline2[96];
	 int16 CantReadCnt2;
	 UCHAR Codeline3[96];
	 int16 CantReadCnt3;
	 } CodeLineStruct;
	 */
	private static String getCodeLines() throws NativeException, IllegalAccessException {
		if(getCodeLines == null) {
			lpCodeLines = new Pointer(MemoryBlockFactory.createMemoryBlock(4 + 2 + 4 + 96 + 2 + 96 + 2 + 96 + 2));
			docId = new LONG(0);
			docId.createPointer();
			getCodeLines = new JNative("SNDP.DLL", "GetCodelines");
			getCodeLines.setRetVal(Type.INT);
		}
		getCodeLines.setParameter(0, lpCodeLines);
		getCodeLines.setParameter(1, docId.getPointer());
		getCodeLines.invoke();
		boolean ret = !getCodeLines.getRetVal().equals("0");
		if(ret) {
			docId.getValueFromPointer();
			return new String(lpCodeLines.getMemory(), 10, 96);
		} else {
			throw new RuntimeException("GetCodeLines failed !");
		}
	}
	
	static JNative getFrontImages;
	static Pointer lpFrontImageStruct;
	/*
	 struct {
	 uint32 DocID;
	 UCHAR* Image1;
	 uint32 Image1Size;
	 UCHAR* Image2;
	 uint32 Image2Size;
	 UCHAR* Image3;
	 uint32 Image3Size;
	 SkewCoordinateStruct SkewCoordinates;
	 } FrontImageStruct;
	 struct {
	 PixelCoordinates blc;
	 PixelCoordinates brc;
	 PixelCoordinates tlc;
	 PixelCoordinates trc;
	 } SkewCoordinateStruct;
	 struct {
	 int x;
	 int y;
	 } PixelCoordinates;
	 
	 */
	private static byte[] getFrontImages() throws NativeException, IllegalAccessException {
		if(getFrontImages == null) {
			lpFrontImageStruct = new Pointer(MemoryBlockFactory.createMemoryBlock(4 * 7 + 4 * 2 * 2));
			getFrontImages = new JNative("SNDP.DLL", "GetFrontImages");
			getFrontImages.setRetVal(Type.INT);
			getFrontImages.setParameter(0, lpFrontImageStruct);
		}
		getFrontImages.setParameter(1, Type.INT, docId.toString());
		getFrontImages.invoke();
		System.err.println("fontimages done");
		String retS = getFrontImages.getRetVal();
		boolean ret = !retS.equals("0");
		if(ret) {
			return JNative.getMemory(lpFrontImageStruct.getAsInt(4), lpFrontImageStruct.getAsInt(8));
		} else {
			System.err.println(ret + "<-" + retS);
			throw new RuntimeException("GetFrontImages failed !");
		}
	}
	static JNative getRearImages;
	static Pointer lpRearImageStruct;
	/*
	 struct {
	 uint32 DocID;
	 UCHAR* Image1;
	 uint32 Image1Size;
	 UCHAR* Image2;
	 uint32 Image2Size;
	 } RearImageStruct;
	 */
	private static byte[] getRearImages() throws NativeException, IllegalAccessException {
		if(getRearImages == null) {
			lpRearImageStruct = new Pointer(MemoryBlockFactory.createMemoryBlock(4 * 5));
			getRearImages = new JNative("SNDP.DLL", "GetRearImages");
			getRearImages.setRetVal(Type.INT);
			getRearImages.setParameter(0, lpRearImageStruct);
		}
		getRearImages.setParameter(1, Type.INT, docId.toString());
		getRearImages.invoke();
		boolean ret = !getRearImages.getRetVal().equals("0");
		if(ret) {
			return JNative.getMemory(lpRearImageStruct.getAsInt(4), lpRearImageStruct.getAsInt(8));
		} else {
			throw new RuntimeException("GetRearImages failed !");
		}
	}
	
	static JNative getDocComplete;
	static UINT docStatus;
	private static int getDocComplete() throws NativeException, IllegalAccessException {
		if(getDocComplete == null) {
			docStatus = new UINT((short)2);
			getDocComplete = new JNative("SNDP.DLL", "GetDocComplete");
			getDocComplete.setRetVal(Type.INT);
			getDocComplete.setParameter(0, docStatus.createPointer());
		}
		getDocComplete.setParameter(1, Type.INT, docId.toString());
		getDocComplete.invoke();
		boolean ret =  !getDocComplete.getRetVal().equals("0");
		if(ret) {
			return docStatus.getValueFromPointer();
		} else {
			throw new RuntimeException("GetDocComplete failed !");
		}
	}
	
	static JNative setPocketAndEndorsement;
	static Pointer lpPocketStruct;
	/*
	 struct {
	 uint32 EndOptions;
	 int32 EndPosition;
	 int32 EndFontNumber;
	 uint32 EndLineLen;
	 UCHAR EndLine[90];
	 int32 Pocket;
	 int32 ImgCarDocType;
	 } PocketStruct;
	 */
	private static void setPocketAndEndorsement() throws NativeException, IllegalAccessException {
		if(setPocketAndEndorsement == null) {
			lpPocketStruct = new Pointer(MemoryBlockFactory.createMemoryBlock(4 * 4 + 90 + 2 * 4));
			lpPocketStruct.zeroMemory();
			int offset = 0;
			offset += lpPocketStruct.setIntAt(offset, 1);
			offset += lpPocketStruct.setIntAt(offset, 1);
			offset += lpPocketStruct.setIntAt(offset, 1);
			
			
			setPocketAndEndorsement = new JNative("SNDP.DLL", "SetPocketAndEndorsement");
			setPocketAndEndorsement.setRetVal(Type.INT);
		}
		if(cmc7. length() < 8) {
			cmc7 = "<1234567<";
		}
		int lPocket = (cmc7.charAt(7) & 1) + 1;
//		if(Math.random() < .1) {
//			lPocket = 254;
//			System.err.println("Document " + docId + " en exception");
//		}
		String endo = "Poche " + lPocket;
		lpPocketStruct.setIntAt(12, endo.length());
		lpPocketStruct.setStringAt(16, endo);
		lpPocketStruct.setIntAt(108, lPocket);
//		byte[] buff = lpPocketStruct.getMemory();
//		lpPocketStruct.setIntAt(106, 0x10000);
		setPocketAndEndorsement.setParameter(0, lpPocketStruct);
		setPocketAndEndorsement.setParameter(1, Type.INT, docId.toString());
		setPocketAndEndorsement.invoke();
		boolean ret =  !setPocketAndEndorsement.getRetVal().equals("0");
		if(!ret) {
			throw new RuntimeException("SetPocketAndEndorsement failed !");
		}
	}
	
	@SuppressWarnings("unused")
	private static int tireUnePiece() {
		return Math.random() > 0.5 ? 1: 2;
	}
	@SuppressWarnings("unused")
	private static String tireUnePieceAsString() {
		return Math.random() > 0.5 ? "Face": "Pile";
	}
	
	
	private static boolean startFlow() throws NativeException, IllegalAccessException {
		JNative startFlow = new JNative("SNDP.DLL", "StartFlow");
		startFlow.setRetVal(Type.INT);
		startFlow.setParameter(0, Type.INT, "0");
		startFlow.invoke();
		boolean ret = !startFlow.getRetVal().equals("0");
		startFlow.dispose();
		System.err.println("StartFlow : " + ret);
		return ret;
	}
	
	static Pointer lpExceptionStruct;
	static JNative getUnitStatus;
	
	public static int getUnitStatus() throws NativeException, NumberFormatException, IllegalAccessException {
		if(lpExceptionStruct == null) {
			lpExceptionStruct = new Pointer(MemoryBlockFactory.createMemoryBlock(4 * 4 + 5));
		}
//		lpExceptionStruct.zeroMemory();
		if(getUnitStatus == null) {
			getUnitStatus = new JNative("SNDP.DLL", "GetUnitStatus");
			getUnitStatus.setRetVal(Type.INT);
			getUnitStatus.setParameter(0, lpExceptionStruct);
		}
		getUnitStatus.invoke();
//		System.err.println("Unit status : " + getUnitStatus.getRetVal());
//		return 0;
		return Integer.parseInt(getUnitStatus.getRetVal());
	}
	
	public static void shutDown() throws NativeException, IllegalAccessException {
		JNative shutdown = new JNative("SNDP.DLL", "ShutDown");
		shutdown.invoke();
		shutdown.dispose();
	}

	public static int loadDDValueFromNVM() {
		byte[] buffer = getNVMData(4);
		if(buffer == null){
			try {
				throw new RuntimeException("Impossible de lire la valeur de la NVRAM");
			} catch (RuntimeException e) {
				e.printStackTrace();
				return 0;
			}
		} else {
			return StructConverter.bytesIntoInt(buffer, 0);
		}
	}
	
	private static void saveDDValueNVM(double lDDocReference) {
		int val = (int)lDDocReference + 1;
		byte[] buf = new byte[4];
		StructConverter.intIntoBytes(val, buf, 0);
		if(!setNVMData(buf)) {
			throw new RuntimeException("Impossible de sauver la valeur dans la NVRAM");
		}
	}
	
	public static byte[] getNVMData(int len) {
		try {
			JNative getNVMData = new JNative("SNDP.dll", "?GetNVMData@@YGHPAEK@Z");
//			MemoryBlockFactory.setPreferredMemoryType(GlobalMemoryBlock.class);
			Pointer data = new Pointer(MemoryBlockFactory.createMemoryBlock(len));
			getNVMData.setRetVal(Type.INT);
			getNVMData.setParameter(0, data);
			getNVMData.setParameter(1, Type.INT, data.getSize() + "");
			getNVMData.invoke();
			if ("0".equals(getNVMData.getRetVal())) {
				return null;
			} else {
				return data.getMemory();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	public static boolean setNVMData(byte[] buffer) {
		try {
			JNative setNVMData = new JNative("SNDP.dll", "?SetNVMData@@YGHPAEK@Z");
			Pointer data = new Pointer(MemoryBlockFactory.createMemoryBlock(buffer.length));
			data.setMemory(buffer);
			setNVMData.setRetVal(Type.INT);
			setNVMData.setParameter(0, data);
			setNVMData.setParameter(1, Type.INT, buffer.length + "");
			setNVMData.invoke();
			String ret = setNVMData.getRetVal();
			System.err.println(ret);
			return !("0".equals(ret));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}

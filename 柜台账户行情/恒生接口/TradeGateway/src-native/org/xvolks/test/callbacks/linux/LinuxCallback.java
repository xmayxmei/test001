package org.xvolks.test.callbacks.linux;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;
import org.xvolks.jnative.pointers.memory.NativeMemoryBlock;
import org.xvolks.jnative.util.Callback;

public class LinuxCallback {
	private final static String LIB_NAME = "/lib/libc.so.6";

	static Callback select;

	private static Callback getCallback() {
		if (select == null) {
			select = new Callback() {

				public int callback(long[] values) { 
					//values[0] should be a dirent *
					//so the name of the currently scanned file should be at offset 11
					System.out.format("values[0] = %s\n", Long.toHexString(values[0]));
					//Pointer p = new Pointer(new NativeMemoryBlock((int)values[0], 4));
					try {
						System.err.println(JNative.getMemoryAsString((int)values[0] + 11, 255));
					} catch (NativeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return 0;
				}

				private int myAddress = -1;

				public int getCallbackAddress() throws NativeException {
					if (myAddress == -1) {
						myAddress = JNative.createCallback(1, this);
					}
					return myAddress;
				}

			};
		}
		return select;
	}

	public static void runit() {
		try {
			JNative scandir = new JNative(LIB_NAME, "scandir");
			scandir.setRetVal(Type.INT);
			scandir.setParameter(0, "/usr");
			Pointer namelist = new Pointer(MemoryBlockFactory
					.createMemoryBlock(4));
			scandir.setParameter(1, namelist);
			scandir.setParameter(2, getCallback().getCallbackAddress());
			scandir.setParameter(3, new JNative(LIB_NAME, "alphasort")
					.getFunctionPointer());
			scandir.invoke();
			System.err.println("Got " + scandir.getRetVal() + " files");
			System.err.println("name list : " + namelist.getAsInt(0));
			Pointer p = new Pointer(new NativeMemoryBlock(namelist.getAsInt(0), scandir.getRetValAsInt()*4));
			System.err.println("NativeMemBlk created");
			for (int i = 0; i < scandir.getRetValAsInt(); i++) {
				
				System.err.format("name list val at %d = %s \n", p.getAsInt(4*i), JNative.getMemoryAsString(p.getAsInt(4*i) + 11, 256));
			}
			scandir.dispose();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}

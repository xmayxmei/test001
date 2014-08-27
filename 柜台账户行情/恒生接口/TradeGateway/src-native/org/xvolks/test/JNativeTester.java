package org.xvolks.test;

import static org.xvolks.jnative.misc.MSG.WindowsConstants.SW_SHOW;
import static org.xvolks.jnative.misc.MSG.WindowsConstants.WS_OVERLAPPEDWINDOW;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.JFileChooser;

import org.xvolks.jnative.Convention;
import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.logging.JNativeLogger.SEVERITY;
import org.xvolks.jnative.misc.MSG;
import org.xvolks.jnative.misc.basicStructures.HWND;
import org.xvolks.jnative.pointers.NullPointer;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;
import org.xvolks.jnative.pointers.memory.NativeMemoryBlock;
import org.xvolks.jnative.util.Kernel32;
import org.xvolks.jnative.util.User32;
import org.xvolks.test.callbacks.TestCallback;
import org.xvolks.test.callbacks.linux.LinuxCallback;

/**
 * 
 * $Id: JNativeTester.java,v 1.28 2007/05/20 12:05:05 thubby Exp $
 * 
 * This software is released under the LGPL.
 * 
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class JNativeTester {
	private static File searchFile(final String pattern, String root) {
		System.out.print("Searching " + pattern + " in " + root);
		for (File f : new File(root).listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				return Pattern.matches(pattern, pathname.getName());
			}
		})) {
			if (f.isFile()) {
				System.out.println("... found " + f);
				return f;
			}
		}
		System.out
				.println("... Not found, sorry cannot demonstrate JNative with "
						+ pattern);
		return null;
	}

	public static void main(String[] args) throws NativeException,
			IllegalAccessException, InterruptedException, IOException {
		
		System.getProperties().put("jnative.loadNative", "manual");
		
		loadLib();
		if (System.getProperty("os.name").toLowerCase().indexOf("linux") != -1) {
			JNative.setDefaultCallingConvention(Convention.STDCALL);
			
			Pointer p_asm = new Pointer(MemoryBlockFactory.createMemoryBlock(16));
			int cpt=0;
			//push ebp
			cpt += p_asm.setByteAt(cpt, (byte)0x55);
//			//push esp
			cpt += p_asm.setByteAt(cpt, (byte)0x54);
//			//lea eax, [esp+0x0c]
			cpt += p_asm.setIntAt(cpt, 0x0c24448d);

			cpt += p_asm.setByteAt(cpt, (byte)0x31); // xor eax, eax
			cpt += p_asm.setByteAt(cpt, (byte)0xc0);
			
			cpt += p_asm.setByteAt(cpt, (byte)0x66); // inc eax
			cpt += p_asm.setByteAt(cpt, (byte)0x40);
			
			
			
//			cpt += p_asm.setByteAt(cpt, (byte)0xb8);
//			cpt += p_asm.setByteAt(cpt, (byte)0x9a);
//			cpt += p_asm.setByteAt(cpt, (byte)0x99);
//			cpt += p_asm.setByteAt(cpt, (byte)0xc9);
//			cpt += p_asm.setByteAt(cpt, (byte)0x40);

			//sub esp, 8
//			cpt += p_asm.setByteAt(cpt, (byte)0x83);
//			cpt += p_asm.setByteAt(cpt, (byte)0xc4);
//			cpt += p_asm.setByteAt(cpt, (byte)0x8);
//
			// pop esp
			cpt += p_asm.setByteAt(cpt, (byte)0x5c);
//			// pop ebp
			cpt += p_asm.setByteAt(cpt, (byte)0x5d);
			// ret
			p_asm.setByteAt(cpt++, (byte)0xc3);
			// ret
//			p_asm.setByteAt(cpt++, (byte)0xc2);
//			p_asm.setByteAt(cpt++, (byte)0x00);
//			p_asm.setByteAt(cpt++, (byte)0x32);
			
			JNative n_asm = new JNative(p_asm.getPointer(), Convention.STDCALL);
			n_asm.setRetVal(Type.INT);
//			for(int i = 0; i<1000000; i++) {
				n_asm.invoke();
//			}
			System.err.println("n_asm returned : " + Integer.toString(n_asm.getRetValAsInt(), 16));
//			System.exit(02);
/*			
			System.err.println("g_type_init");
			JNative g_type_init = new JNative("/usr/lib/libgnomeui-2.so.0", "g_type_init");
			g_type_init.invoke();
			g_type_init.dispose();
			
			JNative gtk_init = new JNative("/usr/lib/libgnomeui-2.so.0", "gtk_init");
			gtk_init.setParameter(0, NullPointer.NULL);
			gtk_init.setParameter(1, NullPointer.NULL);
			gtk_init.invoke();
			
			MessageBoxGnome(
					"You are using Linux !?\nYou are a fool ?\nYou are not doing as all other dummies ?\nCongratulations !",
					"info",
					new String[] { "Welcome to JNative", "Ok"}
			);*/
			/*
			new Thread() {
				public void run() {
					try {
						JNative gtk_main = new JNative("/usr/lib/libbonoboui-2.so.0", "gtk_main");
						gtk_main.invoke();
					} catch (NativeException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}.start();
			*/
			System.err.println(System.getProperty("java.library.path"));
			JNative strcpy = new JNative("/lib/libc.so.6", "strcpy", false);
			strcpy.setRetVal(Type.INT);
			Pointer p = new Pointer(MemoryBlockFactory.createMemoryBlock(100));
			p.zeroMemory();
			p.setStringAt(0, "Toto was not here, but ");
			System.err.println("Before strcat");
			System.err.println("Get string from pointer p: " + p.getAsString());
			strcpy.setParameter(0, p);
			strcpy.setParameter(1, Type.STRING,
					"strcat was used from /lib/libc.so.6");
			strcpy.invoke();
			// System.err.println(strcpy.getRetVal());
			System.err.println("After strcat");
			System.err
					.println("Get string from pointer p : " + p.getAsString());
			
			
			JNative strcat = new JNative("/lib/libc.so.6", "strcat", false);
			strcat.setRetVal(Type.INT);
			Pointer p0 = new Pointer(MemoryBlockFactory.createMemoryBlock(100));
			p0.zeroMemory();
			p0.setStringAt(0, "Toto was not here, but ");
			System.err.println("Before strcat");
			System.err.println("Get string from pointer p: " + p0.getAsString());
			strcat.setParameter(0, p0);
			strcat.setParameter(1, Type.STRING,
					"strcat was used from /lib/libc.so.6");
			strcat.invoke();
			// System.err.println(strcat.getRetVal());
			System.err.println("After strcat");
			System.err
					.println("Get string from pointer p : " + p0.getAsString());
//			strcat.dispose();

			// Be aware the the following libglib-.* is a regular expression and
			// .* represents any characters
			// Not dot followed by any characters.
			File f = searchFile("libglib-.*", "/usr/lib");
			if (f == null) {
				System.err.println("Cannot demonstrate glib");
			} else {
				JNative b = new JNative(f.getCanonicalPath(),
						"g_random_int_range", false);
				b.setRetVal(Type.INT);
				b.setParameter(0, 0);
				b.setParameter(1, 255);
				b.invoke();
				System.err
						.println("Random number from g_random_int in /usr/lib/libglib-2.0.so: "
								+ b.getRetVal());
				b.dispose();
			}
			// String ss[] =
			// JNative.getDLLFileExports("/lib/libparted-1.7.so.1");
			// for(String s : ss) { System.err.println(s); }
			f = searchFile("libparted.*", "/usr/lib");
			if (f == null)
				f = searchFile("libparted.*", "/lib");
			/*
			if (f == null) {
				MessageBoxGnome(
						"Cannot demonstrate gparted calls",
						"info",
						new String[] { "Ok"}
				);
				return;	
			} else {
				MessageBoxGnome(
						"Demonstrate of gparted calls",
						"info",
						new String[] { "Ok"}
				);
			}*/

			JNative ped_device_get = new JNative(f.toString(), "ped_device_get");
			ped_device_get.setRetVal(Type.INT);
			ped_device_get.setParameter(0, Type.STRING, "/dev/hda");
			ped_device_get.invoke();
			Pointer devicePointer = new Pointer(new NativeMemoryBlock(
					ped_device_get.getRetValAsInt(), 19 * 4));
			System.err.format("DevicePointer = %x\n", devicePointer
					.getPointer());
			if (devicePointer.getPointer() != 0) {
				JNative ped_device_open = new JNative(f.toString(),
						"ped_device_open");
				ped_device_open.setRetVal(Type.INT);
				ped_device_open.setParameter(0, devicePointer);
				ped_device_open.invoke();
				int ret = ped_device_open.getRetValAsInt();
				if (ret != 0) {
					System.err.println(JNative.getMemoryAsString(devicePointer
							.getAsInt(4), 64));
					MessageBoxGnome(
							"Your /dev/hda is "+JNative.getMemoryAsString(devicePointer.getAsInt(4), 64),
							"info",
							new String[] { "Welcome to JNative", "Ok"}
					);

					
					JNative ped_device_close = new JNative(f.toString(),
							"ped_device_close");
					ped_device_close.setRetVal(Type.INT);
					ped_device_close.setParameter(0, devicePointer);
					ped_device_close.invoke();
				} else {
					System.err.println("Can't open device!");
				}
			}

			// Be aware the the following libglib-.* is a regular expression and
			// .* represents any characters
			// Not dot followed by any characters.
			f = searchFile("libglib-.*", "/usr/lib");

			// Multi-threading
			class Inline extends Thread {
				final File f;

				public Inline(File f, int id) {
					this.f = f;
					setName("Inline " + id);
				}

				public void run() {
					try {
						JNative b = new JNative(f.getCanonicalPath(),
								"g_random_int_range", false);
						b.setRetVal(Type.INT);
						b.setParameter(0, 0);
						b.setParameter(1, 255);
						for (int i = 0; i < 1000; i++) {
							b.invoke();
							System.err
									.println(Thread.currentThread().getName()
											+ " : Random number from g_random_int in /usr/lib/libglib-2.0.so: "
											+ b.getRetVal());
							yield();
						}
						b.dispose();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			int numThreads = 10;
			List<Inline> threads = new ArrayList<Inline>();
			for (int i = 0; i < numThreads; i++) {
				threads.add(new Inline(f, i));
			}
			/*
			for (int i = 0; i < numThreads; i++) {
				threads.get(i).start();
			}
			for (int i = 0; i < numThreads; i++) {
				threads.get(i).join();
			}
*/
			LinuxCallback.runit();

//			JNative gtk_main_quit = new JNative("/usr/lib/libbonoboui-2.so.0", "gtk_main_quit");
//			gtk_main_quit.invoke();
			

			
		} else if (System.getProperty("os.name").toLowerCase().indexOf(
				"windows") != -1) {
			User32.MessageBox(0,
					"Demonstrates JNative in action with many Win32 calls",
					"Welcome to JNative", 0);

			User32
					.MessageBox(
							0,
							"Demarrage de la calculatrice,\nATTENTION : ne cliquez pas sur OK\nsi vous avez ouvert la calculatrice\navec des donnees non sauvegardees : elles seraient perdues",
							"Tuer un precessus par son nom", 0);
			User32
					.MessageBox(
							0,
							"Starting calculator,\nCAUTION : DO NOT click OK\nif you have calc open\nwith unsaved data : they will be lost",
							"Kill a process by name", 0);
			try {
				Runtime.getRuntime().exec("calc.exe");
				Runtime.getRuntime().exec("calc.exe");
				Runtime.getRuntime().exec("calc.exe");
				Runtime.getRuntime().exec("calc.exe");
				Thread.sleep(3000);
				if (0 != new KillProcess("calc.exe", true).killProcess())
					User32.MessageBox(0, "Process not found", "Error", 0);
			} catch (IOException e) {
				e.printStackTrace();
				User32.MessageBox(0, e.getMessage(), "Error launching notepad",
						0);
			}

			Pointer p = new Pointer(MemoryBlockFactory.createMemoryBlock(500));
			p.setStringAt(0, "This is a test");
			System.err.println("Serching in >" + p.getAsString() + "<");
			System.err.println("Java    found test at offset "
					+ p.getAsString().indexOf("test"));
			System.err.println("JNative found test at offset "
					+ JNative.searchNativePattern(p, "test".getBytes(), 500));
			if (JNative.searchNativePattern(p, "toto".getBytes(), 50) < 0) {
				System.err.println("toto not found");
			}

			try {
				User32.MessageBox(0,
						Kernel32.GlobalMemoryStatusEx().toString(),
						"GlobalMemoryStatusEx", 0);
				System.out.println();
				String[] ss = JNative.getDLLFileExports(System.getenv("WINDIR")
						+ "\\system32\\user32.dll");

				String me = "";
				for (String s : ss) {
					if (s.toLowerCase().indexOf("proc") != -1)
						me += s + "\n";
				}
				User32
						.MessageBox(
								0,
								me,
								"Exported functions of User32.dll containing the word proc",
								0);
				try {
					ss = JNative.getDLLFileExports("c:/windows/twain_32.dll");
					for (String s : ss) {
						System.err.println(s);
					}
				} catch (NativeException e) {
				}
			} catch (Exception e) {
				e.printStackTrace();
				User32.MessageBox(0, "Error #" + Kernel32.GetLastError(),
						"GlobalMemoryStatusEx failed", 0);
			}

			// if(true) return;
			System.err.println(User32.MessageBox(0, Kernel32
					.GetDiskFreeSpaceEx("c:").toString(), "Free space on "
					+ Kernel32.GetComputerName(), 0x33));
			System.err.println("Module : " + JNative.getCurrentModule());
			HWND hwnd = new HWND(User32.CreateWindowEx(0, "Button", "TATA",
					WS_OVERLAPPEDWINDOW, 20, 30, 200, 300, 0, 0, JNative
							.getCurrentModule(), 0));
			/*
			 * JNative.registerWindowProc(hwnd, new WindowProc() {
			 * 
			 * /** Method windowProc
			 * 
			 * @param hwnd an int [in] Handle to the window. @param uMsg an int
			 * [in] Specifies the message. @param wParam an int [in] Specifies
			 * additional message information. The contents of this parameter
			 * depend on the value of the uMsg parameter. @param lParam an int
			 * Specifies additional message information. The contents of this
			 * parameter depend on the value of the uMsg parameter. @return an
			 * int The return value is the result of the message processing and
			 * depends on the message sent.
			 */
			/*
			 * public int windowProc(int hwnd, int uMsg, int wParam, int lParam) {
			 * System.err.println(hwnd + " " + uMsg + " " + wParam + " " +
			 * lParam); try { if (uMsg == WM.WM_CREATE.getValue()) // Initialize
			 * the window. return 0;
			 * 
			 * else if (uMsg == WM.WM_PAINT.getValue()) // Paint the window's
			 * client area. return 1;
			 * 
			 * else if (uMsg == WM.WM_SIZE.getValue()) // Set the size and
			 * position of the window. return 0;
			 * 
			 * else if (uMsg == WM.WM_DESTROY.getValue()) // Clean up
			 * window-specific data objects. return 0; // // Process other
			 * messages. //
			 * 
			 * else // return 1; return User32.defWindowProc(new HWND(hwnd), new
			 * UINT(uMsg), new WPARAM(wParam), new LPARAM(lParam)).getValue(); }
			 * catch (Exception e) { e.printStackTrace(); return 0; } }
			 * 
			 * });
			 */
			User32.ShowWindow(hwnd, SW_SHOW);
			User32.UpdateWindow(hwnd);

			MSG msg = new MSG();
			boolean lQuit = false;
			while (!lQuit) {
				switch (User32.GetMessage(msg, hwnd, 0, 0)) {
				case -1:
					System.err.println("Error occured");
					lQuit = true;
					break;
				case 0:
					System.err.println("WM_QUIT received");
					lQuit = true;
					break;
				default:
				}
				User32.TranslateMessage(msg);
				User32.DispatchMessage(msg);
			}

			System.err.println(hwnd.getValue());
			System.err.println(Kernel32.GetLastError());
			Thread.sleep(6000);

			try {
				TestCallback.runIt();
			} catch (Exception e) {
				User32.MessageBox(0, e.toString(), e.getClass().getName(), 0);
			}
		}
		System.exit(0);
	}

	public static void loadLib() throws IOException {
		final File f;
		if (JNative.isLinux()) {
			f = new File("libJNativeCpp.so");
		} else if (JNative.isWindows()) {
			f = new File("JNativeCpp.dll");
		} else {
			throw new IllegalStateException("This OS is acctually not supported, please contact jnative@free.fr if you want it supported!");
		}
		if (f.exists()) {
			JNative.getLogger().log(SEVERITY.INFO, "Loading native lib "+f.getAbsolutePath());
			System.load(f.getCanonicalPath());
		} else {
			JFileChooser jfc = new JFileChooser(new File("."));
			jfc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {

				@Override
				public boolean accept(File f) {
					return f.isDirectory() || f.getName().toLowerCase().endsWith(JNative.isWindows() ? ".dll" : ".so");
				}

				@Override
				public String getDescription() {
					return JNative.isWindows() ? "DLL : Dynamic Link Library" : " .so : Shared library";
				}
				
			});
			
			if(JFileChooser.APPROVE_OPTION == jfc.showOpenDialog(null)) {
				JNative.getLogger().log(SEVERITY.INFO, "Loading native lib "+jfc.getSelectedFile().getCanonicalPath());
				System.load(jfc.getSelectedFile().getCanonicalPath());
			} else {
				return;
			}
		}
	}

	private static void MessageBoxGnome(String text, String type, String[] buttons) throws NativeException, IllegalAccessException {
		JNative MessageBox = new JNative("/usr/lib/libgnomeui-2.so.0", "gnome_message_box_new");
		MessageBox.setRetVal(Type.INT);
		MessageBox.setParameter(0, text);
		MessageBox.setParameter(1, type /* SEE GNOME_MESSAGE_BOX_INFO*/);
		/* SEE GNOME_STOCK_BUTTON_OK*/
		int i = 2;
		for(String button : buttons) {
			MessageBox.setParameter(i++, button);
		}
		MessageBox.setParameter(i, NullPointer.NULL);
		MessageBox.invoke();
		int gtkWidget = MessageBox.getRetValAsInt();

		JNative gtk_widget_show = new JNative("/usr/lib/libgnomeui-2.so.0", "gtk_widget_show");
		gtk_widget_show.setParameter(0, gtkWidget);
		gtk_widget_show.invoke();
		MessageBox.dispose();
		gtk_widget_show.dispose();
	}
}

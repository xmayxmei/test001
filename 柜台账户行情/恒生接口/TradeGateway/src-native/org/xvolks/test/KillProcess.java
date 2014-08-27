package org.xvolks.test;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.HANDLE;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.util.Kernel32;
import org.xvolks.jnative.util.PsAPI;

/**
 * $Id: KillProcess.java,v 1.3 2006/05/27 14:58:15 mdenty Exp $
 * <p>
 * KillProcess.java
 * </p>
 *
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class KillProcess {
	
	private String name;
	private boolean ignoreCase;
	
	public KillProcess(String name, boolean ignoreCase) {
		this.name = name;
		this.ignoreCase = ignoreCase;
	}
	public int killProcess() throws IllegalAccessException, NativeException {
		int[] pids = PsAPI.EnumProcess(1024);
		int ret = 2;
		System.err.println("Nombre de process " + pids.length);
		for (int i = pids.length; i > 0;) {
			int pid = pids[--i];
			HANDLE lHandle = Kernel32.OpenProcess(Kernel32.PROCESS_QUERY_INFORMATION | Kernel32.PROCESS_VM_READ, false, pid);
			System.err.println("pid=" + pid + ", handle=" + (lHandle == null ? "null" : lHandle.getValue()));
			if (lHandle != null) {
				Pointer hModuleEtoile = PsAPI.EnumProcessModules(lHandle, 1024);
				if (hModuleEtoile.getPointer() > 0 && (ignoreCase ? name.equalsIgnoreCase(PsAPI.GetModuleBaseName(lHandle, hModuleEtoile.getAsInt(0), 1024))  : name.equals(PsAPI.GetModuleBaseName(lHandle, hModuleEtoile.getAsInt(0), 1024)))) {
					System.err.println("Process found");
					HANDLE llHandle = Kernel32.OpenProcess(Kernel32.PROCESS_TERMINATE, false, pid);
					if (llHandle != null) {
						if (Kernel32.TerminateProcess(llHandle, 1)) {
							ret = 0;
						}
					}
				}
				Kernel32.CloseHandle(lHandle);
			} else {
				Kernel32.CloseHandle(new HANDLE(0));
			}
		}
		System.err.println("Fin");
		return ret;
	}
	
	public static void main(String[] args) {
		try {
			System.exit(new KillProcess(args[0], true).killProcess());
		} catch (NativeException e) {
			e.printStackTrace();
			System.exit(128);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.exit(129);
		}
	}
}

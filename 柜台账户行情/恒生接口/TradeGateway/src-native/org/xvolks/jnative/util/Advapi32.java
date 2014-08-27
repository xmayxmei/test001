package org.xvolks.jnative.util;

import java.util.ArrayList;
import org.xvolks.jnative.JNative;
import org.xvolks.jnative.Type;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.HKEY;
import org.xvolks.jnative.misc.REGSAM;
import org.xvolks.jnative.misc.TOKEN_PRIVILEGES;
import org.xvolks.jnative.misc.registry.RegData;
import org.xvolks.jnative.misc.registry.RegKey;
import org.xvolks.jnative.misc.registry.RegQueryKey;
import org.xvolks.jnative.misc.registry.RegValue;
import org.xvolks.jnative.misc.registry.RegValueTypes;
import org.xvolks.jnative.pointers.NullPointer;
import org.xvolks.jnative.misc.basicStructures.*;
import org.xvolks.jnative.misc.registry.REGTYPE;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

/**
 *
 * $Id: Advapi32.java,v 1.28 2009/01/07 22:32:26 thubby Exp $
 *
 * This software is released under the LGPL.
 * @author Created by Marc DENTY - (c) 2006 JNative project
 */
public class Advapi32
{

    public static final String DLL_NAME = "Advapi32.dll";
    private static String mLastErrorCode = "0";
    public static final int STANDARD_RIGHTS_REQUIRED = 0xF0000;
    public static final int SC_MANAGER_CONNECT = 0x1;
    public static final int SC_MANAGER_CREATE_SERVICE = 0x2;
    public static final int SC_MANAGER_ENUMERATE_SERVICE = 0x4;
    public static final int SC_MANAGER_LOCK = 0x8;
    public static final int SC_MANAGER_MODIFY_BOOT_CONFIG = 0x20;
    public static final int SC_MANAGER_QUERY_LOCK_STATUS = 0x10;
    public static final int SC_MANAGER_ALL_ACCESS = (STANDARD_RIGHTS_REQUIRED |
            SC_MANAGER_CONNECT |
            SC_MANAGER_CREATE_SERVICE |
            SC_MANAGER_ENUMERATE_SERVICE |
            SC_MANAGER_LOCK |
            SC_MANAGER_QUERY_LOCK_STATUS |
            SC_MANAGER_MODIFY_BOOT_CONFIG);
    public static final int SERVICE_FILE_SYSTEM_DRIVER = 0x00000002; 	// File system driver service.
    public static final int SERVICE_KERNEL_DRIVER = 0x00000001;      	// Driver service.
    public static final int SERVICE_WIN32_OWN_PROCESS = 0x00000010;  	// Service that runs in its own process.
    public static final int SERVICE_WIN32_SHARE_PROCESS = 0x00000020; 	// Service that shares a process with one or more other services. For more information, see Service Programs.
    public static final int SERVICE_INTERACTIVE_PROCESS = 0x00000100; 	// The service can interact with the desktop.
    public static final int SERVICE_AUTO_START = 0x00000002; 	// A service started automatically by the service control manager during system startup. For more information, see Automatically Starting Services.
    public static final int SERVICE_BOOT_START = 0x00000000; 	// A device driver started by the system loader. This value is valid only for driver services.
    public static final int SERVICE_DEMAND_START = 0x00000003; 	// A service started by the service control manager when a process calls the StartService function. For more information, see Starting Services on Demand.
    public static final int SERVICE_DISABLED = 0x00000004; 	// A service that cannot be started. Attempts to start the service result in the error code ERROR_SERVICE_DISABLED.
    public static final int SERVICE_SYSTEM_START = 0x00000001; 	// A device driver started by the IoInitSystem function. This value is valid only for driver services. For more information, see Interactive Services.
    public static final int SERVICE_ERROR_CRITICAL = 0x00000003; 	// The startup program logs the error in the event log, if possible. If the last-known-good configuration is being started, the startup operation fails. Otherwise, the system is restarted with the last-known good configuration.
    public static final int SERVICE_ERROR_IGNORE = 0x00000000; 	// The startup program ignores the error and continues the startup operation.
    public static final int SERVICE_ERROR_NORMAL = 0x00000001; 	// The startup program logs the error in the event log but continues the startup operation.
    public static final int SERVICE_ERROR_SEVERE = 0x00000002;  // The startup program logs the error in the event log. If the last-known-good configuration is being started, the startup operation continues. Otherwise, the system is restarted with the last-known-good configuration.
    public static final int SERVICE_STOP_CUSTOM = 0x20000000; 	//The reason code is defined by the user. If this flag is not present, the reason code is defined by the system. If this flag is specified with a system reason code, the function call fails.
    // Users can create custom major reason codes in the range SERVICE_STOP_REASON_MAJOR_MIN_CUSTOM (0x00400000) through SERVICE_STOP_REASON_MAJOR_MAX_CUSTOM (0x00ff0000) and minor reason codes in the range SERVICE_STOP_REASON_MINOR_MIN_CUSTOM (0x00000100) through SERVICE_STOP_REASON_MINOR_MAX_CUSTOM (0x0000FFFF).
    public static final int SERVICE_STOP_PLANNED = 0x40000000; 	//The service stop was planned.
    public static final int SERVICE_STOP_UNPLANNED = 0x10000000; //The service stop was not planned.
    public static final int SERVICE_CONTROL_CONTINUE = 0x00000003; 	// Notifies a paused service that it should resume. The hService handle must have the SERVICE_PAUSE_CONTINUE access right.
    public static final int SERVICE_CONTROL_INTERROGATE = 0x00000004; 	//Notifies a service that it should report its current status information to the service control manager. The hService handle must have the SERVICE_INTERROGATE access right.
    // Note that this control is not generally useful as the SCM is aware of the current state of the service.
    public static final int SERVICE_CONTROL_NETBINDADD = 0x00000007;	// Notifies a network service that there is a new component for binding. The hService handle must have the SERVICE_PAUSE_CONTINUE access right. However, this control code has been deprecated; use Plug and Play functionality instead.
    public static final int SERVICE_CONTROL_NETBINDDISABLE = 0x0000000A; 	// Notifies a network service that one of its bindings has been disabled. The hService handle must have the SERVICE_PAUSE_CONTINUE access right. However, this control code has been deprecated; use Plug and Play functionality instead.
    public static final int SERVICE_CONTROL_NETBINDENABLE = 0x00000009; 	// Notifies a network service that a disabled binding has been enabled. The hService handle must have the SERVICE_PAUSE_CONTINUE access right. However, this control code has been deprecated; use Plug and Play functionality instead.
    public static final int SERVICE_CONTROL_NETBINDREMOVE = 0x00000008; 	// Notifies a network service that a component for binding has been removed. The hService handle must have the SERVICE_PAUSE_CONTINUE access right. However, this control code has been deprecated; use Plug and Play functionality instead.
    public static final int SERVICE_CONTROL_PARAMCHANGE = 0x00000006; 	// Notifies a service that its startup parameters have changed. The hService handle must have the SERVICE_PAUSE_CONTINUE access right.
    public static final int SERVICE_CONTROL_PAUSE = 0x00000002; 	// Notifies a service that it should pause. The hService handle must have the SERVICE_PAUSE_CONTINUE access right.
    public static final int SERVICE_CONTROL_STOP = 0x00000001; 	// Notifies a service that it should stop. The hService handle must have the SERVICE_STOP access right.
    //  After sending the stop request to a service, you should not send other controls to the service.
    // Service object specific access type
    public static final int SERVICE_QUERY_CONFIG = 0x1;
    public static final int SERVICE_CHANGE_CONFIG = 0x2;
    public static final int SERVICE_QUERY_STATUS = 0x4;
    public static final int SERVICE_ENUMERATE_DEPENDENTS = 0x8;
    public static final int SERVICE_START = 0x10;
    public static final int SERVICE_STOP = 0x20;
    public static final int SERVICE_PAUSE_CONTINUE = 0x40;
    public static final int SERVICE_INTERROGATE = 0x80;
    public static final int SERVICE_USER_DEFINED_CONTROL = 0x100;
    public static final int SERVICE_ALL_ACCESS = (STANDARD_RIGHTS_REQUIRED |
            SERVICE_QUERY_CONFIG |
            SERVICE_CHANGE_CONFIG |
            SERVICE_QUERY_STATUS |
            SERVICE_ENUMERATE_DEPENDENTS |
            SERVICE_START |
            SERVICE_STOP |
            SERVICE_PAUSE_CONTINUE |
            SERVICE_INTERROGATE |
            SERVICE_USER_DEFINED_CONTROL);

    /*
    BOOL WINAPI StartService(
    SC_HANDLE hService,
    DWORD dwNumServiceArgs,
    LPCTSTR* lpServiceArgVectors
    );
     */
    public static boolean StartService(int hService,
            int dwNumServiceArgs,
            String[] lpServiceArgVectors) throws NativeException, IllegalAccessException
    {

        JNative StartService = new JNative(DLL_NAME, "StartServiceA");
        Pointer arrayOfLPCSTR = null;
        ArrayList<Pointer> savedPointers = new ArrayList<Pointer>();
        try
        {
            StartService.setRetVal(Type.INT);

            int i = 0;
            StartService.setParameter(i++, hService);
            StartService.setParameter(i++, dwNumServiceArgs);
            if (lpServiceArgVectors != null)
            {
                arrayOfLPCSTR = new Pointer(MemoryBlockFactory.createMemoryBlock(lpServiceArgVectors.length * 4));
                int j = 0;
                for (String s : lpServiceArgVectors)
                {
                    final Pointer p = new Pointer(MemoryBlockFactory.createMemoryBlock(s.length() + 1));
                    p.setMemory(s);
                    arrayOfLPCSTR.setIntAt(j, p.getPointer());
                    j += 4;
                    savedPointers.add(p);
                }
                StartService.setParameter(i++, arrayOfLPCSTR);
            }
            else
            {
                StartService.setParameter(i++, 0);
            }
            StartService.invoke();

            return (StartService.getRetValAsInt() != 0);
        }
        finally
        {
            for (Pointer p : savedPointers)
            {
                if (p != null)
                {
                    p.dispose();
                }
            }
            if (arrayOfLPCSTR != null)
            {
                arrayOfLPCSTR.dispose();
            }
        }
    }
    /*
    BOOL WINAPI ControlService(
    SC_HANDLE hService,
    DWORD dwControl,
    LPSERVICE_STATUS lpServiceStatus
    );
     */

    public static boolean ControlService(int hService,
            DWORD dwControl,
            Pointer pControlParams) throws NativeException, IllegalAccessException
    {

        JNative ControlService = new JNative(DLL_NAME, "ControlService");

        ControlService.setRetVal(Type.INT);

        int i = 0;
        ControlService.setParameter(i++, hService);
        ControlService.setParameter(i++, dwControl.getValue());
        ControlService.setParameter(i++, pControlParams);
        ControlService.invoke();

        return (ControlService.getRetValAsInt() != 0);

    }

    /*
    BOOL WINAPI UnlockServiceDatabase(
    SC_LOCK ScLock
    );
     */
    public static boolean UnlockServiceDatabase(int ScLock) throws NativeException, IllegalAccessException
    {
        JNative UnlockServiceDatabase = new JNative(DLL_NAME, "UnlockServiceDatabase");
        UnlockServiceDatabase.setRetVal(Type.INT);

        int i = 0;
        UnlockServiceDatabase.setParameter(i++, ScLock);
        UnlockServiceDatabase.invoke();

        return (UnlockServiceDatabase.getRetValAsInt() != 0);
    }

    /*
    SC_LOCK WINAPI LockServiceDatabase(
    SC_HANDLE hSCManager
    )
     */
    public static int LockServiceDatabase(int hSCManager) throws NativeException, IllegalAccessException
    {
        JNative LockServiceDatabase = new JNative(DLL_NAME, "LockServiceDatabase");
        LockServiceDatabase.setRetVal(Type.INT);

        int i = 0;
        LockServiceDatabase.setParameter(i++, hSCManager);
        LockServiceDatabase.invoke();

        return LockServiceDatabase.getRetValAsInt();

    }

    /*
    BOOL WINAPI ChangeServiceConfig(
    SC_HANDLE hService,
    DWORD dwServiceType,
    DWORD dwStartType,
    DWORD dwErrorControl,
    LPCTSTR lpBinaryPathName,
    LPCTSTR lpLoadOrderGroup,
    LPDWORD lpdwTagId,
    LPCTSTR lpDependencies,
    LPCTSTR lpServiceStartName,
    LPCTSTR lpPassword,
    LPCTSTR lpDisplayName
    );
     */
    public static boolean ChangeServiceConfig(int hService,
            DWORD dwServiceType,
            DWORD dwStartType,
            DWORD dwErrorControl,
            String lpBinaryPathName,
            String lpLoadOrderGroup,
            DWORD lpdwTagId,
            String lpDependencies,
            String lpServiceStartName,
            String lpPassword,
            String lpDisplayName) throws NativeException, IllegalAccessException
    {

        JNative ChangeServiceConfig = new JNative(DLL_NAME, "ChangeServiceConfigA");

        ChangeServiceConfig.setRetVal(Type.INT);

        int i = 0;
        ChangeServiceConfig.setParameter(i++, hService);
        ChangeServiceConfig.setParameter(i++, dwServiceType.getValue());
        ChangeServiceConfig.setParameter(i++, dwStartType.getValue());
        ChangeServiceConfig.setParameter(i++, dwErrorControl.getValue());
        ChangeServiceConfig.setParameter(i++, lpBinaryPathName);
        ChangeServiceConfig.setParameter(i++, lpLoadOrderGroup);
        ChangeServiceConfig.setParameter(i++, lpdwTagId.getValue());
        ChangeServiceConfig.setParameter(i++, lpDependencies);
        ChangeServiceConfig.setParameter(i++, lpServiceStartName);
        ChangeServiceConfig.setParameter(i++, lpPassword);
        ChangeServiceConfig.setParameter(i++, lpDisplayName);
        ChangeServiceConfig.invoke();

        return (ChangeServiceConfig.getRetValAsInt() != 0);
    }

    /*
    SC_HANDLE WINAPI OpenService(
    SC_HANDLE hSCManager,
    LPCTSTR lpServiceName,
    DWORD dwDesiredAccess
    );
     */
    public static int OpenService(int hSCManager,
            String lpServiceName,
            DWORD dwDesiredAccess) throws NativeException, IllegalAccessException
    {
        JNative OpenService = new JNative(DLL_NAME, "OpenServiceA");
        OpenService.setRetVal(Type.INT);

        int i = 0;
        OpenService.setParameter(i++, hSCManager);
        OpenService.setParameter(i++, lpServiceName);
        OpenService.setParameter(i++, dwDesiredAccess.getValue());
        OpenService.invoke();

        return OpenService.getRetValAsInt();
    }

    /*
    BOOL WINAPI DeleteService(
    SC_HANDLE hService
    );
     */
    public static boolean DeleteService(int hSCObject) throws NativeException, IllegalAccessException
    {
        JNative DeleteService = new JNative(DLL_NAME, "DeleteService");

        DeleteService.setRetVal(Type.INT);

        int i = 0;
        DeleteService.setParameter(i++, hSCObject);
        DeleteService.invoke();

        return (DeleteService.getRetValAsInt() != 0);

    }
    /*
    BOOL WINAPI CloseServiceHandle(
    SC_HANDLE hSCObject
    );
     */

    public static boolean CloseServiceHandle(int hSCObject) throws NativeException, IllegalAccessException
    {
        JNative CloseServiceHandle = new JNative(DLL_NAME, "CloseServiceHandle");

        CloseServiceHandle.setRetVal(Type.INT);

        int i = 0;
        CloseServiceHandle.setParameter(i++, hSCObject);
        CloseServiceHandle.invoke();

        return (CloseServiceHandle.getRetValAsInt() != 0);
    }
    /*
    SC_HANDLE WINAPI OpenSCManager(
    LPCTSTR lpMachineName,
    LPCTSTR lpDatabaseName,
    DWORD dwDesiredAccess
    );
     */

    public static int OpenSCManager(String lpMachineName,
            String lpDatabaseName,
            DWORD dwDesiredAccess) throws NativeException, IllegalAccessException
    {
        JNative OpenSCManager = new JNative(DLL_NAME, "OpenSCManagerA");

        OpenSCManager.setRetVal(Type.INT);

        int i = 0;
        OpenSCManager.setParameter(i++, lpMachineName);
        OpenSCManager.setParameter(i++, lpDatabaseName);
        OpenSCManager.setParameter(i++, dwDesiredAccess.getValue());
        OpenSCManager.invoke();

        return OpenSCManager.getRetValAsInt();

    }
    /*
    SC_HANDLE WINAPI CreateService(
    SC_HANDLE hSCManager,
    LPCTSTR lpServiceName,
    LPCTSTR lpDisplayName,
    DWORD dwDesiredAccess,
    DWORD dwServiceType,
    DWORD dwStartType,
    DWORD dwErrorControl,
    LPCTSTR lpBinaryPathName,
    LPCTSTR lpLoadOrderGroup,
    LPDWORD lpdwTagId,
    LPCTSTR lpDependencies,
    LPCTSTR lpServiceStartName,
    LPCTSTR lpPassword
    );
     */

    public static int CreateService(int hSCManager,
            String lpServiceName,
            String lpDisplayName,
            DWORD dwDesiredAccess,
            DWORD dwServiceType,
            DWORD dwStartType,
            DWORD dwErrorControl,
            String lpBinaryPathName,
            String lpLoadOrderGroup,
            DWORD lpdwTagId,
            String lpDependencies,
            String lpServiceStartName,
            String lpPassword) throws NativeException, IllegalAccessException
    {

        JNative CreateService = new JNative(DLL_NAME, "CreateServiceA");

        CreateService.setRetVal(Type.INT);

        int i = 0;
        CreateService.setParameter(i++, hSCManager);
        CreateService.setParameter(i++, lpServiceName);
        CreateService.setParameter(i++, lpDisplayName);
        CreateService.setParameter(i++, dwDesiredAccess.getValue());
        CreateService.setParameter(i++, dwServiceType.getValue());
        CreateService.setParameter(i++, dwStartType.getValue());
        CreateService.setParameter(i++, dwErrorControl.getValue());
        CreateService.setParameter(i++, lpBinaryPathName);
        CreateService.setParameter(i++, lpLoadOrderGroup);
        CreateService.setParameter(i++, lpdwTagId.getValue());
        CreateService.setParameter(i++, lpDependencies);
        CreateService.setParameter(i++, lpServiceStartName);
        CreateService.setParameter(i++, lpPassword);
        CreateService.invoke();

        return CreateService.getRetValAsInt();
    }

    /*
    HANDLE TokenHandle,
    BOOL DisableAllPrivileges,
    PTOKEN_PRIVILEGES NewState,
    DWORD BufferLength,
    PTOKEN_PRIVILEGES PreviousState,
    PDWORD ReturnLength
     */
    public static boolean AdjustTokenPrivileges(HANDLE TokenHandle,
            boolean DisableAllPrivileges,
            TOKEN_PRIVILEGES NewState) throws NativeException, IllegalAccessException
    {
        JNative nAdjustTokenPrivileges = new JNative(DLL_NAME, "AdjustTokenPrivileges");
        nAdjustTokenPrivileges.setRetVal(Type.INT);

        int i = 0;
        nAdjustTokenPrivileges.setParameter(i++, TokenHandle.getValue());
        nAdjustTokenPrivileges.setParameter(i++, DisableAllPrivileges ? -1 : 0);
        nAdjustTokenPrivileges.setParameter(i++, NewState.getPointer());
        nAdjustTokenPrivileges.setParameter(i++, 0);
        nAdjustTokenPrivileges.setParameter(i++, NullPointer.NULL);
        nAdjustTokenPrivileges.setParameter(i++, 0);
        nAdjustTokenPrivileges.invoke();

        return nAdjustTokenPrivileges.getRetValAsInt() != 0;
    }

    /*
    LPCTSTR lpSystemName,
    LPCTSTR lpName,
    PLUID lpLuid
     */
    public static boolean LookupPrivilegeValue(String lpSystemName, String lpName, LONG lpLuid) throws NativeException, IllegalAccessException
    {
        JNative nLookupPrivilegeValue = new JNative(DLL_NAME, "LookupPrivilegeValueA"); // Use ANSI
        nLookupPrivilegeValue.setRetVal(Type.INT);


        int i = 0;
        nLookupPrivilegeValue.setParameter(i++, lpSystemName);
        nLookupPrivilegeValue.setParameter(i++, lpName);
        nLookupPrivilegeValue.setParameter(i++, lpLuid.getPointer());
        nLookupPrivilegeValue.invoke();

        return nLookupPrivilegeValue.getRetValAsInt() != 0;
    }

    /*
    HANDLE ProcessHandle,
    DWORD DesiredAccess,
    PHANDLE TokenHandle
     */
    public static boolean OpenProcessToken(HANDLE ProcessHandle, int DesiredAccess, HANDLE TokenHandle) throws NativeException, IllegalAccessException
    {
        JNative nOpenProcessToken = new JNative(DLL_NAME, "OpenProcessToken");
        nOpenProcessToken.setRetVal(Type.INT);

        int i = 0;
        nOpenProcessToken.setParameter(i++, ProcessHandle.getValue());
        nOpenProcessToken.setParameter(i++, DesiredAccess);
        nOpenProcessToken.setParameter(i++, TokenHandle.getPointer());
        nOpenProcessToken.invoke();

        return nOpenProcessToken.getRetValAsInt() != 0;
    }

    public static boolean RegDeleteValue(HKEY hKey, String lpSubKey) throws NativeException, IllegalAccessException
    {
        JNative nRegDeleteValue = new JNative(DLL_NAME, "RegDeleteValueA");
        nRegDeleteValue.setRetVal(Type.INT);

        int i = 0;
        nRegDeleteValue.setParameter(i++, hKey.getValue());
        nRegDeleteValue.setParameter(i++, Type.STRING, lpSubKey);
        nRegDeleteValue.invoke();

        return nRegDeleteValue.getRetValAsInt() != 0;
    }

    public static boolean RegDeleteKey(HKEY hKey,
            String lpSubKey)
            throws NativeException, IllegalAccessException
    {
        JNative nRegDeleteKey = new JNative(DLL_NAME, "RegDeleteKeyA");
        nRegDeleteKey.setRetVal(Type.INT);

        int i = 0;
        nRegDeleteKey.setParameter(i++, hKey.getValue());
        nRegDeleteKey.setParameter(i++, Type.STRING, lpSubKey);
        nRegDeleteKey.invoke();

        return nRegDeleteKey.getRetValAsInt() != 0;
    }

    /*
    HKEY hKey,
    LPCTSTR lpValueName,
    DWORD Reserved,
    DWORD dwType,
    const BYTE* lpData,
    DWORD cbData
     */
    public static boolean RegSetValueEx(HKEY hKey, String lpValueName, String value, REGTYPE type) throws NativeException, IllegalAccessException
    {
        JNative nRegSetValueExA = new JNative(DLL_NAME, "RegSetValueExA");
        nRegSetValueExA.setRetVal(Type.INT);

        int i = 0;
        nRegSetValueExA.setParameter(i++, hKey.getValue());
        nRegSetValueExA.setParameter(i++, Type.STRING, lpValueName);
        nRegSetValueExA.setParameter(i++, 0);
        nRegSetValueExA.setParameter(i++, type.getValue());
        if (type.equals(REGTYPE.REG_DWORD))
        {
            nRegSetValueExA.setParameter(i++, Type.STRING, StructConverter.getBytes(Integer.parseInt(value)));
            nRegSetValueExA.setParameter(i++, type.getSize());
        }
        else
        {
            nRegSetValueExA.setParameter(i++, Type.STRING, value);
            nRegSetValueExA.setParameter(i++, value.length());
        }

        nRegSetValueExA.invoke();

        mLastErrorCode = nRegSetValueExA.getRetVal();

        if ("0".equals(mLastErrorCode))
        {
            return true;
        }
        return false;
    }

    /*
    HKEY hKey,
    LPCTSTR lpSubKey,
    DWORD Reserved,
    LPTSTR lpClass,
    DWORD dwOptions,
    REGSAM samDesired,
    LPSECURITY_ATTRIBUTES lpSecurityAttributes,
    PHKEY phkResult,
    LPDWORD lpdwDisposition
     */
    public static HKEY RegCreateKeyEx(HKEY hKey,
            String lpSubKey,
            REGSAM samDesired) throws NativeException, IllegalAccessException
    {
        JNative nRegCreateKeyEx = new JNative(DLL_NAME, "RegCreateKeyExA");
        nRegCreateKeyEx.setRetVal(Type.INT);

        HKEY phkResult = new HKEY(0);
        int i = 0;
        nRegCreateKeyEx.setParameter(i++, hKey.getValue());
        nRegCreateKeyEx.setParameter(i++, Type.STRING, lpSubKey);
        nRegCreateKeyEx.setParameter(i++, 0);
        nRegCreateKeyEx.setParameter(i++, 0);
        nRegCreateKeyEx.setParameter(i++, 0);
        nRegCreateKeyEx.setParameter(i++, samDesired.getValue());
        nRegCreateKeyEx.setParameter(i++, 0);
        nRegCreateKeyEx.setParameter(i++, phkResult.getPointer());
        nRegCreateKeyEx.setParameter(i++, 0);
        nRegCreateKeyEx.invoke();

        mLastErrorCode = nRegCreateKeyEx.getRetVal();
        if ("0".equals(mLastErrorCode))
        {
            return phkResult;
        }
        return null;
    }

    public static boolean doesKeyExist(HKEY base, String regKey) throws NativeException, IllegalAccessException
    {
        HKEY key = null;
        try
        {
            key = Advapi32.RegOpenKeyEx(base, regKey, REGSAM.KEY_READ);
            if (key != null)
            {
                return true;
            }
        }
        finally
        {
            closeKey(key);
        }
        return false;
    }

    public static void closeKey(HKEY key)
    {
        try
        {
            if (key != null)
            {
                Advapi32.RegCloseKey(key);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static int getLastErrorCode()
    {
        try
        {
            return Integer.parseInt(mLastErrorCode);
        }
        catch (Throwable e)
        {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * <pre>
     *        	 RegOpenKeyEx
     *
     *        	 Opens the specified registry key. Note that key names are not case sensitive.
     *
     *        	 LONG RegOpenKeyEx(
     *        	 HKEY hKey,
     *        	 LPCTSTR lpSubKey,
     *        	 DWORD ulOptions,
     *        	 REGSAM samDesired,
     *        	 PHKEY phkResult
     *        	 );
     *
     *        	 Parameters
     *
     *        	 hKey
     *        	 [in] A handle to an open registry key. This handle is returned by the RegCreateKeyEx or RegOpenKeyEx function, or it can be one of the following predefined keys:
     *
     *        	 HKEY_CLASSES_ROOT
     *        	 HKEY_CURRENT_USER
     *        	 HKEY_LOCAL_MACHINE
     *        	 HKEY_USERS
     *
     *        	 Windows Me/98/95:  This parameter can also be the following key:
     *
     *        	 HKEY_DYN_DATA
     *
     *        	 lpSubKey
     *        	 [in] The name of the registry subkey to be opened.
     *
     *        	 Key names are not case sensitive.
     *
     *        	 If this parameter is NULL or a pointer to an empty string, the function will open a new handle to the key identified by the hKey parameter.
     *
     *        	 For more information, see Registry Element Size Limits.
     *        	 ulOptions
     *        	 This parameter is reserved and must be zero.
     *        	 samDesired
     *        	 [in] A mask that specifies the desired access rights to the key. The function fails if the security descriptor of the key does not permit the requested access for the calling process. For more information, see Registry Key Security and Access Rights.
     *        	 phkResult
     *        	 [out] A pointer to a variable that receives a handle to the opened key. If the key is not one of the predefined registry keys, call the RegCloseKey function after you have finished using the handle.
     *
     *        	 Return Values
     *
     *        	 If the function succeeds, the return value is ERROR_SUCCESS.
     *
     *        	 If the function fails, the return value is a nonzero error code defined in Winerror.h. You can use the FormatMessage function with the FORMAT_MESSAGE_FROM_SYSTEM flag to get a generic description of the error.
     *        	 Remarks
     *
     *        	 Unlike the RegCreateKeyEx function, the RegOpenKeyEx function does not create the specified key if the key does not exist in the registry.
     *
     *        	 If your service or application impersonates different users, do not use this function with HKEY_CURRENT_USER. Instead, call the RegOpenCurrentUser function.
     *
     *        	 A single registry key can be opened only 65534 times. When attempting the 65535th open operation, this function fails with ERROR_NO_SYSTEM_RESOURCES.
     * </pre>
     *
     * @throws NativeException
     * @throws IllegalAccessException
     */
    public static HKEY RegOpenKeyEx(HKEY key, String lpSubKey, REGSAM samDesired)
            throws NativeException, IllegalAccessException
    {
        JNative nRegOpenKeyEx = new JNative(DLL_NAME, "RegOpenKeyExA");
        nRegOpenKeyEx.setRetVal(Type.INT);

        HKEY phkResult = new HKEY(0);
        nRegOpenKeyEx.setParameter(0, key.getValue());
        nRegOpenKeyEx.setParameter(1, Type.STRING, lpSubKey);
        nRegOpenKeyEx.setParameter(2, 0);
        nRegOpenKeyEx.setParameter(3, samDesired.getValue());
        nRegOpenKeyEx.setParameter(4, phkResult.getPointer());
        nRegOpenKeyEx.invoke();

        mLastErrorCode = nRegOpenKeyEx.getRetVal();
        if ("0".equals(mLastErrorCode))
        {
            return phkResult;
        }
        else
        {
            return null;
        }
    }

    /**
     * <pre>
     *         RegCloseKey
     *
     *        	 Closes a handle to the specified registry key.
     *
     *        	 LONG RegCloseKey(
     *        	 HKEY hKey
     *        	 );
     *
     *        	 Parameters
     *
     *        	 hKey
     *        	 [in] A handle to the open key to be closed. The handle must have been opened by the RegCreateKeyEx, RegOpenKeyEx, or RegConnectRegistry function.
     *
     *        	 Return Values
     *
     *        	 If the function succeeds, the return value is ERROR_SUCCESS.
     *
     *        	 If the function fails, the return value is a nonzero error code defined in Winerror.h. You can use the FormatMessage function with the FORMAT_MESSAGE_FROM_SYSTEM flag to get a generic description of the error.
     *        	 Remarks
     *
     *        	 The handle for a specified key should not be used after it has been closed, because it will no longer be valid. Key handles should not be left open any longer than necessary.
     *
     *        	 The RegCloseKey function does not necessarily write information to the registry before returning; it can take as much as several seconds for the cache to be flushed to the hard disk. If an application must explicitly write registry information to the hard disk, it can use the RegFlushKey function. RegFlushKey, however, uses many system resources and should be called only when necessary.
     *
     * </pre>
     *
     * @throws NativeException
     * @throws IllegalAccessException
     */
    public static boolean RegCloseKey(HKEY hKey) throws NativeException,
            IllegalAccessException
    {
        if (hKey != null)
        {
            JNative nRegCloseKey = new JNative(DLL_NAME, "RegCloseKey");
            nRegCloseKey.setRetVal(Type.INT);

            nRegCloseKey.setParameter(0, hKey.getValue());
            nRegCloseKey.invoke();
            mLastErrorCode = nRegCloseKey.getRetVal();
            return "0".equals(mLastErrorCode);
        }
        return false;
    }

    /**
     * <pre>
     *         RegEnumKeyEx
     *
     *        	 Enumerates the subkeys of the specified open registry key. The function retrieves information about one subkey each time it is called.
     *
     *        	 LONG RegEnumKeyEx(
     *        	 HKEY hKey,
     *        	 DWORD dwIndex,
     *        	 LPTSTR lpName,
     *        	 LPDWORD lpcName,
     *        	 LPDWORD lpReserved,
     *        	 LPTSTR lpClass,
     *        	 LPDWORD lpcClass,
     *        	 PFILETIME lpftLastWriteTime
     *        	 );
     *
     *        	 Parameters
     *
     *        	 hKey
     *        	 [in] A handle to an open registry key. The key must have been opened with the KEY_ENUMERATE_SUB_KEYS access right. For more information, see Registry Key Security and Access Rights.
     *
     *        	 This handle is returned by the RegCreateKeyEx or RegOpenKeyEx function, or it can be one of the following predefined keys:
     *
     *        	 HKEY_CLASSES_ROOT
     *        	 HKEY_CURRENT_CONFIG
     *        	 HKEY_CURRENT_USER
     *        	 HKEY_LOCAL_MACHINE
     *        	 HKEY_PERFORMANCE_DATA
     *        	 HKEY_USERS
     *
     *        	 Windows Me/98/95:  This parameter can also be the following value:
     *
     *        	 HKEY_DYN_DATA
     *
     *        	 dwIndex
     *        	 [in] The index of the subkey to retrieve. This parameter should be zero for the first call to the RegEnumKeyEx function and then incremented for subsequent calls.
     *
     *        	 Because subkeys are not ordered, any new subkey will have an arbitrary index. This means that the function may return subkeys in any order.
     *        	 lpName
     *        	 [out] A pointer to a buffer that receives the name of the subkey, including the terminating null character. The function copies only the name of the subkey, not the full key hierarchy, to the buffer.
     *
     *        	 For more information, see Registry Element Size Limits.
     *        	 lpcName
     *        	 [in, out] A pointer to a variable that specifies the size of the buffer specified by the lpName parameter, in TCHARs. This size should include the terminating null character. When the function returns, the variable pointed to by lpcName contains the number of characters stored in the buffer. The count returned does not include the terminating null character.
     *        	 lpReserved
     *        	 This parameter is reserved and must be NULL.
     *        	 lpClass
     *        	 [in, out] A pointer to a buffer that receives the null-terminated class string of the enumerated subkey. This parameter can be NULL.
     *        	 lpcClass
     *        	 [in, out] A pointer to a variable that specifies the size of the buffer specified by the lpClass parameter, in TCHARs. The size should include the terminating null character. When the function returns, lpcClass contains the number of characters stored in the buffer. The count returned does not include the terminating null character. This parameter can be NULL only if lpClass is NULL.
     *        	 lpftLastWriteTime
     *        	 [out] A pointer to a variable that receives the time at which the enumerated subkey was last written. This parameter can be NULL.
     *
     *        	 Return Values
     *
     *        	 If the function succeeds, the return value is ERROR_SUCCESS.
     *
     *        	 If the function fails, the return value is a system error code. If there are no more subkeys available, the function returns ERROR_NO_MORE_ITEMS.
     *
     *        	 If the lpName buffer is too small to receive the name of the key, the function returns ERROR_MORE_DATA.
     *        	 Remarks
     *
     *        	 To enumerate subkeys, an application should initially call the RegEnumKeyEx function with the dwIndex parameter set to zero. The application should then increment the dwIndex parameter and call RegEnumKeyEx until there are no more subkeys (meaning the function returns ERROR_NO_MORE_ITEMS).
     *
     *        	 The application can also set dwIndex to the index of the last subkey on the first call to the function and decrement the index until the subkey with the index 0 is enumerated. To retrieve the index of the last subkey, use the RegQueryInfoKey function.
     *
     *        	 While an application is using the RegEnumKeyEx function, it should not make calls to any registration functions that might change the key being enumerated.
     *
     * </pre>
     *
     * @param hKey
     * @param dwIndex
     * @param lKey
     * @return
     * @throws NativeException
     * @throws IllegalAccessException
     */
    public static RegKey RegEnumKeyEx(HKEY hKey, int dwIndex, RegKey lKey)
            throws NativeException, IllegalAccessException
    {
        if (lKey == null)
        {
            lKey = new RegKey(255, 1024);
        }
        JNative nRegEnumKey = new JNative(DLL_NAME, "RegEnumKeyExA");
        nRegEnumKey.setRetVal(Type.INT);

        int cur = 0;
        nRegEnumKey.setParameter(cur++, hKey.getValue());
        nRegEnumKey.setParameter(cur++, dwIndex);
        nRegEnumKey.setParameter(cur++, lKey.getLpValueName());
        nRegEnumKey.setParameter(cur++, lKey.getLpcValueName().getPointer());
        nRegEnumKey.setParameter(cur++, NullPointer.NULL);
        nRegEnumKey.setParameter(cur++, lKey.getLpData());
        nRegEnumKey.setParameter(cur++, lKey.getLpcbData().getPointer());
        nRegEnumKey.setParameter(cur++, lKey.getLpLastWriteTime().getPointer());
        nRegEnumKey.invoke();
        mLastErrorCode = nRegEnumKey.getRetVal();
        lKey.setErrorCode(getLastErrorCode());
        return lKey;

    }

    /**
     *
     * <pre>
     *       	 RegQueryInfoKey
     *
     *       	 Retrieves information about the specified registry key.
     *
     *       	 LONG RegQueryInfoKey(
     *       	 HKEY hKey,
     *       	 LPTSTR lpClass,
     *       	 LPDWORD lpcClass,
     *       	 LPDWORD lpReserved,
     *       	 LPDWORD lpcSubKeys,
     *       	 LPDWORD lpcMaxSubKeyLen,
     *       	 LPDWORD lpcMaxClassLen,
     *       	 LPDWORD lpcValues,
     *       	 LPDWORD lpcMaxValueNameLen,
     *       	 LPDWORD lpcMaxValueLen,
     *       	 LPDWORD lpcbSecurityDescriptor,
     *       	 PFILETIME lpftLastWriteTime
     *       	 );
     *
     *       	 Parameters
     *
     *       	 hKey
     *       	 [in] A handle to an open registry key. The key must have been opened with the KEY_QUERY_VALUE access right. For more information, see Registry Key Security and Access Rights.
     *
     *       	 This handle is returned by the RegCreateKeyEx or RegOpenKeyEx function, or it can be one of the following Predefined Keys:
     *
     *
     *       	 HKEY_CLASSES_ROOT
     *       	 HKEY_CURRENT_CONFIG
     *       	 HKEY_CURRENT_USER
     *       	 HKEY_LOCAL_MACHINE
     *       	 HKEY_PERFORMANCE_DATA
     *       	 HKEY_USERS
     *
     *       	 Windows Me/98/95:  This parameter can also be the following key:
     *
     *       	 HKEY_DYN_DATA
     *
     *       	 lpClass
     *       	 [out] A pointer to a buffer that receives the key class. This parameter can be NULL.
     *       	 lpcClass
     *       	 [in, out] A pointer to a variable that specifies the size of the buffer pointed to by the lpClass parameter, in characters.
     *
     *       	 The size should include the terminating null character. When the function returns, this variable contains the size of the class string that is stored in the buffer. The count returned does not include the terminating null character. If the buffer is not big enough, the function returns ERROR_MORE_DATA, and the variable contains the size of the string, in characters, without counting the terminating null character.
     *
     *       	 If lpClass is NULL, lpcClass can be NULL.
     *
     *       	 If the lpClass parameter is a valid address, but the lpcClass parameter is not, for example, it is NULL, then the function returns ERROR_INVALID_PARAMETER.
     *
     *       	 Windows Me/98/95:  If the lpClass parameter is a valid address, but the lpcClass parameter is not, for example, it is NULL, then the function returns ERROR_SUCCESS instead of ERROR_INVALID_PARAMETER. To ensure compatibility with other platforms, verify that lpcClass is valid before calling the function.
     *
     *       	 lpReserved
     *       	 This parameter is reserved and must be NULL.
     *       	 lpcSubKeys
     *       	 [out] A pointer to a variable that receives the number of subkeys that are contained by the specified key. This parameter can be NULL.
     *       	 lpcMaxSubKeyLen
     *       	 [out] A pointer to a variable that receives the size of the key's subkey with the longest name, in characters, not including the terminating null character. This parameter can be NULL.
     *
     *       	 Windows Me/98/95:  The size includes the terminating null character.
     *
     *       	 lpcMaxClassLen
     *       	 [out] A pointer to a variable that receives the size of the longest string that specifies a subkey class, in characters. The count returned does not include the terminating null character. This parameter can be NULL.
     *       	 lpcValues
     *       	 [out] A pointer to a variable that receives the number of values that are associated with the key. This parameter can be NULL.
     *       	 lpcMaxValueNameLen
     *       	 [out] A pointer to a variable that receives the size of the key's longest value name, in characters. The size does not include the terminating null character. This parameter can be NULL.
     *       	 lpcMaxValueLen
     *       	 [out] A pointer to a variable that receives the size of the longest data component among the key's values, in bytes. This parameter can be NULL.
     *       	 lpcbSecurityDescriptor
     *       	 [out] A pointer to a variable that receives the size of the key's security descriptor, in bytes. This parameter can be NULL.
     *       	 lpftLastWriteTime
     *       	 [out] A pointer to a FILETIME structure that receives the last write time. This parameter can be NULL.
     *
     *       	 The function sets the members of the FILETIME structure to indicate the last time that the key or any of its value entries is modified.
     *
     *       	 Windows Me/98/95:  The function sets the members of the FILETIME structure to 0 (zero), because the system does not keep track of registry key last write time information.
     *
     *       	 Return Values
     *
     *       	 If the function succeeds, the return value is ERROR_SUCCESS.
     *
     *       	 If the function fails, the return value is a nonzero error code defined in Winerror.h. You can use the FormatMessage function with the FORMAT_MESSAGE_FROM_SYSTEM flag to get a generic description of the error.
     *
     * </pre>
     *
     * @throws NativeException
     * @throws IllegalAccessException
     */
    public static RegQueryKey RegQueryInfoKey(HKEY hKey, RegQueryKey lKey)
            throws NativeException, IllegalAccessException
    {
        if (lKey == null)
        {
            lKey = new RegQueryKey(1024);
        }

        JNative nRegQueryInfoKey = new JNative(DLL_NAME, "RegQueryInfoKeyA");
        nRegQueryInfoKey.setRetVal(Type.INT);

        int cur = 0;
        nRegQueryInfoKey.setParameter(cur++, hKey.getValue());
        nRegQueryInfoKey.setParameter(cur++, lKey.getLpData());
        nRegQueryInfoKey.setParameter(cur++, lKey.getLpcbData().getPointer());
        nRegQueryInfoKey.setParameter(cur++, NullPointer.NULL);
        nRegQueryInfoKey.setParameter(cur++, lKey.getLpcSubKeys().getPointer());
        nRegQueryInfoKey.setParameter(cur++, lKey.getLpcMaxSubKeyLen().getPointer());
        nRegQueryInfoKey.setParameter(cur++, lKey.getLpcMaxClassLen().getPointer());
        nRegQueryInfoKey.setParameter(cur++, lKey.getLpcValues().getPointer());
        nRegQueryInfoKey.setParameter(cur++, lKey.getLpcMaxValueNameLen().getPointer());
        nRegQueryInfoKey.setParameter(cur++, lKey.getLpcMaxValueLen().getPointer());
        nRegQueryInfoKey.setParameter(cur++, lKey.getLpcbSecurityDescriptor().getPointer());
        nRegQueryInfoKey.setParameter(cur++, lKey.getLpLastWriteTime().getPointer());

        nRegQueryInfoKey.invoke();
        mLastErrorCode = nRegQueryInfoKey.getRetVal();
        lKey.setErrorCode(getLastErrorCode());
        return lKey;
    }

    /**
     * <b>RegQueryValueEx</b>
     *
     * <pre>
     *
     *     	 Retrieves the type and data for the specified value name associated with an open registry key.
     *
     *     	 To ensure that any string values (REG_SZ, REG_MULTI_SZ, and REG_EXPAND_SZ) returned are null-terminated, use the RegGetValue function.
     *
     *     	 LONG RegQueryValueEx(
     *     	 HKEY hKey,
     *     	 LPCTSTR lpValueName,
     *     	 LPDWORD lpReserved,
     *     	 LPDWORD lpType,
     *     	 LPBYTE lpData,
     *     	 LPDWORD lpcbData
     *     	 );
     *
     *     	 Parameters
     *
     *     	 hKey
     *     	 [in] A handle to an open registry key. The key must have been opened with the KEY_QUERY_VALUE access right. For more information, see Registry Key Security and Access Rights.
     *
     *     	 This handle is returned by the RegCreateKeyEx or RegOpenKeyEx function, or it can be one of the following predefined keys:
     *
     *     	 HKEY_CLASSES_ROOT
     *     	 HKEY_CURRENT_CONFIG
     *     	 HKEY_CURRENT_USER
     *     	 HKEY_LOCAL_MACHINE
     *     	 HKEY_PERFORMANCE_DATA
     *     	 HKEY_PERFORMANCE_NLSTEXT
     *     	 HKEY_PERFORMANCE_TEXT
     *     	 HKEY_USERS
     *
     *     	 Windows Me/98/95:  This parameter can also be the following key:
     *
     *     	 HKEY_DYN_DATA
     *
     *     	 lpValueName
     *     	 [in] The name of the registry value.
     *
     *     	 If lpValueName is NULL or an empty string, &quot;&quot;, the function retrieves the type and data for the key's unnamed or default value, if any.
     *
     *     	 For more information, see Registry Element Size Limits.
     *
     *     	 Keys do not automatically have an unnamed or default value. Unnamed values can be of any type.
     *
     *     	 Windows Me/98/95:  Every key has a default value that initially does not contain data. On Windows 95, the default value type is always REG_SZ. On Windows 98 and Windows Me, the type of a key's default value is initially REG_SZ, but RegSetValueEx can specify a default value with a different type.
     *
     *     	 lpReserved
     *     	 This parameter is reserved and must be NULL.
     *     	 lpType
     *     	 [out] A pointer to a variable that receives a code indicating the type of data stored in the specified value. For a list of the possible type codes, see Registry Value Types. The lpType parameter can be NULL if the type code is not required.
     *     	 lpData
     *     	 [out] A pointer to a buffer that receives the value's data. This parameter can be NULL if the data is not required.
     *     	 lpcbData
     *     	 [in, out] A pointer to a variable that specifies the size of the buffer pointed to by the lpData parameter, in bytes. When the function returns, this variable contains the size of the data copied to lpData.
     *
     *     	 The lpcbData parameter can be NULL only if lpData is NULL.
     *
     *     	 If the data has the REG_SZ, REG_MULTI_SZ or REG_EXPAND_SZ type, this size includes any terminating null character or characters. For more information, see Remarks.
     *
     *     	 If the buffer specified by lpData parameter is not large enough to hold the data, the function returns ERROR_MORE_DATA and stores the required buffer size in the variable pointed to by lpcbData. In this case, the contents of the lpData buffer are undefined.
     *
     *     	 If lpData is NULL, and lpcbData is non-NULL, the function returns ERROR_SUCCESS and stores the size of the data, in bytes, in the variable pointed to by lpcbData. This enables an application to determine the best way to allocate a buffer for the value's data.
     *
     *     	 If hKey specifies HKEY_PERFORMANCE_DATA and the lpData buffer is not large enough to contain all of the returned data, RegQueryValueEx returns ERROR_MORE_DATA and the value returned through the lpcbData parameter is undefined. This is because the size of the performance data can change from one call to the next. In this case, you must increase the buffer size and call RegQueryValueEx again passing the updated buffer size in the lpcbData parameter. Repeat this until the function succeeds. You need to maintain a separate variable to keep track of the buffer size, because the value returned by lpcbData is unpredictable.
     *
     *     	 Return Values
     *
     *     	 If the function succeeds, the return value is ERROR_SUCCESS.
     *
     *     	 If the function fails, the return value is a nonzero error code defined in Winerror.h. You can use the FormatMessage function with the FORMAT_MESSAGE_FROM_SYSTEM flag to get a generic description of the error.
     *     	 Remarks
     *
     *     	 An application typically calls RegEnumValue to determine the value names and then RegQueryValueEx to retrieve the data for the names.
     *
     *     	 If the data has the REG_SZ, REG_MULTI_SZ or REG_EXPAND_SZ type, the string may not have been stored with the proper null-terminating characters. Therefore, even if the function returns ERROR_SUCCESS, the application should ensure that the string is properly terminated before using it; otherwise, it may overwrite a buffer. (Note that REG_MULTI_SZ strings should have two null-terminating characters.)
     *
     *     	 If the data has the REG_SZ, REG_MULTI_SZ or REG_EXPAND_SZ type, and the ANSI version of this function is used (either by explicitly calling RegQueryValueExA or by not defining UNICODE before including the Windows.h file), this function converts the stored Unicode string to an ANSI string before copying it to the buffer pointed to by lpData.
     *
     *     	 When calling the RegQueryValueEx function with hKey set to the HKEY_PERFORMANCE_DATA handle and a value string of a specified object, the returned data structure sometimes has unrequested objects. Do not be surprised; this is normal behavior. When calling the RegQueryValueEx function, you should always expect to walk the returned data structure to look for the requested object.
     *     	 Example Code [C++]
     *
     *     	 Ensure that you reinitialize the value pointed to by the lpcbData parameter each time you call this function. This is very important when you call this function in a loop, as in the following code example.
     *
     *     	 #include &lt;windows.h&gt;
     *     	 #include &lt;malloc.h&gt;
     *
     *     	 #define TOTALBYTES    8192
     *     	 #define BYTEINCREMENT 1024
     *
     *     	 DWORD BufferSize = TOTALBYTES;
     *     	 PPERF_DATA_BLOCK PerfData = NULL;
     *
     *     	 while( RegQueryValueEx( HKEY_PERFORMANCE_DATA,
     *     	 TEXT(&quot;Global&quot;),
     *     	 NULL,
     *     	 NULL,
     *     	 (LPBYTE) PerfData,
     *     	 &amp;BufferSize ) == ERROR_MORE_DATA )
     *     	 {
     *     	 // Get a buffer that is big enough.
     *
     *     	 BufferSize += BYTEINCREMENT;
     *     	 PerfData = (PPERF_DATA_BLOCK) realloc( PerfData, BufferSize );
     *     	 }
     * </pre>
     *
     * @param hKey
     * @param lpValueName
     * @param lRegData
     * @return
     * @throws NativeException
     * @throws IllegalAccessException
     */
    public static RegData RegQueryValueEx(HKEY hKey, String lpValueName,
            RegData lRegData) throws NativeException, IllegalAccessException
    {
        if (lRegData == null)
        {
            lRegData = new RegData(1024);
        }
        JNative nRegQueryValueEx = new JNative(DLL_NAME, "RegQueryValueExA");
        nRegQueryValueEx.setRetVal(Type.INT);

        nRegQueryValueEx.setParameter(0, hKey.getValue());
        nRegQueryValueEx.setParameter(1, Type.STRING, lpValueName);
        nRegQueryValueEx.setParameter(2, NullPointer.NULL);
        nRegQueryValueEx.setParameter(3, lRegData.getLpType().getPointer());
        nRegQueryValueEx.setParameter(4, lRegData.getLpData());
        nRegQueryValueEx.setParameter(5, lRegData.getLpcbData().getPointer());
        nRegQueryValueEx.invoke();
        mLastErrorCode = nRegQueryValueEx.getRetVal();
        lRegData.setErrorCode(getLastErrorCode());

        return lRegData;
    }

    /**
     * function created to get the default value of a key like RegQueryDefaultValue(HKEY.HKEY_CLASSES_ROOT, "CLSID\\" + guid);
     * <p>More control on the returned value may be necessary</p>
     * @param parentKey
     * @param leafKeyName
     * @return
     * @throws NativeException
     * @throws IllegalAccessException
     */
    public static final String RegQueryDefaultValue(HKEY parentKey, String leafKeyName) throws NativeException, IllegalAccessException
    {
        HKEY hKey = null;
        String value = null;
        try
        {
            hKey = Advapi32.RegOpenKeyEx(parentKey, leafKeyName, REGSAM.KEY_READ);
            if (hKey == null)
            {
                return null;
            }
            RegData data = Advapi32.RegQueryValueEx(hKey, null, null);
            if (data.getErrorCode() == 0)
            {
                value = data.getLpData().getAsString();
            }
            else
            {
                value = null;
            }
        }
        finally
        {
            if (hKey != null)
            {
                Advapi32.RegCloseKey(hKey);
                hKey = null;
            }
        }
        return value;
    }
    /**
     *
     * <b>RegGetValue</b>
     *
     * <pre>
     *
     *     	 Retrieves the type and data for the specified registry value.
     *
     *     	 LONG RegGetValue(
     *     	 HKEY hkey,
     *     	 LPCTSTR lpSubKey,
     *     	 LPCTSTR lpValue,
     *     	 DWORD dwFlags,
     *     	 LPDWORD pdwType,
     *     	 PVOID pvData,
     *     	 LPDWORD pcbData
     *     	 );
     *
     *     	 Parameters
     *
     *     	 hkey
     *     	 [in] A handle to an open registry key. The key must have been opened with the KEY_QUERY_VALUE access right. For more information, see Registry Key Security and Access Rights.
     *
     *     	 This handle is returned by the RegCreateKeyEx or RegOpenKeyEx function, or it can be one of the following predefined keys:
     *
     *     	 HKEY_CLASSES_ROOT
     *     	 HKEY_CURRENT_CONFIG
     *     	 HKEY_CURRENT_USER
     *     	 HKEY_LOCAL_MACHINE
     *     	 HKEY_PERFORMANCE_DATA
     *     	 HKEY_PERFORMANCE_NLSTEXT
     *     	 HKEY_PERFORMANCE_TEXT
     *     	 HKEY_USERS
     *
     *     	 lpSubKey
     *     	 [in] The name of the registry key. This key must be a subkey of the key specified by the hkey parameter.
     *
     *     	 Key names are not case sensitive.
     *     	 lpValue
     *     	 [in] The name of the registry value.
     *
     *     	 If this parameter is NULL or an empty string, &quot;&quot;, the function retrieves the type and data for the key's unnamed or default value, if any.
     *
     *     	 For more information, see Registry Element Size Limits.
     *
     *     	 Keys do not automatically have an unnamed or default value. Unnamed values can be of any type.
     *     	 dwFlags
     *     	 [in] The flags that restrict the data type of value to be queried. If the data type of the value does not meet this criteria, the function fails. This parameter can be one or more of the following values.
     *     	 Value 	Meaning
     *     	 RRF_RT_ANY
     *     	 0x0000ffff 	No type restriction.
     *     	 RRF_RT_DWORD
     *     	 0x00000018 	Restrict type to 32-bit RRF_RT_REG_BINARY | RRF_RT_REG_DWORD.
     *     	 RRF_RT_QWORD
     *     	 0x00000048 	Restrict type to 64-bit RRF_RT_REG_BINARY | RRF_RT_REG_DWORD.
     *     	 RRF_RT_REG_BINARY
     *     	 0x00000008 	Restrict type to REG_BINARY.
     *     	 RRF_RT_REG_DWORD
     *     	 0x00000010 	Restrict type to REG_DWORD.
     *     	 RRF_RT_REG_EXPAND_SZ
     *     	 0x00000004 	Restrict type to REG_EXPAND_SZ.
     *     	 RRF_RT_REG_MULTI_SZ
     *     	 0x00000020 	Restrict type to REG_MULTI_SZ.
     *     	 RRF_RT_REG_NONE
     *     	 0x00000001 	Restrict type to REG_NONE.
     *     	 RRF_RT_REG_QWORD
     *     	 0x00000040 	Restrict type to REG_QWORD.
     *     	 RRF_RT_REG_SZ
     *     	 0x00000002 	Restrict type to REG_SZ.
     *
     *     	 This parameter can also include one or more of the following values.
     *     	 Value 	Meaning
     *     	 RRF_NOEXPAND
     *     	 0x10000000 	Do not automatically expand environment strings if the value is of type REG_EXPAND_SZ.
     *     	 RRF_ZEROONFAILURE
     *     	 0x20000000 	If pvData is not NULL, set the contents of the buffer to zeroes on failure.
     *     	 pdwType
     *     	 [out] A pointer to a variable that receives a code indicating the type of data stored in the specified value. For a list of the possible type codes, see Registry Value Types. This parameter can be NULL if the type is not required.
     *     	 pvData
     *     	 [out] A pointer to a buffer that receives the value's data. This parameter can be NULL if the data is not required.
     *
     *     	 If the data is a string, the function checks for a terminating null character. If one is not found, the string is stored with a null terminator if the buffer is large enough to accommodate the extra character. Otherwise, the function fails and returns ERROR_MORE_DATA.
     *     	 pcbData
     *     	 [in, out] A pointer to a variable that specifies the size of the buffer pointed to by the pvData parameter, in bytes. When the function returns, this variable contains the size of the data copied to pvData.
     *
     *     	 The pcbData parameter can be NULL only if pvData is NULL.
     *
     *     	 If the data has the REG_SZ, REG_MULTI_SZ or REG_EXPAND_SZ type, this size includes any terminating null character or characters. For more information, see Remarks.
     *
     *     	 If the buffer specified by pvData parameter is not large enough to hold the data, the function returns ERROR_MORE_DATA and stores the required buffer size in the variable pointed to by pcbData. In this case, the contents of the pvData buffer are undefined.
     *
     *     	 If pvData is NULL, and pcbData is non-NULL, the function returns ERROR_SUCCESS and stores the size of the data, in bytes, in the variable pointed to by pcbData. This enables an application to determine the best way to allocate a buffer for the value's data.
     *
     *     	 If hKey specifies HKEY_PERFORMANCE_DATA and the pvData buffer is not large enough to contain all of the returned data, the function returns ERROR_MORE_DATA and the value returned through the pcbData parameter is undefined. This is because the size of the performance data can change from one call to the next. In this case, you must increase the buffer size and call RegGetValue again passing the updated buffer size in the pcbData parameter. Repeat this until the function succeeds. You need to maintain a separate variable to keep track of the buffer size, because the value returned by pcbData is unpredictable.
     *
     *     	 Return Values
     *
     *     	 If the function succeeds, the return value is ERROR_SUCCESS.
     *
     *     	 If the function fails, the return value is a nonzero error code defined in Winerror.h. You can use the FormatMessage function with the FORMAT_MESSAGE_FROM_SYSTEM flag to get a generic description of the error.
     *     	 Remarks
     *
     *     	 An application typically calls RegEnumValue to determine the value names and then RegGetValue to retrieve the data for the names.
     *
     *     	 If the data has the REG_SZ, REG_MULTI_SZ or REG_EXPAND_SZ type, the string may not have been stored with the proper null-terminating characters. Therefore, even if the function returns ERROR_SUCCESS, the application should ensure that the string is properly terminated before using it; otherwise, it may overwrite a buffer. (Note that REG_MULTI_SZ strings should have two null-terminating characters.)
     *
     *     	 If the data has the REG_SZ, REG_MULTI_SZ or REG_EXPAND_SZ type, and the ANSI version of this function is used (either by explicitly calling RegGetValueA or by not defining UNICODE before including the Windows.h file), this function converts the stored Unicode string to an ANSI string before copying it to the buffer pointed to by pvData.
     *
     *     	 When calling this function with hkey set to the HKEY_PERFORMANCE_DATA handle and a value string of a specified object, the returned data structure sometimes has unrequested objects. Do not be surprised; this is normal behavior. You should always expect to walk the returned data structure to look for the requested object.
     *
     *     	 To compile an application that uses this function, define WIN32_WINNT as 0x0600 or later. For more information, see Using the SDK Headers.
     *
     * </pre>
     *
     * @throws NativeException
     * @throws IllegalAccessException
     */
    public static RegData RegGetValue(HKEY hKey, String lpSubKey,
            String lpValue, int dwFlags, RegData lRegData)
            throws NativeException, IllegalAccessException
    {
        if (lRegData == null)
        {
            lRegData = new RegData(1024);
        }
        JNative nRegGetValue = new JNative(DLL_NAME, "RegGetValueA");
        nRegGetValue.setRetVal(Type.INT);

        int cur = 0;
        nRegGetValue.setParameter(cur++, hKey.getValue());
        if (lpSubKey == null)
        {
            nRegGetValue.setParameter(cur++, NullPointer.NULL);
        }
        else
        {
            nRegGetValue.setParameter(cur++, Type.STRING, lpSubKey);
        }
        if (lpValue == null)
        {
            nRegGetValue.setParameter(cur++, NullPointer.NULL);
        }
        else
        {
            nRegGetValue.setParameter(cur++, Type.STRING, lpValue);
        }
        nRegGetValue.setParameter(cur++, dwFlags);

        nRegGetValue.setParameter(cur++, lRegData.getLpType().getPointer());
        nRegGetValue.setParameter(cur++, lRegData.getLpData());
        nRegGetValue.setParameter(cur++, lRegData.getLpcbData().getPointer());

        nRegGetValue.invoke();
        mLastErrorCode = nRegGetValue.getRetVal();
        lRegData.setErrorCode(getLastErrorCode());

        return lRegData;
    }

    /**
     * Those values should be used as dwFlag value in RegGetValue() function
     */
    public interface RRF_Values
    {

        /** No type restriction.*/
        public static final int RRF_RT_ANY = 0x0000ffff;
        /** Restrict type to 32-bit RRF_RT_REG_BINARY | RRF_RT_REG_DWORD. */
        public static final int RRF_RT_DWORD = 0x00000018;
        /** Restrict type to 64-bit RRF_RT_REG_BINARY | RRF_RT_REG_DWORD. */
        public static final int RRF_RT_QWORD = 0x00000048;
        /** Restrict type to REG_BINARY. */
        public static final int RRF_RT_REG_BINARY = 0x00000008;
        /** Restrict type to REG_DWORD. */
        public static final int RRF_RT_REG_DWORD = 0x00000010;
        /** Restrict type to REG_EXPAND_SZ. */
        public static final int RRF_RT_REG_EXPAND_SZ = 0x00000004;
        /** Restrict type to REG_MULTI_SZ. */
        public static final int RRF_RT_REG_MULTI_SZ = 0x00000020;
        /** Restrict type to REG_NONE. */
        public static final int RRF_RT_REG_NONE = 0x00000001;
        /** Restrict type to REG_QWORD. */
        public static final int RRF_RT_REG_QWORD = 0x00000040;
        /** Restrict type to REG_SZ. */
        public static final int RRF_RT_REG_SZ = 0x00000002;
        /** Optional parameter <p>Do not automatically expand environment strings if the value is of type REG_EXPAND_SZ.</p> */
        public static final int RRF_NOEXPAND = 0x10000000;
        /** Optional parameter <p>If pvData is not NULL, set the contents of the buffer to zeroes on failure.</p> */
        public static final int RRF_ZEROONFAILURE = 0x20000000;
    }

    /**
     *
     * <b>RegEnumValue</b>
     *
     * <pre>
     *
     *   	 Enumerates the values for the specified open registry key. The function copies one indexed value name and data block for the key each time it is called.
     *
     *   	 LONG RegEnumValue(
     *   	 HKEY hKey,
     *   	 DWORD dwIndex,
     *   	 LPTSTR lpValueName,
     *   	 LPDWORD lpcValueName,
     *   	 LPDWORD lpReserved,
     *   	 LPDWORD lpType,
     *   	 LPBYTE lpData,
     *   	 LPDWORD lpcbData
     *   	 );
     *
     *   	 Parameters
     *
     *   	 hKey
     *   	 [in] A handle to an open registry key. The key must have been opened with the KEY_QUERY_VALUE access right. For more information, see Registry Key Security and Access Rights.
     *
     *   	 This handle is returned by the RegCreateKeyEx or RegOpenKeyEx function, or it can be one of the following predefined keys:
     *
     *   	 HKEY_CLASSES_ROOT
     *   	 HKEY_CURRENT_CONFIG
     *   	 HKEY_CURRENT_USER
     *   	 HKEY_LOCAL_MACHINE
     *   	 HKEY_PERFORMANCE_DATA
     *   	 HKEY_USERS
     *
     *   	 Windows Me/98/95:  This parameter can also be the following value:
     *
     *   	 HKEY_DYN_DATA
     *
     *   	 dwIndex
     *   	 [in] The index of the value to be retrieved. This parameter should be zero for the first call to the RegEnumValue function and then be incremented for subsequent calls.
     *
     *   	 Because values are not ordered, any new value will have an arbitrary index. This means that the function may return values in any order.
     *   	 lpValueName
     *   	 [out] A pointer to a buffer that receives the name of the value, including the terminating null character.
     *
     *   	 For more information, see Registry Element Size Limits.
     *   	 lpcValueName
     *   	 [in, out] A pointer to a variable that specifies the size of the buffer pointed to by the lpValueName parameter, in TCHARs. This size should include the terminating null character. When the function returns, the variable pointed to by lpcValueName contains the number of characters stored in the buffer. The count returned does not include the terminating null character.
     *   	 lpReserved
     *   	 This parameter is reserved and must be NULL.
     *   	 lpType
     *   	 [out] A pointer to a variable that receives a code indicating the type of data stored in the specified value. For a list of the possible type codes, see Registry Value Types. The lpType parameter can be NULL if the type code is not required.
     *   	 lpData
     *   	 [out] A pointer to a buffer that receives the data for the value entry. This parameter can be NULL if the data is not required.
     *
     *   	 If lpData is NULL and lpcbData is non-NULL, the function stores the size of the data, in bytes, in the variable pointed to by lpcbData. This enables an application to determine the best way to allocate a buffer for the data.
     *   	 lpcbData
     *   	 [in, out] A pointer to a variable that specifies the size of the buffer pointed to by the lpData parameter, in bytes. When the function returns, the variable pointed to by the lpcbData parameter contains the number of bytes stored in the buffer.
     *
     *   	 This parameter can be NULL only if lpData is NULL.
     *
     *   	 If the data has the REG_SZ, REG_MULTI_SZ or REG_EXPAND_SZ type, this size includes any terminating null character or characters. For more information, see Remarks.
     *
     *   	 If the buffer specified by lpData is not large enough to hold the data, the function returns ERROR_MORE_DATA and stores the required buffer size in the variable pointed to by lpcbData. In this case, the contents of lpData are undefined.
     *
     *   	 Registry value names are limited to 32767 bytes. The ANSI version of this function treats this param as a USHORT value. Therefore, if you specify a value greater than 32767 bytes, there is an overflow and the function may return ERROR_MORE_DATA.
     *
     *   	 Return Values
     *
     *   	 If the function succeeds, the return value is ERROR_SUCCESS.
     *
     *   	 If the function fails, the return value is a nonzero error code defined in Winerror.h. You can use the FormatMessage function with the FORMAT_MESSAGE_FROM_SYSTEM flag to get a generic description of the error.
     *   	 Remarks
     *
     *   	 To enumerate values, an application should initially call the RegEnumValue function with the dwIndex parameter set to zero. The application should then increment dwIndex and call the RegEnumValue function until there are no more values (until the function returns ERROR_NO_MORE_ITEMS).
     *
     *   	 The application can also set dwIndex to the index of the last value on the first call to the function and decrement the index until the value with index 0 is enumerated. To retrieve the index of the last value, use the RegQueryInfoKey function.
     *
     *   	 While using RegEnumValue, an application should not call any registry functions that might change the key being queried.
     *
     *   	 If the data has the REG_SZ, REG_MULTI_SZ or REG_EXPAND_SZ type, the string may not have been stored with the proper null-terminating characters. Therefore, even if the function returns ERROR_SUCCESS, the application should ensure that the string is properly terminated before using it; otherwise, it may overwrite a buffer. (Note that REG_MULTI_SZ strings should have two null-terminating characters.)
     *
     *   	 To determine the maximum size of the name and data buffers, use the RegQueryInfoKey function.
     *
     * </pre>
     *
     * @param hKey
     * @param dwIndex
     * @param lRegValue
     * @return
     * @throws NativeException
     * @throws IllegalAccessException
     */
    public static RegValue RegEnumValue(HKEY hKey, int dwIndex,
            RegValue lRegValue) throws NativeException, IllegalAccessException
    {
        if (lRegValue == null)
        {
            lRegValue = new RegValue(512, 1024);
        }
        JNative nRegEnumValue = new JNative(DLL_NAME, "RegEnumValueA");
        nRegEnumValue.setRetVal(Type.INT);

        nRegEnumValue.setParameter(0, hKey.getValue());
        nRegEnumValue.setParameter(1, dwIndex);
        nRegEnumValue.setParameter(2, lRegValue.getLpValueName());
        nRegEnumValue.setParameter(3, lRegValue.getLpcValueName().getPointer());
        nRegEnumValue.setParameter(4, NullPointer.NULL);
        nRegEnumValue.setParameter(5, lRegValue.getLpType().getPointer());
        nRegEnumValue.setParameter(6, lRegValue.getLpData());
        nRegEnumValue.setParameter(7, lRegValue.getLpcbData().getPointer());

        nRegEnumValue.invoke();
        mLastErrorCode = nRegEnumValue.getRetVal();
        lRegValue.setErrorCode(getLastErrorCode());
        return lRegValue;
    }

    public static void main(String[] args) throws NativeException,
            IllegalAccessException
    {
        JNative.getLogger().log("Testing Registry access");
        HKEY hKey = RegOpenKeyEx(HKEY.HKEY_CLASSES_ROOT, "CLSID",
                REGSAM.KEY_READ.or(REGSAM.KEY_QUERY_VALUE));
        if (hKey == null)
        {
            JNative.getLogger().log("error : " + getLastErrorCode());
            return;
        }
        else
        {
            JNative.getLogger().log("hKey : " + hKey.getValue());
        }
        listKey(hKey);
        RegCloseKey(hKey);
    }
    static int cpt = 0;

    /**
     * @param hKey
     * @throws NativeException
     * @throws IllegalAccessException
     */
    public static void listKey(HKEY hKey) throws NativeException,
            IllegalAccessException
    {
        if (cpt > 50)
        {
            return;
        }
        else
        {
            //cpt++;
        }
        RegQueryKey lKey = RegQueryInfoKey(hKey, null);
        // RegKey lRegData;
        // JNative.getLogger().log("Number of values : " +
        // lKey.getLpcValues().getValue());
        for (int i = 0; i < lKey.getLpcValues().getValue(); i++)
        {
            RegValue regValue = RegEnumValue(hKey, i, null);
            RegData lRegData = null;
            String keyValueName = regValue.getLpValueName().getAsString();
            if (keyValueName == null || keyValueName.length() == 0)
            {
                JNative.getLogger().log("Query value : (default)");
            }
            else
            {
                JNative.getLogger().log("Query value : " + keyValueName);
            }
            lRegData = RegQueryValueEx(hKey, keyValueName, null);
            String val = "";
            RegValueTypes valType = RegValueTypes.fromInt(lRegData.getLpType().getValue());
            switch (valType)
            {
                case REG_DWORD:
                    val = lRegData.getLpData().getAsInt(0) + " - (0x" + Integer.toHexString(lRegData.getLpData().getAsInt(0)) + ")";
                    break;
                case REG_DWORD_LITTLE_ENDIAN:
                    val = lRegData.getLpData().getAsShort(0) + 256 * lRegData.getLpData().getAsShort(2) + "";
                    break;
                case REG_BINARY:
                    byte[] p = lRegData.getLpData().getMemory();
                    for (int j = 0; j < lRegData.getLpcbData().getValue(); j++)
                    {
                        val += ("0x" + Integer.toHexString(p[j]) + ", ");
                    }
                    val = val.substring(0, val.length() - 2);
                    break;
                default:
                    val = lRegData.getLpData().getAsString();
                    break;
            }
            JNative.getLogger().log("Type : " + valType);
            JNative.getLogger().log("Value : " + val);
        }
        for (int i = 0; i < lKey.getLpcSubKeys().getValue(); i++)
        {
            RegKey regKey = RegEnumKeyEx(hKey, i, null);
            if (regKey.getErrorCode() != 0 || cpt > 50)
            {
                break;
            }
            else
            {
                JNative.getLogger().log("Key : " + regKey.getLpValueName().getAsString());
                HKEY lhKey = RegOpenKeyEx(hKey, regKey.getLpValueName().getAsString(), REGSAM.KEY_READ.or(REGSAM.KEY_QUERY_VALUE));
                listKey(lhKey);
                RegCloseKey(lhKey);
            }
        }
    }
}

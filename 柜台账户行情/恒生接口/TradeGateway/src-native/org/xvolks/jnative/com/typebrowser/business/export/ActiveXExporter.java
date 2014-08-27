package org.xvolks.jnative.com.typebrowser.business.export;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.xvolks.jnative.Type;
import org.xvolks.jnative.com.interfaces.IDispatch;
import org.xvolks.jnative.com.typebrowser.business.CLSID;
import org.xvolks.jnative.com.typebrowser.business.COMIntrospector;
import org.xvolks.jnative.com.typebrowser.business.description.FunctionDescription;
import org.xvolks.jnative.com.typebrowser.business.description.IDispatchDescription;
import org.xvolks.jnative.com.typebrowser.business.description.ParameterDescription;
import org.xvolks.jnative.com.utils.ByRef;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.logging.JNativeLogger;
import org.xvolks.jnative.misc.HKEY;
import org.xvolks.jnative.misc.REGSAM;
import org.xvolks.jnative.util.Advapi32;
import org.xvolks.jnative.util.constants.COM;
import org.xvolks.jnative.util.constants.COM.DISPATCH_TYPE;

public class ActiveXExporter {
	private final CLSID clsid;
	private final String packageName;
	private File targetDirectory;

	public ActiveXExporter(CLSID clsid, String packageName,
			String targetDirectory) throws NativeException, IllegalAccessException {
		super();
		this.clsid = clsid;
		packageName = packageName.replaceAll("\\.([0-9])", "._$1");
		this.packageName = packageName;
		clsid.loadRegType();
		System.err.println(clsid.getGuid());
		System.err.println(clsid.getProgID());
		System.err.println(clsid.getInprocServer32());
		File targetFile = new File(targetDirectory);
		targetFile.mkdirs();
		String[] dirs = packageName.split("\\.");
		for(String dir : dirs) {
			targetFile = new File(targetFile, dir);
			targetFile.mkdir();
		}
		this.targetDirectory = targetFile; 

	}

	public void export() throws Throwable {
		System.setProperty(JNativeLogger.LOG_LEVEL, "5");
		final IDispatch idispatch = new IDispatch(clsid.getProgID());
		final ByRef<IDispatchDescription> pDescription = new ByRef<IDispatchDescription>(null);
		final ByRef<Throwable> pThrowable = new ByRef<Throwable>(null);
		idispatch.syncExec(new Runnable() {
			public void run() {
				try {
					pDescription.setValue(COMIntrospector.introspectIDispatch(idispatch));
				} catch (Throwable e) {
					pThrowable.setValue(e);
				}
			}
		});
		if(pThrowable.getValue() != null) throw pThrowable.getValue();
		IDispatchDescription description = pDescription.getValue();
		HKEY hKey = Advapi32.RegOpenKeyEx(HKEY.HKEY_CLASSES_ROOT, "CLSID", REGSAM.KEY_READ);
		String name = Advapi32.RegQueryDefaultValue(hKey, clsid.getGuid().toString()).trim();
		int indexOf = name.indexOf(" ");
		if(indexOf!= -1) {
			name = name.substring(0, indexOf);
		}
		BufferedWriter wr = new BufferedWriter(new FileWriter(new File(targetDirectory, name + ".java")));
		try {
			wr.write("package " + packageName + ";"); wr.newLine();
			wr.newLine();
			wr.write("import org.xvolks.jnative.com.interfaces.IDispatch;"); wr.newLine();
			wr.write("import org.xvolks.jnative.misc.basicStructures.HWND;"); wr.newLine();
			wr.write("import org.xvolks.jnative.util.constants.COM.DISPATCH_TYPE;"); wr.newLine();
			wr.write("import org.xvolks.jnative.JNative;"); wr.newLine();
			wr.write("import org.xvolks.jnative.com.typebrowser.business.COMIntrospector;"); wr.newLine();
			wr.write("import org.xvolks.jnative.com.utils.ByRef;"); wr.newLine();
			wr.write("import org.xvolks.jnative.com.utils.COMActuator;"); wr.newLine();
			wr.write("import org.xvolks.jnative.logging.JNativeLogger;"); wr.newLine();
			wr.newLine();
			wr.write("public class "+name+" extends IDispatch {"); wr.newLine();
			wr.write("\tpublic "+name+"(Thread dispatchThread, HWND hwnd) throws Throwable {"); wr.newLine();
			wr.write("\t\tsuper(\""+clsid.getProgID()+"\", IIDIDispatch, dispatchThread, hwnd);"); wr.newLine();
			wr.write("\t}"); wr.newLine();
			wr.newLine();
			for(FunctionDescription fd : description.getFunctionDescriptions()) {
				String doc = fd.getDocumentation().getDoc();
				if(fd.getName().equals("QueryInterface")) continue;
				if(fd.getName().equals("AddRef")) continue;
				if(fd.getName().equals("Release")) continue;
				if(fd.getName().equals("GetTypeInfoCount")) continue;
				if(fd.getName().equals("GetTypeInfo")) continue;
				if(fd.getName().equals("GetTypeInfo")) continue;
				if(fd.getName().equals("GetIDsOfNames")) continue;
				if(fd.getName().equals("Invoke")) continue;
				
				switch (fd.getDesc().getInvkind()) {
					case INVOKE_PROPERTYGET:
						writeGetter(wr, fd, doc);
						break;
					case INVOKE_PROPERTYPUT:
						writeSetter(wr, fd, doc);
						break;
					case INVOKE_FUNC:
						writeMethod(wr, fd, doc);
						break;
					case INVOKE_PROPERTYPUTREF:
						wr.write("INVOKE_PROPERTYPUTREF (not implemented yet) for " + fd.getName()); wr.newLine();
						break;
				}
				wr.write("\t}"); wr.newLine();
				wr.newLine();
			}
			wr.write("\tpublic static void main(String[] args) throws Throwable {"); wr.newLine();
			wr.write("\t\tSystem.setProperty(JNativeLogger.LOG_LEVEL, \"5\");"); wr.newLine();
			wr.write("\t\tJNative.setLoggingEnabled(true);"); wr.newLine();
			wr.write("\t\t"); wr.newLine();
			wr.write("\t\tHWND hwnd = new HWND(0);"); wr.newLine();
			wr.write("\t\tByRef<HWND> pHWND = new ByRef<HWND>(hwnd);"); wr.newLine();
			wr.write("\t\t// install the main message loop before opening a window !!!"); wr.newLine();
			wr.write("\t\tThread t = COMActuator.installMainMessagePumpLoopInThread(pHWND);"); wr.newLine();
			wr.write("\t\tjavax.swing.JFrame f = new javax.swing.JFrame();"); wr.newLine();
			wr.write("\t\tf.setVisible(true);"); wr.newLine();
			wr.write("\t\tSystem.err.println(\"SOP >>>>>>>>>>>>>>>>>>>>>>>>>>>\");"); wr.newLine();
			wr.write("\t\ttry {"); wr.newLine();
			wr.write("\t\t\tfinal "+name+" lect = new "+name+"(t, hwnd);"); wr.newLine();
			wr.write("\t\t\tlect.syncExec(new Runnable() {"); wr.newLine();
			wr.write("\t\t\t\tpublic void run() {"); wr.newLine();
			wr.write("\t\t\t\t\ttry {"); wr.newLine();
			wr.write("\t\t\t\t\t\tCOMIntrospector.introspectIDispatch(lect);"); wr.newLine();
			wr.write("\t\t\t\t\t} catch (Exception e) {"); wr.newLine();
			wr.write("\t\t\t\t\t\te.printStackTrace();"); wr.newLine();
			wr.write("\t\t\t\t\t}"); wr.newLine();
			wr.write("\t\t\t\t}"); wr.newLine();
			wr.write("\t\t\t});"); wr.newLine();
			wr.write("\t\t} finally {"); wr.newLine();
			wr.write("\t\t\tSystem.err.println(\"<<<<<<<<<<<<<<<<<<<<<<<<EOP\");"); wr.newLine();
			wr.write("\t\t\tf.setVisible(false);"); wr.newLine();
			wr.write("\t\t}"); wr.newLine();
			wr.write("\t}"); wr.newLine();
			
		} finally {
			wr.write("}"); wr.newLine();
			wr.close();
			wr.close();
		}
	}

	private void writeGetter(BufferedWriter wr, FunctionDescription fd,
			String doc) throws IOException {
		wr.write("\t/**"); wr.newLine();
		wr.write("\t * Getter of property " + fd.getName()); wr.newLine();
		wr.write("\t * " + (doc == null ? "" : doc)); wr.newLine();
		wr.write("\t */"); wr.newLine();
		wr.write("\tpublic "+COM.getTypeName(fd.getReturnType())+" get"+fd.getName()+"() {"); wr.newLine();
		
		wr.write(String.format("\t\tObject[] params = new Object[%d];", fd.getParameters().size())); wr.newLine();
		String ret = "";
		if(getJNativeParamType(fd.getReturnType()) != Type.VOID) {
			ret = "return (" + COM.getTypeName(fd.getReturnType()) + ") ";
		}
		wr.write(String.format("\t\t%sinvoke(%d, DISPATCH_TYPE.%s, params);", ret, fd.getDesc().getMemid(), DISPATCH_TYPE.DISPATCH_PROPERTYGET.name())); wr.newLine();
	}

	private void writeSetter(BufferedWriter wr, FunctionDescription fd,
			String doc) throws IOException {
		wr.write("\t/**"); wr.newLine();
		wr.write("\t * Setter of property " + fd.getName()); wr.newLine();
		wr.write("\t * " + (doc == null ? "" : doc)); wr.newLine();
		wr.write("\t */"); wr.newLine();
		wr.write("\tpublic void set"+fd.getName()+"("+COM.getTypeName(fd.getParameters().get(0).getType())+" "+fd.getName()+" ) {"); wr.newLine();
		
		wr.write(String.format("\t\tObject[] params = new Object[] { %s };", fd.getName())); wr.newLine();
		wr.write(String.format("\t\tinvoke(%d, DISPATCH_TYPE.%s, params);", fd.getDesc().getMemid(), DISPATCH_TYPE.DISPATCH_PROPERTYPUT.name())); wr.newLine();
		
	}

	private void writeMethod(BufferedWriter wr, FunctionDescription fd,
			String doc) throws IOException {
		wr.write("\t/**"); wr.newLine();
		wr.write("\t * " + (doc == null ? "" : doc)); wr.newLine();
		wr.write("\t */"); wr.newLine();
		wr.write("\tpublic "+COM.getTypeName(fd.getReturnType())+" "+fd.getName()+"("+formatParameters(fd.getParameters())+") {"); wr.newLine();
		
		wr.write(String.format("\t\tObject[] params = new Object[%d];", fd.getParameters().size())); wr.newLine();
		for(int i = 0; i<fd.getParameters().size(); i++) {
			wr.write(String.format("\t\tparams[%d] = %s;", i, fd.getParameters().get(i).getName())); wr.newLine();
		}
		String ret = "";
		if(getJNativeParamType(fd.getReturnType()) != Type.VOID) {
			ret = "return (" + COM.getTypeName(fd.getReturnType()) + ") ";
		}
		wr.write(String.format("\t\t%sinvoke(%d, DISPATCH_TYPE.%s, params);", ret, fd.getDesc().getMemid(), DISPATCH_TYPE.DISPATCH_METHOD.name())); wr.newLine();
	}

	private Type getJNativeParamType(int COMtype) {
		switch (COMtype) {
			case COM.VT_R4:
				return Type.FLOAT;
			case COM.VT_R8:
				return Type.DOUBLE;
			case COM.VT_VOID:
				return Type.VOID;
			default:
				return Type.INT;
		}
	}

	private String formatParameters(List<ParameterDescription> parameters) {
		StringBuilder sb = new StringBuilder();
		for(int i =0; i< parameters.size(); i++) {
			ParameterDescription parameterDescription = parameters.get(i);
//			boolean isOut = (parameterDescription.flags & COM.IDLFLAG.IDLFLAG_FOUT.getValue()) != 0;
//			if(isOut) {
//				sb.append("org.xvolks.jnative.com.utils.ByRef<");
//			}
			sb.append(COM.getTypeName(parameterDescription.getType()));
//			if(isOut) {
//				sb.append(">");
//			}
			sb.append(" ");
			sb.append(parameterDescription.getName());
			if(i<parameters.size()-1) {
				sb.append(", ");
			}
		}
		return sb.toString();
	}
		

	
	
}

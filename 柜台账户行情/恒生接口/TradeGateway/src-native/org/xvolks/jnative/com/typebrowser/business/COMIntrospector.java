package org.xvolks.jnative.com.typebrowser.business;

import java.util.List;

import org.xvolks.jnative.JNative;
import org.xvolks.jnative.com.interfaces.IDispatch;
import org.xvolks.jnative.com.interfaces.ITypeInfo;
import org.xvolks.jnative.com.interfaces.ITypeInfo.Documentation;
import org.xvolks.jnative.com.interfaces.structures.FUNCDESC;
import org.xvolks.jnative.com.interfaces.structures.TYPEATTR;
import org.xvolks.jnative.com.interfaces.structures.VARDESC;
import org.xvolks.jnative.com.typebrowser.business.description.FunctionDescription;
import org.xvolks.jnative.com.typebrowser.business.description.IDispatchDescription;
import org.xvolks.jnative.com.typebrowser.business.description.ParameterDescription;
import org.xvolks.jnative.com.typebrowser.business.description.VariableDescription;
import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.logging.JNativeLogger.SEVERITY;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.NativeMemoryBlock;
import org.xvolks.jnative.util.constants.COM;

public class COMIntrospector {

	static IDispatch currentIDispatch;
	public static IDispatchDescription introspectIDispatch(final IDispatch idispatch) throws NativeException, IllegalAccessException {
		currentIDispatch = idispatch;
		IDispatchDescription description = new IDispatchDescription(idispatch.getProgId());
		int ppTInfo;
		try {
			ppTInfo = idispatch.getTypeInfo(0, 0x800);
		} catch (Exception e) {
			e.printStackTrace();
			ppTInfo = idispatch.getTypeInfo(0, 0);
		}
		
		JNative.getLogger().log(SEVERITY.TRACE, String.format("ITypeInfo pointer is %08x", ppTInfo));
		ITypeInfo iTypeInfo = new ITypeInfo(ppTInfo);
		TYPEATTR typeattr = iTypeInfo.GetTypeAttr();
		JNative.getLogger().log(SEVERITY.DEBUG, "Version of "+idispatch.getProgId()+ " : " + typeattr.getWMajorVerNum() + "." + typeattr.getWMinorVerNum());
		if (typeattr != null) {
			if (typeattr.getCFuncs() > 0) {
				JNative.getLogger().log(SEVERITY.DEBUG, "Functions for " + idispatch.getProgId() + " :");
			}
			for (int i = 0; i < typeattr.getCFuncs(); i++) {
				FUNCDESC funcDesc = iTypeInfo.GetFuncDesc(i);
				description.getFunctionDescriptions().add(introspectFunction(iTypeInfo, funcDesc));
				funcDesc.dispose();
			}
			for (int i = 0; i < typeattr.getCVars(); i++) {
				VARDESC varDesc = iTypeInfo.GetVarDesc(i);
				description.getVariableDescriptions().add(introspectVariable(iTypeInfo, varDesc));
				varDesc.dispose();
			}
		}
		typeattr.dispose();
		iTypeInfo.release();
		return description;
	}

	private static FunctionDescription introspectFunction(ITypeInfo iTypeInfo, FUNCDESC desc) throws NativeException, IllegalAccessException {
		Documentation doc = iTypeInfo.GetDocumentation(desc.getMemid());
		List<String> names = iTypeInfo.GetNames(desc.getMemid(), desc.getCParams() + 1);
		final FunctionDescription description;
		if (names.size() > 0) {
			description = new FunctionDescription(names.get(0), doc, desc);
		} else {
			description = new FunctionDescription("##Anonymous", doc, desc);
		}
		JNative.getLogger().log(SEVERITY.DEBUG, desc.getInvkind().toString() + " " + description.getName());

		Pointer pElemDesc = new Pointer(new NativeMemoryBlock(desc.getLprgelemdescParam(), 16*desc.getCParams()));

		for (int j = 0; j < desc.getCParams(); j++) {
			introspectParameter(j, desc, names, description, pElemDesc);
		}
		description.setReturnType(desc.getElemDesc_typeDesc_desc_vt());
		if (description.getReturnType() == COM.VT_PTR) {
			Pointer pRetType = new Pointer(new NativeMemoryBlock(desc.getElemDesc_typeDesc_desc_union() + 4, 2));
			description.setReturnType(pRetType.getAsShort(0));
		}
		JNative.getLogger().log(SEVERITY.DEBUG, "\tValeur de retour " + COM.getTypeName(description.getReturnType()));
		return description;
	}

	private static VariableDescription introspectVariable(ITypeInfo iTypeInfo, VARDESC desc) throws NativeException, IllegalAccessException {
		int paramIndex = desc.getMemid();
		Documentation documentation = iTypeInfo.GetDocumentation(paramIndex);
		String name = documentation.getName();
		int type = desc.getElemdescVar_tdesc_vt();
		if (type == COM.VT_PTR) {
			Pointer pArgType1 = new Pointer(new NativeMemoryBlock(desc.getElemdescVar_tdesc_union() + 16 * paramIndex, 4));
			type = new Pointer(new NativeMemoryBlock(pArgType1.getAsInt(0) + 4, 2)).getAsShort(0);
			type |= COM.VT_BYREF;
		}

		VariableDescription variableDescription = new VariableDescription(name, documentation, desc);
		JNative.getLogger().log(SEVERITY.DEBUG, "PROPERTY Type "
				+ COM.getTypeName(type) + ", Flags "
				+ desc.getWVarFlags() + ", Name " + name);
		return variableDescription;
	}


	private static void introspectParameter(int paramIndex, FUNCDESC desc,
			List<String> names, final FunctionDescription description, final Pointer pElemDesc)
			throws NativeException {
/*		Pointer argType = new Pointer(new NativeMemoryBlock(desc
				.getLprgelemdescParam()	+ (16 * paramIndex) + 4, 2));
	*/	
		short varType = pElemDesc.getAsShort(16 * paramIndex + 4);
		if (varType == COM.VT_PTR) {
			Pointer pArgType1 = new Pointer(new NativeMemoryBlock(desc.getLprgelemdescParam() + 16 * paramIndex, 4));
			varType = new Pointer(new NativeMemoryBlock(pArgType1.getAsInt(0) + 4, 2)).getAsShort(0);
			varType |= COM.VT_BYREF;
		}
		final String name;
		if (names.size() > paramIndex + 1) {
			name = names.get(paramIndex + 1);
		} else {
			name = "###Anonymous";
		}
		Pointer pFlag = new Pointer(new NativeMemoryBlock(desc
				.getLprgelemdescParam()
				+ paramIndex * 16 + 8 + 4, 2));
		ParameterDescription parameterDescription = new ParameterDescription(
				name, varType, pFlag.getAsShort(0));
		description.getParameters().add(parameterDescription);
		JNative.getLogger().log(SEVERITY.DEBUG, "\tType "
				+ COM.getTypeName(parameterDescription.getType()) + ", Flags "
				+ parameterDescription.getFlags() + ", Name " + name);
	}

}

package org.xvolks.jnative.com.typebrowser.business.description;

import java.util.ArrayList;
import java.util.List;

import org.xvolks.jnative.com.interfaces.ITypeInfo.Documentation;
import org.xvolks.jnative.com.interfaces.structures.FUNCDESC;

public class FunctionDescription extends AbstractDescription {
	private final Documentation documentation;
	private final FUNCDESC desc;
	
	private final List<ParameterDescription> parameters = new ArrayList<ParameterDescription>();
	
	private int returnType;
	
	public FunctionDescription(String name, Documentation documentation, FUNCDESC desc) {
		super(name);
		this.documentation = documentation;
		this.desc = desc;
	}

	public Documentation getDocumentation() {
		return documentation;
	}

	public FUNCDESC getDesc() {
		return desc;
	}

	public List<ParameterDescription> getParameters() {
		return parameters;
	}

	public void setReturnType(int elemDesc_typeDesc_desc) {
		returnType = elemDesc_typeDesc_desc;
	}

	public int getReturnType() {
		return returnType;
	}
	
}
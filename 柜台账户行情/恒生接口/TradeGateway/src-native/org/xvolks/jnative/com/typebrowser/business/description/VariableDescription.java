package org.xvolks.jnative.com.typebrowser.business.description;

import org.xvolks.jnative.com.interfaces.ITypeInfo.Documentation;
import org.xvolks.jnative.com.interfaces.structures.VARDESC;

public class VariableDescription extends AbstractDescription {
	
	private final VARDESC description;
	private final Documentation documentation; 
	
	public VariableDescription(String name, Documentation documentation, VARDESC desc) {
		super(name);
		this.description = desc;
		this.documentation = documentation;
	}

	public VARDESC getDescription() {
		return description;
	}

	public Documentation getDocumentation() {
		return documentation;
	}

}

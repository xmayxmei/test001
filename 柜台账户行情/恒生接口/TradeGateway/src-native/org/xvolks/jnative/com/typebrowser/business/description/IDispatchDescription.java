package org.xvolks.jnative.com.typebrowser.business.description;

import java.util.ArrayList;
import java.util.List;

public class IDispatchDescription extends AbstractDescription {

	private final List<FunctionDescription> functionDescriptions = new ArrayList<FunctionDescription>();
	private final List<VariableDescription> variableDescriptions = new ArrayList<VariableDescription>();

	public IDispatchDescription(String name) {
		super(name);
	}

	public List<VariableDescription> getVariableDescriptions() {
		return variableDescriptions;
	}

	public List<FunctionDescription> getFunctionDescriptions() {
		return functionDescriptions;
	}

}

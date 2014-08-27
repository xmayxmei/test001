package org.xvolks.jnative.com.typebrowser.business.description;


public class AbstractDescription {
	private final String name;
	
	protected AbstractDescription(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}

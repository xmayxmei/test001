package org.xvolks.jnative.com.typebrowser.business.description;

public class ParameterDescription {
	public final String name;
	public final short flags;
	public final short type;
	public ParameterDescription(String name, short type, short flags) {
		super();
		this.name = name;
		this.flags = flags;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public short getFlags() {
		return flags;
	}
	public short getType() {
		return type;
	}
}

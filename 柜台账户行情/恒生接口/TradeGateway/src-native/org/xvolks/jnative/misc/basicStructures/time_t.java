package org.xvolks.jnative.misc.basicStructures;

import java.util.Date;

public class time_t extends LONG {

	public time_t(int value) {
		super(value);
	}
	
	public Date getAsDate() {
		System.err.println(getValue());
		return new Date(getValue() * 1000);
	}

}

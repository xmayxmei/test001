package org.xvolks.jnative.com.utils;

public class ByRef<T> {
	private T value;
	
	public ByRef(T value) {
		this.value = value;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return "ByRef<"+value.getClass()+">="+value;
	}
}

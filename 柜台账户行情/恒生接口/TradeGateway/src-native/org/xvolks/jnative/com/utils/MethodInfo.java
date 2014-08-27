/**
 * 
 */
package org.xvolks.jnative.com.utils;

/**
 * @author Marc DENTY (mdt) - May 28, 2008
 * $Id: MethodInfo.java,v 1.1 2008/05/28 18:36:48 mdenty Exp $
 *
 */
public class MethodInfo {
	private final String methodName;
	private final String methodSignature;
	private final int[] methodParamType;

	/**
     * @param methodName
     * @param methodSignature
     * @param methodParamType
     */
    public MethodInfo(String methodName, String methodSignature, int[] methodParamType) {
	    super();
	    this.methodName = methodName;
	    this.methodSignature = methodSignature;
	    this.methodParamType = methodParamType;
    }
	public String getMethodName() {
		return methodName;
	}
	public String getMethodSignature() {
		return methodSignature;
	}
	public int getMethodParamCount() {
		return methodParamType.length;
	}
	public int getMethodParamType(int index) {
		return methodParamType[index];
	}

}

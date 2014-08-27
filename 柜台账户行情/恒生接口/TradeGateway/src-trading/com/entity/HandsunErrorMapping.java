package com.entity;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

/**
 * <code>HandsunErrorMapping<code> 
 *
 * @author Colin, Jimmy
 * @since Handsun v0.0.1(June 26,2014)
 *
 */
@XStreamAlias("handsunError")
public class HandsunErrorMapping {
	@XStreamImplicit
	private List<Error> lstError;
	
	public List<Error> getLstError() {
		return lstError;
	}

	public void setLstError(List<Error> lstError) {
		this.lstError = lstError;
	}

	@XStreamAlias("error")
	public static class Error {
		@XStreamAsAttribute
		@XStreamAlias("functionId")
		private String functionId;
		@XStreamImplicit
		private List<Part> lstPart;
		public String getFunctionId() {
			return functionId;
		}
		public void setFunctionId(String functionId) {
			this.functionId = functionId;
		}
		public List<Part> getLstPart() {
			return lstPart;
		}
		public void setLstPart(List<Part> lstPart) {
			this.lstPart = lstPart;
		}
		
	}
	
	@XStreamAlias("part")
	public static class Part {
		@XStreamAsAttribute
		private String errCode;
		@XStreamAsAttribute
		private String errMsg;
		
		public String getErrCode() {
			return errCode;
		}
		public void setErrCode(String errCode) {
			this.errCode = errCode;
		}
		public String getErrMsg() {
			return errMsg;
		}
		public void setErrMsg(String errMsg) {
			this.errMsg = errMsg;
		}
	}
}

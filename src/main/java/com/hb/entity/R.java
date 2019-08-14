package com.hb.entity;



public class R {
	boolean state;

	String message;

	Object result;
	
	Object resultExt;
	
	public R(boolean state, String message, Object result) {
		this.state = state;
		this.message = message;
		this.result = result;
	}
	
	public R(boolean state, String message, Object result, Object resultExt) {
		this.state = state;
		this.message = message;
		this.result = result;
		this.resultExt = resultExt ;
	}	

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}	
	
	public Object getResultExt() {
		return resultExt;
	}

	public void setResultExt(Object resultExt) {
		this.resultExt = resultExt;
	}

	public String toString(){
		return "state:" + state + ", message:" + message + ", result:" + result;
	}
	
	public static final R FALSE = new R(false, "操作失败！", null);
	
	public static final R TRUE = new R(true, "操作成功！", null);
}

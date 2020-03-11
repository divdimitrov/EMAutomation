package concep.seleniumWD.core;

public class WaitValue {
	
	private int pageLoadTimeOut;
	private int ajaxTimeOut;
	private int elementTimeOut;
	
	public int getElementTimeOut() {
		return elementTimeOut;
	}
	public void setElementTimeOut(int elementTimeOut) {
		this.elementTimeOut = elementTimeOut;
	}
	public int getPageLoadTimeOut() {
		return pageLoadTimeOut;
	}
	public void setPageLoadTimeOut(int pageLoadTimeOut) {
		this.pageLoadTimeOut = pageLoadTimeOut;
	}
	public int getAjaxTimeOut() {
		return ajaxTimeOut;
	}
	public void setAjaxTimeOut(int ajaxTimeOut) {
		this.ajaxTimeOut = ajaxTimeOut;
	}
	

}

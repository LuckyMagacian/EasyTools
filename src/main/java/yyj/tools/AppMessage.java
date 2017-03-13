package com.lanxi.tools;
/**
 * 应用响应信息类
 * 用于与前端交互
 * @author 1
 */
public class AppMessage {
	private String errCode;		/**错误代码 0000->成功*/
	private String errMsg;		/**错误信息*/
	private Object content;		/**回复内容*/
	/**
	 * @return 错误代码 0000为成功
	 */
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
	public Object getContent() {
		return content;
	}
	public void setContent(Object content) {
		this.content = content;
	}
	
}

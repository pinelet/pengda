package com.pinelet.utp.manage.biz;

import javax.servlet.http.HttpSession;

public class LoginManage extends BusinessImpl {
	
	private String defaultDeviceConsume;
	
	public void setDefaultDeviceConsume(String s) {
		this.defaultDeviceConsume = s;
	}
	/**
	 * 对登录人员的会话进行管理
	 * @param session
	 */
	public void sessionManage(HttpSession session) {
		//增加默认耗材失效时间配置
		session.setAttribute("defaultconsume", defaultDeviceConsume);
	}
}

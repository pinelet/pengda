package com.pinelet.utp;

public interface Constants {

	/**
	 * 管理员角色
	 */
	public static final String ROLE_ADMIN = "R_ADMIN";
	public static final String ROLE_ADMIN_DEC = "系统管理员";
	
	/**
	 * 客户厂家角色编号
	 */
	public static final String ROLE_CLIENT = "R_CLIENT";
	public static final String ROLE_CLIENT_DEC = "客户厂家";
	
	/**
	 * 用户角色编号
	 */
	public static final String ROLE_USER = "R_USER";
	public static final String ROLE_USER_DEC = "用户";
	
	/**
	 * 耗材状态
	 */
	public static final String CONSUME_ALERT = "alert";//报警
	public static final String CONSUME_NORMAL = "normal";//正常
	public static final String CONSUME_LOST = "lost";//失效
	
	/**
	 * 设备服务状态 
	 */
	public static final String SERVICESTATUS_NORMAL = "1";//正常服务
	public static final String SERVICESTATUS_STOP = "0";//关闭服务
	
	/**
	 * 设备运行状态
	 */
	public static final String DEVICESTATUS_OPEN = "1";//启用
	public static final String DEVICESTATUS_CLOSE = "0";//停用
	
	/**
	 * 卡状态
	 */
	public static final String CARDSTATUS_NORMAL = "1";//正常
	public static final String CARDSTATUS_LOSE = "2";//挂失
	public static final String CARDSTATUS_CANCEL = "0";//作废
	
	/**
	 * 卡状态同步类型
	 */
	public static final String CARDSYNC_BLACK = "1";//黑名单同步
	public static final String CARDSYNC_WHITE = "2";//白名单（黑名单撤销）同步
	
	/**
	 * 信息同步结果
	 */
	public static final String SYNCRESULT_SUCCESS = "1";//通知成功
	public static final String SYNCRESULT_FAIL = "2";//通知失败
	public static final String SYNCRESULT_NOTODO = "0";//未通知
	
	/**
	 * 控制命令类型
	 */
	public static final String COMMANDTYPE_RUN = "1"; //运行命令
}

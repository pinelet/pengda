package com.pinelet.utp.core;

public interface CoreConstants {

	public static final String DEFAULT_CHARSET = "UTF-8";
	
	//心跳类型
	public static final String EX_HEARTBEST = "A";
	
	//心跳类型后继处理
	public static final String EX_HEARTBEST_FOLLOW = "AF";
	
	//消费交易类型
	public static final String EX_CONSUME = "B";
	
	//充值交易类型
	public static final String EX_CHARGE = "C";
	
	//报警交易类型
	public static final String EX_ALERT = "D";
	
	//身份验证类型
	public static final String EX_AUTH = "E";
	
	//设备监测类型
	public static final String EX_MONITOR = "F";
	
	//设备运行参数类型
	public static final String EX_MAINTENANCE = "G";
	
	/**
	 * 以下为支付类交易
	 */
	//服务器测试客户端连接状况
	public static final String EX_STATUS = "H";
	
	//服务器发送交易信息
	public static final String EX_PAY = "I";
	
	//设备请求直接交易信息(应该是设备上的终端做支付成功后，通知服务器交易结果)
	public static final String EX_DEPAY = "J";
	
	
	/**
	 * 报文回应处理结果
	 */
	public static final String RESULT_SUCCESS = "1";//处理成功
	public static final String RESULT_FAIL = "0";//处理失败
	
	/**
	 * 记录信息类型(D报警)
	 */
	//卡通讯错误
	public static final String ALERTTYPE_CARDCOMM_ERROR = "CARDCOM";
	//低压报警
	public static final String ALERTTYPE_LOWPRESSURE = "LOWPR";
	//高液位报警
	public static final String ALERTTYPE_HIGHPOSITION = "HIGHPO";
	//低液位报警
	public static final String ALERTTYPE_LOWPOSITION = "LOWPO";
	//制水
	public static final String ALERTTYPE_MAKEWATER = "MAKEW";
	//停售
	public static final String ALERTTYPE_OFFSALE = "OFFS";
	//售水
	public static final String ALERTTYPE_ONSALE = "ONS";

	/**
	 * 记录信息类型(F监测)
	 */
	//DEVTEM  TDSEL  PH
	public static final String MONITORTYPE_DEVICETEMPERATURE = "DEVTEM";
	
	public static final String MONITORTYPE_TDS = "TDSEL";
	
	public static final String MONITORTYPE_PH = "PH";
	
	/**
	 * 心跳同步类型
	 */
	public static final String HEARTBESTSYNCTYPE_NOTHING = "0";
	
	public static final String HEARTBESTSYNCTYPE_BLACK = "1";
	
	public static final String HEARTBESTSYNCTYPE_PARAMS = "2";
	
	
}

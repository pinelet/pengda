package com.pinelet.utp;

/**
 * 异常常量定义
 * 
 * @author Administrator
 * 
 */
public interface ErrorConstants extends Constants {

	// 用户名错误
	public static final String UERNAMEERROR = "UERNAMEERROR";

	// 密码错误
	public static final String PASSWORDERROR = "PASSWORDERROR";

	// 用户名/密码错误
	public static final String LOGINERROR = "LOGINERROR";

	// 解析用户菜单错误
	public static final String USERMENUERROR = "USERMENUERROR";

	// 发送成功
	public static final String SENDSUCC_KEY = "000000";

	// 有必输项未填写
	public static final String ANYINFOISNULL_KEY = "000001";

	// 短信服务商发送信息失败
	public static final String SENDMSGFAIL_KEY = "000017";

	// 短信服务商发送定时信息失败
	public static final String SENDSETTIMEMSGFAIL_KEY = "000018";

	// 客户端网络故障
	public static final String CLIENTNETWORKTROUBLE_KEY = "000101";

	// 短信服务商服务器返回错误,错误值XX
	public static final String SERVERRETERROR_KEY = "000305";

	// 目标电话号码不符合规则，电话号码必须是以0、1开头
	public static final String MOBILENOERROR_KEY = "000307";

	// 平台返回找不到超时的短信，该信息是否成功无法确定
	public static final String MSGLOST_KEY = "000997";

	// 由于客户端网络问题导致信息发送超时，该信息是否成功下发无法确定
	public static final String TIMEOUT_KEY = "000998";

	// 方法参数错误
	public static final String ILLEGALARGUMENT_KEY = "000201";

	// 数组越界
	public static final String ARRAYINDEXOUTOFBOUNDS_KEY = "000202";

	// 访问空对象
	public static final String NULLPOINTER_KEY = "000203";

	// 数据存储异常
	public static final String SQLEXCEPTION_KEY = "000204";

	// 号码格式错误
	public static final String MOBILEFORMATERROR_KEY = "000205";

	// 数据查询异常
	public static final String SQLQUERYEXEPION_KEY = "000206";

	// 其他异常
	public static final String OTHEREXCEPTION_KEY = "000207";

	// 短信模板未启用
	public static final String SMSMODELSTATEDISABLE_KEY = "000208";

	// 数据删除异常
	public static final String SQLDELETEEXCEPTION_KEY = "000209";

	// 数据插入异常
	public static final String SQLINSERTEXCEPTION_KEY = "000210";

	// 数据库修改异常
	public static final String SQLUPDATEEXEPION_KEY = "000211";

	// 客户端注册失败
	public static final String CLIENTREGISTEXCEPTION_KEY = "000212";

	// 客户端网络故障
	public static final String CLIENTNETEXCEPTION_KEY = "000213";
	
	
	// 生成压缩文件过程中,缺少必要文件
	public static final String FILENOTFOUNDEXCEPTION_KEY = "000301";

	// 生成压缩文件过程中,缺少必要文件
	public static final String FILENOTFOUNDEXCEPTION_VAL = "生成压缩文件过程中,缺少必要文件";

	// 生成压缩文件过程中发生异常
	public static final String MAKEZIPPACKEXCEPTION_KEY = "000302";

	// 生成压缩文件过程中发生异常
	public static final String MAKEZIPPACKEXCEPTION_VAL = "生成压缩文件过程中发生异常";

	// 接口上传文件写入异常
	public static final String MMS_IMGINPUTEXCEPTION_KAY = "000303";

	public static final String MMS_IMGINPUTEXCEPTION_VAL = "接口上传文件写入异常";

	// 发送成功
	public static final String SENDSUCC_VAL = "短信发送成功";

	// 有必输项未填写
	public static final String ANYINFOISNULL_VAL = "有必输项未填写";

	// 目标电话号码不符合规则，电话号码必须是以0、1开头
	public static final String MOBILENOERROR_VAL = "目标电话号码不符合规则，电话号码必须是以0、1开头";

	// 传入的方法参数错误
	public static final String ILLEGALARGUMENT_VAL = "外界传入的参数错误";

	// 数组越界
	public static final String ARRAYINDEXOUTOFBOUNDS_VAL = "数组索引超出最大值";

	// 访问空对象
	public static final String NULLPOINTER_VAL = "访问空对象";

	// 数据存储异常
	public static final String SQLEXCEPTION_VAL = "数据在存储时发生未知错误";

	// 其他异常
	public static final String OTHEREXCEPTION_VAL = "信息处理过程中发生未知错误";

	// 号码格式错误
	public static final String MOBILEFORMATERROR_VAL = "号码格式错误";

	// 数据查询异常
	public static final String SQLQUERYEXEPION_VAL = "数据查询异常";

	// 数据修改异常
	public static final String SQLUPDATEEXEPION_VAL = "数据修改异常";

	// 数据删除异常
	public static final String SQLDELETEEXCEPTION_VAL = "数据库删除信息异常";

	// 数据插入异常
	public static final String SQLINSERTEXCEPTION_VAL = "数据库插入信息异常";

	// 对应组织机构下没有相应模板
	public static final String MODELNOTINORG_VAL = "对应组织机构下没有相应模板";

	// 企业信息注册失败
	public static final String REGISTDETAILEXCEPTION_VAL = "企业信息注册失败";

	// 提交报文缺少头信息 No Message Header
	public static final String NOMESSAGEHEADER = "提交报文缺少头信息";

	// 没有意义的头信息 No header block for role next.
	public static final String NOHEADERBLOCK = "头信息缺失或无意义";

	// 头信息中包含未认证信息 No authentication info in header block.
	public static final String NOAUTHENTICATIONINFOINHEADERBLOCK = "头信息中包含未认证信息";

	/**
	 * 短信信息导入模板下载错误信息
	 */

	// 文件写入失败
	public static final String SMS_MSGUPLOAD_ERROR_001 = "SMS_MSGUPLOAD_ERROR_001";

	// 文件读取失败
	public static final String SMS_MSGUPLOAD_ERROR_002 = "SMS_MSGUPLOAD_ERROR_002";

	// 导入失败
	public static final String SMS_MSGUPLOAD_ERROR_003 = "SMS_MSGUPLOAD_ERROR_003";

	// 批次号为空
	public static final String SMS_MSGUPLOAD_ERROR_004 = "SMS_MSGUPLOAD_ERROR_004";

	// 必输项为空
	public static final String SMS_MSGUPLOAD_ERROR_005 = "第$行，第#列数据，不可为空！";

	// 发送时间格式错误
	public static final String SMS_MSGUPLOAD_ERROR_006 = "第$行，第#列数据，发送时间格式错误！";

	// 邮件接口异常
	public static final String SMS_EMAILREMIND_001 = "发送邮件异常！";

	public static final String SMS_EMAILREMIND_002 = "修改状态异常！";

	
}

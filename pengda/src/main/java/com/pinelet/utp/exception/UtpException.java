package com.pinelet.utp.exception;

public class UtpException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4005301798036950891L;

	private String errorMsgkey;

	private Object[] errorMsgParams;

	private boolean ifErrorMsgParams = false;

	/**
	 * 传入错误信息关键字的构造函数
	 * @param errorMsgkey 错误信息关键字
	 */
	public UtpException(String errorMsgkey) {
		this.errorMsgkey = errorMsgkey;
		ifErrorMsgParams = false;
	}

	/**
	 * 传入错误信息关键字的构造函数
	 * @param errorMsgkey 错误信息关键字
	 * @param cause 异常原因
	 */
	public UtpException(String errorMsgkey, Throwable cause) {
		super(cause);
		this.errorMsgkey = errorMsgkey;
		ifErrorMsgParams = false;
	}

	/**
	 * 传入错误信息关键字和错误信息参数的构造函数
	 * @param errorMsgkey 错误信息关键字
	 * @param errorMsgParams 错误信息参数
	 */
	public UtpException(String errorMsgkey, Object[] errorMsgParams) {
		this.errorMsgkey = errorMsgkey;
		this.errorMsgParams = errorMsgParams;
		ifErrorMsgParams = true;
	}

	/**
	 * 传入错误信息关键字和错误信息参数的构造函数.
	 * @param errorMsgkey 错误信息关键字
	 * @param errorMsgParams 错误信息参数
	 * @param cause 异常原因
	 */
	public UtpException(String errorMsgkey, Object[] errorMsgParams,
			Throwable cause) {
		super(cause);
		this.errorMsgkey = errorMsgkey;
		this.errorMsgParams = errorMsgParams;
		ifErrorMsgParams = true;
	}

	/**
	 * 获取错误信息关键字
	 * @return 错误信息关键字
	 */
	public String getErrorMsgkey() {
		return errorMsgkey;
	}

	/**
	 * 获取错误信息参数
	 * @return 错误信息参数
	 */
	public Object[] getErrorMsgParams() {
		return errorMsgParams;
	}

	/**
	 * 是否包含错误信息参数
	 * @return 是否包含错误信息参数
	 */
	public boolean hasErrorMsgParams() {
		return ifErrorMsgParams;
	}
	
	/**
	 * 判断是否是MisException的实例或其子类的实例
	 * @param ex 异常
	 * @return 判断结果
	 */
	public static boolean isSubofHapException(Exception ex) {
		if(null == ex) {
			return false;
		}
		return (ex instanceof UtpException);
	}
}

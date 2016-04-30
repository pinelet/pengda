package com.pinelet.utp.core.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.session.IoSession;
import org.springframework.util.StringUtils;

import com.pinelet.utp.core.CoreConstants;
import com.pinelet.utp.core.handler.PayServerHandler;
import com.pinelet.utp.exception.UtpException;

/**
 * H命令：接收设备回应是否在正常工作状态
 * @author fathead
 * &|H|1234,1|$  或者  &|H|1234,0|$
 */
public class ValidStatusService extends AbstractService {

	@Override
	public ReqMessage check(String[] request) throws SQLException {
		ReqMessage message = null;
		String[] data = StringUtils.split(request[2], ",");
		
		if (data != null && data.length == 2) {
			message = new ReqMessage();
			message.setStatusdata(data[1]);
		//机器ID
			message.setImei(data[0]);
		//时间戳
			message.setTimestamp(System.currentTimeMillis());
			message.setData(this.parseData(request[2]));
		}
		return message;
	}

	/**
	 * 从设备处接收请求如果为可用，则发起支付流程，否则页面提示设备不可用。
	 * 前提：session中已保存有支付相关信息
	 */
	@Override
	public String process(IoSession session, ReqMessage data) throws UtpException {
		//0、不可用 1、可用 2、ID错误
		// 修改当前状态
		PayServerHandler.sessionStatus.put(data.getImei(), data);
		return "";
	}

	@Override
	protected List parseData(String origin) {
		return new ArrayList<DataEntity>();	
	}

	@Override
	protected String buildReturnMessage(String data) {
		return "";
	}

}

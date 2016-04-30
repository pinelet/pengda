package com.pinelet.utp.core.service;

import java.sql.SQLException;
import java.util.List;

import org.apache.mina.core.session.IoSession;

import com.pinelet.utp.exception.UtpException;

/**
 * I命令：支付成功后通知设备进行相应操作（出水、充值）
 * @author fathead
 * @param
 *&|I|1234,1|$  或者  &|I|1234,0|$
 */
public class ConfirmService extends AbstractService {

	@Override
	public ReqMessage check(String[] request) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(IoSession session, ReqMessage data) throws UtpException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List parseData(String origin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String buildReturnMessage(String data) {
		// TODO Auto-generated method stub
		return null;
	}

}

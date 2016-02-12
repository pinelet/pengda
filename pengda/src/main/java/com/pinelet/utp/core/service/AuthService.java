package com.pinelet.utp.core.service;

import java.util.List;

import org.apache.mina.core.session.IoSession;

public class AuthService extends AbstractService {

	@Override
	public ReqMessage check(String[] request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<DataEntity> parseData(String origin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String process(IoSession session, ReqMessage data) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String buildReturnMessage(String data) {
		//TODO 生成校验和字段
		return "&|e|" + data + "|00|$";
	}

}

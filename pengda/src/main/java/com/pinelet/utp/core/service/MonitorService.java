package com.pinelet.utp.core.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinelet.utp.core.CoreConstants;
import com.pinelet.utp.dao.DevicerecordDAO;
import com.pinelet.utp.entity.Devicerecord;
import com.pinelet.utp.exception.UtpException;

public class MonitorService extends AbstractService {

	@Autowired
	DevicerecordDAO devicerecordDAO; 
	
	@Override
	protected String buildReturnMessage(String data) {
		return "&|f|" + data + "|00|$";
	}

	@Override
	public ReqMessage check(String[] request) throws SQLException {
		ReqMessage message = simpleCheck(request);
		if (!haveDevice(message)) return null;
		return message;
	}

	@Override
	protected List<Devicerecord> parseData(String origin) {
		String[] transData = StringUtils.splitPreserveAllTokens(origin, ",");
		if (transData.length % 7 != 0) {
			loger.error("{} error origin data -[{}]", CoreConstants.EX_CONSUME, origin);
			return null;
		}
		Devicerecord entity = new Devicerecord();
		entity.setDevicetemp(transData[0]);
		entity.setTdsel(transData[1]);
		entity.setPh(transData[2]);
		try {
			entity.setRecordtime(DateTransfer(transData[6]));
		} catch (ParseException e) {
			loger.error("{}'s recordtime -[{}]", CoreConstants.EX_MONITOR, transData[6]);
			return null;
		}
		List<Devicerecord> list = new ArrayList<Devicerecord>(1);
		list.add(entity);
		return list;
	}

	@Override
	public String process(IoSession session, ReqMessage data) throws UtpException {
		Devicerecord entity = (Devicerecord)data.getData().get(0);
		entity.setGprscode(data.getImei());
		entity.setRecordtype(CoreConstants.EX_MONITOR);
		try {
			devicerecordDAO.insert(entity);
		} catch (SQLException e) {
			loger.error("add monitor info fail !", e);
			throw new UtpException("add monitor info fail !", e);
		}
		return buildReturnMessage(CoreConstants.RESULT_SUCCESS);
	}

}

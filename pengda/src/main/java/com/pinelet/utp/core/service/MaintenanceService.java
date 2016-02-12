package com.pinelet.utp.core.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinelet.utp.core.CoreConstants;
import com.pinelet.utp.dao.DeviceworkDAO;
import com.pinelet.utp.entity.Devicework;
import com.pinelet.utp.exception.UtpException;

public class MaintenanceService extends AbstractService {

	@Autowired
	DeviceworkDAO deviceworkDAO;
	
	@Override
	protected String buildReturnMessage(String data) {
		return "&|g|" + data + "|00|$";
	}

	@Override
	public ReqMessage check(String[] request) throws SQLException {
		ReqMessage message = simpleCheck(request);
		if (!haveDevice(message)) return null;
		return message;
	}

	@Override
	protected List parseData(String origin) {
		String[] transData = StringUtils.splitPreserveAllTokens(origin, ",");
		if (transData.length % 2 != 0) {
			loger.error("{} error origin data -[{}]", CoreConstants.EX_MAINTENANCE, origin);
			return null;
		}
		Devicework entity = new Devicework();
		List list = new ArrayList(1);
		try {
			for (int i = 0; i < transData.length; i++) {
				BeanUtils.copyProperty(entity, transData[i].toLowerCase(), transData[++i]);
			}
		} catch (IllegalAccessException e) {
			loger.error("",e);
			if (loger.isDebugEnabled())e.printStackTrace();
			return null;
		} catch (InvocationTargetException e) {
			loger.error("",e);
			if (loger.isDebugEnabled())e.printStackTrace();
			return null;
		}
		list.add(entity);
		return list;
	}

	@Override
	public String process(IoSession session, ReqMessage data) throws UtpException {
		Devicework entity = (Devicework)data.getData().get(0);
		entity.setGprscode(data.getImei());
		try {
			deviceworkDAO.updateByPrimaryKeySelective(entity);
		} catch (SQLException e) {
			loger.error("add devicework info fail !", e);
			throw new UtpException("add devicework info fail !", e);
		}
		return buildReturnMessage(CoreConstants.RESULT_SUCCESS);
	}

}

/***********************************************************************
 * Module:  AbstractService.java
 * Author:  Administrator
 * Purpose: Defines the Class AbstractService
 ***********************************************************************/

package com.pinelet.utp.core.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.pinelet.utp.core.CoreConstants;
import com.pinelet.utp.dao.DeviceinfoDAO;
import com.pinelet.utp.entity.Deviceinfo;
import com.pinelet.utp.exception.UtpException;

/** @pdOid 28783df9-a409-4637-83d5-20e5da253ed0 */
public abstract class AbstractService implements IService {

	@Autowired
	@Qualifier("deviceinfoDAO")
	private DeviceinfoDAO deviceinfoDAO;

	Logger loger = LoggerFactory.getLogger(this.getClass());

	/**
	 * @param data
	 * @pdOid 6bf4de8c-7230-48a0-9c38-7ef7eca115ac
	 */
	public abstract ReqMessage check(String[] request) throws SQLException;

	/**
	 * @param data
	 * @pdOid f9ee5153-1840-4e9f-a54e-a241cbfe4004
	 */
	public abstract String process(IoSession session, ReqMessage data) throws UtpException;

	
	/**
	 * @param request
	 * @pdOid 94de52fe-cabb-4483-9de8-9b74dc3be427
	 */

	public String operation(IoSession session, java.lang.String[] request) {
		// 简单实现
		String response = null;
		ReqMessage req = null;
		try {
			if (request != null) {
				req = check(request);
				// 如果信息对象不存在，说明验证未通过。回应处理失败
				if (req == null || req.getData() == null)
					return buildReturnMessage(CoreConstants.RESULT_FAIL);
				response = process(session, req);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response = buildReturnMessage(CoreConstants.RESULT_FAIL);
		} catch (UtpException e) {
			e.printStackTrace();
			response = buildReturnMessage(CoreConstants.RESULT_FAIL);
		}
		return response;
	}

	/**
	 * 对转入的报文进行一般验证
	 * 
	 * @param request
	 */
	protected ReqMessage simpleCheck(String[] request) {
		ReqMessage message = null;
		if (request.length != 7)
			return message;
		// 获取版本号、交易码、身份码、数据以及校验和
		message = new ReqMessage();
		message.setVersion(request[1]);
		message.setImei(request[2]);
		message.setOptcode(request[3]);
		message.setData(parseData(request[4]));
		message.setChecksum(request[5]);
		// FIXME 校验和验证
		return message;
	}

	/**
	 * 对报文中的业务信息数据项进行解析
	 * 
	 * @param origin
	 *            原始报文
	 * @return data实体
	 */
	protected abstract List parseData(String origin);

	/**
	 * 根据回应内容组成回应报文
	 * 
	 * @param data
	 * @return
	 */
	protected abstract String buildReturnMessage(String data);

	/**
	 * 日期格式转化，将yyMMddHHmm转化为yyyy-MM-dd HH:mm
	 * 
	 * @param date
	 * @return
	 */
	protected static String DateTransfer(String aDate) throws ParseException {
		SimpleDateFormat fromformat = new SimpleDateFormat("yyMMddHHmm");
		SimpleDateFormat toformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return toformat.format(fromformat.parse(aDate));
	}

	/**
	 * 判断数据库中此设备是否存在
	 * 
	 * @param message
	 * @return
	 */
	protected boolean haveDevice(ReqMessage message) throws SQLException {
		Deviceinfo deviceinfo = deviceinfoDAO.selectByPrimaryKey(message
				.getImei());
		if (deviceinfo == null) {
			loger.error("device {} is not exist.", message.getImei());
			return false;
		}
		return true;
	}
}
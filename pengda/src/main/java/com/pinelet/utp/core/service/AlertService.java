package com.pinelet.utp.core.service;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinelet.utp.core.CoreConstants;
import com.pinelet.utp.dao.DeviceworkDAO;
import com.pinelet.utp.entity.Devicework;
import com.pinelet.utp.exception.UtpException;

/**
 * D命令：报警命令
 * @author Administrator
 *&|1|867278001345457|D|0102,1302042033|00|$
 *|		|		|		|		|	card通讯错误	|	低压报警	|	高液位报警	|	低液位报警	|
 *|		|		|		|		|				|	制水		|	停售			|	售水			|
 */
public class AlertService extends AbstractService {

	Logger loger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	DeviceworkDAO deviceworkDAO;
	
	@Override
	public ReqMessage check(String[] request) {
		return simpleCheck(request);
	}

	@Override
	protected List<DataEntity> parseData(String origin) {
		String[] transData = StringUtils.splitPreserveAllTokens(origin, ",");
		if (transData.length != 2) {
			loger.error("{} error origin data -[{}]", CoreConstants.EX_ALERT, origin);
			return null;
		}
		AlertEntity entity = null;
		try {
			entity = parseAlert(transData[0]);
			entity.setOperationTime(DateTransfer(transData[1]));
		} catch (ParseException e) {
			loger.error("{}'s error origin data -[{}]", CoreConstants.EX_ALERT, origin);
			return null;
		} catch (Exception e) {
			loger.error("{}'s error origin data -[{}]", CoreConstants.EX_ALERT, origin);
			return null;
		}
		List<DataEntity> entitys = new ArrayList<DataEntity>();
		entitys.add(entity);
		return entitys;
	}
	
	/**
	 *|		|		|		|		|	card通讯错误	|	低压报警	|	高液位报警	|	低液位报警	|
	 *|		|		|		|		|				|	制水		|	停售			|	售水			|
	 * @return
	 */
	private AlertEntity parseAlert(String aAlertData) {
		AlertEntity alert = new AlertEntity();
		String binAlerts = "000000000000000" + Integer.toBinaryString(Integer.decode("0x" + aAlertData));
		char[] charAlerts = binAlerts.substring(binAlerts.length() - 16).toCharArray();
		if (charAlerts[4] == '1') alert.setCommAlert(true);  
		if (charAlerts[5] == '1') alert.setLowpressureAlert(true);
		if (charAlerts[6] == '1') alert.setHighpositionAlert(true);
		if (charAlerts[7] == '1') alert.setLowpositionAlert(true);
		if (charAlerts[13] == '1') alert.setMakewater(true);
		if (charAlerts[14] == '1') alert.setOffsale(true);
		if (charAlerts[15] == '1') alert.setOnsale(true);	
		return alert;
	}

	@Override
	public String process(IoSession session, ReqMessage aDate) throws UtpException {
		AlertEntity alert = (AlertEntity)aDate.getData().get(0);
		Devicework work = new Devicework();
		work.setGprscode(aDate.getImei());
		work.setCardcom(alert.isCommAlert());
		work.setLowpressure(alert.isLowpressureAlert());
		work.setHighposition(alert.isHighpositionAlert());
		work.setLowposition(alert.isLowpositionAlert());
		work.setMakewater(alert.isMakewater());
		work.setOffsale(alert.isOffsale());
		work.setOnsale(alert.isOnsale());
		try {
			deviceworkDAO.updateByPrimaryKeySelective(work);
			return buildReturnMessage(CoreConstants.RESULT_SUCCESS);
		} catch (Exception e) {
			loger.error("update devicework fail !", e);
			throw new UtpException("update devicework fail !", e);
		}
	}

	/**
	 * 构建回应报文
	 * @param data
	 * @return
	 */
	@Override
	protected String buildReturnMessage(String data) {
		//TODO 生成校验和字段
		return "&|d|" + data + "|00|$";
	}

}

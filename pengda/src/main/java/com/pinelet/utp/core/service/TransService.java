package com.pinelet.utp.core.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.pinelet.utp.ErrorConstants;
import com.pinelet.utp.core.CoreConstants;
import com.pinelet.utp.dao.CardinfoDAO;
import com.pinelet.utp.dao.CardsyncDAO;
import com.pinelet.utp.dao.DeviceinfoDAO;
import com.pinelet.utp.dao.TransinfoDAO;
import com.pinelet.utp.entity.Cardinfo;
import com.pinelet.utp.entity.Deviceinfo;
import com.pinelet.utp.entity.Transinfo;
import com.pinelet.utp.exception.UtpException;

/**
 * B命令：上发消费数据
 * @author xiaosonggong
 *&|1|867278001345457|B|70614579,351,6834,1302041943|00|$
 */
public class TransService extends AbstractService {
	
	@Autowired
	TransinfoDAO transinfoDAO;
	
	@Autowired
	private CardinfoDAO cardDAO;

	
	@Autowired
	private DeviceinfoDAO deviceinfoDAO ;
	
	
	@Override
	public ReqMessage check(String[] request) throws SQLException {
		ReqMessage message = simpleCheck(request);
		if (!haveDevice(message)) return null;
		return message;
	}

	/**
	 * 对用户的消费信息进行解析
	 */
	@Override
	protected List parseData(String origin) {
		String[] transData = StringUtils.splitPreserveAllTokens(origin, ",");
		if (transData.length % 4 != 0) {
			loger.error("{} error origin data -[{}]", CoreConstants.EX_CONSUME, origin);
			return null;
		}
		TransEntity entity = null;
		List<DataEntity> entitys = new ArrayList<DataEntity>();
		for (int i = 0;  i < transData.length; ) {
			entity = new TransEntity();
			entity.setCardno(transData[i++]);
			entity.setSum(transData[i++]);
			entity.setBalance(transData[i++]);
			//将yyMMddHHmm格式转化为yyyy-MM-dd HH:mm
			try {
				entity.setTranstime(DateTransfer(transData[i++]));
			} catch (ParseException e) {
				loger.error("{}'s transtime -[{}]", CoreConstants.EX_CONSUME, transData[i-1]);
				return null;
			}
			entitys.add(entity);
		}
		return entitys;
	}

	@Override
	public String process(IoSession session, ReqMessage message) throws UtpException {
		try {
			//交易表中记录交易信息
			TransEntity entity = null;
			Transinfo info = null;
			Cardinfo cardinfo = null;
			List<DataEntity> list = message.getData();
			String pid = deviceinfoDAO.selectByPrimaryKey(message.getImei()).getPid();
			for(int i = 0; i < list.size(); i++) {
				entity = (TransEntity)list.get(i);
				info = new Transinfo();
				info.setPid(pid);
				info.setGprscode(message.getImei());//gprs号
				info.setTransid(System.currentTimeMillis() + RandomStringUtils.randomNumeric(4));//流水号
				info.setCardcode(entity.getCardno());//卡号
				info.setTranstype(message.getOptcode());//交易类型：B
				info.setExpenditure(new BigDecimal(entity.getSum()).movePointLeft(2));//消费金额,并将单位从分转为元
				info.setCardbalance(new BigDecimal(entity.getBalance()).movePointLeft(2));//卡余额,并将单位从分转为元
				info.setTransdate(entity.getTranstime());//交易时间 格式在check中已转为yyyy-MM-dd HH:mm 
				info.setVersion(message.getVersion());//交易版本
				transinfoDAO.insert(info);
				//更新卡信息中最后卡余额
				updateCardCurrentBalance(pid, message.getImei(), entity.getCardno(), info.getCardbalance(), entity.getTranstime());
			}
		} catch (Exception e) {
			loger.error("update transinfo fail !", e);
			throw new UtpException("update transinfo fail !", e);
		}
		
		return buildReturnMessage(CoreConstants.RESULT_SUCCESS);
	}
	
	/**
	 * 根据GPRS号对应的设备所在用户,更新此用户下的卡最终余额
	 * @param gprscode 设备GPRS号
	 * @param cardcode  卡号
	 * @param balance  最终余额
	 * @param transtime 交易时间
	 * @return pid 人员账户号
	 */
	public void updateCardCurrentBalance(String pid, String gprscode, String cardcode, BigDecimal balance, String transtime) throws UtpException {
		Cardinfo card  = null;
		try {
			card = new Cardinfo();
			card.setPid(pid);
			card.setBalance(balance);
			card.setCardcode(cardcode);
			card.setTransdate(transtime);
			cardDAO.updateByPrimaryKeySelective(card);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UtpException(ErrorConstants.SQLEXCEPTION_KEY, e);
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
		return "&|b|" + data + "|00|$";
	}

}

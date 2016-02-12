package com.pinelet.utp.core.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinelet.utp.Constants;
import com.pinelet.utp.core.CoreConstants;
import com.pinelet.utp.dao.CardsyncDAO;
import com.pinelet.utp.dao.CommandinfoDAO;
import com.pinelet.utp.entity.Cardsync;
import com.pinelet.utp.entity.CardsyncExample;
import com.pinelet.utp.entity.Commandinfo;
import com.pinelet.utp.entity.CommandinfoExample;
import com.pinelet.utp.exception.UtpException;

public class HeartBeatService extends AbstractService {

	@Autowired
	CardsyncDAO cardsyncDAO;
	
	@Autowired
	CommandinfoDAO commandDAO;
	
	@Override
	public ReqMessage check(String[] request) {
		return simpleCheck(request);
	}

	@Override
	public String process(IoSession session, ReqMessage message) throws UtpException {
		String resdata = null;
		try {
			//查询数据库判断是否存在控制指令
			resdata = getControlCommand(session, message.getImei());
			//如果不存在控制指令，则查询数据库判断是否存在需要更新的黑名单
			if (CoreConstants.HEARTBESTSYNCTYPE_NOTHING.equals(resdata))
				resdata = getBlackCard(session, message.getImei());
		} catch (Exception e) {
			loger.error("select sync card  or  command is fail.",e);
			if (loger.isDebugEnabled()) e.printStackTrace();
			throw new UtpException("select sync card  or  command is fail.",e);
		}
		//如果黑名单删除或增加也不存在，则回应无数据(50组)
		return buildReturnMessage(resdata);
	}

	/**
	 * 心跳处理不需解析
	 */
	@Override
	protected List<DataEntity> parseData(String origin) {
		return new ArrayList<DataEntity>();
	}
	
	/**
	 * 生成黑名单信息字串
	 * @param gprs 设备的GPRS号
	 * @return 1,卡号1,1,卡号2,1,卡号3,2,卡号4
	 */
	private String getBlackCard(IoSession session, String gprs) throws SQLException {
		//查询前50个黑名单/白名单信息
		CardsyncExample example = new CardsyncExample();
		
		//gprs号且状态不为通知成功
		example.createCriteria().andGprscodeEqualTo(gprs).andSyncresultNotEqualTo(Constants.SYNCRESULT_SUCCESS);
		List<Cardsync> cardlist = cardsyncDAO.selectByExamplePage(example, 0, 50);
		StringBuffer cardliststr = new StringBuffer("1|");
		if (cardlist != null && cardlist.size() > 0) {
			Cardsync sync = null;
			for (int i = 0; i < cardlist.size(); i++) {
				sync = cardlist.get(i);
				cardliststr.append(sync.getSynctype())
							.append(',')
							.append(sync.getCardcode())
							.append(',');
			}
			
			//将gprs号记入session,此次同步的卡信息记入session
			session.setAttribute("sync", cardlist);
		}
		else return CoreConstants.HEARTBESTSYNCTYPE_NOTHING;
		return cardliststr.substring(0, cardliststr.length() - 1);
	}
	
	/**
	 * 生成控制命令信息字串
	 * @param gprs 设备的GPRS号
	 * @return
	 */
	private String getControlCommand(IoSession session, String gprs) throws SQLException {
		CommandinfoExample example = new CommandinfoExample();
		example.createCriteria().andGprscodeEqualTo(gprs).andSyncresultEqualTo(Constants.SYNCRESULT_NOTODO);
		example.setOrderByClause("recordtime");
		List<Commandinfo> list = commandDAO.selectByExample(example);
		if (list == null || list.size() == 0) return CoreConstants.HEARTBESTSYNCTYPE_NOTHING;
		else {
			//将此次同步的命令信息记入session
			session.setAttribute("sync", list);
			return CoreConstants.HEARTBESTSYNCTYPE_PARAMS + "|" + list.get(0).getCommandcontent();
		}
	}

	/**
	 * 构建回应报文
	 * @param data
	 * @return
	 */
	protected String buildReturnMessage(String data) {
		//TODO 生成校验和字段
		return "&|a|" + data + "|00|$";
	}
}

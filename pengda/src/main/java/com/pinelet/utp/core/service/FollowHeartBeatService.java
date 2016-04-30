package com.pinelet.utp.core.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinelet.utp.Constants;
import com.pinelet.utp.core.CoreConstants;
import com.pinelet.utp.dao.CardsyncDAO;
import com.pinelet.utp.dao.CommandinfoDAO;
import com.pinelet.utp.entity.Cardsync;
import com.pinelet.utp.entity.Commandinfo;

public class FollowHeartBeatService extends AbstractService {

	@Autowired
	CardsyncDAO cardsyncDAO;
	
	@Autowired
	CommandinfoDAO commandDAO;

	Logger loger = LoggerFactory.getLogger(this.getClass());

	@Override
	protected String buildReturnMessage(String data) {
		// TODO Auto-generated method stub
		return "";
	}

	@Override
	public ReqMessage check(String[] request) throws SQLException {
		ReqMessage message = new ReqMessage();
		String str = request[2];
		if (CoreConstants.RESULT_SUCCESS.equals(str))
			message.setStatusdata(request[2]);
			message.setData(this.parseData(str));
		return message;
	}

	@Override
	protected List<DataEntity> parseData(String origin) {

		return new ArrayList<DataEntity>();
	}

	/**
	 * 更新卡同步状态为已通知
	 */
	@Override
	public String process(IoSession session, ReqMessage data) {
		// 判断session是否存在gprs号,存在则更新session中所有黑名单卡的同步状态
		Object temp = session.getAttribute("sync");
		if (temp == null || !CoreConstants.RESULT_SUCCESS.equals(data.getStatusdata()))
			return "";
		// TODO 需要判断此处temp对象list存入是什么对象
		if (temp instanceof List<?>) {
			List synclist = (List) temp;
			Cardsync cardsync = null;
			Commandinfo comsync = null;
			//如果为黑名单卡同步
			try {
				if (synclist.get(0) instanceof Cardsync) {
					for (int i = 0; i < synclist.size(); i++) {
						cardsync = (Cardsync)synclist.get(i);
						cardsync.setSyncresult(Constants.SYNCRESULT_SUCCESS);
						cardsync.setSynctime(System.currentTimeMillis());
						cardsyncDAO.updateByPrimaryKeySelective(cardsync);
					}	
				}
				//如果为参数命令同步
				else if (synclist.get(0) instanceof Commandinfo) {
						comsync = (Commandinfo)synclist.get(0);
						comsync.setSyncresult(Constants.SYNCRESULT_SUCCESS);
						commandDAO.updateByPrimaryKeySelective(comsync);
				}
			} catch (SQLException e) {
				loger.error("sync card syncresult to success is fail.", e);
				if (loger.isDebugEnabled())
					e.printStackTrace();
			}
		}
		session.removeAttribute("sync");
		return "";
	}

}

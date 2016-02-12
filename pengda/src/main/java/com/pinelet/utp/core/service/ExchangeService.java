package com.pinelet.utp.core.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinelet.utp.core.CoreConstants;

public class ExchangeService extends AbstractService {

	Logger loger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public ReqMessage check(String[] request) {
		return simpleCheck(request);
	}

	/**
	 * 对用户的充值信息进行解析
	 */
	@Override
	protected List<DataEntity> parseData(String origin) {
		String[] transData = StringUtils.splitPreserveAllTokens(origin, ",");
		if (transData.length % 4 != 0) {
			loger.error("error origin data -[{}]", origin);
			return null;
		}
		TransEntity entity = null;
		List<DataEntity> entitys = new ArrayList<DataEntity>();
		SimpleDateFormat fromformat = new SimpleDateFormat("yyMMddHHmm");
		SimpleDateFormat toformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		for (int i = 0; transData.length < i; ) {
			entity = new TransEntity();
			entity.setCardno(transData[i++]);
			entity.setSum(transData[i++]);
			entity.setBalance(transData[i++]);
			//将yyMMddHHmm格式转化为yyyy-MM-dd HH:mm
			try {
				entity.setTranstime(toformat.format(fromformat.parse(transData[i++])));
			} catch (ParseException e) {
				loger.error("{}'s transtime -[{}]", CoreConstants.EX_CONSUME, transData[i-1]);
				entity.setTranstime("");
			}
			entitys.add(entity);
		}
		return entitys;
	}

	@Override
	public String process(IoSession session, ReqMessage data) {
		//TODO 根据需求记录或更新卡信息？记录充值交易信息
		return null;
	}

	@Override
	protected String buildReturnMessage(String data) {
		//TODO 生成校验和字段
		return "&|c|" + data + "|00|$";
	}

}

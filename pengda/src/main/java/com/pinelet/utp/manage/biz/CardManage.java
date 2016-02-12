package com.pinelet.utp.manage.biz;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pinelet.utp.Constants;
import com.pinelet.utp.ErrorConstants;
import com.pinelet.utp.dao.CardinfoDAO;
import com.pinelet.utp.dao.CardsyncDAO;
import com.pinelet.utp.dao.CardsyncDAOImpl;
import com.pinelet.utp.dao.DeviceinfoDAO;
import com.pinelet.utp.entity.Cardinfo;
import com.pinelet.utp.entity.CardinfoExample;
import com.pinelet.utp.entity.CardinfoKey;
import com.pinelet.utp.entity.Cardsync;
import com.pinelet.utp.entity.CardsyncExample;
import com.pinelet.utp.entity.Deviceinfo;
import com.pinelet.utp.entity.DeviceinfoExample;
import com.pinelet.utp.entity.CardinfoExample.Criteria;
import com.pinelet.utp.exception.UtpException;

public class CardManage extends BusinessImpl {

	Logger loger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("cardDAO")
	private CardinfoDAO cardinfoDAO ;

	@Autowired
	@Qualifier("cardsyncDAO")
	private CardsyncDAO cardsyncDAO ;
	
	@Autowired
	@Qualifier("deviceinfoDAO")
	private DeviceinfoDAO deviceinfoDAO ;
	
	/**
	 * 新增加卡信息
	 * @param card
	 * @throws UtpException
	 */
	public void addCard(Cardinfo card) throws UtpException {
		try {
			//将卡信息加入
			cardinfoDAO.insertSelective(card);
			//判断卡状态如果为挂失/黑名单
			if (Constants.CARDSTATUS_LOSE.equals(card.getCardstatus())) 
				//同步黑名单
				syncBlack(card);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UtpException(ErrorConstants.SQLEXCEPTION_KEY, e);
		}
	}
	
	/**
	 * 删除卡信息
	 * @param pid
	 * @param cardcode
	 * @throws UtpException
	 */
	public void delCard(Cardinfo card) throws UtpException {
		try {
			cardinfoDAO.deleteByPrimaryKey(card);
			CardsyncExample example = new CardsyncExample();
			example.createCriteria()
				.andCardcodeEqualTo(card.getCardcode())
				.andPidEqualTo(card.getPid());
			cardsyncDAO.deleteByExample(example);
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UtpException(ErrorConstants.SQLEXCEPTION_KEY, e);
		}
	}
	/**
	 * 修改卡信息
	 * @param card
	 * @throws UtpException
	 */
	public void updateCard(Cardinfo card) throws UtpException {
		String cardStatus = null;
		try {
			//如果提供卡状态为正常，数据库中卡状态为黑名单，则需要更新黑名单为白名单
			if (Constants.CARDSTATUS_NORMAL.equals(card.getCardstatus())) {
				cardStatus = cardinfoDAO.selectByPrimaryKey(card).getCardstatus();
				if (Constants.CARDSTATUS_LOSE.equals(cardStatus)) {
					//同步更新为白名单
					syncWhite(card);
				}
			}
			//如果提供卡状态为挂失，数据库中卡状态为正常，则需要新增黑名单
			if (Constants.CARDSTATUS_LOSE.equals(card.getCardstatus())) {
				cardStatus = cardinfoDAO.selectByPrimaryKey(card).getCardstatus();
				if (Constants.CARDSTATUS_NORMAL.equals(cardStatus)) {
					//同步更新为黑名单
					syncBlack(card);
				}
			}
			cardinfoDAO.updateByPrimaryKeySelective(card);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UtpException(ErrorConstants.SQLEXCEPTION_KEY, e);
		}
	}
	
	/**
	 * 在同步表中，更新已同步黑名单为白名单，未同步的黑名单删除
	 * @param card
	 * @throws SQLException
	 */
	public void syncWhite(Cardinfo card) throws SQLException {
		CardsyncExample delExample = new CardsyncExample();
		CardsyncExample updateExample = new CardsyncExample();
		Cardsync record = new Cardsync();
		//删除未同步的黑名单
		delExample.createCriteria()
				.andPidEqualTo(card.getPid())
				.andCardcodeEqualTo(card.getCardcode())
				.andSynctypeEqualTo(Constants.CARDSYNC_BLACK)
				.andSyncresultNotEqualTo(Constants.SYNCRESULT_SUCCESS);
		cardsyncDAO.deleteByExample(delExample);
		//更新已同步黑名单为白名单
		updateExample.createCriteria()
				.andPidEqualTo(card.getPid())
				.andCardcodeEqualTo(card.getCardcode())
				.andSynctypeEqualTo(Constants.CARDSYNC_BLACK)
				.andSyncresultEqualTo(Constants.SYNCRESULT_SUCCESS);
		record.setSynctype(Constants.CARDSYNC_WHITE);
		record.setSyncresult(Constants.SYNCRESULT_NOTODO);
		cardsyncDAO.updateByExampleSelective(record, updateExample);
	}
	
	/**
	 * 根据卡号将此用户下设备的黑名单同步
	 * @param card
	 * @throws SQLException
	 */
	public void syncBlack(Cardinfo card) throws SQLException {
		CardsyncExample syncExample = new CardsyncExample();
		DeviceinfoExample deviceExample = new DeviceinfoExample();
		List<Deviceinfo> devicelist = null;
		SqlMapClient client = null;
			//查询同步表是否已存在黑名单
			syncExample.createCriteria()
				.andPidEqualTo(card.getPid())
				.andCardcodeEqualTo(card.getCardcode())
				.andSynctypeEqualTo(Constants.CARDSYNC_BLACK)
				.andSyncresultEqualTo(Constants.SYNCRESULT_NOTODO);
			//如不存在则加入
			try {
				if ( cardsyncDAO.countByExample(syncExample) == 0) {
					//查询此用户下的所有服务中的设备列表
					deviceExample.createCriteria()
							.andPidEqualTo(card.getPid());
					devicelist = deviceinfoDAO.selectByExample(deviceExample);
					//将所有对应的下发记录放入同步表
					if (devicelist == null || devicelist.size() == 0) return;
					client = ((CardsyncDAOImpl)cardsyncDAO).getSqlMapClient();
					client.startBatch();
					Cardsync sync = null;
					Deviceinfo device = null;
					for (int i = 0; i < devicelist.size(); i++) {
						sync = new Cardsync();
						device = devicelist.get(i);
						sync.setCardcode(card.getCardcode());
						sync.setGprscode(device.getGprscode());
						sync.setId(RandomStringUtils.randomNumeric(40));
						sync.setPid(card.getPid());
						sync.setRecordtime(System.currentTimeMillis());
						sync.setSyncresult(Constants.SYNCRESULT_NOTODO);
						sync.setSynctype(Constants.CARDSYNC_BLACK);
						cardsyncDAO.insert(sync);
					}
					client.executeBatch();
				}
			} catch (SQLException e) {
				loger.error(e.getMessage());
				throw e;
			} finally {

			}
	}
	
	/**
	 * 根据条件查询卡信息列表
	 * @param card 目前查询条件为卡号与卡状态(默认用户号)
	 * @return
	 */
	public Map<String, Object> selectCardList(Cardinfo card) throws UtpException {
		CardinfoExample example = new CardinfoExample();
		List<Cardinfo> list = null;
		Map<String, Object> result = new HashMap<String, Object>(2);
		int count = 0;
		Criteria criteria = example.createCriteria().andPidEqualTo(card.getPid());
		if (card.getCardcode() != null && !"".equals(card.getCardcode().trim()))
			criteria.andCardcodeEqualTo(card.getCardcode());
		if (card.getCardstatus() != null && !"".equals(card.getCardstatus()))
			criteria.andCardstatusEqualTo(card.getCardstatus());
		try {
			count = cardinfoDAO.countByExample(example);
			list = cardinfoDAO.selectByExamplePage(example,  card.getRows()*(card.getPage()-1), card.getRows());
			result.put("total", count);
			result.put("rows", list);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UtpException(ErrorConstants.SQLQUERYEXEPION_KEY, e);
		}
		return result;
	}
	
	/**
	 * 根据指定的卡状态与用户查询卡列表
	 * @param aPid 用户账户
	 * @param aCardStatus 卡状态
	 * @return
	 */
	public List<Cardinfo> selectCardList(String aPid, String aCardStatus)  throws UtpException {
		List<Cardinfo> list = null;
		CardinfoExample example = new CardinfoExample();
		example.createCriteria().andPidEqualTo(aPid).andCardstatusEqualTo(aCardStatus);
		try {
			list = cardinfoDAO.selectByExample(example);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UtpException(ErrorConstants.SQLQUERYEXEPION_KEY, e);
		}
		return list;
	}
	

	/**
	 * 根据卡号和用户号双主键查询卡信息
	 * @param pid
	 * @param cardcode
	 * @return
	 * @throws UtpException
	 */
	public Cardinfo selectCard(String pid, String cardcode) throws UtpException {
		CardinfoKey key = new CardinfoKey();
		key.setPid(pid);
		key.setCardcode(cardcode);
		Cardinfo card = null;
		try {
			card = cardinfoDAO.selectByPrimaryKey(key);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UtpException(ErrorConstants.SQLEXCEPTION_KEY, e);
		}
		return card;
	}
}

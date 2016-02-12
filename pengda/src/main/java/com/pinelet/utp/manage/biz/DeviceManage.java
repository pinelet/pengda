package com.pinelet.utp.manage.biz;

import java.sql.SQLException;
import java.util.Date;
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
import com.pinelet.utp.dao.CardsyncDAO;
import com.pinelet.utp.dao.CardsyncDAOImpl;
import com.pinelet.utp.dao.DeviceinfoDAO;
import com.pinelet.utp.dao.DeviceworkDAO;
import com.pinelet.utp.dao.spec.QueryDAOSpec;
import com.pinelet.utp.entity.AlertResult;
import com.pinelet.utp.entity.Cardinfo;
import com.pinelet.utp.entity.Cardsync;
import com.pinelet.utp.entity.Deviceinfo;
import com.pinelet.utp.entity.DeviceinfoExample;
import com.pinelet.utp.entity.Devicework;
import com.pinelet.utp.exception.UtpException;
import com.pinelet.utp.manage.form.ModelImpl;
import com.pinelet.utp.util.DateUtil;

public class DeviceManage extends BusinessImpl {

	Logger loger = LoggerFactory.getLogger(this.getClass());
	
	private int defaultDeadline ;
	
	private int defaultDeviceConsume;
	
	public void setDefaultDeadline (int time) {
		defaultDeadline = time;
	}
	
	public void setDefaultDeviceConsume (int time) {
		defaultDeviceConsume = time;
	}
	@Autowired
	private DeviceinfoDAO deviceinfoDAO ;
	
	@Autowired
	private CardManage cardManage;
	
	@Autowired
	@Qualifier("cardsyncDAO")
	private CardsyncDAO cardsyncDAO ;
	
	@Autowired
	DeviceworkDAO deviceworkDAO;
	
	@Autowired
	QueryDAOSpec queryDAOSpec;
	/**
	 * 取得某用户的所有有效期内的，服务状态为正常的设备列表
	 * @param pid
	 * @param model
	 * @return
	 * @throws UtpException
	 */
	public List<Deviceinfo> getDeviceList(String pid, ModelImpl model) throws UtpException {
		DeviceinfoExample example = new DeviceinfoExample();
		//有效期内&服务状态为正常
		example.createCriteria()
				.andPidEqualTo(pid)
				.andDeadlineGreaterThan(DateUtil.getFormatDate())
				.andStatusEqualTo(Constants.SERVICESTATUS_NORMAL);

		List<Deviceinfo> deviceinfoList = null;
		try {
			deviceinfoList = deviceinfoDAO.selectByExamplePage(example, model.getRows()*(model.getPage()-1), model.getRows());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UtpException(ErrorConstants.SQLQUERYEXEPION_KEY, e);
		}
		return deviceinfoList;
	}
	
	/**
	 * 转化耗材失效时间为距离失效天数
	 * @deprecated
	 */
	
	public void transferConsumDate(List<Deviceinfo> list) {
		Deviceinfo device = null;
		long rodate;
		long ppdate;
		long carbondate;
		long minedate;

		for (int i = 0; i < list.size(); i++) {
			device = list.get(i);
			if (device.getRodate() != null && !"".equals(device.getRodate())) {
				rodate = DateUtil.compare(device.getRodate());
				ppdate = DateUtil.compare(device.getPpdate());
				carbondate = DateUtil.compare(device.getCarbondate());
				minedate = DateUtil.compare(device.getMinedate());
				device.setRodate(String.valueOf(rodate));
				device.setPpdate(String.valueOf(ppdate));
				device.setCarbondate(String.valueOf(carbondate));
				device.setMinedate(String.valueOf(minedate));
			}
		}
	}
		

	/**
	 * 取得当前人员的所有有效期内的，服务状态为正常的设备数量
	 * @param pid
	 * @return
	 */
	public int getDeviceCount(String pid) throws UtpException {
		DeviceinfoExample example = new DeviceinfoExample();
		example.createCriteria().andPidEqualTo(pid).andDeadlineGreaterThan(DateUtil.getFormatDate())
		.andStatusEqualTo(Constants.SERVICESTATUS_NORMAL);
		int total  = 0;
		try {
			total = deviceinfoDAO.countByExample(example);
		} catch (SQLException e) {
			throw new UtpException(ErrorConstants.SQLQUERYEXEPION_KEY, e);
		}
		return total;
	}
	
	/**
	 * 新增设备（根据默认设定）
	 * @param device
	 * @throws UtpException
	 */
	public void addDevice(Deviceinfo device) throws UtpException {
		List<Cardinfo> list = null;
		//设定默认有效期时间
		long deadtime = 1000L*60*60*24*defaultDeadline;
		device.setDeadline(DateUtil.formatDate(new Date(System.currentTimeMillis() + deadtime), null));
		//设定默认服务状态为正常
		device.setDevicestatus(Constants.DEVICESTATUS_OPEN);
		//设定默认设备状态为启用
		device.setStatus(Constants.SERVICESTATUS_NORMAL);
		//同时增加设备横表空记录
		Devicework work = new Devicework();
		work.setGprscode(device.getGprscode());
		try {
			deviceinfoDAO.insert(device);
			deviceworkDAO.insertSelective(work);
			//还需要查询是否有挂失卡
			list = cardManage.selectCardList(device.getPid(), Constants.CARDSTATUS_LOSE);
			if (list != null && list.size() > 0) {
				//增加黑名单同步信息
				addBlack(device, list);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new UtpException(ErrorConstants.SQLEXCEPTION_KEY, e);
		} 
	}
	
	/**
	 * 根据设备将此用户下卡黑名单同步
	 * @param device 设备
	 * @param cards 黑名单列表
	 */
	public void addBlack(Deviceinfo device, List<Cardinfo> cards) throws SQLException {
		SqlMapClient client = ((CardsyncDAOImpl)cardsyncDAO).getSqlMapClient();
		Cardsync sync = null;
		Cardinfo card = null;
		try {
			client.startBatch();
			for (int i = 0; i < cards.size(); i++) {
				sync = new Cardsync();
				card = cards.get(i);
				sync.setCardcode(card.getCardcode());
				sync.setGprscode(device.getGprscode());
				sync.setPid(device.getPid());
				sync.setId(RandomStringUtils.randomNumeric(40));
				sync.setRecordtime(System.currentTimeMillis());
				sync.setSyncresult(Constants.SYNCRESULT_NOTODO);
				sync.setSynctype(Constants.CARDSYNC_BLACK);
				cardsyncDAO.insert(sync);
			}
			client.executeBatch();
		} catch (SQLException e) {
			loger.error(e.getMessage());
			throw e;
		}
	}
	
	/**
	 * 修改设备
	 * @param device
	 * @throws UtpException
	 */
	public void updateDevice(Deviceinfo device) throws UtpException {
		try {
			deviceinfoDAO.updateByPrimaryKeySelective(device);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UtpException(ErrorConstants.SQLEXCEPTION_KEY, e);
		}
	}
	
	/**
	 * 删除设备
	 * @param gprscode
	 * @throws UtpException
	 */
	public void deleteDevice(String gprscode) throws UtpException {
		try {
			deviceinfoDAO.deleteByPrimaryKey(gprscode);
			deviceworkDAO.deleteByPrimaryKey(gprscode);
			//FIXME 还需要查询是否有黑名单一并删除
			//FIXME 查询设备记录表一并删除
			//FIXME 查询设备运行表一并删除
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UtpException(ErrorConstants.SQLEXCEPTION_KEY, e);
		}
	}
	
	/**
	 * 查询指定设备详细信息
	 * @param gprscode
	 * @return
	 * @throws UtpException
	 */
	public Deviceinfo selectDevice(String gprscode) throws UtpException {
		Deviceinfo device = null;
		try {
			device = deviceinfoDAO.selectByPrimaryKey(gprscode);
			//if(device.g)
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UtpException(ErrorConstants.SQLEXCEPTION_KEY, e);
		}
		return device;
	}
	
	/**
	 * 查询指定客户下的设备报警信息
	 * @param pid
	 * @return
	 * @throws UtpException
	 */
	public List<AlertResult> getAlertList(String pid, int start, int limit) throws UtpException {
		List<AlertResult> list = null;
		Map<String, String> map = new HashMap<String, String>();
		try {
			// 需要默认状态为服务正常的
			map.put("pid", pid);
			map.put("status", Constants.SERVICESTATUS_NORMAL);
			list = queryDAOSpec.queryAlertlistByPid(map, start, limit);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UtpException(ErrorConstants.SQLEXCEPTION_KEY, e);
		}
		return list;
	}
	
	/**
	 * 查义指定客户下的设备报警信息数目
	 * @param pid
	 * @return
	 * @throws UtpException
	 * @see getAlertList
	 */
	public int getAlertCount(String pid) throws UtpException {
		int count;
		Map<String, String> map = new HashMap<String, String>();
		try {
			//需要默认状态为服务正常的
			map.put("pid", pid);
			map.put("status", Constants.SERVICESTATUS_NORMAL);
			count = queryDAOSpec.queryAlertCountByPid(map);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UtpException(ErrorConstants.SQLEXCEPTION_KEY, e);
		}
		return count;
	}
	
}

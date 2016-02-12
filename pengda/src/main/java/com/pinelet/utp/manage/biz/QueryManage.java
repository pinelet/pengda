package com.pinelet.utp.manage.biz;

import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.pinelet.utp.dao.CardinfoDAO;
import com.pinelet.utp.dao.TransinfoDAO;
import com.pinelet.utp.dao.spec.QueryDAOSpec;
import com.pinelet.utp.entity.Cardinfo;
import com.pinelet.utp.entity.CardinfoExample;
import com.pinelet.utp.entity.CardinfoExample.Criteria;
import com.pinelet.utp.exception.UtpException;
import com.pinelet.utp.manage.form.QueryForm;
import com.pinelet.utp.util.ExportExcel;

public class QueryManage extends BusinessImpl {

	Logger loger = LoggerFactory.getLogger(QueryManage.class);
	
	@Autowired
	private QueryDAOSpec queryDAOSpec;

	@Autowired
	private CardinfoDAO cardDAO;
	
	
	/**
	 * 根据指定的用户号，卡号，设备号,设备地址以及起止日期查询交易流水
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> QueryCardtrans(QueryForm form) throws Exception {
		List<Map> list = null;
		Map<String, Object> result = new HashMap<String, Object>(2);
		Map countMap = queryDAOSpec.queryCardTransCount(form);
		list = queryDAOSpec.queryCardTransList(form, form.getRows()*(form.getPage()-1), form.getRows());
		result.put("total", countMap.get("total"));
		result.put("rows", list);
		countMap.remove("total");
		return addFooter(result, countMap, "transId");
	}
	
	/**
	 * 根据指定的用户号，卡号，设备号,设备地址以及起止日期导出交易流水
	 * @param headers 显示的表格头列名
	 * @param form
	 * @param out
	 * @throws Exception
	 */
	public void exportCardtrans(String[] headers, QueryForm form, OutputStream out)  throws Exception {
		List<Map> list = null;
		list = queryDAOSpec.queryCardTransList(form, 0, 1000);
		String[] columnsName = new String[] {"transId","cardcode","devicecode","address","expenditure","cardbalance","transdate","remark"};
		ExportExcel<QueryForm> ex = new ExportExcel<QueryForm>(); 
		ex.exportExcel(headers, columnsName, list, out);

	}
	
	/**
	 * 根据指定的卡号，卡持有人，持卡人电话以及办卡地址查询卡信息
	 * @param form
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> queryCardlist(QueryForm form) throws Exception {

		Map<String, Object> result = new HashMap<String, Object>(2);

		CardinfoExample example = new CardinfoExample();
		Criteria criteria = example.createCriteria().andPidEqualTo(form.getPid());
		if (!(form.getCardcode() == null || form.getCardcode().equals("")))
			criteria.andCardcodeEqualTo(form.getCardcode());
		if (!(form.getCardname() == null || form.getCardname().equals("")))
			criteria.andCardnameEqualTo(form.getCardname());
		if (!(form.getPhone() == null || form.getPhone().equals("")))
			criteria.andPhoneEqualTo(form.getPhone());
		if (!(form.getAddress() == null || form.getAddress().equals("")))
			criteria.andAddressLike(form.getAddress());
		if (!(form.getBalance() == null || form.getBalance().equals("")))
			criteria.andBalanceLessThanOrEqualTo(form.getBalance());
		result.put("total", cardDAO.countByExample(example));
		result.put("rows", cardDAO.selectByExamplePage(example, form.getRows()*(form.getPage()-1), form.getRows()));
		return result;
	}
	
	private Map<String, Object> addFooter(Map<String, Object> jsonMap, Map footerMap, String totalTypeName) {
		List list = new ArrayList();
		footerMap.put(totalTypeName,"合计：");
		list.add(footerMap);
		jsonMap.put("footer", list);
		return jsonMap;
	}
}

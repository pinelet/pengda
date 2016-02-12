package com.pinelet.utp.dao.spec;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.pinelet.utp.dao.UTPDAO;
import com.pinelet.utp.entity.AlertResult;
import com.pinelet.utp.manage.form.QueryForm;

public interface QueryDAOSpec extends UTPDAO {
	
	public List<AlertResult> queryAlertlistByPid(Map pid, int start, int limit) throws SQLException;
	
	public int queryAlertCountByPid(Map pid) throws SQLException;
	
	public List<Map> queryCardTransList(QueryForm form, int start, int limit) throws SQLException;
	
	public Map queryCardTransCount(QueryForm form) throws SQLException;
	
	//public List<Map> queryCardinfoList(QueryForm form, int start, int limit) throws SQLException;
	
	//public int queryCardinfoList(QueryForm form) throws SQLException;
}

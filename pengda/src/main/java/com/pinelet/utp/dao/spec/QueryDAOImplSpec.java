package com.pinelet.utp.dao.spec;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pinelet.utp.entity.AlertResult;
import com.pinelet.utp.manage.form.QueryForm;

public class  QueryDAOImplSpec implements QueryDAOSpec {
	
	private SqlMapClient sqlMapClient;

    public QueryDAOImplSpec() {
        super();
    }
    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }
	public List<AlertResult> queryAlertlistByPid(Map pid, int start, int limit) throws SQLException {
		return this.getSqlMapClient().queryForList("query.selectAlertByPid", pid, start, limit);
	}
	public int queryAlertCountByPid(Map pid) throws SQLException {
		return (Integer) this.getSqlMapClient().queryForObject("query.selectAlertcountByPid", pid);
	}
	public Map queryCardTransCount(QueryForm form) throws SQLException {
		return (Map) this.getSqlMapClient().queryForObject("query.querytransinfocount", form);
	}
	public List<Map> queryCardTransList(QueryForm form, int start, int limit)
			throws SQLException {
		return this.getSqlMapClient().queryForList("query.querytransinfolist", form, start, limit);
	}
}

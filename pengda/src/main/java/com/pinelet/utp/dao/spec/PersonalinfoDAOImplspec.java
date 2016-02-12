package com.pinelet.utp.dao.spec;

import java.sql.SQLException;
import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pinelet.utp.dao.PersonalinfoDAOImpl;

public class PersonalinfoDAOImplspec extends PersonalinfoDAOImpl implements PersonalinfoDAOSpec {
    
    
	/* (non-Javadoc)
	 * @see com.pinelet.utp.dao.spec.PersonalinfoDAOSepc#queryClientOrgList()
	 */
	public List queryClientOrgList() throws SQLException {
		return this.getSqlMapClient().queryForList("personalinfo.selectClientOrg");
	}
}

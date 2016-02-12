package com.pinelet.utp.dao.spec;

import java.sql.SQLException;
import java.util.List;

import com.pinelet.utp.dao.PersonalinfoDAO;
import com.pinelet.utp.dao.PersonalinfoDAOImpl;

public interface PersonalinfoDAOSpec extends PersonalinfoDAO {

	public abstract List queryClientOrgList() throws SQLException;

}
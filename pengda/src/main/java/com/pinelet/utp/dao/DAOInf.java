package com.pinelet.utp.dao;

import java.sql.Connection;

public interface DAOInf extends UTPDAO {
	public void setConnection(Connection con);
}

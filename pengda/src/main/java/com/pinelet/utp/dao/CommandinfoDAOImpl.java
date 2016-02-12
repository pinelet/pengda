package com.pinelet.utp.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pinelet.utp.entity.Commandinfo;
import com.pinelet.utp.entity.CommandinfoExample;
import java.sql.SQLException;
import java.util.List;

public class CommandinfoDAOImpl implements CommandinfoDAO {
    /**
     * This field was generated by CreditEase ibator-extending.
     * This field corresponds to the database table commandinfo
     *
     *  Sat Mar 30 15:41:48 CST 2013
     */
    private SqlMapClient sqlMapClient;

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table commandinfo
     *
     *  Sat Mar 30 15:41:48 CST 2013
     */
    public CommandinfoDAOImpl() {
        super();
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table commandinfo
     *
     *  Sat Mar 30 15:41:48 CST 2013
     */
    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table commandinfo
     *
     *  Sat Mar 30 15:41:48 CST 2013
     */
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table commandinfo
     *
     *  Sat Mar 30 15:41:48 CST 2013
     */
    public int countByExample(CommandinfoExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("commandinfo.ibatorexgenerated_countByExample", example);
        return count;
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table commandinfo
     *
     *  Sat Mar 30 15:41:48 CST 2013
     */
    public int deleteByExample(CommandinfoExample example) throws SQLException {
        int rows = sqlMapClient.delete("commandinfo.ibatorexgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table commandinfo
     *
     *  Sat Mar 30 15:41:48 CST 2013
     */
    public int deleteByPrimaryKey(String commandid) throws SQLException {
        Commandinfo key = new Commandinfo();
        key.setCommandid(commandid);
        int rows = sqlMapClient.delete("commandinfo.ibatorexgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table commandinfo
     *
     *  Sat Mar 30 15:41:48 CST 2013
     */
    public void insert(Commandinfo record) throws SQLException {
        sqlMapClient.insert("commandinfo.ibatorexgenerated_insert", record);
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table commandinfo
     *
     *  Sat Mar 30 15:41:48 CST 2013
     */
    public void insertSelective(Commandinfo record) throws SQLException {
        sqlMapClient.insert("commandinfo.ibatorexgenerated_insertSelective", record);
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table commandinfo
     *
     *  Sat Mar 30 15:41:48 CST 2013
     */
    @SuppressWarnings("unchecked")
    public List<Commandinfo> selectByExample(CommandinfoExample example) throws SQLException {
        List<Commandinfo> list = sqlMapClient.queryForList("commandinfo.ibatorexgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table commandinfo
     *
     *  Sat Mar 30 15:41:48 CST 2013
     */
    @SuppressWarnings("unchecked")
    public List<Commandinfo> selectByExamplePage(CommandinfoExample example, int start, int limit) throws SQLException {
        List<Commandinfo> list = sqlMapClient.queryForList("commandinfo.ibatorexgenerated_selectByExample", example, start, limit);
        return list;
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table commandinfo
     *
     *  Sat Mar 30 15:41:48 CST 2013
     */
    public Commandinfo selectByPrimaryKey(String commandid) throws SQLException {
        Commandinfo key = new Commandinfo();
        key.setCommandid(commandid);
        Commandinfo record = (Commandinfo) sqlMapClient.queryForObject("commandinfo.ibatorexgenerated_selectByPrimaryKey", key);
        return record;
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table commandinfo
     *
     *  Sat Mar 30 15:41:48 CST 2013
     */
    public int updateByExampleSelective(Commandinfo record, CommandinfoExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("commandinfo.ibatorexgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table commandinfo
     *
     *  Sat Mar 30 15:41:48 CST 2013
     */
    public int updateByExample(Commandinfo record, CommandinfoExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("commandinfo.ibatorexgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table commandinfo
     *
     *  Sat Mar 30 15:41:48 CST 2013
     */
    public int updateByPrimaryKeySelective(Commandinfo record) throws SQLException {
        int rows = sqlMapClient.update("commandinfo.ibatorexgenerated_updateByPrimaryKeySelective", record);
        return rows;
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table commandinfo
     *
     *  Sat Mar 30 15:41:48 CST 2013
     */
    public int updateByPrimaryKey(Commandinfo record) throws SQLException {
        int rows = sqlMapClient.update("commandinfo.ibatorexgenerated_updateByPrimaryKey", record);
        return rows;
    }

    /**
     * This class was generated by CreditEase ibator-extending.
     * This class corresponds to the database table commandinfo
     *
     *  Sat Mar 30 15:41:48 CST 2013
     */
    private static class UpdateByExampleParms extends CommandinfoExample {
        private Object record;

        public UpdateByExampleParms(Object record, CommandinfoExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}
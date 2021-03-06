package com.pinelet.utp.dao;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.pinelet.utp.entity.PersonrelExample;
import com.pinelet.utp.entity.PersonrelKey;
import java.sql.SQLException;
import java.util.List;

public class PersonrelDAOImpl implements PersonrelDAO {
    /**
     * This field was generated by CreditEase ibator-extending.
     * This field corresponds to the database table personrel
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    private SqlMapClient sqlMapClient;

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personrel
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    public PersonrelDAOImpl() {
        super();
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personrel
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    public void setSqlMapClient(SqlMapClient sqlMapClient) {
        this.sqlMapClient = sqlMapClient;
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personrel
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    public SqlMapClient getSqlMapClient() {
        return sqlMapClient;
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personrel
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    public int countByExample(PersonrelExample example) throws SQLException {
        Integer count = (Integer)  sqlMapClient.queryForObject("personrel.ibatorexgenerated_countByExample", example);
        return count;
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personrel
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    public int deleteByExample(PersonrelExample example) throws SQLException {
        int rows = sqlMapClient.delete("personrel.ibatorexgenerated_deleteByExample", example);
        return rows;
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personrel
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    public int deleteByPrimaryKey(PersonrelKey key) throws SQLException {
        int rows = sqlMapClient.delete("personrel.ibatorexgenerated_deleteByPrimaryKey", key);
        return rows;
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personrel
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    public void insert(PersonrelKey record) throws SQLException {
        sqlMapClient.insert("personrel.ibatorexgenerated_insert", record);
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personrel
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    public void insertSelective(PersonrelKey record) throws SQLException {
        sqlMapClient.insert("personrel.ibatorexgenerated_insertSelective", record);
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personrel
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    @SuppressWarnings("unchecked")
    public List<PersonrelKey> selectByExample(PersonrelExample example) throws SQLException {
        List<PersonrelKey> list = sqlMapClient.queryForList("personrel.ibatorexgenerated_selectByExample", example);
        return list;
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personrel
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    @SuppressWarnings("unchecked")
    public List<PersonrelKey> selectByExamplePage(PersonrelExample example, int start, int limit) throws SQLException {
        List<PersonrelKey> list = sqlMapClient.queryForList("personrel.ibatorexgenerated_selectByExample", example, start, limit);
        return list;
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personrel
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    public int updateByExampleSelective(PersonrelKey record, PersonrelExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("personrel.ibatorexgenerated_updateByExampleSelective", parms);
        return rows;
    }

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personrel
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    public int updateByExample(PersonrelKey record, PersonrelExample example) throws SQLException {
        UpdateByExampleParms parms = new UpdateByExampleParms(record, example);
        int rows = sqlMapClient.update("personrel.ibatorexgenerated_updateByExample", parms);
        return rows;
    }

    /**
     * This class was generated by CreditEase ibator-extending.
     * This class corresponds to the database table personrel
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    private static class UpdateByExampleParms extends PersonrelExample {
        private Object record;

        public UpdateByExampleParms(Object record, PersonrelExample example) {
            super(example);
            this.record = record;
        }

        public Object getRecord() {
            return record;
        }
    }
}
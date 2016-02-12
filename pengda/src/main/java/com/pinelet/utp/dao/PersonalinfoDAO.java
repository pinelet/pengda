package com.pinelet.utp.dao;

import com.pinelet.utp.entity.Personalinfo;
import com.pinelet.utp.entity.PersonalinfoExample;
import java.sql.SQLException;
import java.util.List;

public interface PersonalinfoDAO extends UTPDAO {
    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personalinfo
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    int countByExample(PersonalinfoExample example) throws SQLException;

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personalinfo
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    int deleteByExample(PersonalinfoExample example) throws SQLException;

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personalinfo
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    int deleteByPrimaryKey(String pid) throws SQLException;

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personalinfo
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    void insert(Personalinfo record) throws SQLException;

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personalinfo
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    void insertSelective(Personalinfo record) throws SQLException;

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personalinfo
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    List<Personalinfo> selectByExample(PersonalinfoExample example) throws SQLException;

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personalinfo
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    List<Personalinfo> selectByExamplePage(PersonalinfoExample example, int start, int limit) throws SQLException;

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personalinfo
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    Personalinfo selectByPrimaryKey(String pid) throws SQLException;

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personalinfo
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    int updateByExampleSelective(Personalinfo record, PersonalinfoExample example) throws SQLException;

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personalinfo
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    int updateByExample(Personalinfo record, PersonalinfoExample example) throws SQLException;

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personalinfo
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    int updateByPrimaryKeySelective(Personalinfo record) throws SQLException;

    /**
     * This method was generated by CreditEase ibator-extending.
     * This method corresponds to the database table personalinfo
     *
     *  Sun Jan 13 16:31:41 CST 2013
     */
    int updateByPrimaryKey(Personalinfo record) throws SQLException;
}
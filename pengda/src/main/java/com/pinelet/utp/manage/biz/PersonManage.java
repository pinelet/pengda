package com.pinelet.utp.manage.biz;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.pinelet.utp.dao.DeviceinfoDAO;
import com.pinelet.utp.dao.PersonalinfoDAO;
import com.pinelet.utp.dao.PersonrelDAO;
import com.pinelet.utp.entity.Personalinfo;
import com.pinelet.utp.entity.PersonalinfoExample;
import com.pinelet.utp.entity.PersonrelExample;

public class PersonManage extends BusinessImpl {

	@Autowired
	private PersonalinfoDAO personDAO ;
	
	@Autowired
	private PersonrelDAO personrelDAO;
	
	/**
	 * 取得指定角色类型的人员
	 * @param personType 用户或客户厂家
	 * @return 人员信息：total 总人员数，rows 人员信息列表
	 */
	public Map getAllPersonBy (String personType, int start, int limit) throws SQLException {
		PersonalinfoExample example = new PersonalinfoExample();
		example.createCriteria().andRoleidEqualTo(personType);
		Map map = new HashMap();
		map.put("total", personDAO.countByExample(example));
		map.put("rows",personDAO.selectByExamplePage(example, start, limit));
		return map;
	}
	
	/**
	 * 取得指定角色类型的人员
	 * @param personType 用户或客户厂家
	 * @return 人员信息列表
	 */
	public List getAllPerson (String personType) throws SQLException {
		PersonalinfoExample example = new PersonalinfoExample();
		example.createCriteria().andRoleidEqualTo(personType);
		return personDAO.selectByExample(example);
	}
	
	/**
	 * 取得指定机构下的人员
	 * @param OrgNo 机构号
	 * @param personType 用户或客户厂家
	 * @return 人员信息列表
	 * @throws SQLException
	 */
	public Map getAllPersonbyOrg(String OrgNo, String personType) throws SQLException {
		PersonalinfoExample example = new PersonalinfoExample();
		example.createCriteria()
		.andOrgnoEqualTo(OrgNo)
		.andRoleidEqualTo(personType);
		Map map = new HashMap();
		map.put("total", personDAO.countByExample(example));
		map.put("rows",personDAO.selectByExamplePage(example, 0, 15));
		return map;
	}
	
	/**
	 * 新增客户
	 * @param person
	 * @throws SQLException
	 */
	public void addPerson(Personalinfo person) throws SQLException {
		personDAO.insert(person);
		
	}
	
	/**
	 * 修改客户
	 * @param person
	 * @throws SQLException
	 */
	public void updatePerson(Personalinfo person) throws SQLException {
		personDAO.updateByPrimaryKeySelective(person);
	}
	
	/**
	 * 删除客户
	 * @param pid
	 * @throws SQLException
	 */
	public String deletePerson(String pid) throws SQLException {
		PersonrelExample example = new PersonrelExample();
		example.createCriteria().andPidEqualTo(pid);
		List list = personrelDAO.selectByExample(example);
		if (list.size() == 0) {
			personDAO.deleteByPrimaryKey(pid);
			return "删除成功";
		}
		return "无法删除";
	}
	
	/**
	 * 根据人员账户查询信息
	 * @param pid
	 * @return 
	 * @throws SQLException
	 */
	public Personalinfo selectPerson(String pid) throws SQLException {
		return personDAO.selectByPrimaryKey(pid);
	}
}

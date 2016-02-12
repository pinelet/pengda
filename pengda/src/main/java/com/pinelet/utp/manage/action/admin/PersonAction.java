package com.pinelet.utp.manage.action.admin;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.pinelet.utp.Constants;
import com.pinelet.utp.ErrorConstants;
import com.pinelet.utp.entity.Personalinfo;
import com.pinelet.utp.exception.UtpException;
import com.pinelet.utp.manage.action.ActionImpl;
import com.pinelet.utp.manage.biz.OrgManage;
import com.pinelet.utp.manage.biz.PersonManage;

@ParentPackage("admindefault")
@Namespace("/manage")
public class PersonAction extends ActionImpl {

	private static final long serialVersionUID = -7594160243266032090L;

	static Logger loger = LoggerFactory.getLogger(PersonAction.class);
	
	private Personalinfo model = new Personalinfo();
	
	@Autowired
	@Qualifier("personManage")
	private PersonManage personManage;
	
	@Autowired
	@Qualifier("orgManage")
	private OrgManage orgManage;
	
	public Object getModel() {
		return model;
	}

	/**
	 * 查询所有的人员
	 * @return
	 * @throws UtpException
	 */
	@Action(value = "queryPersonlist", 
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success", 
								type = "json", 
								params={"root","jsonmap"})})
	public String queryPerson() throws UtpException {
		//如果有组织机构号，则做为查询条件
		String orgno = model.getOrgno();
		List personlist = null;
		try {
			if (orgno != null && !"".equals(orgno))
				jsonmap = personManage.getAllPersonbyOrg(orgno, model.getRoleid());
			else {	
				personlist = personManage.getAllPerson(model.getRoleid());
				jsonmap = new HashMap();
				jsonmap.put("total", personlist.size());
				jsonmap.put("rows", personlist);
			}	
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UtpException(ErrorConstants.SQLQUERYEXEPION_KEY, e);
		}
		return SUCCESS;
	}
	
	/**
	 *新增客户厂家信息
	 * @return
	 */
	@Action(value = "addclient", 
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success", 
								type = "json", 
								params={"root","jsonmap","noCache","true", "contentType", "text/html"})})
	public String addPerson() throws UtpException {
		try {
			if (model.getOrgno() == null) 
				model.setOrgno(RandomStringUtils.randomNumeric(15));
			else model.setOrgname(orgManage.getOrgName(model.getOrgno()));
			personManage.addPerson(model);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UtpException(ErrorConstants.SQLEXCEPTION_KEY, e);
		}
		jsonmap = new HashMap();
		jsonmap.put("success", true);
		jsonmap.put("msg", "新增人员信息成功!");
		return SUCCESS;
	}
	
	/**
	 * 修改客户厂家信息
	 * @return
	 * @throws UtpException
	 */
	@Action(value = "updateclient", 
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success", 
								type = "json", 
								params={"root","jsonmap","noCache","true", "contentType", "text/html"})})
	public String updatePerson() throws UtpException {
		try {
			personManage.updatePerson(model);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UtpException(ErrorConstants.SQLEXCEPTION_KEY, e);
		}
		jsonmap = new HashMap();
		jsonmap.put("success", true);
		jsonmap.put("msg", "更新人员信息成功!");
		return SUCCESS;
	}
	
	
	/**
	 * 删除客户厂家信息
	 * @return
	 * @throws UtpException
	 */
	@Action(value = "delclient", 
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success", 
								type = "json", 
								params={"root","jsonmap"})})
	public String deletePerson() throws UtpException {
		try {
			personManage.deletePerson(model.getPid());
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UtpException(ErrorConstants.SQLEXCEPTION_KEY, e);
		}
		jsonmap = new HashMap();
		jsonmap.put("success", true);
		jsonmap.put("msg", "ok delete something!");
		return SUCCESS;
	}
}

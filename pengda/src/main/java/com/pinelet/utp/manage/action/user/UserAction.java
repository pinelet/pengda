package com.pinelet.utp.manage.action.user;

import java.sql.SQLException;
import java.util.HashMap;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.pinelet.utp.ErrorConstants;
import com.pinelet.utp.entity.Personalinfo;
import com.pinelet.utp.exception.UtpException;
import com.pinelet.utp.manage.action.ActionImpl;
import com.pinelet.utp.manage.biz.PersonManage;


@ParentPackage("userdefault")
@Namespace("/user")
public class UserAction extends ActionImpl {

	private static final long serialVersionUID = -7594160243266032090L;

	static Logger loger = LoggerFactory.getLogger(UserAction.class);
	
	private Personalinfo model = new Personalinfo();
	
	@Autowired
	@Qualifier("personManage")
	private PersonManage personManage;
	
	public Object getModel() {
		return model;
	}
	
	/**
	 * 查询当前人员信息
	 * @return
	 * @throws UtpException
	 */
	@Action(value = "user", 
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success", location = "/user/user.jsp")})
	public String init() throws UtpException {
		Authentication auth  = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		UserDetails user = null;
		if (principal instanceof UserDetails) {
			user = (UserDetails)principal;
			try {
				model = personManage.selectPerson(user.getUsername());
			} catch (SQLException e) {
				e.printStackTrace();
				throw new UtpException(ErrorConstants.SQLEXCEPTION_KEY, e);
			}
			return SUCCESS;
			}
		return SUCCESS;
	}
	
	/**
	 * 修改用户信息
	 * @return
	 * @throws UtpException
	 */
	@Action(value = "updateuser", 
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
		jsonmap.put("msg", "ok update something!");
		return SUCCESS;
	}
}

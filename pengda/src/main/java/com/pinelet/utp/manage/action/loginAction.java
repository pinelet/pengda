package com.pinelet.utp.manage.action;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.pinelet.utp.exception.UtpException;
import com.pinelet.utp.manage.biz.LoginManage;
import com.pinelet.utp.manage.form.UserForm;

@Namespace("/")
public class loginAction extends ActionImpl {

		private static final long serialVersionUID = -3010417962723018291L;

		private UserForm userForm = new UserForm();

		private Logger loger = LoggerFactory.getLogger(this.getClass());

		@Autowired
		@Qualifier("loginManage")
		private LoginManage loginManage;
		
		
		// 构造数据form对象
		public Object getModel() {
			return userForm;
		}

		@Action(value = "main", interceptorRefs = @InterceptorRef("defaultStack"), results = {
				@Result(name = "success", location = "/layout.jsp"),
				@Result(name = "input", location = "/login.jsp") })
		public String execute() throws UtpException {
			Authentication auth  = SecurityContextHolder.getContext().getAuthentication();
			Object principal = auth.getPrincipal();
			UserDetails user = null;
			if (principal instanceof UserDetails) {
				user = (UserDetails)principal;
				userForm.setUsername(user.getUsername());
				userForm.setRole(auth.getAuthorities().toArray()[0].toString());
				//在会话中加入配置信息
				HttpSession session = request.getSession();
				session.setAttribute("user", userForm);
				loginManage.sessionManage(session);
				return SUCCESS;
				}
			else return INPUT;
		}
		
		@Action(value = "returnlogin", interceptorRefs = @InterceptorRef("defaultStack"), results = {
			@Result(name = "success", location = "/login.jsp"),
			@Result(name = "input", location = "/login.jsp") })
		public String gotologin() {
			return SUCCESS;
		}
}

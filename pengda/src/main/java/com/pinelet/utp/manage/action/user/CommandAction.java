package com.pinelet.utp.manage.action.user;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinelet.utp.exception.UtpException;
import com.pinelet.utp.manage.action.ActionImpl;
import com.pinelet.utp.manage.biz.CommandManage;
import com.pinelet.utp.manage.biz.DeviceManage;
import com.pinelet.utp.manage.form.CommandForm;

/**
 * 接收客户提交的控制命令
 * @author Administrator
 *
 */
@ParentPackage("default")
@Namespace("/user")
public class CommandAction extends ActionImpl {

	private static final long serialVersionUID = 1546717815819819992L;

	static Logger loger = LoggerFactory.getLogger(CommandAction.class);
	
	CommandForm model = new CommandForm();
	
	@Autowired
	private CommandManage commandManage;
	
	private Map jsonMap;
	
	public Map getJsonMap() {
		return jsonMap;
	}
	
	public Object getModel() {
		return model;
	}
	
	/**
	 * 记录客户提交的控制命令
	 * @return
	 */
	@Action(value= "savecommand",
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success",type="json", params={"root","jsonMap","noCache","true", "contentType", "text/html"})}
			)
	public String recordCommand() throws UtpException {
		commandManage.addCommand(model);
		jsonMap = new HashMap(1);
		jsonMap.put("msg", "已成功添加设备控制命令!");
		return SUCCESS;
	}

}

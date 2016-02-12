package com.pinelet.utp.manage.action.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinelet.utp.ErrorConstants;
import com.pinelet.utp.entity.AlertResult;
import com.pinelet.utp.entity.Deviceinfo;
import com.pinelet.utp.exception.UtpException;
import com.pinelet.utp.manage.action.ActionImpl;
import com.pinelet.utp.manage.biz.DeviceManage;
import com.pinelet.utp.manage.form.DeviceForm;
import com.pinelet.utp.manage.form.UserForm;

@ParentPackage("default")
@Namespace("/user")
public class DeviceAction extends ActionImpl {

	private static final long serialVersionUID = -4968235707909947519L;
	
	static Logger loger = LoggerFactory.getLogger(DeviceAction.class);
	
	private DeviceForm model = new DeviceForm();
	
	@Autowired
	private DeviceManage deviceManage;
	
	private Map jsonMap;
	
	public Map getJsonMap() {
		return jsonMap;
	}
	
	public Object getModel() {
		return model;
	}
	
	/**
	 * 查询当前用户下的所有正常状态设备列表
	 * @return
	 * @throws UtpException
	 */
	@Action(value = "queryDevicelist", 
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success", 
								type = "json", 
								params={"root","jsonMap"})})
	public String selectDeviceList() throws UtpException {
		List<Deviceinfo> device = deviceManage.getDeviceList(model.getPid(), model);
		jsonMap = new HashMap(3);
		jsonMap.put("total", deviceManage.getDeviceCount(model.getPid()));
		jsonMap.put("rows", device);
		return SUCCESS;
	}
	
	/**
	 * 查询当前客户设备报警列表
	 * @return
	 * @throws UtpException
	 */
	@Action(value = "queryAlertlist", 
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success", 
								type = "json", 
								params={"root","jsonMap"})})
	public String selectAlertList() throws UtpException {
		List<AlertResult> alerts = deviceManage.getAlertList(model.getPid(), model.getRows()*(model.getPage()-1), model.getRows());
		jsonMap = new HashMap(3);
		jsonMap.put("total", deviceManage.getAlertCount(model.getPid()));
		jsonMap.put("rows", alerts);
		return SUCCESS;
	}
	@Action(value= "addevice",
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success",type="json", params={"root","jsonMap","noCache","true", "contentType", "text/html"})}
			)
	public String addDevice() throws UtpException {
		//取得当前用户ID
		UserForm form = (UserForm)request.getSession().getAttribute("user");
		model.setPid(form.getUsername());
		deviceManage.addDevice(model);

		jsonMap = new HashMap(1);
		jsonMap.put("msg", "新增设备已成功!");
		return SUCCESS;
	}
	
	@Action(value= "updatedevice",
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success",type="json", params={"root","jsonMap","noCache","true", "contentType", "text/html"})}
			)
	public String updateDevice() throws UtpException {
		deviceManage.updateDevice(model);
		jsonMap = new HashMap(1);
		jsonMap.put("msg", "修改设备信息已成功!");
		return SUCCESS;
	}
	
	@Action(value= "deldevice",
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success",type="json", params={"contentType", "text/html", "root","jsonMap"})}
			)
	public String deleteDevice() throws UtpException {
		deviceManage.deleteDevice(model.getGprscode());
		jsonMap = new HashMap(1);
		jsonMap.put("msg", "删除设备信息已成功!");
		return SUCCESS;
	}
	
	/**
	 * 判断主键的唯一性
	 * @return
	 * @throws UtpException
	 */
	@Action(value= "unique",
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success",type="json", params={"contentType", "text/html", "root","jsonMap"})}
			)
	public String queryUnique() throws UtpException {
		Deviceinfo device = deviceManage.selectDevice(model.getId());
		jsonMap = new HashMap(1);
		if (device != null) {
			jsonMap.put("msg", true);
		}
		else jsonMap.put("msg", false);
		return SUCCESS;
	}
}

package com.pinelet.utp.manage.action.user;

import java.io.OutputStream;
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
import com.pinelet.utp.manage.biz.QueryManage;
import com.pinelet.utp.manage.form.QueryForm;
import com.pinelet.utp.manage.form.UserForm;
import com.pinelet.utp.util.DateUtil;
import com.pinelet.utp.util.ExportExcel;

@ParentPackage("default")
@Namespace("/user")
public class QueryAction extends ActionImpl {

	private static final long serialVersionUID = 1917932143262871342L;

	static Logger loger = LoggerFactory.getLogger(QueryAction.class);
	
	@Autowired
	private QueryManage queryManage;
	
	private QueryForm model = new QueryForm();
	
	private OutputStream out;
	
	private String filename;
	
	private Map<String, Object> jsonMap;
	
	public Map<String, Object> getJsonMap() {
		return jsonMap;
	}
	
	public Object getModel() {
		return model;
	}
	
	public Object getStream() {
		return out;
	}
	
	public String getFilename() {
		return filename;
	}

	/**
	 * 卡消费记录查询
	 * @return
	 * @throws UtpException
	 */
	@Action(value = "querycardtrans", 
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success", 
								type = "json", 
								params={"root","jsonMap"})})
	public String querytrans() throws Exception {
		//取得当前用户ID
		UserForm form = (UserForm)request.getSession().getAttribute("user");
		model.setPid(form.getUsername());
		jsonMap = queryManage.QueryCardtrans(model);
		return SUCCESS;
	}
	
	/**
	 * 卡信息查询
	 * @return
	 * @throws UtpException
	 */
	@Action(value = "querycardinfo", 
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success", 
								type = "json", 
								params={"root","jsonMap"})})
	public String queryCardinfo() throws Exception {
		//取得当前用户ID
		UserForm form = (UserForm)request.getSession().getAttribute("user");
		model.setPid(form.getUsername());
		jsonMap = queryManage.queryCardlist(model);
		return SUCCESS;
	}
	
	/**
	 * 设备交易查询
	 * @return
	 * @throws UtpException
	 */
	@Action(value = "querydevtrans", 
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success", 
								type = "json", 
								params={"root","jsonMap"})})
	public String queryDeviceinfo() throws Exception {
		//取得当前用户ID
		UserForm form = (UserForm)request.getSession().getAttribute("user");
		model.setPid(form.getUsername());
		jsonMap = queryManage.QueryCardtrans(model);
		return SUCCESS;
	}
	
	/**
	 * 下载交易流水
	 * @return
	 * @throws Exception
	 */
	@Action(value = "downloadtrans", 
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success", 
								type = "stream", 
								params={
									"inputName","stream",
									"bufferSize", "1024",
									"contentType", "application/ms-excel;charset=UTF-8"
									})})
	public String downloadTransExcel() throws Exception {
		//取得当前用户ID
		UserForm form = (UserForm)request.getSession().getAttribute("user");
		model.setPid(form.getUsername());
		//设置回应协议头为excel
		//response.setContentType("application/ms-excel;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment;filename=jyxx" + DateUtil.getFormatDate() + ".xls");
		filename = "jyxx" + DateUtil.getFormatDate() + ".xls";
		//组织表头
		String[] headers = new String[] {"流水号", "卡号", "设备编号", "设备地址", "消费金额(元)", "卡余额(元)","交易时间","备注"};
		//
		out = response.getOutputStream();
		queryManage.exportCardtrans(headers, model, out);
		out.flush();
		out.close();
		return SUCCESS;
	}
	
	/**
	 * 设备运行状态查询
	 * @return
	 * @throws UtpException
	 */
	@Action(value = "querydevstatus", 
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success", 
								type = "json", 
								params={"root","jsonMap"})})
	public String queryDevicestatus() throws UtpException {
		return SUCCESS;
	}
}

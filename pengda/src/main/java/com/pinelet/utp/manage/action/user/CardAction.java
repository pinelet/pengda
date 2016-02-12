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

import com.pinelet.utp.entity.Cardinfo;
import com.pinelet.utp.exception.UtpException;
import com.pinelet.utp.manage.action.ActionImpl;
import com.pinelet.utp.manage.biz.CardManage;
import com.pinelet.utp.manage.form.CardForm;
import com.pinelet.utp.manage.form.UserForm;

@ParentPackage("default")
@Namespace("/user")
public class CardAction extends ActionImpl {
	
	private static final long serialVersionUID = 9178223276545768401L;

	static Logger loger = LoggerFactory.getLogger(CardAction.class);
	
	private CardForm model = new CardForm();
	
	@Autowired
	private CardManage cardManage;
	
	private Map<String, Object> jsonMap;
	
	public Map<String, Object> getJsonMap() {
		return jsonMap;
	}
	
	public Object getModel() {
		return model;
	}
	
	/**
	 * 查询当前用户下的所有未作废的卡列表
	 * @return
	 * @throws UtpException
	 */
	@Action(value = "querycardlist", 
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success", 
								type = "json", 
								params={"root","jsonMap"})})
	public String selectCardList() throws UtpException {
		String pid = ((UserForm)request.getSession().getAttribute("user")).getUsername();
		model.setPid(pid);
		jsonMap = cardManage.selectCardList(model);
		return SUCCESS;
	}
	
	
	/**
	 * 判断卡号的唯一性
	 * @return
	 * @throws UtpException
	 */
	@Action(value= "cardunique",
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success",type="json", params={"contentType", "text/html", "root","jsonMap"})}
			)
	public String queryUnique() throws UtpException {
		UserForm form = (UserForm)request.getSession().getAttribute("user");		
		Cardinfo device = cardManage.selectCard(form.getUsername(), model.getId());
		jsonMap = new HashMap<String, Object>(1);
		if (device != null) {
			jsonMap.put("msg", true);
		}
		else jsonMap.put("msg", false);
		return SUCCESS;
	}
	
	@Action(value= "addcard",
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success",type="json", params={"root","jsonMap","noCache","true", "contentType", "text/html"})}
			)
	public String addCard() throws UtpException {
		UserForm form = (UserForm)request.getSession().getAttribute("user");
		model.setPid(form.getUsername());
		cardManage.addCard(model);
		jsonMap = new HashMap<String, Object>(1);
		jsonMap.put("msg", "新增卡信息已成功!");
		return SUCCESS;
	}
	
	
	@Action(value= "updatecard",
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success",type="json", params={"root","jsonMap","noCache","true", "contentType", "text/html"})}
			)
	public String updateCard() throws UtpException {
		UserForm form = (UserForm)request.getSession().getAttribute("user");
		model.setPid(form.getUsername());
		cardManage.updateCard(model);
		jsonMap = new HashMap<String, Object>(1);
		jsonMap.put("msg", "修改卡信息已成功!");
		return SUCCESS;
	}
	
	@Action(value= "delcard",
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success",type="json", params={"root","jsonMap","noCache","true", "contentType", "text/html"})}
			)
	public String delCard() throws UtpException {
		UserForm form = (UserForm)request.getSession().getAttribute("user");
		model.setPid(form.getUsername());
		cardManage.delCard(model);
		jsonMap = new HashMap<String, Object>(1);
		jsonMap.put("msg", "删除卡信息已成功!");
		return SUCCESS;
	}
}

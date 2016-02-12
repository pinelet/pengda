package com.pinelet.utp.manage.action.admin;

import java.sql.SQLException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.pinelet.utp.ErrorConstants;
import com.pinelet.utp.entity.Personalinfo;
import com.pinelet.utp.exception.UtpException;
import com.pinelet.utp.manage.action.ActionImpl;
import com.pinelet.utp.manage.biz.OrgManage;

@ParentPackage("admindefault")
@Namespace("/manage")
public class OrgAction extends ActionImpl {

	private Personalinfo model = new Personalinfo();
	
	@Autowired
	@Qualifier("orgManage")
	private OrgManage orgManage;
	
	
	public Object getModel() {
		return model;
	}

	/**
	 * 取得系统中所有厂家编号与名称
	 * @return
	 */
	@Action(value = "orglist", 
			interceptorRefs = @InterceptorRef("defaultStack"), 
			results = { @Result(name = "success", 
								type = "json", 
								params={"root","jsonlist"})})
	public String getOrgList() throws UtpException {
		try {
			jsonlist = orgManage.getClientOrgList();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UtpException(ErrorConstants.SQLQUERYEXEPION_KEY, e);
		}
		return SUCCESS;
	}
}

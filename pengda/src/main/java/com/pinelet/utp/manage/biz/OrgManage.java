package com.pinelet.utp.manage.biz;

import java.sql.SQLException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.pinelet.utp.Constants;
import com.pinelet.utp.dao.PersonalinfoDAO;
import com.pinelet.utp.dao.spec.PersonalinfoDAOSpec;
import com.pinelet.utp.entity.PersonalinfoExample;
import com.pinelet.utp.manage.form.UserForm;

public class OrgManage extends BusinessImpl {

	@Autowired
	private PersonalinfoDAOSpec personDAOSpec ;
	

	public List getClientOrgList() throws SQLException {
		return personDAOSpec.queryClientOrgList();
	}
	
	public String getOrgName(String orgno) throws SQLException {
		PersonalinfoExample example = new PersonalinfoExample();
		example.createCriteria().andOrgnoEqualTo(orgno);
		return personDAOSpec.selectByExample(example).get(0).getOrgname();
	}
}

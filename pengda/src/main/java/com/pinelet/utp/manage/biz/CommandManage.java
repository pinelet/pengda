package com.pinelet.utp.manage.biz;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.pinelet.utp.Constants;
import com.pinelet.utp.ErrorConstants;
import com.pinelet.utp.dao.CommandinfoDAO;
import com.pinelet.utp.entity.Commandinfo;
import com.pinelet.utp.entity.Devicework;
import com.pinelet.utp.exception.UtpException;
import com.pinelet.utp.manage.form.CommandForm;

public class CommandManage extends BusinessImpl {

	Logger loger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	@Qualifier("commandDAO")
	CommandinfoDAO commandinfoDAO;
	
	/**
	 * 将客户提交的控制命令记入命令同步表，待心跳时发送
	 * @throws UtpException
	 */
	public void addCommand(CommandForm record) throws UtpException {
		Commandinfo command = new Commandinfo();
		command.setCommandid(RandomStringUtils.randomNumeric(32));
		command.setGprscode(record.getGprscode());
		command.setPid(record.getPid());
		command.setCommandtype(Constants.COMMANDTYPE_RUN);
		command.setRecordtime(System.currentTimeMillis());
		command.setCommandcontent(buildCommand(record));
		command.setSyncresult(Constants.SYNCRESULT_NOTODO);
		try {
			commandinfoDAO.insertSelective(command);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new UtpException(ErrorConstants.SQLEXCEPTION_KEY, e);
		}
	}
	
	/**
	 * 根据心跳报文下发控制参数类型定义，组成报文串内容部分
	 * 字段中boolean类型与null值全部忽略
	 * &|a|2|(this)|00|$
	 * @param work
	 * @return
	 * @
	 */
	private String buildCommand(Devicework work) {
		Field[] tFields = Devicework.class.getDeclaredFields();
		String value = null;
		StringBuffer contents = new StringBuffer();
		boolean isEmpty = true;
		try {
			for (int i = 0; i < tFields.length; i++) {
				//从实例中取得指定属性的值
				value = BeanUtils.getSimpleProperty(work, tFields[i].getName());	
				if(tFields[i].getType().isInstance(value) && !"".equals(value) && !"gprscode".equals(tFields[i].getName())) {
					isEmpty = false;
					contents.append(tFields[i].getName()).append(",").append(value).append(",");
				}
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		if (isEmpty) return null;
		else return contents.substring(0, contents.length() - 1);
	}
}

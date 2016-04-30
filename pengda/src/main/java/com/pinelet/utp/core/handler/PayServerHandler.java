package com.pinelet.utp.core.handler;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Request;
import javax.servlet.http.HttpServletRequest;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.pinelet.utp.core.CoreConstants;
import com.pinelet.utp.core.service.Message;

/**
 * 实现发送设备状态验证&实现通知设备处理结果
 * @author fathead
 *
 */
@Path("/")
public class PayServerHandler implements ApplicationContextAware {

	private ApplicationContext context = null;
	
	@Context
    private Request request;
	
	@Autowired
	NioSocketAcceptor portalAcceptor;
	
	public static ConcurrentHashMap<String, Message> sessionStatus = new ConcurrentHashMap<String, Message>();
	//默认等待设备的回应时间
	private long rolltime = 10000; 
	//设备当前状态ＴＴＬ时间
	private long ttl = 30*1000;
	
	private Logger loger = LoggerFactory.getLogger(getClass());
	
	private IoSession getIoSession (String id) {
		//acceptor = (NioSocketAcceptor)context.getBean("portalAcceptor");
		Map<Long, IoSession> sessions = portalAcceptor.getManagedSessions();
		for (Entry<Long, IoSession> session : sessions.entrySet()) {
			//要考虑异步接受回应处理
			IoSession token = session.getValue();
			Object sessionid = token.getAttribute("id");
			if (id.equals(sessionid) && token.isConnected()) {
				return token;
			}
		}
		return null;
	}

	@GET
	@Path("/verify/{id}")
	public boolean verifyStatus(@PathParam("id") String id) {
		flushTTL();
		IoSession session = getIoSession(id);
		if (loger.isDebugEnabled()) loger.debug("intance verifystatus -session is [{}]", session);
		if (session != null) {
			if (sessionStatus.containsKey(id)) {
				Message msg = sessionStatus.get(id);
				if (CoreConstants.RESULT_SUCCESS.equals(msg.getStatusdata()))
					return true;
			}
			long start = System.currentTimeMillis();
			session.write("&|h|" + id + "|00|$\n");
			if (loger.isDebugEnabled()) loger.debug("send verify request to client [{}]", id);
			//等一段时间是否有回应状态，否则就回应验证失败
			while(start + rolltime >= System.currentTimeMillis()) {
				if (sessionStatus.containsKey(id)) {
					Message msg = sessionStatus.get(id);
					if (CoreConstants.RESULT_SUCCESS.equals(msg.getStatusdata()))
						return true;
					else return false;
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
				}
			}
			if (loger.isDebugEnabled()) loger.debug("wait timeout to getSession status [{}]", id);
		}
		return false;
	}
	
	private void flushTTL() {
		for ( Entry<String,Message> entry : sessionStatus.entrySet()) {
			if (entry.getValue().getTimestamp() + ttl < System.currentTimeMillis()) {
				sessionStatus.remove(entry.getKey());
			}
		}
	}

	/**
	 * 
	 * @param request
	 * data:机器ID，交易类型（1充值，2打水），流水号（10位)，金额(分)，客户信息，操作时间
	 */
	@POST
	@Path("/act")
	public void devAction(@FormParam("data") String data) {

		if (data == null || !data.contains(",")) {
			loger.error("act info error -[{}]", data);
			return;
		}
		String info[] = data.split(",");
		IoSession session = getIoSession(info[0]);
		if (session == null) {
			loger.error("current device connection failed -[{}] and info[{}]", info[0], data);
			return;
		}
		session.write("&|i|" + data + "|00|$\n");
	}
	
	@Override
	public void setApplicationContext(ApplicationContext arg0) throws BeansException {
		context = arg0;
	}
}

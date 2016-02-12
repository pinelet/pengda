package com.pinelet.utp.core.handler;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.pinelet.utp.core.CoreConstants;
import com.pinelet.utp.core.service.IService;

public class unionServerHandler extends IoHandlerAdapter implements ApplicationContextAware{

	private Logger loger = LoggerFactory.getLogger(unionServerHandler.class);
	
	private ApplicationContext context = null;
	/**
	 * IP过滤
	 */
	@Override
	public void sessionOpened(IoSession session) {
		InetSocketAddress clientAddress = (InetSocketAddress)session.getRemoteAddress();
		loger.warn("host[{}], port[{}]", clientAddress.getHostName(), clientAddress.getPort());
	}
	/**
	 * 异常处理
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		loger.error(cause.getMessage(), cause);
		if (session != null && session.isConnected())
			session.close(true);
	}
	

	/**
	 * 接收报文
	 */
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		String originalMsg = message.toString();
		loger.debug(originalMsg);
		   
	   //头&尾$限定符是否存在
	   if ( !StringUtils.startsWith(originalMsg.trim(), "&|") || !StringUtils.endsWith(originalMsg.trim(), "|$")) {
		   session.close(true);
		   return; 
	   }
	   String[] reqMessage = StringUtils.splitPreserveAllTokens(originalMsg.trim(), "|");
	   //取得报文中交易类型编号reqMessage[3]
	   IService service = null;
	   if (reqMessage[3].length() == 1 && StringUtils.isAlpha(reqMessage[3]))
		   //适配处理对象
		   service = (IService)context.getBean("trx" + reqMessage[3]);
	   //如果为心跳交易的回应
	   else if (CoreConstants.EX_HEARTBEST.equals(reqMessage[1])) 
		   service = (IService)context.getBean("trx" + CoreConstants.EX_HEARTBEST_FOLLOW);
	   else return;
	   String resMessage = service.operation(session, reqMessage);
	   session.write(resMessage);
	}
	
	/**
	 * 发送报文
	 */
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		loger.debug(message.toString());
		//if (session.getAttribute("sync") == null)
		//	session.close(false);
	}
	
	/**
	 * 空闲时间到期
	 */
	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		session.close(true);
	}
	
	
	public void setApplicationContext(ApplicationContext app)
			throws BeansException {
		context = app;
		
	}
}

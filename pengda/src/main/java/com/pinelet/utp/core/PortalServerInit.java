package com.pinelet.utp.core;

import static com.pinelet.utp.core.CoreConstants.DEFAULT_CHARSET;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinelet.utp.core.handler.unionServerHandler;

/**
 * 此类是使用代码的方式配置mina服务端
 * @author xiaosonggong
 * @deprecated 此方法由spring配置方式取代
 */
public class PortalServerInit implements Runnable {

	Logger loger = LoggerFactory.getLogger(PortalServerInit.class);
	
	private void initialize() {
		IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory(Charset
						.forName(DEFAULT_CHARSET))));
		acceptor.setHandler(new unionServerHandler());
		acceptor.getSessionConfig().setReadBufferSize(1024);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
		acceptor.setDefaultLocalAddress(new InetSocketAddress(9920));
		try {
			acceptor.bind();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		initialize();

	}

}

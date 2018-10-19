package com.kingsun.core.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NormailcientTest {

	private PrintWriter out = null;
	private Socket socket = null;
	private BufferedReader in = null;
	
	//@Before
	public void setUp() throws Exception {
		socket = new Socket("pay.ssjcx.com", 9920);
		out = new PrintWriter(socket.getOutputStream());
	}

	//@After
	public void tearDown() throws Exception {
		try {
			out.flush();
			//out.close();
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println(in.readLine());
			//in.close();
			//socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 心跳
	 */
	public void _testRequestA() throws Exception {
		Normalclient client = new Normalclient();
		out.println(client.requestA("D99998"));
		out.flush();
		out.close();
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		System.out.println("first in: " + in.readLine());
		in.close();
		out = new PrintWriter(socket.getOutputStream());
		out.println(client.requestAFsuccess("&|A|1|00|$"));
	}
	
	/**
	 * 先发心跳做为一次注册，
	 * 之后只要收到请求，就回应状态正常
	 * @throws Exception
	 */
	//@Test
	public void _testRequesth() throws Exception {
		//上发心跳
		Normalclient client = new Normalclient();
		out.println(client.requestA("13611303594"));
		out.flush();
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		System.out.println("first in: " + in.readLine());
		out.println(client.requestAFsuccess("&|A|1|00|$"));
		while(true) {
			String ins = in.readLine();
			System.out.println("client receive verify rquest-[" + ins + "]");
			//回应成功
			if (ins != null && !ins.isEmpty()) {
				String resStatus = client.verifyHsuccess("13611303594", 1);
				out.println(resStatus);
				out.flush();
				System.out.println("client send verify result-["+resStatus+"]");
			} else if (ins == null) break;
		}
	}
	


	/**
	 * 上发消费数据
	 */
	public void _testRequestB() {
		List<Information> list = new ArrayList<Information>();
		Information informat = null;
		for (int i = 0; i < 3; i++) {
			informat = new Information();
			informat.setCardCode("1381137");
			informat.setConsum(1250 + i);
			informat.setBalance(5020);
			SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmm");
			informat.setOptime(formatter.format(new Date()));
			list.add(informat);
		}
		out.println(new Normalclient().requestB("8672780013454579", list));
		
	}

	/**
	 * 上发充值数据
	 */
	public void _testRequestC() {
		List<Information> list = new ArrayList<Information>();
		Information informat = null;
		for (int i = 0; i < 3; i++) {
			informat = new Information();
			informat.setCardCode("1361130");
			informat.setConsum(1250 + i);
			informat.setBalance(10000);
			SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmm");
			informat.setOptime(formatter.format(new Date()));
			list.add(informat);
		}
		out.println(new Normalclient().requestC("S99999", list));
	}

	/**
	 * 上发报警数据
	 */
	public void _testRequestD() {
		String alert = "0000111100001101";
		out.println(new Normalclient().requestD("867278001345457", alert, new Date()));
	}

	/**
	 * 验证
	 */
	public void _testRequestE() {
		out.println(new Normalclient().requestE("D99999", "123"));
	}
	
	/**
	 * 监控
	 */
	public void _testRequestF() {
		out.println(new Normalclient().requestF("D99998", "-120,123,7,,,,1304021754"));
	}
	
	/**
	 * 
	 */
	public void _testRequestG() {
		out.println(new Normalclient().requestG("D99998", "mm,1,LL,2,tl,13912345678,oV,50"));
	}
}

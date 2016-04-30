package com.kingsun.core.Test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Normalclient {

	/**
	 * 客户端请求控制指令测试
	 * @param IMEICode
	 * @return
	 */
	public String requestA(String IMEICode) {
		return "&|1|" + IMEICode + "|A||00|$";
	}
	
	public String requestAFsuccess(String IMEICode) {
		return "&|A|1|00|$";
	}
	
	public String requestAFfail(String IMEICode) {
		return "&|A|0|00|$";
	}
	
	/**
	 * 上发验证成功
	 * @param IMEICode
	 * @param success 1可用，0不可用，2ID错误
	 * @return
	 */
	public String verifyHsuccess(String IMEICode, int success) {
		return "&|H|" + IMEICode + "," + success + "|$";
	}
	/**
	 * 客户端上送客户交易消费信息
	 * @param IMEICode 设备IMEI号
	 * @param consumes 消费信息
	 * @return
	 */
	public String requestB(String IMEICode, List<Information> consumes) {
		StringBuffer info = new StringBuffer("&|1|");
		info.append(IMEICode).append("|B|");
		Information consume = null;
		for (int i = 0; i < consumes.size(); i++) {
			consume = consumes.get(i);
			info.append(consume.getCardCode()).append(',')
			.append(consume.getConsum()).append(',')
			.append(consume.getBalance()).append(',')
			.append(consume.getOptime()).append(',');
		}
		info.deleteCharAt(info.lastIndexOf(","));
		return info.append("|00|$").toString();
	}
	
	/**
	 * 客户端上送充值数据信息
	 * @param sofeCode 软件号
	 * @param consumes 充值信息
	 * @return
	 */
	public String requestC(String sofeCode, List<Information> consumes) {
		StringBuffer info = new StringBuffer("&|1|");
		info.append(sofeCode).append("|C|");
		Information consume = null;
		for (int i = 0; i < consumes.size(); i++) {
			consume = consumes.get(i);
			info.append(consume.getCardCode()).append(',')
			.append(consume.getConsum()).append(',')
			.append(consume.getBalance()).append(',')
			.append(consume.getOptime()).append(',');
		}
		info.deleteCharAt(info.lastIndexOf(","));
		return info.append("|00|$").toString();
	}
	
	/**
	 * 报警信息
	 * @param IMEICode
	 * @param alert 十六位二进制显示数如：1110001011101111
	 * @return
	 */
	public String requestD(String IMEICode, String alert, Date time) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmm");
		StringBuffer info = new StringBuffer("&|1|");
		info.append(IMEICode).append("|D|");
		info.append(Integer.toHexString(Integer.parseInt(alert, 2)));
		info.append(",").append(formatter.format(time));
		return info.append("|00|$").toString();
	}
	
	/**
	 * 验证
	 * @param sofeCode 软件号
	 * @param password 密码
	 * @return
	 */
	public String requestE(String sofeCode, String pwd) {
		StringBuffer info = new StringBuffer("&|1|");
		info.append(sofeCode).append("|E|").append(pwd);
		return info.append("|00|$").toString();
	}
	
	public String requestF(String IMEICode, String monitor) {
		StringBuffer info = new StringBuffer("&|1|");
		info.append(IMEICode).append("|F|").append(monitor);
		return info.append("|00|$").toString();
	}
	
	public String requestG(String IMEICode, String maintenance) {
		StringBuffer info = new StringBuffer("&|1|");
		info.append(IMEICode).append("|G|").append(maintenance);
		return info.append("|00|$").toString();
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {

		Socket socket = new Socket("127.0.0.1", 9920);
		//Socket socket = new Socket("10.10.38.92", 9920);
		PrintWriter out = new PrintWriter(socket.getOutputStream());
		switch(Integer.parseInt(args[1])) {
		case 1:
			break;
		}
		out.println("some thing from local");
		out.flush();
		out.close();
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		System.out.println(in.readLine());
		in.close();
	}

}

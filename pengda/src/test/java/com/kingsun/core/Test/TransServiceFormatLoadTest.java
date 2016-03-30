package com.kingsun.core.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.pinelet.utp.core.service.AbstractService;
import com.pinelet.utp.core.service.DataEntity;
import com.pinelet.utp.core.service.ReqMessage;

public class TransServiceFormatLoadTest implements Runnable {

	private String iDate = null;
	
	public TransServiceFormatLoadTest(String aDate) {
		iDate = aDate;
	}
	
	public TransServiceFormatLoadTest() {
		
	}
	public void run() {
		TransServiceFormatLoadTest.format(Thread.currentThread().getName());
	}
	
	public static void format(String now) {
		Date date = null;
		SimpleDateFormat fromformat = new SimpleDateFormat("yyMMddHHmm");
		SimpleDateFormat toformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			date = fromformat.parse(now);
			System.out.println(now + "[" + date + "]" + "[" + toformat.format(date) + "]");
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] str) throws Exception {
		int d = 1203010000;
		TransServiceFormatLoadTest test = new TransServiceFormatLoadTest();
		Thread thread = null;
		for (int i = 0; i < 40; i++) {
			thread = new Thread(test);
			thread.setName(String.valueOf(d + i));
			thread.start();
			//Thread.sleep(1);
		}
	}

}

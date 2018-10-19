package com.kingsun.core.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.TypeVariable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.pinelet.utp.entity.Cardsync;
import com.pinelet.utp.entity.Commandinfo;
import com.pinelet.utp.entity.Devicework;

import junit.framework.TestCase;

public class DeviceManageTest extends TestCase {

	public void _testDate() {
		String  d1 = "2013-12-11";
		String  d2 = "2013-10-11";
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd");
		int days = 0;
		try {
			days = DateUtils.truncatedCompareTo(formatter1.parse(d1), formatter2.parse(d2), Calendar.DAY_OF_YEAR);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		System.out.print(days);
	}
	
	public void testBinary() {
		String b6 = "070e";
		//十六进制转为二进制显示
		String binb6 = Integer.toBinaryString(Integer.decode("0x" + b6));
		//char[] byteb6 = binb6.toCharArray();
		System.out.println(binb6);
		//二进制转为十六进制显示
		System.out.println(Integer.toHexString(Integer.parseInt(binb6, 2)));
		
		//System.out.println(Integer.toString(0x0002, 2));

	}
	
	/**
	 * 日期格式转化
	 */
	public void testDateFormat() {
		String  d2 = "";
		SimpleDateFormat formatter1 = new SimpleDateFormat("yyMMddHHmm");
		SimpleDateFormat formatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			System.out.println(formatter2.format((formatter1.parse(d2))));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 强制取整转化
	 */
	public void testInt() {
		Float fl = new Float(120.72);
		System.out.println(fl.intValue());
	}
	
	public void testRandomString() {
		System.out.println(RandomStringUtils.randomNumeric(40));
	}
	
	public void testClassload() {
		Field[] tField1s = Devicework.class.getFields();
		Field[] tField2s = Devicework.class.getDeclaredFields();
		TypeVariable[] types = Devicework.class.getTypeParameters();
		String obj = null;
		try {
			obj = BeanUtils.getSimpleProperty(new Devicework(), tField2s[10].getName());
			System.out.println(String.class.isInstance(obj));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testListInstance() {
		List<Cardsync> cardlist = new ArrayList();
		List<Commandinfo> comlist = new ArrayList();
		cardlist.add(new Cardsync());
		comlist.add(new Commandinfo());
		Map map = new HashMap();
		map.put("cardsync", cardlist);	
		map.put("comsync", comlist);
		Object objs = map.get("cardsync");
		List list = null;
		System.out.println(objs instanceof List);
		if (objs instanceof List<?>) list = (List)objs;
		Object obj = list.get(0);
		System.out.println(list.get(0) instanceof Commandinfo);
		System.out.println(list.get(0) instanceof Cardsync);
		System.out.println(null instanceof Cardsync);
	}
}

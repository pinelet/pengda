package com.kingsun.core.Test;

import java.util.Date;

import org.apache.commons.lang3.time.DurationFormatUtils;

import com.pinelet.utp.util.DateUtil;

import junit.framework.TestCase;

public class FunMathTest extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testBetween2Day() throws Exception {
		String start = "2013-01-01";
		String end = "2014-01-31";
		System.out.println(DurationFormatUtils.formatPeriod(DateUtil.parseDate(start).getTime(), DateUtil.parseDate(end).getTime(), "dd"));
		//395
	}
}

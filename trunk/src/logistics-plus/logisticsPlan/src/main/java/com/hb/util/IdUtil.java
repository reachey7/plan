package com.hb.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

public class IdUtil {
	public IdUtil() {
	}

	/**
	 * 生成业务流水号 系统标识（sysFlg.length位）+时间（12位，年月日时分秒）+系统流水（randomCount位）
	 * 
	 * @param sysFlg
	 *            系统标识
	 * @return string
	 */
	public static synchronized String createSerial(String sysFlg) {
		safeSleep(0);
		SimpleDateFormat sdft = new SimpleDateFormat("yyMMddHHmmss");
		Date nowdate = new Date();
		String str = sdft.format(nowdate);
		return sysFlg + str + RandomStringUtils.randomNumeric(4);
	}
	/**
	 * 生成业务流水号 系统标识（sysFlg.length位）+时间（15位，年月日时分秒毫秒）+系统流水（randomCount位）
	 *
	 * @param sysFlg
	 *            系统标识
	 * @return string
	 */
	public static synchronized String createSerialSS(String sysFlg) {
		safeSleep(0);
		SimpleDateFormat sdft = new SimpleDateFormat("yyMMddHHmmssSSS");
		Date nowdate = new Date();
		String str = sdft.format(nowdate);
		return sysFlg + str + RandomStringUtils.randomNumeric(4);
	}

	/**
	 * @param millis
	 */
	public static void safeSleep(long millis) {

		try {
			Thread.sleep(millis);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		System.out.println(createSerial("LOG"));
	}
}

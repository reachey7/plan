package com.hb.bean;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * <p>
 * Title: ExceptionUtil<／p>
 * <p>
 * Description: <／p>
 * 
 * @author xiaguang
 * @date 2018年1月5日 上午11:07:10
 */
public class ExceptionUtil {

	/**
	 * @Title: getStackTrace
	 * @Description: 将异常的完整堆栈追踪信息保存到字符串中
	 * @param t 异常对象
	 * @return 追踪信息字符串
	 */
	public static String getStackTrace(Throwable t) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);

		try {
			t.printStackTrace(pw);
			return sw.toString();
		} finally {
			pw.close();
		}
	}
}
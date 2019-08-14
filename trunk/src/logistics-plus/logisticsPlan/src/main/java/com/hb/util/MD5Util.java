package com.hb.util;

import java.security.MessageDigest;
import java.util.UUID;

/**
 * 生成文件MD5
 *
 * @Title: MD5Util.java
 * @Package com.pde.ams.p9.util
 * @Description:
 * @author jinzl
 * @date 2012-4-16 下午01:15:32
 * @version V1.0
 */
public class MD5Util {
	
    private static MessageDigest md5 = null;
    
    static {
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 用于获取一个String的md5值
     * @param string
     * @return
     */
    public static String getMD5Str(String str) {
        byte[] bs = md5.digest(str.getBytes());
        StringBuilder sb = new StringBuilder(40);
        for(byte x:bs) {
            if((x & 0xff)>>4 == 0) {
                sb.append("0").append(Integer.toHexString(x & 0xff));
            } else {
                sb.append(Integer.toHexString(x & 0xff));
            }
        }
        return sb.toString();
    }
	
	public static String createGuid() {
		UUID uuid = UUID.randomUUID();  
		return uuid.toString().replaceAll("-", "") ;
	}
}

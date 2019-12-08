package com.kaisn.utils;

import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {

	public static String str;
	public static final String EMPTY_STRING = "";

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };

	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0)
			n = 256 + n;
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 转换字节数组为16进制字串
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString
					.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}

	public static String getUniqueString() 
	{
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return format.format(new Date());
	}

	public static boolean isBlack(String str) 
	{
		if(str == null)
		{
			return true;
		}
		if("".equals(str))
		{
			return true;
		}
		return false;
	}
	
	public static boolean isNotBlack(String str) 
	{
		if(str == null)
		{
			return false;
		}
		if("".equals(str))
		{
			return false;
		}
		return true;
	}
	
	public static boolean equals(String str1,String str2)
	{
		boolean flag=false;
		if(str1!=null)
		{
			flag=str1.equals(str1);
		}
		if(str2!=null){
			flag=str2.equals(str1);
		}
		return flag;
	}

	public static String subString(String str1,int len)
	{
		boolean isNotBlack = isNotBlack(str1);
		if(isNotBlack)
		{
			return str1.length()>len ? str1.substring(0,10) : str1;
		}
		return str1;
	}

	public static void main(String[] args) 
	{
		System.out.println(getUniqueString());
	}
}

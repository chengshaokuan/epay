package com.csk.epay.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	//工具类的构造方法一般是私有的
	private DateUtil() {
	}
	
	public static String getSystemTime(){
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}
}

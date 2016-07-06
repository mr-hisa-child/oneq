package com.oneq.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	
	public static final String FORMAT_YYYYMMDD = "yyyy/MM/dd";
	
	public static Date now(){
		return new Date();
	}
	
	public static Date toDate(final String str,final String format){
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try{
			return sdf.parse(str);
		}catch(ParseException e){
			return null;
		}
	}
}

package com.autho.authservice.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
	public static String getCurrentDateTime() {
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currentDate = dateFormat.format(date);
		return currentDate;
	}
}

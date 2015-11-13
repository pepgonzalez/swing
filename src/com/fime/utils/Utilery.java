package com.fime.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utilery {

	public static boolean isInteger(String text){
		try{
			int value = Integer.parseInt(text);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public static boolean isFloat(String text){
		try{
			float value = Float.parseFloat(text);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
	public static boolean isDate(String text){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		try{
			Date date = formatter.parse(text);
			return true;
		}catch(Exception e){
			return false;
		}
	}
	
}

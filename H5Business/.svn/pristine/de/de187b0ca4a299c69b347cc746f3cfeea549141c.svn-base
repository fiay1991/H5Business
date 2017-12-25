package com.park.scanpay.tools;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderNumTools {
    //private static Logger logger =Logger.getLogger(OrderNumTools.class);
	public static String creatOrderNum(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmmss");
		int max=99999;
        int min=10000;
        Random random = new Random();

		Date currentTime = new Date();//得到当前系统时间
		String date = formatter.format(currentTime); //将日期时间格式化 
		String str_date = date.toString(); //将Date型日期时间转换成字符串形式 
		String str_random =String.valueOf(random.nextInt(max)%(max-min+1) + min);//生成10000-99999之间的随机数
		str_date=str_date+str_random;
		return str_date;
	}
//    public static void main(String args[]){
//    	System.out.println("最终="+creatOrderNum());
//    	
//    }
}

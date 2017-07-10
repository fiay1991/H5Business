package com.park.h5business.tools;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class MoneyTool {
	
	/**
	 * 分转换为元.
	 * 
	 * @param fen
	 *            分
	 * @return 元
	 */
	public static String fromFenToYuan(final String fen) {
		if (null ==fen || "0".equals(fen) || "".equals(fen)) {
			return "0.00";
		}
		String yuan = "";
		final int MULTIPLIER = 100;
		Pattern pattern = Pattern.compile("^\\d+$");
		Matcher matcher = pattern.matcher(fen);
		if (matcher.matches()) {
			yuan = new BigDecimal(fen).divide(new BigDecimal(MULTIPLIER)).setScale(2).toString();
		}
		return yuan;
	}

	/**
	 * 元转换为分.
	 * 
	 * @param yuan
	 *            元
	 * @return 分
	 */
	public static String fromYuanToFen(final String yuan) {
		if (null ==yuan || "0".equals(yuan) || "".equals(yuan)) {
			return "0";
		}
		String fen = "";
		Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]{1,2})?$");
		Matcher matcher = pattern.matcher(yuan);
		if (matcher.matches()) {
			try {
				NumberFormat format = NumberFormat.getInstance();
				Number number = format.parse(yuan);
				double temp = number.doubleValue() * 100.0;
				// 默认情况下GroupingUsed属性为true 不设置为false时,输出结果为2,012
				format.setGroupingUsed(false);
				// 设置返回数的小数部分所允许的最大位数
				format.setMaximumFractionDigits(0);
				fen = format.format(temp);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} 
		return fen;
	}
	
	public static String floatToString(float price){
		DecimalFormat decimalFormat=new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
		return decimalFormat.format(price);
	}
}

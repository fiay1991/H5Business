package com.park.scanpay.tools;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.Logger;


public class SignTools {
	
	public static final  String DEFAULT_ENC_NAME="HmacSHA256";
	
	public  static  Logger logger =Logger.getLogger(SignTools.class);
	/**
	 * 加密
	 * @param strSrc 要加密的字符串
	 * @param encName 加密类型 （加密算法有MD5,SHA-1,SHA-256等  默认为SHA-256）
	 * @return
	 */
	public static String encrypt(String strSrc, String key){
		String result ="";
		if (null ==strSrc || "".equals(strSrc)) {  
            return result;  
        } 
		
		try {
//			strSrc=URLEncoder.encode(strSrc, "UTF-8");
			byte[] bytes = strSrc.getBytes();
			result= bytes2Hex(encryptHMAC(bytes,key));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public static String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
	/**
	 * HMAC加密
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptHMAC(byte[] data, String key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(key.getBytes(), DEFAULT_ENC_NAME);
		Mac mac = Mac.getInstance(secretKey.getAlgorithm());
		mac.init(secretKey);
		return mac.doFinal(data);
	}
	
	
}

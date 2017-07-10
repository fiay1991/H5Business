package com.park.h5business.tools;

import java.net.URLEncoder;
import java.util.HashMap;
import com.park.h5business.response.OpenIdResponse;

public class GetWeiXinCodeTools {
	
	
	public static String getCodeRequest(String code,String appid, String appsecret){ 

		 String GetCodeRequest = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+appsecret+"&code="+code+"&grant_type=authorization_code";
         String result = null;  
         result = GetCodeRequest; 
         return result; 
     } 
     public static String urlEnodeUTF8(String str){ 
         String result = str; 
         if (null ==result || "".equals(result) ) {
			return result;
         }
         try { 
             result = URLEncoder.encode(str,"UTF-8"); 
         } catch (Exception e) { 
             e.printStackTrace(); 
         } 
         return result; 
     } 
     
     public static String getWeiXinCode(String code,String appid,String appsecret){
 		String final_url=GetWeiXinCodeTools.getCodeRequest(code, appid, appsecret);
 		String result =HttpJsonTools.HttpClientGet(final_url, "",new HashMap<String, String>());
 		OpenIdResponse openIdResponse =DateChangeTools.gson2bean(result, OpenIdResponse.class);
 		if (null !=openIdResponse && null !=openIdResponse.getOpenid()) {
 			return openIdResponse.getOpenid();
 		}else {
 			return "";
 		}
 	}
}

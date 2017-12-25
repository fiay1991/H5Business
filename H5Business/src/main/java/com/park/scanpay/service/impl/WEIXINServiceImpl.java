package com.park.scanpay.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.park.scanpay.domain.OrderPayRecord;
import com.park.scanpay.profile.URLProfile;
import com.park.scanpay.response.Response;
import com.park.scanpay.response.UnifiedorderResponse;
import com.park.scanpay.service.BusinessService;
import com.park.scanpay.service.UnifiedOrderService;
import com.park.scanpay.tools.DateChangeTools;
import com.park.scanpay.tools.GetWeiXinCodeTools;
import com.park.scanpay.tools.StringTool;
@Repository(value="WEIXINServiceImpl")
public class WEIXINServiceImpl extends BaseServiceImpl implements BusinessService {
	
	@Autowired
	URLProfile urlProfile;
	
	@Resource(name="unifiedOrderServiceImpl")
	private UnifiedOrderService unifiedOrderService;
	
	private Logger logger = Logger.getLogger(getClass());
	
	private String   openid;
	/**
	 * 微信浏览器重定向地址
	 */
	@Override
	public String redirectURL(String self_url) {
		try {
			self_url=URLEncoder.encode(self_url, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String redirect_url="https://open.weixin.qq.com/connect/oauth2/authorize?appid="+urlProfile.getWEIXINAPPID()+"&redirect_uri="+self_url+"&response_type=code&scope=snsapi_base&state=1#wechat_redirect";
		return redirect_url;
	}
	
	
	public String creatOrder(String order_num,float total_price,float unpay_price,float discount_amount, String code){
		openid = generateOpenid("", code);
		OrderPayRecord orderPayRecord =getOrderPayRecord(order_num, total_price, unpay_price, discount_amount);
		orderPayRecord.setPayWay("3");//微信支付
//		int result =orderPayRecordDao.add(orderPayRecord);
//		if (result>0) {
			return orderPayRecord.getTradeNo();
//		}
//		return null;
	}
	
	public boolean getUnifiedorder(String alicode, String code,String orderid,String parkid,float free,Model model) {
		if(StringTool.isNotNull(openid)){
			 String result=unifiedOrderService.WEIXINunifiedOrder(openid,orderid, parkid, free);
			 logger.info("微信统一下单微信返回结果："+result);
			 Response rs =DateChangeTools.gson2bean(result, Response.class);
			 if (null !=rs && "2000".equals(rs.getResult_code())) {
				UnifiedorderResponse unifiedorderResponse=DateChangeTools.gson2bean(rs.getObject().toString(), UnifiedorderResponse.class) ;
				model.addAttribute("unifiedorder",unifiedorderResponse);
				model.addAttribute("openid",openid);
				model.addAttribute("terminal","WEIXIN");
				/**支付成功跳转页面**/
				model.addAttribute("return_url",urlProfile.getWXRETURNURL()+"order_num="+orderid+"&price="+free+"&openid="+openid+"&prepayid="+unifiedorderResponse.getPrepay_id());
				return true;
			 }else if(null !=rs){
				logger.info("微信返回结果："+"错误代码="+rs.getResult_code() +" *** "+"错误原因="+rs.getContent());
				return false;
			}else{
				return false;
			}
		}else {
			logger.info("openid为空");
			return false;
		}
		
	}
	public String generateOpenid(String alicode, String code){
		this.openid =GetWeiXinCodeTools.getWeiXinCode(code,urlProfile.getWEIXINAPPID(),urlProfile.getWEIXINAPPSECRET());
		return openid;
	}
	public String getOpenid() {
		return openid;
	}

	public void setOpenid(String openid) {
		this.openid = openid;
	}
}

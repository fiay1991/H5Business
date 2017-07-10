package com.park.h5business.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.park.h5business.dao.OrderInfoDao;
import com.park.h5business.dao.OrderPayRecordDao;
import com.park.h5business.domain.OrderPayRecord;
import com.park.h5business.factory.DestributeFactory;
import com.park.h5business.profile.URLProfile;
import com.park.h5business.service.BusinessService;
import com.park.h5business.service.PHPCommService;
import com.park.h5business.service.ScanService;
import com.park.h5business.service.UnifiedOrderService;
import com.park.h5business.tools.BackResultTools;
import com.park.h5business.tools.StringTool;

@Repository(value="scanServiceImpl")
public class ScanServiceImpl implements ScanService{
	private Logger logger = Logger.getLogger(getClass());
	
	@Resource(name="phpCommServiceImpl")
	private PHPCommService phpCommService;
	
	@Resource(name="orderInfoDaoImpl")
	private OrderInfoDao orderInfoDao;
	
	@Resource(name="unifiedOrderServiceImpl")
	private UnifiedOrderService unifiedOrderService;
	
	@Resource(name="orderPayRecordDaoImpl")
	private OrderPayRecordDao orderPayRecordDao;
	@Autowired
	URLProfile urlProfile;
	
	@Resource(name="destributeFactory")
	private DestributeFactory destributeFactory;
	

	/**
	 * 微信修改云平台订单
	 */
	public String updateOrder(String order_num,String price,String openid,String prepayid,Model model){
		logger.info("微信获取的参数：*** order_num="+order_num+"  price="+price+" openid="+openid+" prepayid="+prepayid);
		if (null ==order_num || null ==price) {
			return "h5_error";
		}
		/* add by fangct at 20170608
		 * 查询该订单详情，判断是不是已经通知过
		 * */
		OrderPayRecord orderPayRecord = orderPayRecordDao.getOrderPayRecord(order_num);
		if(orderPayRecord != null && orderPayRecord.getPay_status() == 2){
			return BackResultTools.setValue("h5_success",orderPayRecord.getOrder_num(),"order_num", model);
		}
		String result =phpCommService.weixinnotify(order_num, price,prepayid);
		if ("success".equals(result)) {
			return BackResultTools.setValue("h5_success",orderPayRecord.getOrder_num(),"order_num", model);
			//return "h5_success";
		}else {
			return "h5_error";
		}
	}
	/**
	 * 支付宝支付中心获取订单状态  成功修改云平台订单
	 */
	public String alipayupdateOrder(String order_num,String price,Model model){
		logger.info("支付宝获取的参数：*** order_num="+order_num+" ** price="+price);
		if (StringTool.isNotNull(order_num) && StringTool.isNotNull(price)) {
			/* add by fangct at 20170608
			 * 查询该订单详情，判断是不是已经通知过
			 * */
			OrderPayRecord orderPayRecord = orderPayRecordDao.getOrderPayRecord(order_num);
			if(orderPayRecord != null && orderPayRecord.getPay_status() == 2){
				return BackResultTools.setValue("h5_success",orderPayRecord.getOrder_num(),"order_num", model);
			}
			/**
			 * 获取支付结果
			 */
			String result =phpCommService.weixinnotify(order_num, price,"");
			if ("success".equals(result)) {
				return BackResultTools.setValue("h5_success",orderPayRecord.getOrder_num(),"order_num", model);
				//return "h5_success";
			}else {
				return "h5_error";
			}
		}else{
			return "h5_error";
		}
		
		
	}

	@Override
	public String scanpay(String parkid, String ticketid, String plate,
			String ver, String useragent,Model model) {
		String msg="抱歉支付失败，请稍后重试";//默认错误信息提示
		String page="h5_error";//默认跳转页面
		/*** 验证参数 */
		if (null ==parkid || "".equals(parkid) || null ==ticketid || "".equals(ticketid) 
			|| null ==ver || "".equals(ver) ) {
			logger.error("参数为空.."+"parkid="+parkid+"** ticketid="+ticketid+"** plate="+plate+" ** ver="+ver);
			msg+="(参数为空)";
			return BackResultTools.setValue(page, msg, model);
		}
		String urlparams="";
		try {
			if (StringTool.isNotNull(plate)) {
				plate =URLEncoder.encode(plate, "UTF-8");
			}
			urlparams = "ticketid="+ticketid+"&parkid="+parkid+"&plate="+plate+"&ver="+ver+"&useragent="+useragent;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		BusinessService bs =destributeFactory.create(useragent);
		return "redirect:"+bs.redirectURL(urlparams);
	}
}

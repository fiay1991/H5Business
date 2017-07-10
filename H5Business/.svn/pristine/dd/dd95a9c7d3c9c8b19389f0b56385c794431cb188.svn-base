package com.park.h5business.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.park.h5business.dao.OrderPayRecordDao;
import com.park.h5business.domain.OrderPayRecord;
import com.park.h5business.profile.URLProfile;
import com.park.h5business.service.BusinessService;
import com.park.h5business.service.UnifiedOrderService;
import com.park.h5business.tools.StringTool;
@Repository(value="alipayServiceImpl")
public class AlipayServiceImpl extends BaseServiceImpl implements BusinessService{
	@Autowired
	URLProfile urlProfile;
	
	@Resource(name="orderPayRecordDaoImpl")
	private OrderPayRecordDao orderPayRecordDao;
	
	@Resource(name="unifiedOrderServiceImpl")
	private UnifiedOrderService unifiedOrderService;
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Override
	public String redirectURL(String urlparams) {
		String self_url=urlProfile.getREDIRURL()+"?"+urlparams;
		return self_url;
	}
	
	public String creatOrder(String order_num,float total_price,float unpay_price,float discount_amount, String code){
		OrderPayRecord orderPayRecord =getOrderPayRecord(order_num, total_price, unpay_price, discount_amount);
		orderPayRecord.setPay_way(1);
		int result =orderPayRecordDao.add(orderPayRecord);
		if (result>0) {
			return orderPayRecord.getOrder_num_pay();
		}
		return null;
	}

	@Override
	public boolean getUnifiedorder(String code, String orderid, String parkid,float free, Model model) {
		String result=unifiedOrderService.AliPayunifiedOrder(orderid, parkid, free);
		if (StringTool.isNotNull(result)) {
				model.addAttribute("unifiedorder",result);
				model.addAttribute("terminal","AliPay");
				return true;
		}else {
				logger.info("支付中心返回结果："+"错误代码="+result);
				return false;
		}
	}

}

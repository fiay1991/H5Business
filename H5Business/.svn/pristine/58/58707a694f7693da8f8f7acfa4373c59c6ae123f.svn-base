package com.park.scanpay.factory;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Repository;

import com.park.scanpay.service.BusinessService;
import com.park.scanpay.service.impl.AlipayServiceImpl;
import com.park.scanpay.service.impl.WEIXINServiceImpl;
@Repository(value="destributeFactory")
public class DestributeFactory implements ApplicationContextAware{
	
	private ApplicationContext context;
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		 context = applicationContext;
	}
	
	/**
	 * 根据不同的命令码 处理不同业务
	 */
	public BusinessService create(String  useragent){
		BusinessService bs =null;
		int code=0;
		if (useragent.indexOf("MicroMessenger")>=0) {
			code=1;
		}
		if (useragent.indexOf("Alipay")>=0) {
			code=2;
		}
		switch (code) {
			case 1:
				bs = context.getBean(WEIXINServiceImpl.class);
				break;
			case 2:
				bs = context.getBean(AlipayServiceImpl.class);
				break;
			default:
				bs = context.getBean(AlipayServiceImpl.class);
		}
		return bs;
	}
}

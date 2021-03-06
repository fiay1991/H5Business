package com.park.scanpay.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;

import com.park.scanpay.config.ParkConfig;
import com.park.scanpay.domain.OrderInfo;
import com.park.scanpay.factory.DestributeFactory;
import com.park.scanpay.profile.URLProfile;
import com.park.scanpay.request.PageRequest;
import com.park.scanpay.response.OrderResponse;
import com.park.scanpay.service.BusinessService;
import com.park.scanpay.service.CloudCoreService;
import com.park.scanpay.service.PHPCommService;
import com.park.scanpay.service.PullOrderService;
import com.park.scanpay.tools.BackResultTools;
import com.park.scanpay.tools.DateTools;
import com.park.scanpay.tools.MoneyTool;
import com.park.scanpay.tools.StringTool;
import com.park.scanpay.vo.ScanpayVO;
@Repository(value="pullOrderServiceImpl")
public class PullOrderServiceImpl implements PullOrderService {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Resource(name="phpCommServiceImpl")
	private PHPCommService phpCommService;
	
	@Resource(name="destributeFactory")
	private DestributeFactory destributeFactory;
	
	@Resource(name="cloudCoreServiceImpl")
	private CloudCoreService cloudCoreService;
	
	@Resource(name="cloudCoreServiceImpl")
	private CloudCoreServiceImpl cloudCoreServiceImpl;
	
	@Autowired
	URLProfile urlProfile;
	
	/**
	 * 拉取账单  
	 */
	@Override
	public String pullorder(String alicode, String code,String parkid, String ticketid, String plate,
			String ver,String useragent,Model model) {
		String msg="抱歉支付失败，请稍后重试";//默认错误信息提示
		String page="h5_error";//默认跳转页面
		/*** 验证参数 */
		if (parkid != null && !parkid.equals("") && 
				plate != null && !plate.equals("") || 
				parkid != null && !parkid.equals("") && !parkid.equals("null") && 
				ticketid != null && !ticketid.equals("")){
			ScanpayVO scanpayVO = getScanpayVO(parkid, plate, ticketid, ver);
			try {
				/*** 根据信息查询主订单 */
				OrderInfo orderInfo = cloudCoreService.findOrderByPlate(scanpayVO.getParkid(), scanpayVO.getPlate());
				if (StringTool.isNotNull(orderInfo)) {
					scanpayVO.setOrderid(orderInfo.getOrderNum());
					/*** 获取用户在云端的缴费状态和金额 */
					OrderResponse orderResponse = cloudCoreService.peymentQuery(scanpayVO);
					if (StringTool.isNotNull(orderResponse)) {
						/**
						 * 判断是否离场
						 */
						if (isLeave(orderResponse)) {
							/*** 跳转到离场提示页面 */
							return BackResultTools.setValue("h5_leave",orderResponse.getOrderid(),"order_num", model);
						}
						/**
						 * 计算支付时间和当前时间的时间差
						 */
						String paydate=DateTools.secondTostring(orderResponse.getPay_time());
						long min =DateTools.time_interval(paydate, DateTools.secondTostring(DateTools.phpNowDate()));
						/**
						 * 判断是否在指定时间内（ParkConfig.overtime）扫码重复扫码
						 */
						if (isRepeateScan(orderResponse, min)) {
							/*** 跳转到二次扫码提示页面 */
							Map<String, Object> map =new HashMap<String, Object>();
							map.put("time", DateTools.addTime(paydate, ParkConfig.overtime));
							map.put("order_num", orderResponse.getOrderid());
							return BackResultTools.setValue("h5_repeatescan",map, model);
						}
						PageRequest pageReponse =new PageRequest();
						pageReponse.setPrice(MoneyTool.floatToString(orderResponse.getUnpay_price()));
						pageReponse.setEnter_time_date(DateTools.secondTostringDate(Integer.parseInt(orderInfo.getEnterTime())));
						pageReponse.setEnter_time_time(DateTools.secondTostringTime(Integer.parseInt(orderInfo.getEnterTime())));
						String parkName = cloudCoreServiceImpl.findParkName(parkid);
						pageReponse.setPark_name(parkName);
						pageReponse.setPlate(scanpayVO.getPlate());
						pageReponse.setOrder_id(scanpayVO.getOrderid());
						pageReponse.setParkid(parkid);
						/**
						 * 判断是否在免费时长内
						 */
						if (isFree(orderResponse)) {
							pageReponse.setTotal_count(DateTools.secondTotime(orderResponse.getTotal_count()));
							return BackResultTools.setValue("h5_freetime", pageReponse, "page", model);
						}
						/**
						 * 判断是否超时/正常支付流程
						 */
						if (orderResponse.getStatus()==3 || orderResponse.getStatus()==2){
							/**
							 * 生成子订单
							 */
							BusinessService bs =destributeFactory.create(useragent);
							String order_num_pay=bs.creatOrder(parkid, orderResponse.getOrderid(),orderResponse.getTotal_price(), orderResponse.getUnpay_price(),orderResponse.getDiscount_price(),code);
							if (StringTool.isNotNull(order_num_pay)) {
								pageReponse.setOrder_num_pay(order_num_pay);
								if (bs.getUnifiedorder(alicode, code, order_num_pay, parkid, orderResponse.getUnpay_price(), model)) {
									if (orderResponse.getStatus()==3) {
										pageReponse.setOverhanging_time(DateTools.minuteTotime(min));
										pageReponse.setPay_time(DateTools.secondTostring(orderResponse.getPay_time()));
										/*** 跳转到超时支付页面 */
										return BackResultTools.setValue("h5_overtime", pageReponse, "page", model);
									}else {
										pageReponse.setTotal_count(DateTools.secondTotime(orderResponse.getTotal_count()));
										return BackResultTools.setValue("h5_normalpay", pageReponse, "page", model);
									}
								}else {
									/*** 跳转到错误页面 */
									msg+="(统一下单失败)";
									return BackResultTools.setValue(page, msg, model);
								}
							}else {
								/*** 跳转到错误页面 */
								msg+="(创建子订单失败)";
								return BackResultTools.setValue(page, msg, model);
							}
						}
						else {
							/*** 跳转到错误页面 */
							msg+="(订单状态异常)";
							return BackResultTools.setValue(page, msg, model);
						}
					}else {
						/*** 跳转到错误页面 */
						msg+="(获取用户在云端的缴费状态和金额信息为空)";
						return BackResultTools.setValue(page, msg, model);
					}
				}else {
					/*** 跳转到错误页面 */
					logger.error("根据车场id和车牌未找到订单相关信息.. parkid="+parkid+" ** plate="+scanpayVO.getPlate()+" ** ticketid ="+scanpayVO.getTicketid());
					msg+="(未找到主相关订单信息)";
					return BackResultTools.setValue(page, msg, model);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("错误代码："+e);
				
				msg+="(" + e + ")";
				return BackResultTools.setValue(page, msg, model);
			}
		}else {
			logger.error("参数为空.."+"parkid="+parkid+"** ticketid="+ticketid+"** plate="+plate+" ** ver="+ver);
			msg+="(参数为空)";
			return BackResultTools.setValue(page, msg, model);
		}
		//return BackResultTools.setValue(page, msg, model);
	}
	
	/**
	 * 判断是否在指定时间内（ParkConfig.overtime）扫码重复扫码
	 */
	public boolean isRepeateScan(OrderResponse orderResponse,long min){
		if (orderResponse.getStatus()==1 && min<=ParkConfig.overtime) {
			return true;
		} else if (orderResponse.getStatus()==1 && orderResponse.getUnpay_price()==0 && orderResponse.getPay_time()> 0) {
			return true;
		}
		return false;	
	}
	/**
	 * 判断是否在免费时长内
	 */
	public boolean isFree(OrderResponse orderResponse){
		if (orderResponse.getStatus()==1 && orderResponse.getUnpay_price()==0 && orderResponse.getPay_time()==0) {
			return true;
		}
		return false;	
	}
	/**
	 * 判断是否离场
	 * @param orderResponse
	 * @param orderInfo
	 * @return
	 */
	public boolean isLeave(OrderResponse orderResponse){
		if (orderResponse.getStatus()==1 && orderResponse.getService_status() ==2) {
			return true;
		}
		return false;
	}
	/**
	 * @return
	 */
	public ScanpayVO getScanpayVO(String parkid,String plate,String ticketid,String ver){
		ScanpayVO scanpayVO =new ScanpayVO();
		scanpayVO.setParkid(parkid);
		scanpayVO.setPlate(plate);
		scanpayVO.setTicketid(ticketid);
		scanpayVO.setVer(ver);
		return scanpayVO;
	}
}

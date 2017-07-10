package com.park.h5business.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.FileSystems;
import java.util.Hashtable;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.park.h5business.dao.OrderInfoDao;
import com.park.h5business.dao.OrderPayRecordDao;
import com.park.h5business.domain.Demo;
import com.park.h5business.domain.OrderInfo;
import com.park.h5business.domain.OrderPayRecord;
import com.park.h5business.request.PageRequest;
import com.park.h5business.response.UnifiedorderResponse;
import com.park.h5business.service.impl.DemoServiceImpl;
import com.park.h5business.tools.DateTools;
import com.park.h5business.tools.MoneyTool;
import com.park.h5business.vo.ScanpayVO;


/**
 * @author liuyang 时间：2016年3月24日 功能： 备注：
 */
@Controller
@RequestMapping("/DemoController")
public class DemoController {

	@Resource(name = "DemoServiceImpl")
	private DemoServiceImpl demoServiceImpl;
	
	@Resource(name="orderPayRecordDaoImpl")
	private OrderPayRecordDao orderPayRecordDao;
	
	@Resource(name="orderInfoDaoImpl")
	private OrderInfoDao orderInfoDao;

	@ResponseBody
	@RequestMapping("/find")
	public List<Demo> find() {
		return demoServiceImpl.findAll();
	}
	
	@RequestMapping("/page")
	public String getPage(Model model){
		System.out.println("test");
		
		PageRequest pageReponse =new PageRequest();
		pageReponse.setPrice(MoneyTool.floatToString(new Float("0.01")));
		pageReponse.setOverhanging_time("");
		pageReponse.setTotal_count(DateTools.secondTotime(1234523));
		pageReponse.setEnter_time_date(DateTools.secondTostringDate(1000003948));
		pageReponse.setEnter_time_time(DateTools.secondTostringTime(1000003948));
		pageReponse.setPay_time(DateTools.secondTostring(108983));
		pageReponse.setPark_name("测试数据");
		pageReponse.setPlate("京N123456");
		System.out.println("日期："+pageReponse.getEnter_time_date());
		System.out.println("时间："+pageReponse.getEnter_time_time());
		System.out.println("金额："+pageReponse.getPrice());
		model.addAttribute("page", pageReponse);
		UnifiedorderResponse unifiedorderResponse=new UnifiedorderResponse();
		unifiedorderResponse.setAppId("wx9dd007c6f8f418e5");
		unifiedorderResponse.setNonceStr("123");
		unifiedorderResponse.setPaySign("3524542364");
		unifiedorderResponse.setPrepay_id("1261552501");
		model.addAttribute("unifiedorder", unifiedorderResponse);
		model.addAttribute("msg", "测试");
		return "h5_normalpay";
		//return "success";
		
	}
	@RequestMapping("/success")
	public String getSuccess(HttpServletRequest request ,Model model){
		String order_num =request.getParameter("order_num");// 
		model.addAttribute("order_num",order_num);
		return "h5_success";
		//return "success";
		
	}
	@RequestMapping("/leave")
	public String getleave(HttpServletRequest request ,Model model){
		String order_num =request.getParameter("order_num");// 
		model.addAttribute("order_num",order_num);
		return "h5_leave";
		//return "success";
		
	}
	@RequestMapping("/scan")
	public String getsacn(HttpServletRequest request ,Model model){
		String order_num =request.getParameter("order_num");// 
		model.addAttribute("order_num",order_num);
		return "h5_repeatescan";
		//return "success";
		
	}
	
	@Test
	@RequestMapping("/sc")
	public void test(){
		 //String contents = "http://alphah5business.51park.cn/H5Business/scan/scanpay?ticketid=123456&parkid=4429&plate=京N123456&ver=1.0";
		// String contents = "http://127.0.0.1:8080/scan/scanpay?ticketid=123456&parkid=4429&plate=京N123456&ver=1.0";
		//String contents = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx9dd007c6f8f418e5&redirect_uri=http://weixindev.51park.com.cn/H5Business/scan/scan?ticketid=123456%26parkid=4429%26plate=京N123456%26ver=1.0&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect";
		// String contents = "http://alphah5business.51park.cn/H5Business/pull/scan?ticketid=123456&parkid=4429&plate=%E4%BA%ACN123456&ver=1.0";
		 //String contents = "http://h5business.51park.cn/H5Business/scan/scanpay?ticketid=1487836801824&parkid=56020&plate=京KX9050&ver=1.0";
		// String contents = "http://weixin.51park.com.cn/H5Business/scan/scanpay?ticketid=14878209747314&parkid=56020&plate=京QX8176&ver=1.0";
//		String order_num =request.getParameter("order_num");// 
//		String contents = "http://weixindev.51park.com.cn/H5Business/DemoController/success?order_num="+order_num;
		String contents = "http://localhost:8080/H5Business/scan/scanpark?parkid=56019";

		 
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
				 hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
				 BitMatrix matrix = null;
				
				 try {
					 matrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, 300, 300, hints);
				 } catch (WriterException e) {	
					 e.printStackTrace();			
				 }
				 //BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(matrix);
				 try {
					MatrixToImageWriter.writeToPath(matrix, "png", FileSystems.getDefault().getPath("D:\\", "ceshi.png"));
					 File file = new File("D:\\ceshi.png");
					 FileInputStream inputStream = new FileInputStream(file);
					 byte[] data = new byte[(int)file.length()];
					 inputStream.read(data);
					 inputStream.close();
				 } catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	}
	@ResponseBody
	@RequestMapping("/add")
	public String  add(){
		OrderPayRecord orderPayRecord =new OrderPayRecord();
		orderPayRecord.setOrder_num("test1234");
		orderPayRecord.setCost_after(new BigDecimal("0.01"));
		orderPayRecord.setCost_before(new BigDecimal("0.01"));
		orderPayRecord.setCreate_time((int) (System.currentTimeMillis()/1000));
		orderPayRecord.setUpdate_time((int) (System.currentTimeMillis()/1000));
		orderPayRecord.setOpenid("qwertttt");
		orderPayRecord.setPay_status(2);
		orderPayRecord.setPay_way(3);//微信支付
		orderPayRecord.setTransaction_id("test");
		System.out.println(orderPayRecord.getCreate_time());
		orderPayRecordDao.add(orderPayRecord);
		return "success";
	}

	@ResponseBody
	@RequestMapping(value="/getOrderInfo", produces = "application/json;charset=UTF-8")
	public OrderInfo getOrderInfo(HttpServletRequest request){
		/**
		 * 获取参数
		 */
		String parkid =request.getParameter("parkid");// 停车场id
		String plate =request.getParameter("plate");//车牌号
		String ticketid=request.getParameter("ticketid");//票/卡号
		
		ScanpayVO scanpayVO = new ScanpayVO();
		scanpayVO.setParkid(parkid);
		scanpayVO.setPlate(plate);
		scanpayVO.setTicketid(ticketid);
		
		OrderInfo orderInfo = orderInfoDao.getOrderInfo(scanpayVO);
		
		return orderInfo;
	}
}
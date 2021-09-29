package com.company.homemaking.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

@Slf4j
public class SmsUtil {
	
	//快通道账号密码
	private static final String quickCorpId = "SC000459";
	private static final String quickPwd = "123qwe";
	
	//慢通道账号密码
	private static final String slowCorpId = "SC00883";
	private static final String slowPwd = "123456a";
	
	//快通道--用于发送验证码等实时性要求高的内容
	private static final int SMS_QUICK_TYPE = 1;
	//慢通道--用于发送时效性不强的内容
	private static final int SMS_SLOW_TYPE = 0;
	
	
	/*
	 * 发送方法  其他方法同理      返回0 或者 1 都是  提交成功
	 */
	private static int sendSMS(String Mobile,String Content,String send_time,int type){
		URL url = null;
		
		String CorpID = slowCorpId;//账户名
		String Pwd = slowPwd;//密码
		
		if(type == SMS_QUICK_TYPE){
			CorpID = quickCorpId;
			Pwd = quickPwd;
		}

		if (StringUtils.isBlank(send_time)) {
			send_time = DateUtils.currentTime14();
		}
		BufferedReader in = null;
		int inputLine = 0;
		try {
			String send_content=URLEncoder.encode(Content.replaceAll("<br/>", " "), "GBK");//发送内容
			url = new URL("  http://inolink.com/WS/BatchSend.aspx?CorpID="+CorpID+"&Pwd="+Pwd+"&Mobile="+Mobile+"&Content="+send_content+"&Cell=&SendTime="+send_time);
			log.debug("开始发送短信手机号码为 ："+Mobile);
			in = new BufferedReader(new InputStreamReader(url.openStream()));
			inputLine = new Integer(in.readLine()).intValue();
		} catch (Exception e) {
			log.error("网络异常,发送短信失败！");
			inputLine=-2;
		}
		log.debug("结束发送短信返回值：  "+inputLine);
		return inputLine;
	}
	
	/**
	 * 发快通道短信的接口
	 * @param Mobile 电话
	 * @param Content 内容
	 * @param send_time 发送时间 固定14位长度字符串，比如：20060912152435代表2006年9月12日15时24
	 * @return
	 */
	public static int sendQuickSMS(String Mobile,String Content,String send_time){
		return sendSMS(Mobile,Content,send_time,SMS_QUICK_TYPE);
	}
	
	/**
	 * 发慢通道短信的接口
	 * @param Mobile
	 * @param Content
	 * @param send_time 发送时间 固定14位长度字符串，比如：20060912152435代表2006年9月12日15时24
	 * @return
	 */
	public static int sendSlowSMS(String Mobile,String Content,String send_time){
		return sendSMS(Mobile,Content,send_time,SMS_SLOW_TYPE);
	}

}

package com.lanxi.tools;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 * 短信通道 短信类
 * @author yangyuanjian
 *
 */
public class SmsUtil {
	/**
	 * 发送短信
	 * @param sms 短信对象
	 * @return 短信发送结果字符串
	 */
	public static String postSms(TempSms sms){
		try{
			if(sms.getTdId()==null)
				sms.setTdId(TempSms.SMS_TDID_MOBILE);
			if(sms.getSign()==null)
				signSms(sms);
			Map<String, String> map=getKeyValue(sms);
			List<String> keys=new ArrayList<>();
			for(Map.Entry<String, String>each:map.entrySet())
				keys.add(each.getKey());
			Collections.sort(keys);
			StringBuffer strBuff=new StringBuffer();
			for(String each:keys){
				strBuff.append(each+"=");
				strBuff.append(map.get(each));
				strBuff.append("&");
			}
			return HttpUtil.postStr(strBuff.toString().substring(0,strBuff.length()-1), ConfigUtil.get("smsUrl"), "utf-8");
		}catch (Exception e) {
			throw new AppException("发送短信异常",e);
		}
	}
	/**
	 * 以Map形式获取短信内容
	 * @param sms 短信对象
	 * @return 短信属性-值映射
	 */
	public static Map<String, String> getKeyValue(TempSms sms){
		try {
		Map<String, String> map		=new LinkedHashMap<>();
		Map<String, Field>  fields	=BeanUtil.getFieldsNoStatic(TempSms.class);
			for(Map.Entry<String, Field> each:fields.entrySet()){
				String name = each.getKey();
				Field  field= each.getValue();
				field.setAccessible(true);
					map.put(name, field.get(sms)+"");
			}
			return map;
		} catch (Exception e) {
			throw new AppException("获取短信内容异常",e);
		}
	}
	/**
	 * 对短信进行签名
	 * @param sms 短信对象
	 * @return 签名字符串
	 */
	public static String signSms(TempSms sms){
		Map<String, String> map=getKeyValue(sms);
		List<String> keys=new ArrayList<>();
		for(Map.Entry<String, String>each:map.entrySet())
			keys.add(each.getKey());
		
		Collections.sort(keys);
		
		StringBuffer strBuff=new StringBuffer();
		for(String each:keys){
			if(each.equals("sign"))
				continue;
			strBuff.append(each+"=");
			strBuff.append(map.get(each));
			strBuff.append("&");
		}
		String sign=SignUtil.md5LowerCase(strBuff.toString().substring(0,strBuff.length()-1)+ConfigUtil.get("smsKey"),"utf-8");
		sms.setSign(sign);
		return sign;
	}
	public static class TempSms {
		/**短信通道-移动-免费*/
		public static String SMS_TDID_MOBILE="1";
		/**短信通道-沃动-收费*/
		public static String SMS_TDID_WODONG="2";
		/**商户号*/
		public static String SMS_MCHTID		="10";
		
		/**商户号 ->10*/
		private String mchtId;		
		/**流水号->10+年月日时分秒+4位随机数*/
		private String orderId;		
		/**手机号*/
		private String mobile;		
		/**短信内容*/
		private String content;		
		/**发送日期*/
		private String tradeDate;	
		/**发送时间*/
		private String tradeTime;	
		/**签名*/
		private String sign;		
		/**短信通道号 1移动 2沃动*/
		private String tdId;		
		public String getMchtId() {
			return mchtId;
		}
		public void setMchtId(String mchtId) {
			this.mchtId = mchtId;
		}
		public String getOrderId() {
			return orderId;
		}
		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		public String getTradeDate() {
			return tradeDate;
		}
		public void setTradeDate(String tradeDate) {
			this.tradeDate = tradeDate;
		}
		public String getTradeTime() {
			return tradeTime;
		}
		public void setTradeTime(String tradeTime) {
			this.tradeTime = tradeTime;
		}
		public String getSign() {
			return sign;
		}
		public void setSign(String sign) {
			this.sign = sign;
		}
		public String getTdId() {
			return tdId;
		}
		public void setTdId(String tdId) {
			this.tdId = tdId;
		}
		@Override
		public String toString() {
			return "TempSms [mchtId=" + mchtId + ", orderId=" + orderId + ", mobile=" + mobile + ", content=" + content
					+ ", tradeDate=" + tradeDate + ", tradeTime=" + tradeTime + ", sign=" + sign + ", tdId=" + tdId + "]";
		}
	}
}

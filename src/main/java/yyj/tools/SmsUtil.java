package yyj.tools;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import yyj.bean.TempSms;

public class SmsUtil {
	/**
	 * 发送短信
	 * @param sms
	 * @return
	 */
	public static String postSms(TempSms sms){
		// TODO 短信过滤 只发给夏栋
		if(!sms.getMobile().equals("15757135741"))
			return "0000";
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
	 * @param sms
	 * @return
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
	 * @param sms
	 * @return
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
}

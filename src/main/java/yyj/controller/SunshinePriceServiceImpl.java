package yyj.controller;


import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMElement;
import org.springframework.stereotype.Service;

import yyj.tools.AppException;
import yyj.tools.HttpUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 1 on 2016/11/17.
 */
@Service("sunshinePriceService")
public class SunshinePriceServiceImpl {
    //-----------------------------------------公用区------------------------------------------------------
	    public String queryCarType(Map<String,String> map) {
	        try {
	            String param="?";
	            for(Map.Entry<String,String> each:map.entrySet()){
	                String key=each.getKey();
	                String value=each.getValue();
	                if(value!=null&&!value.trim().equals(""))
	                    param=param+key+"="+value+"&";
	            }
	            if(param.endsWith("&"))
	                param=param.substring(0,param.length()-1);
	            String rs=HttpUtil.get("http://1.202.156.227:7002/Net/netCarModelsDataWebAction.action"+param,"utf-8");
	            return rs;
	        }catch (Exception e){
	            throw new AppException("转发查询阳光车辆品牌型号请求异常",e);
	        }

	    }

}

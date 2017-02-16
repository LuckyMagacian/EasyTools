package yyj.controller;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import yyj.tools.BeanUtil;
import yyj.tools.FileUtil;
import yyj.tools.SqlUtilForDB;
import yyj.tools.SqlUtilForDB.ColumnInfo;
import yyj.tools.SqlUtilForDB.ForeginKeyInfo;
import yyj.tools.SqlUtilForDB.IndexInfo;
import yyj.tools.SqlUtilForDB.PrimaryKeyInfo;
import yyj.tools.SqlUtilForDB.TableInfo;
import static yyj.tools.SqlUtilForDB.*;
@Controller
public class TestController {
	private static Logger logger=Logger.getLogger(TestController.class);
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/testAjax.do", produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String  testAjax(HttpServletRequest req,HttpServletResponse res){
		try {
			req.setCharacterEncoding("utf-8");
			System.out.println("testAjax--------------------------");
			Connection conn=SqlUtilForDB.getConnection();
			String tableName=req.getParameter("tableName");
			tableName=tableName==null?null:tableName.trim();
			String infoType=req.getParameter("infoType");
			infoType=infoType==null?null:infoType.trim();
			System.out.println(JSONObject.toJSONString(req.getParameterMap()));
			Class<?> clazz=null;
			Object   result=null;
			switch (infoType) {
			case "":
				List<ColumnInfo> columnInfos=SqlUtilForDB.getColumnInfos(conn, tableName);
				clazz=ColumnInfo.class;
				result=columnInfos;
				break;
			case "column":
				columnInfos=SqlUtilForDB.getColumnInfos(conn, tableName);
				clazz=ColumnInfo.class;
				result=columnInfos;
				break;
			case "pk":
				List<PrimaryKeyInfo> pkInfos=SqlUtilForDB.getPrimaryKeyInfos(conn, tableName);
				clazz=PrimaryKeyInfo.class;
				result=pkInfos;
				break;
			case "index":
				List<IndexInfo> 	indexInfos=SqlUtilForDB.getIndexInfos(conn, tableName);
				clazz=IndexInfo.class;
				result=indexInfos;
				break;
			case "fk":
				List<ForeginKeyInfo> fkInfos=SqlUtilForDB.getForeginKeyInfos(conn, tableName);
				clazz=ForeginKeyInfo.class;
				result=fkInfos;
				break;
			case "table":
				clazz=TableInfo.class;
				result=new ArrayList<>();
				((List<SqlUtilForDB.TableInfo>)result).add(getTableInfo(conn, tableName));
				break;
			case "db":
				clazz=DatabaseInfo.class;
				result=new ArrayList<>();
				((List<SqlUtilForDB.DatabaseInfo>)result).add(getDataBaseInfo(conn));
				break;
			default:
				columnInfos=SqlUtilForDB.getColumnInfos(conn, tableName);
				clazz=ColumnInfo.class;
				result=columnInfos;
				break;
			}
			System.out.println(clazz.getName());
			System.out.println(result);
			List<Method> getters=BeanUtil.getGetters(clazz);
			Map<String, Method> setters=BeanUtil.getSetterMethods(clazz);
			for(Object each:(List<?>)result){
				for(Method one:getters)
					if(one.invoke(each)==null)
						setters.get("set"+one.getName().substring(3)).invoke(each, "null");
			}
			return JSONObject.toJSONString(result);
		} catch (Exception e) {
			throw new RuntimeException("测试异常",e);
		}
	}
}


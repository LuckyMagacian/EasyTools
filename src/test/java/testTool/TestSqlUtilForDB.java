package testTool;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;

import yyj.tools.SqlUtilForDB;

public class TestSqlUtilForDB {

	@Test
	public void getProperties(){
		System.out.println(SqlUtilForDB.getProperties());
	}
	@Test
	public void getConnection(){
		System.out.println(SqlUtilForDB.getConnection());
	}
	@Test
	public void getTableNames(){
		System.out.println(SqlUtilForDB.getTableNames(SqlUtilForDB.getConnection()));
	}
	@Test
	public void getTables(){
		System.out.println(JSONObject.toJSONString(SqlUtilForDB.getTables(SqlUtilForDB.getConnection())));
	}
}

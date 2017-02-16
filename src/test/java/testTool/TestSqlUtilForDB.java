package testTool;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

import org.junit.Test;

import com.alibaba.fastjson.JSONObject;
import com.mysql.fabric.xmlrpc.base.Param;

import yyj.tools.BeanUtil;
import yyj.tools.CheckReplaceUtil;
import yyj.tools.FileUtil;
import yyj.tools.SqlUtilForDB;
import yyj.tools.SqlUtilForDB.ColumnInfo;
import yyj.tools.SqlUtilForDB.DBTable;
import yyj.tools.SqlUtilForDB.DatabaseInfo;
import yyj.tools.SqlUtilForDB.ForeginKeyInfo;
import yyj.tools.SqlUtilForDB.IndexInfo;
import yyj.tools.SqlUtilForDB.PrimaryKeyInfo;
import yyj.tools.SqlUtilForDB.TableInfo;
import static yyj.tools.SqlUtilForDB.*;
@Table
public class TestSqlUtilForDB {

	@Test
	public void getProperties(){
		System.out.println(SqlUtilForDB.getProperties());
	}
	@Test
	public void getConnection(){
		System.out.println(TestSqlUtilForDB.class.getPackage().getName());
	}
	@Test
	public void getTableNames() throws ClassNotFoundException{
		System.out.println(ColumnInfo.class.getSimpleName());
	}
	@Test
	public void getTables(){
	}
	
	@Test
	public void getTabless() throws Exception{
		Connection conn=SqlUtilForDB.getConnection();
		DatabaseMetaData metaData = conn.getMetaData();
		ResultSet tableSet = metaData.getTables(null, null, null, null);
//		DatabaseInfo databaseInfo	=SqlUtilForDB.getDataBaseInfo(conn);
//		System.out.println(databaseInfo);
//		TableInfo    tableInfo		=SqlUtilForDB.getTableInfo(conn, "t_car_owner");
//		System.out.println(tableInfo);
		List<ColumnInfo> columnInfos=SqlUtilForDB.getColumnInfos(conn, "test_type");
		System.out.println(listToMap(columnInfos));
//		List<IndexInfo> indexInfos=SqlUtilForDB.getIndexInfos(conn, "t_car_owner");
//		System.out.println(indexInfos);
//		List<PrimaryKeyInfo> pkInfos=SqlUtilForDB.getPrimaryKeyInfos(conn, "t_car_owner");
//		System.out.println(pkInfos);
//		List<ForeginKeyInfo> fkInfos=SqlUtilForDB.getForeginKeyInfos(conn, "t_car_owner");
//		System.out.println(fkInfos);
//		List<DBTable> tables=SqlUtilForDB.getTables(conn);
//		System.out.println(tables);
	}
	@Test
	public void testMakeFile(){
		Connection conn=SqlUtilForDB.getConnection();
		String string=makeBeanFile(getTable(conn, "t_car_owner"));
		System.out.println(FileUtil.javaFormat(string));
	}
	@Test
	public void testULtoU(){
		String string="123";
		List<String> strings=new ArrayList<>();
		strings.add(string);
		String string2=new String("123");
		System.out.println(strings.contains(string2));
		System.out.println(string.equals(string2));
		System.out.println(string==string2);
	}

}

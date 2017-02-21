package testTool;

import static yyj.tools.SqlUtilForDB.createSelect;
import static yyj.tools.SqlUtilForDB.getIndexInfos;
import static yyj.tools.SqlUtilForDB.getTable;
import static yyj.tools.SqlUtilForDB.getTables;
import static yyj.tools.SqlUtilForDB.listToMap;
import static yyj.tools.SqlUtilForDB.makeBeanFiles;
import static yyj.tools.SqlUtilForDB.*;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.List;

import javax.persistence.Table;

import org.apache.ibatis.annotations.Param;
import org.junit.Test;

import yyj.tools.FileUtil;
import yyj.tools.HttpUtil;
import yyj.tools.SqlUtilForDB;
import yyj.tools.SqlUtilForDB.ColumnInfo;
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
		makeBeanFiles(getTables(conn),  "t_",false,false);
	}
	
	@Test
	public void testMakeMapper(){
		Connection conn=SqlUtilForDB.getConnection();
		System.out.println(createSelect(getTable(conn, "t_car_owner"),"t_","asc",false));
	}
	@Test
	public void testULtoU() throws Exception{
		Connection conn=SqlUtilForDB.getConnection();
		System.out.println(getIndexInfos(conn, "t_car_owner"));
	}
	@Test
	public void testResultMap(){
		Connection conn=SqlUtilForDB.getConnection();
		System.out.println(FileUtil.xmlFormat(makeMybatisFile(getTable(conn, "t_car_owner"),"t_","asc",false,false)));
//		FileUtil.xmlFormat(makeMybatisFile(getTable(conn, "t_car_owner"),"t_","asc",false,false));
	}
	@Test
	public void testa(){
//		Connection conn=SqlUtilForDB.getConnection();
//		System.out.println(makeMybatisDao(getTable(conn, "t_car_owner"), "t_", false,false));

//		System.out.println(createInsert(getTable(conn, "test_type"), null));
//		makeMybatisFile(getTable(conn, "t_car_owner"), "t_", null, false, false);
		String temp="http://1.202.156.227:7002/Net/netCarModelsDataWebAction.action?";
		String vl="宝马BMW Z4 35i跑车 2009款 35i 锋尚型 2座";
		temp+="vehicleName=";
		temp+=HttpUtil.urlEncode(vl);
		String url="&pageNo=1&pageSize=10";
		temp+=url;
		System.out.println(temp);
		System.out.println();
	}
	@Test
	public void test2(){
		Connection conn=SqlUtilForDB.getConnection();
//		makeBeanFile(getTable(conn, "t_car_owner"), false, "t_", false);
		makeAll(conn, "t_", null, false, false);
	}

}

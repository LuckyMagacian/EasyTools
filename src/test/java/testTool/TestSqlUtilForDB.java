package testTool;

import static yyj.tools.SqlUtilForDB.getTables;
import static yyj.tools.SqlUtilForDB.listToMap;
import static yyj.tools.SqlUtilForDB.makeBeanFiles;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Table;

import org.junit.Test;

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
		makeBeanFiles(getTables(conn), false,"t_");
//		System.out.println(string);
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

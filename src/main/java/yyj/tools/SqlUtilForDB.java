package yyj.tools;

import java.io.File;
import java.io.FileReader;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import javax.sql.DataSource;
import javax.swing.JOptionPane;

import org.apache.commons.dbcp.BasicDataSourceFactory;



public class SqlUtilForDB {
	/** jdbc配置文件 */
	private static Properties properties = new Properties();
	/** jdbc配置文件路径 */
	private static String path = "properties/jdbc.properties";
	/** sql数据类型与java数据类型映射 */
	private static HashMap<String, Class<?>>  ddtTojdt;
	
//	private static HashMap<Class<?>, String> jdtToddt;
	/** 加载配置文件 */
	static {
	
		try {
			File file = FileUtil.getFileOppositeClassPath("jdbc.properties");
			if (!file.exists())
				file = FileUtil.getFileOppositeClassPath(path);
			FileReader reader = new FileReader(file);
			properties.load(reader);
			ddtTojdtInit();
			jdtToddtInit();
		} catch (Exception e) {
			throw new RuntimeException("sqlutil初始化jdbc配置异常", e);
		}
	}
	private static void ddtTojdtInit(){
		ddtTojdt = new LinkedHashMap<>();
		ddtTojdt.put("int",Integer.class);
		ddtTojdt.put("integer",Integer.class);
		ddtTojdt.put("mediumint",Integer.class);
		ddtTojdt.put("smallint",Integer.class);
		ddtTojdt.put("tinyint",Integer.class);
		ddtTojdt.put("boolean",Integer.class);
		ddtTojdt.put("long", Long.class);
		ddtTojdt.put("bigint",Long.class);
		ddtTojdt.put("double",Double.class);
		ddtTojdt.put("float",Float.class);
		ddtTojdt.put("number0",Integer.class);
		ddtTojdt.put("number",BigDecimal.class);
		ddtTojdt.put("decimal0",Integer.class);
		ddtTojdt.put("decimal",BigDecimal.class);
		ddtTojdt.put("numeric0",Integer.class);
		ddtTojdt.put("numeric",BigDecimal.class);
		ddtTojdt.put("money",BigDecimal.class);
		ddtTojdt.put("smallmoney",BigDecimal.class);
		ddtTojdt.put("date",Date.class);
		ddtTojdt.put("datetime",Date.class);
		ddtTojdt.put("smalldatetime",Date.class);
		ddtTojdt.put("year",Date.class);
		ddtTojdt.put("timestamp",Timestamp.class);
		ddtTojdt.put("time",Time.class);
		ddtTojdt.put("clob",Clob.class);
		ddtTojdt.put("blob",Blob.class);
		ddtTojdt.put("bit",Byte[].class);
		ddtTojdt.put("raw",Byte[].class);
		ddtTojdt.put("longraw",Byte[].class);
		ddtTojdt.put("longblob",Byte[].class);
		ddtTojdt.put("mediumblob",Byte[].class);
		ddtTojdt.put("tinyblob",Byte[].class);
		ddtTojdt.put("varbinary",Byte[].class);
		ddtTojdt.put("binary",Byte[].class);
		ddtTojdt.put("uniqueidentifier",Byte[].class);
		ddtTojdt.put("image",Byte[].class);
		ddtTojdt.put("char1",Integer.class);
		ddtTojdt.put("char",String.class);
		ddtTojdt.put("enum",String.class);
		ddtTojdt.put("longtext",String.class);
		ddtTojdt.put("mediumtext",String.class);
		ddtTojdt.put("set",String.class);
		ddtTojdt.put("text",String.class);
		ddtTojdt.put("tinytext",String.class);
		ddtTojdt.put("varchar",String.class);
		ddtTojdt.put("varchar2",String.class);
		ddtTojdt.put("nvarchar",String.class);
		ddtTojdt.put("real",Double.class);
		ddtTojdt.put("longvarchar",String.class);
		ddtTojdt.put("point", String.class);
		ddtTojdt.put("linestring", String.class);
		ddtTojdt.put("polygon", String.class);
		ddtTojdt.put("geometry", String.class);
		ddtTojdt.put("multipoint", String.class);
		ddtTojdt.put("multilinestring", String.class);
		ddtTojdt.put("multipolygon", String.class);
		ddtTojdt.put("geometrycollection", String.class);
		ddtTojdt.put("json", String.class);
	}
	private static void jdtToddtInit(){
//		jdtToddt=new LinkedHashMap<>();
		
	}
	/**
	 * 获取jdbc配置
	 * 
	 * @return
	 */
	public static Properties getProperties() {
		return properties;
	}

	/**
	 * 获取数据库连接
	 * 
	 * @return
	 */
	public static Connection getConnection() {
		try {
			Class.forName(properties.getProperty("driver"));
			Connection conn = DriverManager.getConnection(properties.getProperty("url"),
					properties.getProperty("username"), properties.getProperty("password"));
			return conn;
		} catch (Exception e) {
			throw new RuntimeException("获取数据库连接异常", e);
		}
	}
	/**
	 * 将DTtable中获取的list信息转换为map形式,key为字段名称 仅限ColumnInfo,IndexInfo,PrimaryKeyInfo,ForeginKeyInfo
	 * @param list
	 * @return
	 */
	public final static <T> Map<String, T> listToMap(List<T> list){
		try{
			if(list==null||list.isEmpty())
				return new HashMap<>();
			Class<?> clazz=null;
			Map<String,T> map=new HashMap<>();
			clazz=list.get(0).getClass();
			for(T each:list){
				switch(clazz.getSimpleName()){
				case "ColumnInfo":
					map.put(((ColumnInfo) each).getColumnName(),each);
				break;
				case "IndexInfo":
					map.put(((IndexInfo) each).getColumnName(),each);
				break;
				case "PrimaryKeyInfo":
					map.put(((PrimaryKeyInfo) each).getColumnName(),each);
				break;
				case "ForeginKeyInfo":
					map.put(((ForeginKeyInfo) each).getColumnName(),each);
				break;
				}
			}
			return map;
		}catch (Exception e) {
			throw new RuntimeException("list转map异常");
		}
	}
	

	/**
	 * 从连接池获取连接
	 * 
	 * @return
	 */
	public static Connection getPoolConnection() {
		try {
			DataSource dataSource = BasicDataSourceFactory.createDataSource(properties);
			Connection conn = null;
			conn = dataSource.getConnection();
			return conn;
		} catch (Exception e) {
			throw new RuntimeException("从连接池获取连接异常", e);
		}
	}

	/**
	 * 关闭连接
	 * 
	 * @param conn
	 */
	public static void closeConnection(Connection conn) {
		try {
			if (null != conn && !conn.isClosed()) {
				conn.setAutoCommit(true);
				conn.close();
			} else
				return;
		} catch (Exception e) {
			throw new RuntimeException("关闭连接异常", e);
		}
	}

	/**
	 * 获取数据库中所有表的名称
	 * 
	 * @param conn
	 * @return
	 */
	public static List<String> getTableNames(Connection conn) {
		if (conn == null)
			return getTableNames();
		try {
			List<String> names = new ArrayList<>();
			DatabaseMetaData meta = conn.getMetaData();
			ResultSet resultSet = meta.getTables(null, null, null, new String[] { "TABLE" });
			while (resultSet.next())
				names.add(resultSet.getString("TABLE_NAME"));
			return names;
		} catch (Exception e) {
		}
		return null;
	}

	/**
	 * 获取数据库中所有表的名称
	 * 
	 * @return
	 */
	public static List<String> getTableNames() {
		return getTableNames(getConnection());
	}

	/**
	 * 获取指定表中所有的字段名
	 * 
	 * @param tableName
	 * @return
	 */
	public static List<String> getColumnNames(Connection conn, String tableName) {
		try {
			List<String> list = new ArrayList<>();
			DatabaseMetaData metaData = conn.getMetaData();
			ResultSet resultSet = metaData.getColumns(null, null, tableName, null);
			while (resultSet.next())
				list.add(resultSet.getString(4));
			return list;
		} catch (Exception e) {
			throw new RuntimeException("获取字段信息异常", e);
		}
	}
	/**
	 * 获取数据库信息
	 * @param conn
	 * @return
	 */
	public static DatabaseInfo getDataBaseInfo(Connection conn){
		try {
			DatabaseMetaData metaData = conn.getMetaData();
			DatabaseInfo dbInfo=new DatabaseInfo();
			dbInfo.setProductName(metaData.getDatabaseProductName());
			dbInfo.setProductVersion(metaData.getDatabaseProductVersion());
			dbInfo.setDriverName(metaData.getDriverName());
			dbInfo.setDriverVersion(metaData.getDriverVersion());
			return dbInfo;
		} catch (Exception e) {
			throw new RuntimeException("获取数据库信息异常",e);
		}
	}
	/**
	 * 获取数据库信息
	 * @param metaData 数据库元数据
	 * @return
	 */
	public static DatabaseInfo getDataBaseInfo(DatabaseMetaData metaData){
		try {
			DatabaseInfo dbInfo=new DatabaseInfo();
			dbInfo.setProductName(metaData.getDatabaseProductName());
			dbInfo.setProductVersion(metaData.getDatabaseProductVersion());
			dbInfo.setDriverName(metaData.getDriverName());
			dbInfo.setDriverVersion(metaData.getDriverVersion());
			return dbInfo;
		} catch (Exception e) {
			throw new RuntimeException("获取数据库信息异常",e);
		}
	}
	/**
	 * 获取第一张表中的表信息
	 * @param tableSet
	 * @return
	 */
	public static TableInfo    getTableInfo(ResultSet tableSet){
			try {
				if (tableSet.isBeforeFirst())
					tableSet.next();
				if(!tableSet.isAfterLast()){
					TableInfo tempT=new TableInfo();
					tempT.setTableName(tableSet.getString("TABLE_NAME"));
					tempT.setType(tableSet.getString("TABLE_TYPE"));
					tempT.setRemark(tableSet.getString("REMARKS"));
					return tempT;
				}
				return null;
			} catch (Exception e) {
				throw new RuntimeException("获取指定表信息异常1",e);
			}
	}
	/**
	 * 获取指定名称的表的表信息
	 * @param conn
	 * @return
	 */
	public static TableInfo    getTableInfo(Connection conn,String tableName){
		try {
			DatabaseMetaData metaData = conn.getMetaData();
			return getTableInfo(metaData, tableName);			
		} catch (Exception e) {
			throw new RuntimeException("获取指定表信息异常3",e);
		}
	}
	/**
	 * 获取指定名称的表的表信息
	 * @param metaData
	 * @return
	 */
	public static TableInfo    getTableInfo(DatabaseMetaData metaData,String tableName){
		try {
			ResultSet tableSet = metaData.getTables(null, null,tableName, null);
			return getTableInfo(tableSet);
		} catch (Exception e) {
			throw new RuntimeException("获取指定表信息异常2",e);
		}
	}
	/**
	 * 获取单个字段信息
	 * @param columnSet
	 * @return
	 */
	public static ColumnInfo 		 getColumnInfo(ResultSet columnSet){
		try{
			if(columnSet.isBeforeFirst())
				columnSet.next();
			if(!columnSet.isAfterLast()){
				ColumnInfo tempC=new ColumnInfo();
				tempC.setTableName(columnSet.getString("TABLE_NAME"));
				tempC.setColumnName(columnSet.getString("COLUMN_NAME"));
				tempC.setDataType(columnSet.getString("DATA_TYPE"));
				tempC.setColumnSize(columnSet.getString("COLUMN_SIZE"));
				tempC.setNullAble(columnSet.getString("NULLABLE"));
				tempC.setSqlType(columnSet.getString("SQL_DATA_TYPE"));
				tempC.setDecimalDigits(columnSet.getString("DECIMAL_DIGITS"));
				tempC.setNumPrecRadix(columnSet.getString("NUM_PREC_RADIX"));
				tempC.setRemark(columnSet.getString("REMARKS"));
				tempC.setDefaultValue(columnSet.getString("COLUMN_DEF"));
				tempC.setIsAutoCrement(columnSet.getString("IS_AUTOINCREMENT"));
				tempC.setOrdinalPosition(columnSet.getString("ORDINAL_POSITION"));
				tempC.setCharSize(columnSet.getString("CHAR_OCTET_LENGTH"));
				tempC.setJavaType(columnSet.getString("TYPE_NAME"));
				return tempC;
			}
			return null;
		}catch (Exception e) {
			throw new RuntimeException("获取指定字段信息异常1",e);
		}
	}
	/**
	 * 获取指定表名的指定字段信息 
	 * @param metaData 		数据库元数据
	 * @param tableName		表名
	 * @param columnName	字段名
	 * @return				字段信息
	 */
	public static ColumnInfo 		 getColumnInfo(DatabaseMetaData metaData,String tableName,String columnName){
		try {
			ResultSet columnSet=metaData.getColumns(null, null, tableName, columnName);
			return getColumnInfo(columnSet);
		} catch (Exception e) {
			throw new RuntimeException("获取字段信息异常2",e);
		}
	}
	/**
	 * 获取指定表名的指定字段信息
	 * @param conn			数据库连接
	 * @param tableName		表名
	 * @param columnName	字段名
	 * @return 				字段信息
	 */
	public static ColumnInfo         getColumnInfo(Connection conn,String tableName,String columnName){
		try {
			if(conn==null)
				throw new NullPointerException("connection can't be null");
			if(tableName==null)
				throw new NullPointerException("tableName can't be null");
			if(columnName==null)
				throw new NullPointerException("columnName can't be null");
			DatabaseMetaData metaData=conn.getMetaData();
			return getColumnInfo(metaData, tableName, columnName);
		} catch (Exception e) {
			throw new RuntimeException("获取字段信息异常3",e);
		}
	}
	/**
	 * 获取所有字段信息
	 * @param columnSet
	 * @return
	 */
	public static List<ColumnInfo>   getColumnInfos(ResultSet columnSet){
		try {
			if(columnSet==null)
				throw new NullPointerException("columnSet can't be null");
			List<ColumnInfo> infos=new ArrayList<>();
			while(columnSet.next()){
				infos.add(getColumnInfo(columnSet));
			}
			return infos;
		} catch (Exception e) {
			throw new RuntimeException("获取字段信息异常1",e);
		}
	}
	/**
	 * 获取指定表名的所有字段信息
	 * @param metaData
	 * @param tableName
	 * @return
	 */
	public static List<ColumnInfo>   getColumnInfos(DatabaseMetaData metaData,String tableName){
		try {
			if(tableName==null)
				throw new NullPointerException("tableName can't be null");
			if(metaData==null)
				throw new NullPointerException("metaData can't be null");
			ResultSet columnSet=metaData.getColumns(null, null, tableName,null);
			return getColumnInfos(columnSet);
		} catch (Exception e) {
			throw new RuntimeException("获取字段信息异常2",e);
		}
	}
	/**
	 * 获取指定表名的所有字段信息
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public static List<ColumnInfo> 	 getColumnInfos(Connection conn,String tableName){
		try {
			if(conn==null)
				throw new NullPointerException("connection can't be null");
			if(tableName==null)
				throw new NullPointerException("tableName can't be null");
			DatabaseMetaData metaData = conn.getMetaData();
			return getColumnInfos(metaData, tableName);
		} catch (Exception e) {
			throw new RuntimeException("获取字段信息异常3",e);
		}
	}
	/**
	 * 获取主键信息
	 * @param pkSet
	 * @return
	 */
	public static List<PrimaryKeyInfo> getPrimaryKeyInfos(ResultSet pkSet){
		try {
			List<PrimaryKeyInfo> infos=new ArrayList<>();
			while(pkSet.next()){
				PrimaryKeyInfo temp=new PrimaryKeyInfo();
				temp.setColumnName(pkSet.getString("COLUMN_NAME"));
				temp.setKeySequence(pkSet.getString("KEY_SEQ"));
				temp.setPrimaryKeyName(pkSet.getString("PK_NAME"));
				temp.setTableName(pkSet.getString("TABLE_NAME"));
				infos.add(temp);
			}
			return infos;
		} catch (Exception e) {
			throw new RuntimeException("获取主键信息异常1",e);
		}
	}
	/**
	 * 获取指定表的主键信息
	 * @param metaData
	 * @param tableName
	 * @return
	 */
	public static List<PrimaryKeyInfo> getPrimaryKeyInfos(DatabaseMetaData metaData,String tableName){
		try {
			ResultSet pkSet=metaData.getPrimaryKeys(null, null, tableName);
			return getPrimaryKeyInfos(pkSet);
		} catch (Exception e) {
			throw new RuntimeException("获取主键信息异常2",e);
		}
	}
	/**
	 * 获取指定表的主键信息
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public static List<PrimaryKeyInfo> getPrimaryKeyInfos(Connection conn,String tableName){
		try {
			DatabaseMetaData metaData=conn.getMetaData();
			return getPrimaryKeyInfos(metaData, tableName);
		} catch (Exception e) {
			throw new RuntimeException("获取主键信息异常3",e);
		}
	}
	/**
	 * 获取外键信息
	 * @param pkSet
	 * @return
	 */
	public static List<ForeginKeyInfo> getForeginKeyInfos(ResultSet fkSet){
		try {
			List<ForeginKeyInfo> infos=new ArrayList<>();
			while(fkSet.next()){
				ForeginKeyInfo temp=new ForeginKeyInfo();
				temp.setTableName(fkSet.getString("PKTABLE_NAME"));
				temp.setColumnName(fkSet.getString("PKCOLUMN_NAME"));
				temp.setForeginTableName(fkSet.getString("FKTABLE_NAME"));
				temp.setForeginColumnName(fkSet.getString("FKCOLUMN_NAME"));
				temp.setKeySequence(fkSet.getString("KEY_SEQ"));
				temp.setPrimaryName(fkSet.getString("PK_NAME"));
				temp.setForeginName(fkSet.getString("FK_NAME"));
				infos.add(temp);
			}
			return infos;	
		} catch (Exception e) {
			throw new RuntimeException("获取外键信息异常1",e);
		}
	}
	/**
	 * 获取指定表的外键信息
	 * @param metaData
	 * @param tableName
	 * @return
	 */
	public static List<ForeginKeyInfo> getForeginKeyInfos(DatabaseMetaData metaData,String tableName){
		try {
			ResultSet fkSet=metaData.getExportedKeys(null, null, tableName);
			return getForeginKeyInfos(fkSet);
		} catch (Exception e) {
			throw new RuntimeException("获取外键信息异常2",e);
		}
	}
	/**
	 * 获取指定表的外键信息
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public static List<ForeginKeyInfo> getForeginKeyInfos(Connection conn,String tableName){
		try {
			DatabaseMetaData metaData=conn.getMetaData();
			return getForeginKeyInfos(metaData, tableName);
		} catch (Exception e) {
			throw new RuntimeException("获取外键信息异常3",e);
		}
	}
	/**
	 * 获取索引信息 不包含主键
	 * @param indexSet
	 * @return
	 */
	public static List<IndexInfo> 	   getIndexInfos(ResultSet indexSet){
		try {
			List<IndexInfo> infos=new ArrayList<>();
			while(indexSet.next()){
				IndexInfo temp=new IndexInfo();
				temp.setIndexName(indexSet.getString("INDEX_NAME"));
				if("PRIMARY".equals(temp.getIndexName()))
					continue;
				temp.setIsUnique(!indexSet.getBoolean("NON_UNIQUE")+"");
				temp.setIndexQualifier(indexSet.getString("INDEX_QUALIFIER"));
				temp.setType(indexSet.getString("TYPE"));
				temp.setColumnName(indexSet.getString("COLUMN_NAME"));
				temp.setAscOrDesc(indexSet.getString("ASC_OR_DESC"));
				temp.setCardinality(indexSet.getString("CARDINALITY"));
				temp.setIndexPosition(indexSet.getString("ORDINAL_POSITION"));
				infos.add(temp);
			}
			return infos;
		} catch (Exception e) {
			throw new RuntimeException("获取索引信息异常1",e);
		}
	}
	/**
	 * 获取表中所有索引信息 不包含主键
	 * @param metaData
	 * @param tableName
	 * @return
	 */
	public static List<IndexInfo> 	   getIndexInfos(DatabaseMetaData metaData,String tableName){
		try {
			ResultSet indexSet=metaData.getIndexInfo(null, null, tableName, true, true);
			return getIndexInfos(indexSet);
		} catch (Exception e) {
			throw new RuntimeException("获取索引信息异常2",e);
		}
	}
	/**
	 * 获取表中所有索引信息 不包含主键
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public static List<IndexInfo> 	   getIndexList(Connection conn,String tableName){
		try {
			DatabaseMetaData metaData=conn.getMetaData();
			return getIndexInfos(metaData, tableName);
		} catch (Exception e) {
			throw new RuntimeException("获取索引信息异常3",e);
		}
	}
	/**
	 * 以map形式获取索引信息
	 * @param conn 		
	 * @param tableName
	 * @return
	 * 		map<indexPosition,List<indexInfo>>
	 */
	public static Map<String , List<IndexInfo>> getIndexInfos(Connection conn,String tableName){
		try {
			Map<String , List<IndexInfo>>map=new LinkedHashMap<>();
			List<IndexInfo> indexInfos=getIndexList(conn, tableName);
			for(IndexInfo each:indexInfos){
				if(map.containsKey(each.getIndexName()))
					map.get(each.getIndexName()).add(each);
				else{
					List<IndexInfo> list=new ArrayList<>();
					list.add(each);
					map.put(each.getIndexName(),list);
				}
			}
			return map;
		} catch (Exception e) {
			throw new RuntimeException("获取索引信息异常4",e);
		}
	}
	
	/**
	 * 获取指定名称的表
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public static DBTable getTable(Connection conn,String tableName){
		try{
			DBTable  table=new DBTable();
			DatabaseMetaData metaData = conn.getMetaData();
			ResultSet tableSet = metaData.getTables(null, null, tableName, null);
			while(tableSet.next()){
				table=new DBTable();
				table.setDbInfo(getDataBaseInfo(conn));
				table.setTableInfo(getTableInfo(tableSet));
				table.setColumnInfos(getColumnInfos(conn, table.getTableInfo().getTableName()));
				table.setIndexInfo(getIndexInfos(conn, table.getTableInfo().getTableName()));
				table.setPkInfo(getPrimaryKeyInfos(conn, table.getTableInfo().getTableName()));
				table.setFkInfo(getForeginKeyInfos(conn, table.getTableInfo().getTableName()));
			}
			return table;
		}catch (Exception e) {
			throw new RuntimeException("获取表信息异常",e);
		}
	}
	
	/**
	 * 获取数据库中的所有表及视图
	 * 
	 * @param conn
	 * @return
	 */
	public static List<DBTable> getTables(Connection conn) { 
		try {
			List<DBTable> tables = new ArrayList<>();
			DatabaseMetaData metaData = conn.getMetaData();
			ResultSet tableSet = metaData.getTables(null, null, null, null);
			while(tableSet.next()){
				DBTable temp=new DBTable();
				temp.setDbInfo(getDataBaseInfo(conn));
				temp.setTableInfo(getTableInfo(tableSet));
				temp.setColumnInfos(getColumnInfos(conn, temp.getTableInfo().getTableName()));
				temp.setIndexInfo(getIndexInfos(conn, temp.getTableInfo().getTableName()));
				temp.setPkInfo(getPrimaryKeyInfos(conn, temp.getTableInfo().getTableName()));
				temp.setFkInfo(getForeginKeyInfos(conn, temp.getTableInfo().getTableName()));
				tables.add(temp);
			}
			return tables;
		} catch (Exception e) {
			throw new RuntimeException("获取表数据异常", e);
		}
	}
	/**
	 * 使用Table中的信息来构建javaBean类
	 * @param table
	 * @param annotationFlag  
	 * 			true  会增加hibernate注解
	 * 			false 不会增加注解
	 * @prefix 表前缀,配置后生成的javaBean名称会过滤前缀
	 * @return
	 */
	public static String makeBeanFile(DBTable table,boolean annotationFlag,String prefix,boolean remind){
		String fileContent=null;
		try {
			prefix=prefix==null?"":prefix;
			StringBuffer buffer=new StringBuffer();
			Map<String , String> map=getSomeElement(table, prefix); 
			//-------------------------------package---------------------------------
			String packagePath=map.get("package");
			buffer.append("package "+packagePath+";\n\n");
			//--------------------------------import---------------------------------
			List<ColumnInfo> columnInfos=table.getColumnInfos();
			List<PrimaryKeyInfo> pkInfos=table.getPkInfo();
			Map<String , PrimaryKeyInfo> pkMap=listToMap(pkInfos);
			Set<String> tempSet=new HashSet<>();
			for(ColumnInfo each:columnInfos){
				String paramType=each.getJavaType();
				paramType=paramType.startsWith("[L")?paramType.substring(2):paramType;
				paramType=paramType.endsWith(";")?paramType.substring(0, paramType.length()-1):paramType;
				tempSet.add(paramType);
			}
			for(String each:tempSet)
				if(each.startsWith("[L")||each.endsWith(";"))
					continue;
				else
					buffer.append("import "+each+";\n");
			buffer.append("\n");
			if(annotationFlag){
				buffer.append("import "+"javax.persistence.Column"+";\n");
				buffer.append("import "+"javax.persistence.Entity"+";\n");
				buffer.append("import "+"javax.persistence.Table"+";\n");
				buffer.append("import "+"javax.persistence.Id"+";\n");
				buffer.append("\n");
			}
			//--------------------------------class name------------------------------
			String className=map.get("className");
			//---------------------------------class comment---------------------------
			String classRemark=table.getTableInfo().getRemark();
			buffer.append("/**\n");
			buffer.append("*");
			buffer.append(classRemark==null||classRemark.isEmpty()?"no comment":classRemark);
			buffer.append("\n");
			buffer.append("*"+"@author yyj | auto generator\n");
			buffer.append("*"+"@version "+"1.0.0 "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())+"\n");
			buffer.append("*/\n");
			if(annotationFlag){
				buffer.append("@Entity"+"\n");
				buffer.append("@Table"+"(name=\""+table.getTableInfo().getTableName()+"\")\n");
			}
			buffer.append("\n");
			//----------------------------------class body-------------------------------
			buffer.append("public class "+className+"{"+"\n");
			//---------------------------------- field ---------------------------------
			for(ColumnInfo each:columnInfos){
				String javaType=each.getJavaType();
				javaType=javaType.substring(javaType.lastIndexOf('.')+1,javaType.length());
				javaType=javaType.replace(";","[]");
				String name=each.getColumnName();
				name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
				String remark=each.getRemark();
				buffer.append("/**");
				buffer.append(remark==null|remark.isEmpty()?"no comment":remark);
				buffer.append("*/\n");
				if(annotationFlag){
					buffer.append("@Id"+"\n");
					buffer.append("@Column(");
					buffer.append("name=\""+each.getColumnName()+"\",");
					buffer.append("unique="+pkMap.containsKey(each.getColumnName())+",");
					buffer.append("nullable="+each.getNullAble()+")\n");
				}
				buffer.append("private "+javaType+" "+name+";\n\n");
			}
			//---------------------------------------set & get method------------------------
			for(ColumnInfo each:columnInfos){
				String javaType=each.getJavaType();
				javaType=javaType.substring(javaType.lastIndexOf('.')+1,javaType.length());
				javaType=javaType.replace(";","[]");
				String name=each.getColumnName();
				name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
				String remark=each.getRemark();
				buffer.append("/**获取");
				buffer.append(remark==null|remark.isEmpty()?name:remark);
				buffer.append("*/\n");
				buffer.append("public "+javaType+" get"+CheckReplaceUtil.firstCharUpcase(name)+"(){\n");
				buffer.append("return this."+name+";\n");
				buffer.append("}\n\n");
				buffer.append("/**设置");
				buffer.append(remark==null|remark.isEmpty()?name:remark);
				buffer.append("*/\n");
				buffer.append("public "+"void"+" set"+CheckReplaceUtil.firstCharUpcase(name)+"("+javaType+" "+name+"){\n");
				buffer.append("this."+name+"="+name+";\n\n");
				buffer.append("}\n");
			}
			buffer.append("}\n");
			//--------------------------------------java file format-----------------------------
			fileContent=FileUtil.javaFormat(buffer.toString());
			//---------------------------------------save javabean file-------------------------
			String path=SqlUtilForDB.class.getClassLoader().getResource("").toURI().getPath();
			path=path.substring(0,path.indexOf("target"))+"src/main/java/";
			String[] strs=packagePath.split("\\.");
			for(String each:strs)
				path+=each+"/";
			File file=new File(path);
			if(!file.exists()||!file.isDirectory())
				file.mkdir();
			path+=className+".java";
			file=new File(path);
			FileUtil.writeStrToFile(fileContent, file, "utf-8");
			if(remind)
				JOptionPane.showMessageDialog(null, "生成单个javaBean成功\n路径:"+packagePath+"\n请刷新项目", "提示", JOptionPane.ERROR_MESSAGE); 
			return fileContent;
		} catch (Exception e) {
			throw new RuntimeException("构建bean文件内容异常",e);
		}
	}
	/**
	 * 构建bean
	 * @param list
	 * @param annotationFlag
	 */
	public static void makeBeanFiles(List<DBTable> list,String prefix,boolean annotationFlag,boolean remind){
		prefix=prefix==null?"":prefix;
		String packagePath=SqlUtilForDB.class.getPackage().getName();
		packagePath=packagePath.substring(0,packagePath.lastIndexOf('.')+1);
		packagePath+="entity";
		for(DBTable each:list)
			makeBeanFile(each, annotationFlag,prefix,false);
		if(remind)
			JOptionPane.showMessageDialog(null, "生成所有javaBean成功\n路径:"+packagePath+"\n请刷新项目", "提示", JOptionPane.ERROR_MESSAGE); 
	}
	/**
	 * 根据表信息生成mybatis映射文件
	 * @param table
	 * @return
	 */
	
	public static String makeMybatisFile(DBTable table,String prefix,String descOrAsc,boolean paging,boolean remind){
		try {
			String fileContent=null;
			prefix=prefix==null?"":prefix;
			StringBuffer temp = new StringBuffer();
			Map<String , String> map=getSomeElement(table, prefix); 
			temp.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + "<!DOCTYPE mapper PUBLIC "
					+ "\"-//ibatis.apache.org//DTD Mapper 3.0//EN\" "
					+ "\"http://ibatis.apache.org/dtd/ibatis-3-mapper.dtd\">\n");
			temp.append("<mapper namespace=\""+map.get("package").replaceFirst("entity", "dao.")+map.get("className").replace("Bean","")+"Dao\">\n");
			temp.append(createInsert(table, prefix));
			temp.append(createDelete(table, prefix));
			temp.append(createUpdate(table, prefix));
			temp.append(createSelect(table, prefix,descOrAsc,paging));
			temp.append(createResultMap(table,prefix));
			temp.append("</mapper>");
			fileContent=temp.toString();
			//--------------------------------------------------------------------------------------
			String packagePath=SqlUtilForDB.class.getPackage().getName();
			packagePath=packagePath.substring(0,packagePath.lastIndexOf('.')+1);
			packagePath+="dao";
			String path=SqlUtilForDB.class.getClassLoader().getResource("").toURI().getPath();
			path=path.substring(0,path.indexOf("target"))+"src/main/java/";
			String[] strs=packagePath.split("\\.");
			for(String each:strs)
				path+=each+"/";
			File file=new File(path);
			if(!file.exists()||!file.isDirectory())
				file.mkdir();
			path+=map.get("className").replace("Bean","");
			path+="Mapper.xml";
			file=new File(path);
			fileContent=FileUtil.xmlFormat(fileContent);
			FileUtil.writeStrToFile(fileContent, file, "utf-8");
			if(remind)
				JOptionPane.showMessageDialog(null, "生成mybatis Mapper文件成功\n路径:"+packagePath+"\n请刷新项目", "提示", JOptionPane.ERROR_MESSAGE); 
			return fileContent;
		} catch (Exception e) {
			throw new RuntimeException("");
		}
	}
	/**
	 * 传入DBTable列表生成mybatis mapper文件
	 * @param list 		DBTable列表
	 * @param prefix	表名前缀修复
	 * @param descOrAsc 选择时倒序或者正序
	 * @param paging 	分页|不分页
	 * @param remind 	是否弹窗提示
	 */
	public static void makeMybatisFiles(List<DBTable> list,String prefix,String descOrAsc,boolean paging,boolean remind){
		prefix=prefix==null?"":prefix;
		String packagePath=SqlUtilForDB.class.getPackage().getName();
		packagePath=packagePath.substring(0,packagePath.lastIndexOf('.')+1);
		packagePath+="dao";
		for(DBTable each:list)
			makeMybatisFile(each, prefix,descOrAsc,paging, false);
		if(remind)
			JOptionPane.showMessageDialog(null, "生成mybatis Mapper成功\n路径:"+packagePath+"\n请刷新项目", "提示", JOptionPane.ERROR_MESSAGE); 
	}
	/**
	 * 根据DBtable信息生成 mybatisDao
	 * @param table 			表信息
	 * @param prefix 			表名前缀过滤	
	 * @param remind 			是否弹窗提示
	 * @return
	 */
	public static String makeMybatisDao(DBTable table,String prefix,boolean remind){
		
		String fileContent=null;
		try {
			prefix=prefix==null?"":prefix;
			StringBuffer buffer=new StringBuffer();
			//-------------------------------package---------------------------------
			String packagePath=SqlUtilForDB.class.getPackage().getName();
			packagePath=packagePath.substring(0,packagePath.lastIndexOf('.')+1);
			packagePath+="dao";
			buffer.append("package "+packagePath+";\n\n");
			Map<String , String> map=getSomeElement(table, prefix); 
			String className=map.get("className");
			//--------------------------------import---------------------------------
			List<ColumnInfo> columnInfos=table.getColumnInfos();
			List<PrimaryKeyInfo> pkInfos=table.getPkInfo();
			Map<String, ColumnInfo>columnMap=listToMap(columnInfos);
			Map<String , List<IndexInfo>> indexInfos=table.getIndexInfo();
			buffer.append("import "+map.get("class")+";\n");
			buffer.append("import org.apache.ibatis.annotations.Param;\n");
			buffer.append("import java.util.*;\n");
			//---------------------------------class comment---------------------------
			String classRemark=table.getTableInfo().getRemark();
			buffer.append("/**\n");
			buffer.append("*");
			buffer.append(classRemark==null||classRemark.isEmpty()?"no comment":classRemark);
			buffer.append("\n");
			buffer.append("*"+"@author yyj | auto generator\n");
			buffer.append("*"+"@version "+"1.0.0 "+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date())+"\n");
			buffer.append("*/\n");
			buffer.append("\n");
			//----------------------------------class body-------------------------------
			buffer.append("public interface "+map.get("className").replaceFirst("Bean", "")+"Dao"+"{"+"\n");
			buffer.append("\n\n"); 
			//----------------------------------insert-----------------------------------------------
			buffer.append("public void add"+map.get("className")+"("+map.get("className")+" "+CheckReplaceUtil.firstCharLowcase(map.get("className"))+");\n");
			buffer.append("\n"); 
			//----------------------------------delete-----------------------------------------------
			buffer.append("public void delete"+map.get("className")+"ByClass("+map.get("className")+" "+CheckReplaceUtil.firstCharLowcase(map.get("className"))+");\n");
			String paramType=null;
			if(pkInfos!=null&&!pkInfos.isEmpty()){
				for(PrimaryKeyInfo each:pkInfos){
					String name=each.getColumnName();
					name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
					name=CheckReplaceUtil.firstCharUpcase(name);
					paramType=columnMap.get(each.getColumnName()).getJavaType();
					paramType=paramType.startsWith("[L")?paramType.substring(2):paramType;
					paramType=paramType.endsWith(";")?paramType.substring(0, paramType.length()-1)+"[]":paramType;
					paramType=paramType.substring(paramType.lastIndexOf(".")+1);
					buffer.append("public void delete"+map.get("className")+"ByPk"+name+"("+paramType+" "+CheckReplaceUtil.firstCharLowcase(name)+");\n");
				}
			}
			if(indexInfos!=null&&!indexInfos.isEmpty()){
				for(Entry<String, List<IndexInfo>> each:indexInfos.entrySet()){					
					List<IndexInfo> value=each.getValue();
					if(value.get(0).getIsUnique().equals("true"))
						buffer.append("public void delete"+map.get("className")+"ByUniqueIndexOn");
					else
						buffer.append("public void delete"+map.get("className")+"ByIndexOn");
					for(IndexInfo one:value){
						String name=one.getColumnName();
						name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
						buffer.append(CheckReplaceUtil.firstCharUpcase(name)+"And");
					}
					buffer.replace(buffer.length() - 3, buffer.length(), "");
					buffer.append("(");
					for(IndexInfo one:value){
						String name=one.getColumnName();
						name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
						name=CheckReplaceUtil.firstCharUpcase(name);
						paramType=columnMap.get(one.getColumnName()).getJavaType();
						paramType=paramType.startsWith("[L")?paramType.substring(2):paramType;
						paramType=paramType.endsWith(";")?paramType.substring(0, paramType.length()-1)+"[]":paramType;
						paramType=paramType.substring(paramType.lastIndexOf(".")+1);
						buffer.append("@Param(value=\""+CheckReplaceUtil.firstCharLowcase(name)+"\")"+paramType+" "+CheckReplaceUtil.firstCharLowcase(name)+",");
					}
					buffer.replace(buffer.length()-1,buffer.length(),"");
					buffer.append(");\n");
				}
			}
			buffer.append("\n"); 
			//----------------------------------update-----------------------------------------------
			buffer.append("public void update"+map.get("className")+"ByClass(@Param(value=\""+CheckReplaceUtil.firstCharLowcase(map.get("className"))+"\")"+map.get("className")+" "+CheckReplaceUtil.firstCharLowcase(map.get("className"))+",@Param(value=\"param\")"+map.get("className")+" param);\n");
			if(pkInfos!=null&&!pkInfos.isEmpty()){
				for(PrimaryKeyInfo each:pkInfos){
					String name=each.getColumnName();
					name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
					name=CheckReplaceUtil.firstCharUpcase(name);
					paramType=columnMap.get(each.getColumnName()).getJavaType();
					paramType=paramType.startsWith("[L")?paramType.substring(2):paramType;
					paramType=paramType.endsWith(";")?paramType.substring(0, paramType.length()-1)+"[]":paramType;
					paramType=paramType.substring(paramType.lastIndexOf(".")+1);
					buffer.append("public void update"+map.get("className")+"ByPk"+name+"(@Param(value=\""+CheckReplaceUtil.firstCharLowcase(map.get("className"))+"\")"+map.get("className")+" "+CheckReplaceUtil.firstCharLowcase(map.get("className"))+","+paramType+" "+CheckReplaceUtil.firstCharLowcase(name)+");\n");
				}
				if(indexInfos!=null&&!indexInfos.isEmpty()){
					for(Entry<String, List<IndexInfo>> each:indexInfos.entrySet()){
						List<IndexInfo> value=each.getValue();
						if(value.get(0).getIsUnique().equals("true"))
							buffer.append("public void update"+map.get("className")+"ByUniqueIndexOn");
						else
							buffer.append("public void update"+map.get("className")+"ByIndexOn");
						for(IndexInfo one:value){
							String name=one.getColumnName();
							name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
							buffer.append(CheckReplaceUtil.firstCharUpcase(name)+"And");
						}
						buffer.replace(buffer.length() - 3, buffer.length(), "");
						buffer.append("(@Param(value=\""+CheckReplaceUtil.firstCharLowcase(map.get("className"))+"\")"+map.get("className")+" "+CheckReplaceUtil.firstCharLowcase(map.get("className"))+",");
						for(IndexInfo one:value){
							String name=one.getColumnName();
							name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
							name=CheckReplaceUtil.firstCharUpcase(name);
							paramType=columnMap.get(one.getColumnName()).getJavaType();
							paramType=paramType.startsWith("[L")?paramType.substring(2):paramType;
							paramType=paramType.endsWith(";")?paramType.substring(0, paramType.length()-1)+"[]":paramType;
							paramType=paramType.substring(paramType.lastIndexOf(".")+1);
							buffer.append("@Param(value=\""+CheckReplaceUtil.firstCharLowcase(name)+"\")"+paramType+" "+CheckReplaceUtil.firstCharLowcase(name)+",");
						}
						buffer.replace(buffer.length()-1,buffer.length(),"");
						buffer.append(");\n");
					}
				}
			}
			buffer.append("\n"); 
			//----------------------------------select-----------------------------------------------
			buffer.append("public List<"+map.get("className")+"> select"+map.get("className")+"ByClass");
			buffer.append("("+map.get("className")+" "+CheckReplaceUtil.firstCharLowcase(map.get("className"))+");\n");
		
			if(pkInfos!=null&&!pkInfos.isEmpty()){
				for(PrimaryKeyInfo each:pkInfos){
					String name=each.getColumnName();
					name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
					name=CheckReplaceUtil.firstCharUpcase(name);
					paramType=columnMap.get(each.getColumnName()).getJavaType();
					paramType=paramType.startsWith("[L")?paramType.substring(2):paramType;
					paramType=paramType.endsWith(";")?paramType.substring(0, paramType.length()-1)+"[]":paramType;
					paramType=paramType.substring(paramType.lastIndexOf(".")+1);
					buffer.append("public "+map.get("className")+" select"+map.get("className")+"ByPk"+name);
					buffer.append("("+paramType+" "+CheckReplaceUtil.firstCharLowcase(name)+");\n");
				}
			}
			
			if(indexInfos!=null&&!indexInfos.isEmpty()){
				for(Entry<String, List<IndexInfo>> each:indexInfos.entrySet()){
					List<IndexInfo> value=each.getValue();
					if(value.get(0).getIsUnique().equals("true"))
						buffer.append("public "+map.get("className")+" select"+map.get("className")+"ByUniqueIndexOn");
					else
						buffer.append("public List<"+map.get("className")+"> select"+map.get("className")+"ByIndexOn");
					for(IndexInfo one:value){
						String name=one.getColumnName();
						name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
						buffer.append(CheckReplaceUtil.firstCharUpcase(name)+"And");
					}
					buffer.replace(buffer.length() - 3, buffer.length(), "");
					buffer.append("(");
					for(IndexInfo one:value){
						String name=one.getColumnName();
						name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
						name=CheckReplaceUtil.firstCharUpcase(name);
						paramType=columnMap.get(one.getColumnName()).getJavaType();
						paramType=paramType.startsWith("[L")?paramType.substring(2):paramType;
						paramType=paramType.endsWith(";")?paramType.substring(0, paramType.length()-1)+"[]":paramType;
						paramType=paramType.substring(paramType.lastIndexOf(".")+1);
						buffer.append("@Param(value=\""+CheckReplaceUtil.firstCharLowcase(name)+"\")"+paramType+" "+CheckReplaceUtil.firstCharLowcase(name)+",");
					}
					buffer.replace(buffer.length()-1, buffer.length(), "");
					buffer.append(");\n");
				}
			}
			buffer.append("\n"); 
			//--------------------------------------------------------------------------------------
			buffer.append("}");
			fileContent=buffer.toString();
			fileContent=FileUtil.javaFormat(buffer.toString());
			String path=SqlUtilForDB.class.getClassLoader().getResource("").toURI().getPath();
			path=path.substring(0,path.indexOf("target"))+"src/main/java/";
			String[] strs=packagePath.split("\\.");
			for(String each:strs)
				path+=each+"/";
			File file=new File(path);
			if(!file.exists()||!file.isDirectory())
				file.mkdir();
			path+=className+"Dao.java";
			file=new File(path);
			FileUtil.writeStrToFile(fileContent, file, "utf-8");
			if(remind)
				JOptionPane.showMessageDialog(null, "生成单个Dao成功\n路径:"+packagePath+"\n请刷新项目", "提示", JOptionPane.ERROR_MESSAGE); 
			return fileContent;
		} catch (Exception e) {
			throw new RuntimeException("生成单个dao文件异常",e);
		}
	}
	/**
	 * 传入 DBTable列表 生成 dao层
	 * @param list
	 * @param prefix
	 * @param remind
	 */
	public static void makeMybatisDaoes(List<DBTable> list,String prefix,boolean remind){
		try {
			prefix=prefix==null?"":prefix;
			String packagePath=SqlUtilForDB.class.getPackage().getName();
			packagePath=packagePath.substring(0,packagePath.lastIndexOf('.')+1);
			packagePath+="dao";
			for(DBTable each:list)
				makeMybatisDao(each, prefix, false);
			if(remind)
				JOptionPane.showMessageDialog(null, "生成所有Dao成功\n路径:"+packagePath+"\n请刷新项目", "提示", JOptionPane.ERROR_MESSAGE); 
		} catch (Exception e) {
			throw new RuntimeException("生成所有dao文件异常",e);
		}
	}
	/**
	 * 构建 javaBean ,mapper, dao 
	 * @param list
	 * @param prefix
	 * @param descOrAsc
	 * @param paging
	 * @param annotationFlag
	 */
	public static void makeAll(List<DBTable> list,String prefix,String descOrAsc,boolean paging,boolean annotationFlag){
		try {
			prefix=prefix==null?"":prefix;
			String packagePath=SqlUtilForDB.class.getPackage().getName();
			packagePath=packagePath.substring(0,packagePath.lastIndexOf('.')+1);
			packagePath+="dao";
			makeBeanFiles(list, prefix, annotationFlag, false);
			makeMybatisDaoes(list, prefix,false);
			makeMybatisFiles(list, prefix, descOrAsc, paging, false);
			JOptionPane.showMessageDialog(null, "生成Dao与mapper成功\n路径:"+packagePath+"\n"+"生成javaBean成功\n路径:"+packagePath.replace("dao", "entity")+"\n请刷新项目", "提示", JOptionPane.ERROR_MESSAGE);
		} catch (Exception e) {
			throw new RuntimeException("自动生成文件异常",e);
		}
	}
	/**
	 * 构建 javaBean ,mapper, dao 
	 * @param conn
	 * @param prefix
	 * @param descOrAsc
	 * @param paging
	 * @param annotationFlag
	 */
	public static void makeAll(Connection conn,String prefix,String descOrAsc,boolean paging,boolean annotationFlag){
		try {
			prefix=prefix==null?"":prefix;
			makeAll(getTables(conn), prefix, descOrAsc, paging, annotationFlag);
		} catch (Exception e) {
			throw new RuntimeException("自动生成文件异常",e);
		}
	}
	
	/**
	 * 抽取 table中的 信息
	 * @param table 	表
	 * @param prefix 	前缀过滤
	 * @return
	 */
	private static Map<String , String> getSomeElement(DBTable table,String prefix){
		prefix=prefix==null?"":prefix;
		Map<String,String> map=new HashMap<>(); 
		String packagePath=SqlUtilForDB.class.getPackage().getName();
		packagePath=packagePath.substring(0,packagePath.lastIndexOf('.')+1);
		packagePath+="entity";
		map.put("package",packagePath);
		String tableName=table.getTableInfo().getTableName();
		map.put("table",tableName);
		
		String className=tableName;
		if(prefix!=null&&!prefix.isEmpty())
			className=className.startsWith(prefix)?className.replaceFirst(prefix, ""):className;
		className=CheckReplaceUtil.firstCharUpcase(className);
		className=CheckReplaceUtil.underlineLowcaserToUpcase(className);
		
		String simpleClassName=className;
		map.put("className", simpleClassName);
		
		
		className=packagePath+"."+className;
		map.put("class", className);
		return map;
	}
	/**
	 * 使用table中的信息创建mybatis insert部分
	 * @param table 	数据库表
	 * @param prefix 	表名前缀,过滤前缀后需要与实体类名称相同
	 * @return
	 */
	public static String createInsert(DBTable table,String prefix){
		try {
			prefix=prefix==null?"":prefix;
			StringBuffer temp = new StringBuffer();
			Map<String , String> map=getSomeElement(table, prefix); 
			temp.append("<insert id=\"add" + map.get("className").replace("Bean", "") + "\" " + "parameterType=\"" +  map.get("class") + "\">\n");
			temp.append("insert into " + map.get("table") + " \n(");
			List<ColumnInfo> columnInfos=table.getColumnInfos();
			for(ColumnInfo each:columnInfos)
				temp.append(each.getColumnName()+",");
			temp.replace(temp.length()-1,temp.length(),"");
			temp.append(")\nvalues\n(");
			for(ColumnInfo each:columnInfos){
				String name=each.getColumnName();
				name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
				if(each.getIsAutoCrement().equals("true")||!each.getNullAble().equals("true")){
					if(!temp.toString().endsWith("\n"))
						temp.append("\n");
					temp.append("<if test=\"" + name + " != null\">#{"+name+"},");
					temp.append("</if>\n");
					temp.append("<if test=\"" + name + " == null\">default,");
					temp.append("</if>\n");
				}else
					temp.append("#{"+ name +"},");
			}
			temp.replace(temp.length() - 1, temp.length(), "");
			temp.append("\n)");
			temp.append("\n</insert>\n");
			return temp.toString();
		} catch (Exception e) {
			throw new RuntimeException("构建mybatis mapper 文件 insert语句异常",e);
		}
	}
	
	public static String createDelete(DBTable table,String prefix){
		try {
			prefix=prefix==null?"":prefix;
			StringBuffer temp = new StringBuffer();
			Map<String , String> map=getSomeElement(table, prefix); 
			List<ColumnInfo> columnInfos=table.getColumnInfos();
			Map<String, ColumnInfo> columnMap=listToMap(columnInfos);
			List<PrimaryKeyInfo> pkInfos=table.getPkInfo();
			Map<String , List<IndexInfo>> indexInfos=table.getIndexInfo();
			//--------------------------------delete by class-----------------------------------------
			String paramType=map.get("class");
			temp.append("<delete id=\"delete" + map.get("className").replace("Bean", "")+"ByClass" + "\" " + "parameterType=\"" + paramType + "\">\n");
			temp.append("delete from " + map.get("table"));
			temp.append("\n<where>\n");
			for(ColumnInfo each:columnInfos){
				String name=each.getColumnName();
				name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
				temp.append("<if test=\"" + name + " != null\"> and " + each.getColumnName()+ " = #{" + name + "}  </if>  \n");
			}
			temp.append("</where>\n</delete>\n");
			//---------------------------------delete by primary key-----------------------------------
			if(pkInfos!=null&&!pkInfos.isEmpty()){
				for(PrimaryKeyInfo each:pkInfos){
					String name=each.getColumnName();
					name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
					name=CheckReplaceUtil.firstCharUpcase(name);
					paramType=columnMap.get(each.getColumnName()).getJavaType();
					paramType=paramType.startsWith("[L")?paramType.substring(2):paramType;
					paramType=paramType.endsWith(";")?paramType.substring(0, paramType.length()-1):paramType;
					temp.append("<delete id=\"delete" + map.get("className").replace("Bean", "")+"ByPk" +name+ "\" " + "parameterType=\"" + columnMap.get(each.getColumnName()).getJavaType()+ "\">\n");
					temp.append("delete from " + map.get("table"));
					temp.append("\n<where>\n");
					temp.append(each.getColumnName()+"="+"{"+name+"}\n");
					temp.append("</where>\n</delete>\n");
				}
			}
			//---------------------------------delete by index----------------------------------
			if(indexInfos!=null&&!indexInfos.isEmpty()){
				for(Entry<String, List<IndexInfo>> each:indexInfos.entrySet()){
					List<IndexInfo> value=each.getValue();
					if(value.get(0).getIsUnique().equals("true"))
						temp.append("<delete id=\"delete" + map.get("className").replace("Bean", "")+"ByUniqueIndexOn");
					else
						temp.append("<delete id=\"delete" + map.get("className").replace("Bean", "")+"ByIndexOn");
					for(IndexInfo one:value){
						String name=one.getColumnName();
						name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
						temp.append(CheckReplaceUtil.firstCharUpcase(name)+"And");
					}
					temp.replace(temp.length() - 3, temp.length(), "");
					temp.append("\">\n");
					temp.append("delete from " + map.get("table"));
					temp.append("\n<where>\n");
					for(IndexInfo one:value){
						String name=one.getColumnName();
						name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
						temp.append("AND " + one.getColumnName()+ " = #{" + name + "}\n");
					}
					temp.append("</where>\n</delete>\n");
				}
			}
			return temp.toString(); 
 		} catch (Exception e) {
			throw new RuntimeException("构建mybatis mapper 文件 delete语句异常",e);
		}
	}
	
	public static String createUpdate(DBTable table,String prefix){
		try {
			prefix=prefix==null?"":prefix;
			StringBuffer temp = new StringBuffer();
			Map<String , String> map=getSomeElement(table, prefix); 
			List<ColumnInfo> columnInfos=table.getColumnInfos();
			Map<String, ColumnInfo> columnMap=listToMap(columnInfos);
			List<PrimaryKeyInfo> pKeyInfos=table.getPkInfo();
			Map<String , List<IndexInfo>> indexInfos=table.getIndexInfo();
			String paramType=map.get("class");
			//-----------------------------------------by class---------------------------------------
			temp.append("<update id=\"update" + map.get("className").replace("Bean", "")+"ByClass" + "\">\n");
			temp.append("update " + map.get("table") + "\n<set> \n");
			for(ColumnInfo one:columnInfos){
				String columnName=one.getColumnName();
				columnName=CheckReplaceUtil.underlineLowcaserToUpcase(columnName);
				temp.append(one.getColumnName()+"="+"{"+CheckReplaceUtil.firstCharLowcase(map.get("className"))+"."+columnName+"},\n");
			}
			temp.replace(temp.length()-2, temp.length(), "\n");
			temp.append("</set>\n<where>\n");
			for(ColumnInfo one:columnInfos){
				String columnName=one.getColumnName();
				columnName=CheckReplaceUtil.underlineLowcaserToUpcase(columnName);
				temp.append("<if test=\"" + columnName + " != null\">" +one.getColumnName() + " = #{"+"param."+ columnName + "},</if>  \n");
			}
			temp.append("</where>\n</update>\n");
			//-----------------------------------------by primary key----------------------------------
			if(pKeyInfos!=null&&!pKeyInfos.isEmpty()){
				for(PrimaryKeyInfo each:pKeyInfos){
					String name=each.getColumnName();
					name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
					name=CheckReplaceUtil.firstCharUpcase(name);
					paramType=columnMap.get(each.getColumnName()).getJavaType();
					paramType=paramType.startsWith("[L")?paramType.substring(2):paramType;
					paramType=paramType.endsWith(";")?paramType.substring(0, paramType.length()-1):paramType;
					temp.append("<update id=\"update" + map.get("className").replace("Bean", "") + "ByPk"+name+"\" " + "parameterType=\"" + paramType + "\">\n");
					temp.append("update " + map.get("table") + "\n<set> \n");
					for(ColumnInfo one:columnInfos){
						String columnName=one.getColumnName();
						columnName=CheckReplaceUtil.underlineLowcaserToUpcase(columnName);
						temp.append(one.getColumnName()+"="+"{"+CheckReplaceUtil.firstCharLowcase(map.get("className"))+"."+columnName+"},\n");
					}
					temp.replace(temp.length()-2, temp.length(), "\n");
					temp.append("</set>\n<where>\n");
					temp.append(each.getColumnName()+"="+"{"+name+"}\n");
					temp.append("</where>\n</update>\n");
				}
			}
			//-----------------------------------------by unique index----------------------------------
			if(indexInfos!=null&&!indexInfos.isEmpty()){
				for(Entry<String, List<IndexInfo>> each:indexInfos.entrySet()){
					List<IndexInfo> value=each.getValue();
					if(value.get(0).getIsUnique().equals("true"))
						temp.append("<update id=\"update" + map.get("className").replace("Bean", "")+"ByUniqueIndexOn");
					else
						temp.append("<update id=\"update" + map.get("className").replace("Bean", "")+"ByIndexOn");
					for(IndexInfo one:value){
						String name=one.getColumnName();
						name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
						temp.append(CheckReplaceUtil.firstCharUpcase(name)+"And");
					}
					temp.replace(temp.length() - 3, temp.length(), "");
					temp.append("\">\n");
					temp.append("update " + map.get("table") + "\n<set> \n");
					for(ColumnInfo one:columnInfos){
						String columnName=one.getColumnName();
						columnName=CheckReplaceUtil.underlineLowcaserToUpcase(columnName);
						temp.append(one.getColumnName()+"="+"{"+CheckReplaceUtil.firstCharLowcase(map.get("className"))+"."+columnName+"},\n");
					}
					temp.replace(temp.length()-2, temp.length(), "\n");
					temp.append("</set>\n<where>\n");
					for(IndexInfo one:value){
						String name=one.getColumnName();
						name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
						temp.append("AND "+one.getColumnName()+"="+"{"+name+"}\n");
					}
					temp.append("</where>\n</update>\n");
				}				
			}
			return temp.toString();
		} catch (Exception e) {
			throw new RuntimeException("构建mybatis mapper 文件 update语句异常",e);
		}
	}
	
	public static String createSelect(DBTable table,String prefix,String descOrAsc,boolean paging){
		try {
			prefix=prefix==null?"":prefix;
			descOrAsc=descOrAsc==null?"ASC":descOrAsc;
			descOrAsc=descOrAsc.equalsIgnoreCase("asc")?"ASC":descOrAsc.equalsIgnoreCase("desc")?"DESC":"ASC";
			StringBuffer temp = new StringBuffer();
			Map<String , String> map=getSomeElement(table, prefix); 
			List<ColumnInfo> columnInfos=table.getColumnInfos();
			Map<String, ColumnInfo> columnMap=listToMap(columnInfos);
			List<PrimaryKeyInfo> pkInfos=table.getPkInfo();
			Map<String , List<IndexInfo>> indexInfos=table.getIndexInfo();
			String paramType=map.get("class");
			//--------------------------------by class-------------------------------------------------
			temp.append("<select id=\"select" + map.get("className").replace("Bean", "")+"ByClass" + "\"");
			temp.append(" resultMap=\""+CheckReplaceUtil.firstCharLowcase(map.get("className").replace("Bean", ""))+ "Map\" ");
			temp.append(" resultType=\""+map.get("class")+"\"");
			temp.append(" parameterType=\"" + paramType + "\">\n");
			temp.append("select \n");
			for(ColumnInfo each:columnInfos)
				temp.append(each.getColumnName()+",");
			temp.replace(temp.length() - 1, temp.length(), "");
			temp.append("\nfrom " + map.get("table"));
			temp.append("\n<where> \n");
			for(ColumnInfo one:columnInfos){
				String columnName=one.getColumnName();
				columnName=CheckReplaceUtil.underlineLowcaserToUpcase(columnName);
				temp.append("<if test=\"" + columnName + " != null\">" + one.getColumnName() + " = #{"+ columnName + "},</if>  \n");
			}
			temp.append("</where>\n</select>\n");
			//--------------------------------by primary key-------------------------------------------
			if(pkInfos!=null&&!pkInfos.isEmpty()){
				for(PrimaryKeyInfo each:pkInfos){
					String name=each.getColumnName();
					name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
					name=CheckReplaceUtil.firstCharUpcase(name);
					paramType=columnMap.get(each.getColumnName()).getJavaType();
					paramType=paramType.startsWith("[L")?paramType.substring(2):paramType;
					paramType=paramType.endsWith(";")?paramType.substring(0, paramType.length()-1):paramType;
					temp.append("<select id=\"select" + map.get("className").replace("Bean", "") + "ByPk"+name+"\" ");
					temp.append(" resultMap=\""+CheckReplaceUtil.firstCharLowcase(map.get("className").replace("Bean", ""))+ "Map\" ");
					temp.append(" resultType=\""+map.get("class")+"\"");
					temp.append(" parameterType=\"" + paramType + "\">\n");
					temp.append("select \n");
					for(ColumnInfo one:columnInfos)
						temp.append(one.getColumnName()+",");
					temp.replace(temp.length() - 1, temp.length(), "");
					temp.append("\nfrom " + map.get("table"));
					temp.append("\n<where> \n");
					temp.append(each.getColumnName()+"="+"{"+name+"}\n");
					temp.append("</where>\n</select>\n");
				}
			}
			//--------------------------------by index------------------------------------------
			if(indexInfos!=null&&!indexInfos.isEmpty()){
				for(Entry<String, List<IndexInfo>> each:indexInfos.entrySet()){
					List<IndexInfo> value=each.getValue();
					if(value.get(0).getIsUnique().equals("true"))
						temp.append("<select id=\"select" + map.get("className").replace("Bean", "")+"ByUniqueIndexOn");
					else
						temp.append("<select id=\"select" + map.get("className").replace("Bean", "")+"ByIndexOn");
					for(IndexInfo one:value){
						String name=one.getColumnName();
						name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
						temp.append(CheckReplaceUtil.firstCharUpcase(name)+"And");
					}
					temp.replace(temp.length() - 3, temp.length(), "");
					temp.append("\"");
					temp.append(" resultType=\""+map.get("class")+"\"");
					temp.append(" resultMap=\""+CheckReplaceUtil.firstCharLowcase(map.get("className").replace("Bean", ""))+ "Map\" ");
					temp.append(">\n");
					temp.append("select \n");
					for(ColumnInfo one:columnInfos)
						temp.append(one.getColumnName()+",");
					temp.replace(temp.length() - 1, temp.length(), "");
					temp.append("\nfrom " + map.get("table"));
					temp.append("\n<where> \n");
					for(IndexInfo one:value){
						String name=one.getColumnName();
						name=CheckReplaceUtil.underlineLowcaserToUpcase(name);
						temp.append("AND "+one.getColumnName()+"="+"{"+name+"}\n");
					}
					temp.append("</where>\n</select>\n");
				}				
			}
			return temp.toString();
		} catch (Exception e) {
			throw new RuntimeException("构建mybatis mapper 文件 select语句异常",e);
		}
	}
	
	public static String createResultMap(DBTable table,String prefix){
		try {
			prefix=prefix==null?"":prefix;
			StringBuffer temp = new StringBuffer();
			Map<String , String> map=getSomeElement(table, prefix); 
			List<ColumnInfo> columnInfos=table.getColumnInfos();
			String paramType=map.get("class");
			temp.append("<resultMap id=\"" + CheckReplaceUtil.firstCharLowcase(map.get("className").replace("Bean", ""))+ "Map\" " + "type=\"" + paramType + "\">\n");
			for(ColumnInfo each:columnInfos){
				String columnName=each.getColumnName();
				columnName=CheckReplaceUtil.underlineLowcaserToUpcase(columnName);
				temp.append("<result property=\"" + columnName + "\" 			column=\"" + each.getColumnName() + "\"></result>\n");
			}
			temp.append("</resultMap>\n");
			return temp.toString();
		} catch (Exception e) {
			throw new RuntimeException("构建mybatis mapper 文件 resultmap语句异常",e);
		}
	}
	
	
	
	/**
	 * 数据库表类
	 * 
	 * @author 1
	 *
	 */
	public static class DBTable {
		/** 表类型-表 */
		public static final String TABLE_TYPE_TABLE = "TABLE";
		/** 表类型-视图 */
		public static final String TABLE_TYPE_VIEW = "VIEW";
		/** 表类型-系统表 */
		public static final String TABLE_TYPE_SYSTEM_TABLE = "SYSTEM TABLE";
		/** 表类型-全局临时表 */
		public static final String TABLE_TYPE_GLOBAL_TEMPORARY = "GLOBAL TEMPORARY";
		/** 表类型-本地临时表 */
		public static final String TABLE_TYPE_LOCAL_TEMPORARY = "LOCAL  TEMPORARY";
		/** 表类型-别名表 */
		public static final String TABLE_TYPE_ALIAS = "ALIAS";
		/** 表类型-指针表 */
		public static final String TABLE_TYPE_SYSNONYM = "SYSNONYM";

		/** 数据库信息 */
		private DatabaseInfo dbInfo;
		/** 表信息 */
		private TableInfo tableInfo;
		/** 字段信息 */
		private List<ColumnInfo> columnInfos;
		/** 主键信息 */
		private List<PrimaryKeyInfo> pkInfo;
		/** 外键信息 */
		private List<ForeginKeyInfo> fkInfo;
		/** 索引信息 */
		private Map<String , List<IndexInfo>> indexInfo;
		/** 存储过程信息 */
		private List<ProceduresInfo> proceduresInfo;

		public DBTable() {
			columnInfos		=new ArrayList<>();
			pkInfo			=new ArrayList<>();
			fkInfo			=new ArrayList<>();
			indexInfo		=new LinkedHashMap<>();
			proceduresInfo	=new ArrayList<>();
		}
		
		public DatabaseInfo getDbInfo() {
			if(dbInfo==null)
				dbInfo			=new DatabaseInfo();
			return dbInfo;
		}

		public void setDbInfo(DatabaseInfo dbInfo) {
			this.dbInfo = dbInfo;
		}

		public TableInfo getTableInfo() {
			if(tableInfo==null)
				tableInfo		=new TableInfo();
			return tableInfo;
		}

		public void setTableInfo(TableInfo tableInfo) {
			this.tableInfo = tableInfo;
		}

		public List<ColumnInfo> getColumnInfos() {
			if(columnInfos==null)
				columnInfos=new ArrayList<>();
			return columnInfos;
		}

		public void setColumnInfos(List<ColumnInfo> columnInfos) {
			this.columnInfos = columnInfos;
		}

		public List<PrimaryKeyInfo> getPkInfo() {
			if(pkInfo==null)
				pkInfo=new ArrayList<>();
			return pkInfo;
		}

		public void setPkInfo(List<PrimaryKeyInfo> pkInfo) {
			this.pkInfo = pkInfo;
		}

		public List<ForeginKeyInfo> getFkInfo() {
			if(fkInfo==null)
				fkInfo= new ArrayList<>();
			return fkInfo;
		}

		public void setFkInfo(List<ForeginKeyInfo> fkInfo) {
			this.fkInfo = fkInfo;
		}

		public Map<String , List<IndexInfo>> getIndexInfo() {
			if(indexInfo==null)
				indexInfo=new LinkedHashMap<>();
			return indexInfo;
		}

		public void setIndexInfo(Map<String , List<IndexInfo>> indexInfo) {
			this.indexInfo = indexInfo;
		}

		public List<ProceduresInfo> getProceduresInfo() {
			if(proceduresInfo==null)
				proceduresInfo=new ArrayList<>();
			return proceduresInfo;
		}

		public void setProceduresInfo(List<ProceduresInfo> proceduresInfo) {
			this.proceduresInfo = proceduresInfo;
		}

		@Override
		public String toString() {
			return BeanUtil.toString(this);
		}

	}
	/**
	 * 数据信息类
	 * @author 1
	 */
	public static class DatabaseInfo {
		/** 数据库名称 */
		private String productName;
		/** 数据版本 */
		private String productVersion;
		/** 驱动名称 */
		private String driverName;
		/** 驱动版本 */
		private String driverVersion;

		public String getProductName() {
			return productName;
		}

		public void setProductName(String productName) {
			this.productName = productName;
		}

		public String getProductVersion() {
			return productVersion;
		}

		public void setProductVersion(String productVersion) {
			this.productVersion = productVersion;
		}

		public String getDriverVersion() {
			return driverVersion;
		}

		public void setDriverVersion(String driverVersion) {
			this.driverVersion = driverVersion;
		}

		public String getDriverName() {
			return driverName;
		}

		public void setDriverName(String driverName) {
			this.driverName = driverName;
		}

		@Override
		public String toString() {
			return BeanUtil.toString(this);
		}
	}
	/**
	 * 表信息类
	 * @author 1
	 */
	public static class TableInfo {
		/** 表名称 */
		private String tableName;
		/** 表类型 */
		private String type;
		/** 表注释 */
		private String remark;

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		@Override
		public String toString() {
			return BeanUtil.toString(this);
		}
	}
	/**
	 * 字段信息类
	 * @author 1
	 */
	public static class ColumnInfo {
		/** 表名称 */
		private String tableName;
		/** 字段名称 */
		private String columnName;
		/** 对应的java.sql数据类型 int 名称 */
		private String dataType;
		/**数据库数据类型-未使用*/
		private String sqlType;
		/** 对应的java数据类型名称 */
		private String javaType;
		/** 字段大小 */
		private String columnSize;
		/** 是否可以为null */
		private String nullAble;
		/** 默认值 */
		private String defaultValue;
		/** 字符型长度上限 */
		private String charSize;
		/** 索引位置 */
		private String ordinalPosition;
		/** 备注 */
		private String remark;
		/** 是否自增 */
		private String isAutoCrement;
		/**小数位数*/
		private String decimalDigits;
		/**基数*/
		private String numPrecRadix;
		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		public String getDataType() {
			return dataType;
		}

		public void setDataType(String dataType) {
			switch (dataType) {
				case "-16":dataType="LONGNVARCHAR";break;
				case "-15":dataType="NCHAR";break;
				case "-9":dataType="NVARCHAR";break;
				case "-8":dataType="ROWID";break;
				case "-7":	dataType="BIT";		break;
				case "-6":	dataType="TINYINT";	break;
				case "-5":dataType="BIGINT";break;
				case "-4":dataType="LONGVARBINARY";break;
				case "-3":dataType="VARBINARY";break;
				case "-2":dataType="BINARY";break;
				case "-1":dataType="LONGVARCHAR";break;
				case "0":dataType="NULL";break;
				case "1":dataType="CHAR";break;
				case "2":dataType="NUMERIC";break;
				case "3":dataType="DECIMAL";break;
				case "4":dataType="INTEGER";break;
				case "5" :	dataType="SMALLINT";break;
				case "6":dataType="FLOAT";break;
				case "7":dataType="REAL";break;
				case "8":dataType="DOUBLE";break;
				case "12":dataType="VARCHAR";break;
				case "16":dataType="BOOLEAN";break;
				case "70":dataType="DATALINK";break;
				case "91":dataType="DATE";break;
				case "92":dataType="TIME";break;
				case "93":dataType="TIMESTAMP";break;
				case "1111":dataType="OTHER";break;
				case "2000":dataType="JAVA_OBJECT";break;
				case "2001":dataType="DISTINCT";break;
				case "2002":dataType="STRUCT";break;
				case "2003":dataType="ARRAY";break;
				case "2004":dataType="BLOB";break;
				case "2005":dataType="CLOB";break;
				case "2006":dataType="REF";break;
				case "2009":dataType="SQLXML";break;
				case "2011":dataType="NCLOB";break;
				case "2012":dataType="REF_CURSOR";break;
				case "2013":dataType="TIME_WITH_TIMEZONE";break;
				case "2014":dataType="TIMESTAMP_WITH_TIMEZONE";break;
				default:dataType="OTHER";break;
			}
			this.dataType = dataType;
		}

		public String getSqlType() {
			return sqlType;
		}

		public void setSqlType(String sqlType) {
			this.sqlType = sqlType;
		}

		public String getJavaType() {
			return javaType;
		}

		public void setJavaType(String javaType) {
			Class<?> clazz=ddtTojdt.get(javaType.toLowerCase());
			this.javaType =clazz==null?ddtTojdt.get(getDataType())==null?null:ddtTojdt.get(getDataType()).getName():clazz.getName();
		}

		public String getColumnSize() {
			return columnSize;
		}

		public void setColumnSize(String columnSize) {
			this.columnSize = columnSize;
		}

		public String getNullAble() {
			return nullAble;
		}

		public void setNullAble(String nullAble) {
			nullAble=nullAble.equals("1")?true+"":false+"";
			this.nullAble = nullAble;
		}

		public String getDefaultValue() {
			return defaultValue;
		}

		public void setDefaultValue(String defaultValue) {
			this.defaultValue = defaultValue;
		}

		public String getCharSize() {
			return charSize;
		}

		public void setCharSize(String charSize) {
			this.charSize = charSize;
		}

		public String getOrdinalPosition() {
			return ordinalPosition;
		}

		public void setOrdinalPosition(String ordinalPosition) {
			this.ordinalPosition = ordinalPosition;
		}

		public String getRemark() {
			return remark;
		}

		public void setRemark(String remark) {
			this.remark = remark;
		}

		public String getIsAutoCrement() {
			return isAutoCrement;
		}

		public void setIsAutoCrement(String isAutoCrement) {
			if(isAutoCrement.equals("YES"))
				isAutoCrement="true";
			else
				isAutoCrement="false";
			this.isAutoCrement = isAutoCrement;
		}

		
		public String getDecimalDigits() {
			return decimalDigits;
		}

		public void setDecimalDigits(String decimalDigits) {
			this.decimalDigits = decimalDigits;
		}

		public String getNumPrecRadix() {
			return numPrecRadix;
		}

		public void setNumPrecRadix(String numPrecRadix) {
			this.numPrecRadix = numPrecRadix;
		}

		@Override
		public String toString() {
			return BeanUtil.toString(this);
		}
	}
	/**
	 * 主键信息类
	 * @author 1
	 *
	 */
	public static class PrimaryKeyInfo {
		/** 表名 */
		private String tableName;
		/** 字段名 */
		private String columnName;
		/** 主键名 */
		private String primaryKeyName;
		/** 序列号 主键内值1表示第一列的主键，值2代表主键内的第二列 */
		private String keySequence;

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		public String getPrimaryKeyName() {
			return primaryKeyName;
		}

		public void setPrimaryKeyName(String primaryKeyName) {
			this.primaryKeyName = primaryKeyName;
		}

		public String getKeySequence() {
			return keySequence;
		}

		public void setKeySequence(String keySequence) {
			this.keySequence = keySequence;
		}

		@Override
		public String toString() {
			return BeanUtil.toString(this);
		}
	}
	/**
	 * 外键信息类
	 * @author 1
	 *
	 */
	public static class ForeginKeyInfo {
		/** 主键表名 */
		private String tableName;
		/** 主键字段名 */
		private String columnName;
		/** 主键名称 */
		private String primaryName;
		/** 外键表名 */
		private String foreginTableName;
		/** 外键字段名 */
		private String foreginColumnName;
		/** 外键名称 */
		private String foreginName;
		/** 序列号 */
		private String keySequence;

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		public String getForeginTableName() {
			return foreginTableName;
		}

		public void setForeginTableName(String foreginTableName) {
			this.foreginTableName = foreginTableName;
		}

		public String getForeginColumnName() {
			return foreginColumnName;
		}

		public void setForeginColumnName(String foreginColumnName) {
			this.foreginColumnName = foreginColumnName;
		}

		public String getKeySequence() {
			return keySequence;
		}

		public void setKeySequence(String keySequence) {
			this.keySequence = keySequence;
		}

		public String getPrimaryName() {
			return primaryName;
		}

		public void setPrimaryName(String primaryName) {
			this.primaryName = primaryName;
		}

		public String getForeginName() {
			return foreginName;
		}

		public void setForeginName(String foreginName) {
			this.foreginName = foreginName;
		}

		@Override
		public String toString() {
			return BeanUtil.toString(this);
		}
	}
	/**
	 * 索引信息类
	 * @author 1
	 *
	 */
	public static class IndexInfo {
		/** 是否唯一 */
		private String isUnique;
		/** 索引名称 */
		private String indexName;
		/** 索引类型 */
		private String type;
		/** 索引列序号 */
		private String indexPosition;
		/** 字段名称 */
		private String columnName;
		/** 索引目录 */
		private String indexQualifier;
		/** 基数 */
		private String cardinality;
		/** 列排序顺序 */
		private String ascOrDesc;

		public String getIsUnique() {
			return isUnique;
		}

		public void setIsUnique(String isUnique) {
			this.isUnique = isUnique;
		}

		public String getIndexName() {
			return indexName;
		}

		public void setIndexName(String indexName) {
			this.indexName = indexName;
		}

		public String getIndexPosition() {
			return indexPosition;
		}

		public void setIndexPosition(String indexPosition) {
			this.indexPosition = indexPosition;
		}

		public String getColumnName() {
			return columnName;
		}

		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}

		public String getIndexQualifier() {
			return indexQualifier;
		}

		public void setIndexQualifier(String indexQualifier) {
			this.indexQualifier = indexQualifier;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			switch (type) {
			case "0":type="没有索引";break;
			case "1":type="聚集索引";break;
			case "2":type="哈希索引";break;
			case "3":type="其他索引";break;
			}
			this.type = type;
		}

		public String getCardinality() {
			return cardinality;
		}

		public void setCardinality(String cardinality) {
			this.cardinality = cardinality;
		}

		public String getAscOrDesc() {
			return ascOrDesc;
		}

		public void setAscOrDesc(String ascOrDesc) {
			this.ascOrDesc = ascOrDesc;
		}

		@Override
		public String toString() {
			return BeanUtil.toString(this);
		}
	}
	/**
	 * 存储过程信息类
	 * @author 1
	 *
	 */
	public static class ProceduresInfo {
		/**存储过程名称*/
		private String name;
		/**存储过程类型-有无返回值*/
		private String type;
		/**存储过程字段名称*/
		private String columnName;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		
		public String getColumnName() {
			return columnName;
		}
		public void setColumnName(String columnName) {
			this.columnName = columnName;
		}
		@Override
		public String toString() {
			return BeanUtil.toString(this);
		}
	}

}

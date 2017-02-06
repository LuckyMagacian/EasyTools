package yyj.tools;

import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

public class SqlUtilForDB {
	/** jdbc配置文件 */
	private static Properties properties = new Properties();
	/** jdbc配置文件路径 */
	private static String path = "jdbc.properties";
	/** 加载配置文件 */
	static {
		try {
			File file = FileUtil.getClassPathFile(path);
			FileReader reader = new FileReader(file);
			properties.load(reader);
		} catch (Exception e) {
			throw new AppException("sqlutil初始化jdbc配置异常", e);
		}
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
			Connection conn = DriverManager.getConnection(properties.getProperty("dbUrl"),
					properties.getProperty("username"), properties.getProperty("password"));
			return conn;
		} catch (Exception e) {
			throw new AppException("获取数据库连接异常", e);
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
			throw new AppException("从连接池获取连接异常", e);
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
			throw new AppException("关闭连接异常", e);
		}
	}

	/**
	 * 获取数据库产品的名称->oracle | mysql .et
	 * 
	 * @param conn
	 * @return
	 */
	public static String getDataBasePructName(Connection conn) {
		try {
			DatabaseMetaData metaData = conn.getMetaData();
			return metaData.getDatabaseProductName();
		} catch (Exception e) {
			throw new AppException("获取数据库信息异常", e);
		}
	}

	/**
	 * 获取数据库产品版本
	 * 
	 * @param conn
	 * @return
	 */
	public static String getDatabaseProductVersion(Connection conn) {
		try {
			DatabaseMetaData metaData = conn.getMetaData();
			return metaData.getDatabaseProductVersion();
		} catch (Exception e) {
			throw new AppException("获取数据库信息异常", e);
		}
	}

	/**
	 * 获取数据库驱动名称
	 * 
	 * @param conn
	 * @return
	 */
	public static String getDriverName(Connection conn) {
		try {
			DatabaseMetaData metaData = conn.getMetaData();
			return metaData.getDriverName();
		} catch (Exception e) {
			throw new AppException("获取数据库信息异常", e);
		}
	}

	/**
	 * 获取数据库驱动版本
	 * 
	 * @param conn
	 * @return
	 */
	public static String getDriverVersion(Connection conn) {
		try {
			DatabaseMetaData metaData = conn.getMetaData();
			return metaData.getDriverVersion();
		} catch (Exception e) {
			throw new AppException("获取数据库信息异常", e);
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
			throw new AppException("获取字段信息异常", e);
		}
	}

	/**
	 * 获取数据库中的所有表
	 * 
	 * @param conn
	 * @return
	 */
	public static List<DBTable> getTables(Connection conn) {
		try {
			List<DBTable> tables = new ArrayList<>();
			DatabaseMetaData metaData = conn.getMetaData();
			ResultSet tableSet = metaData.getTables(null, null, null, null);
			while (tableSet.next()) {
				DBTable tempTable = new DBTable();
				tempTable.setDatabaseProductName(metaData.getDatabaseProductName());
				tempTable.setDatabaseProductVersion(metaData.getDatabaseProductVersion());
				tempTable.setDatabaseDriverName(metaData.getDriverName());
				tempTable.setDatabaseDriverVersion(metaData.getDriverVersion());
				tempTable.setTableName(tableSet.getString("TABLE_NAME"));
				tempTable.setType(tableSet.getString("TABLE_TYPE"));
				ResultSet columnSet = metaData.getColumns(null, null, tempTable.getTableName(), null);
				List<DBColumn> colmns = new ArrayList<>();
				while (columnSet.next()) {
					DBColumn tempColumn = new DBColumn();
					tempColumn.setCharSize(columnSet.getInt("CHAR_OCTET_LENGTH"));
					tempColumn.setColumnName(columnSet.getString("COLUMN_NAME"));
					tempColumn.setColumnSize(columnSet.getInt("COLUMN_SIZE"));
					tempColumn.setDefaultValue(columnSet.getString("COLUMN_DEF"));
					tempColumn.setIndex(columnSet.getInt("ORDINAL_POSITION"));
					tempColumn.setJavaDataType(columnSet.getString("TYPE_NAME"));
					tempColumn.setNullAble(columnSet.getBoolean("NULLABLE"));
					tempColumn.setSqlDataType(columnSet.getInt("SQL_DATA_TYPE"));
					tempColumn.setTableName(columnSet.getString("TABLE_NAME"));
					tempColumn.setRemark(columnSet.getString("REMARKS"));
					colmns.add(tempColumn);
				}
				tempTable.setColumns(colmns);
				tables.add(tempTable);
			}
			return tables;
		} catch (Exception e) {
			throw new AppException("获取表数据异常", e);
		}
	}
}

/**
 * 数据库表类
 * 
 * @author 1
 *
 */
class DBTable {
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

	/** 数据库产品名称 */
	private String databaseProductName;
	/** 数据库产品版本 */
	private String databaseProductVersion;
	/** 数据库驱动名称 */
	private String databaseDriverName;
	/** 数据库驱动版本 */
	private String databaseDriverVersion;
	/** 表名 */
	private String tableName;
	/** 表类型 */
	private String type;
	/** 字段集合 */
	private List<DBColumn> columns;

	public DBTable() {

	}

	public String getDatabaseProductName() {
		return databaseProductName;
	}

	public void setDatabaseProductName(String databaseProductName) {
		this.databaseProductName = databaseProductName;
	}

	public String getDatabaseProductVersion() {
		return databaseProductVersion;
	}

	public void setDatabaseProductVersion(String databaseProductVersion) {
		this.databaseProductVersion = databaseProductVersion;
	}

	public String getDatabaseDriverName() {
		return databaseDriverName;
	}

	public void setDatabaseDriverName(String databaseDriverName) {
		this.databaseDriverName = databaseDriverName;
	}

	public String getDatabaseDriverVersion() {
		return databaseDriverVersion;
	}

	public void setDatabaseDriverVersion(String databaseDriverVersion) {
		this.databaseDriverVersion = databaseDriverVersion;
	}

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

	public List<DBColumn> getColumns() {
		return columns;
	}

	public void setColumns(List<DBColumn> columns) {
		this.columns = columns;
	}

	@Override
	public String toString() {
		return "DBTable [databaseProductName=" + databaseProductName + ", databaseProductVersion="
				+ databaseProductVersion + ", databaseDriverName=" + databaseDriverName + ", databaseDriverVersion="
				+ databaseDriverVersion + ", tableName=" + tableName + ", type=" + type + ", columns=" + columns + "]";
	}

}

/**
 * 数据库字段类
 * 
 * @author 1
 *
 */
class DBColumn {
	/** 表名 */
	private String tableName;
	/** 数据库中数据类型 */
	private Integer sqlDataType;
	/** 对应的java数据类型 */
	private String javaDataType;
	/** 字段名称 */
	private String columnName;
	/** 字段大小 */
	private Integer columnSize;
	/** 可否为空 */
	private Boolean nullAble;
	/** 默认值 */
	private String defaultValue;
	/** 字符型最大字符数 */
	private Integer charSize;
	/** 在表中的索引位置 */
	private Integer index;
	/** 备注*/
	private String  remark;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public Integer getSqlDataType() {
		return sqlDataType;
	}

	public void setSqlDataType(Integer sqlDataType) {
		this.sqlDataType = sqlDataType;
	}

	public String getJavaDataType() {
		return javaDataType;
	}

	public void setJavaDataType(String javaDataType) {
		this.javaDataType = javaDataType;
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public Integer getColumnSize() {
		return columnSize;
	}

	public void setColumnSize(Integer columnSize) {
		this.columnSize = columnSize;
	}

	public Boolean getNullAble() {
		return nullAble;
	}

	public void setNullAble(Boolean nullAble) {
		this.nullAble = nullAble;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Integer getCharSize() {
		return charSize;
	}

	public void setCharSize(Integer charSize) {
		this.charSize = charSize;
	}

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "DBColumn [tableName=" + tableName + ", sqlDataType=" + sqlDataType + ", javaDataType=" + javaDataType
				+ ", columnName=" + columnName + ", columnSize=" + columnSize + ", nullAble=" + nullAble
				+ ", defaultValue=" + defaultValue + ", charSize=" + charSize + ", index=" + index + ", remark="
				+ remark + "]";
	}

}

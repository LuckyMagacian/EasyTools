package yyj.tools;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static yyj.tools.FileUtil.*;
import static yyj.tools.SqlUtilForDB.*;

public class EasyAccountCheck {
	/**单次读取条数*/
	private int     step;
	/**记录读取指针*/
	private int     position;
	/**对账文件*/
	private File    file;
	/**数据库连接*/
	private Connection conn;
	/**数据分隔符*/
	private String   splitchar;
	/**jdbc配置文件*/
	private Properties properties;
	/**有无汇总*/
	private boolean hastotal;
	/**流水表名*/
	private String 	tablename;
	/**我方数据-临时表表名*/
	private String  temptablename;
	/**错账表名*/
	private String  errtablename;
	/**单条记录*/
	private String  record;
	/**记录缓存*/
	private List<String> records;
	/**索引 -> 字段名映射表*/
	private Map<Integer, String>        indexMapColumn;
	/**唯一索引 -> 字段名映射表*/
	private Map<Integer, String> 	    uniqueIndexColumn;
	/**待比较值索引 -> 字段名 映射表*/
	private Map<Integer, String>  	    valueColumn;
	/**附加判定字段与值*/
	private Map<String, Object> 		moreColumnValue;
	
	public EasyAccountCheck() {
		step=1000;
		splitchar="\\|";
	}
	
	public boolean isHastotal() {
		return hastotal;
	}

	public void setHastotal(boolean hastotal) {
		this.hastotal = hastotal;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	
	
	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public int getPosition() {
		return position;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public Connection getConn() {
		return conn;
	}

	public void setConn(Connection conn) {
		this.conn = conn;
	}

	public String getSplitchar() {
		return splitchar;
	}

	public void setSplitchar(String splitchar) {
		this.splitchar = splitchar;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String getErrtablename() {
		return errtablename;
	}

	public void setErrtablename(String errtablename) {
		this.errtablename = errtablename;
	}

	public String getRecord() {
		return record;
	}

	public void setRecord(String record) {
		this.record = record;
	}

	public List<String> getRecords() {
		return records;
	}

	public void setRecords(List<String> records) {
		this.records = records;
	}

	public Map<Integer, String> getIndexMapColumn() {
		indexMapColumn=indexMapColumn==null?new LinkedHashMap<Integer,String>():indexMapColumn;
		return indexMapColumn;
	}

	public void setIndexMapColumn(Map<Integer, String> indexMapColumn) {
		this.indexMapColumn = indexMapColumn;
	}

	public Map<Integer, String> getUniqueIndexColumn() {
		uniqueIndexColumn=uniqueIndexColumn==null?new LinkedHashMap<Integer,String>():uniqueIndexColumn;
		return uniqueIndexColumn;
	}

	public void setUniqueIndexColumn(Map<Integer, String> uniqueIndexColumn) {
		this.uniqueIndexColumn = uniqueIndexColumn;
	}

	public Map<Integer, String> getValueColumn() {
		valueColumn=valueColumn==null?new LinkedHashMap<Integer,String>():valueColumn;
		return valueColumn;
	}

	public void setValueColumn(Map<Integer, String> valueColumn) {
		this.valueColumn = valueColumn;
	}

	public Map<String, Object> getMoreColumnValue() {
		moreColumnValue=moreColumnValue==null?new LinkedHashMap<String,Object>():moreColumnValue;
		return moreColumnValue;
	}

	public void setMoreColumnValue(Map<String, Object> moreColumnValue) {
		this.moreColumnValue = moreColumnValue;
	}

	public void loadFile(){
		file=loadFileInClassPath(".txt");
	}
	
	public void loadJdbc(){
		try{
			properties=new Properties();
			properties.load(new FileInputStream(loadFileInClassPath(".properties")));
			conn=getConnection(properties);
		}catch (Exception e) {
			throw new RuntimeException("加载jdbc配置文件异常");
		}
	}
	
	public void loadRecords(){
		if(position==0)
			position=hastotal?2:1;
		records=readFileByLineStartAndStop(file, position,position+step,"utf-8");
		position=position+step;
	}
	
	
	
	@Override
	public String toString() {
		return BeanUtil.toString(this);
	}
	
	public static void main(String[] args){
		EasyAccountCheck eac=new EasyAccountCheck();
		eac.setHastotal(true);
		eac.loadFile();
		eac.loadRecords();
		eac.loadJdbc();
		eac.setTablename("t_car_owner");
		Map<Integer, String> indexColumn=eac.getIndexMapColumn();
		indexColumn.put(1, "car_plate_no");
		indexColumn.put(2, "customer_name");
		indexColumn.put(3, "id_card");
		Map<Integer,String> uindex=eac.getUniqueIndexColumn();
		uindex.put(1, indexColumn.get(1));
		uindex.put(2,indexColumn.get(2));
		Map<Integer, String> values=eac.getValueColumn();
		values.put(3, indexColumn.get(3));
		eac.setErrtablename("err_account");
	}
} 

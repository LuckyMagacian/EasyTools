package yyj.tools;

import static yyj.tools.FileUtil.loadFileInClassPath;
import static yyj.tools.FileUtil.readFileByLineStartAndStop;
import static yyj.tools.SqlUtilForDB.getConnection;
import static yyj.tools.SqlUtilForDB.isExist;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.log4j.Logger;
public class AccountCompareUtil {
	/**配置日志记录*/
	static{
		/**清除上次的对账记录*/
		LoggerUtil.setClear(true);
		LoggerUtil.init();
	}
	/**日志记录*/
	private static Logger log=Logger.getLogger(AccountCompareUtil.class);
	/**文件读取完毕记录 true代表文件尚未读取完成 false反之*/
	private static boolean     hasNext;
	/**单次读取条数*/
	private static int     	step;
	/**记录读取指针*/
	private static int     	position;
	/**当前记录指针*/
	private static int         current;
	/**对账文件*/
	private static File    	file;
	/**数据库连接*/
	private static Connection 	conn;
	/**数据分隔符*/
	private static String   	splitchar;
	/**jdbc配置文件*/
	private static Properties 	properties;
	/**有无汇总*/
	private static boolean 	hastotal;
	/**流水表名*/
	private static String 		tablename;
	/**错账表名 		默认为流水表名_err*/
	private static String  	errtablename;
	/**我方记录临时表--	默认为流水表名_copy*/
	private static String      temptablename;
	/**单条记录*/
	private static LinkedHashMap<String, Object>  		record;
	/**记录缓存*/
	private static List<String> 						records;
	/**索引 -> 字段名映射表*/
	private static LinkedHashMap<Integer, String>      indexColumnMap;
	/**唯一索引下标*/
	private static ArrayList<Integer> 	   		 		uniqueIndexIndex;
	/**泛选字段索引下标*/
	private static LinkedHashMap<String, Object> 		multiColumnValue;
	/**待比较值索引下标*/
	private static ArrayList<Integer>  	    		valueColumn;
	/**附加判定字段与值*/
	private static Map<String, Object> 				moreColumnValue;
	/**数据库中获取到的记录信息*/
	private static Map<String, Object> 				dbRecord;
	static {
		log.info("配置默认参数");
		step=1000;
		splitchar="\\|";
		dbRecord=new HashMap<>();
		hastotal=true;
		hasNext=true;
	}
	public static int getStep() {
		return step;
	}
	public static void setStep(int step1) {
		step = step1;
	}
	public static int getPosition() {
		return position;
	}
	public static void setPosition(int position1) {
		position = position1;
	}
	public static int getCurrent() {
		return current;
	}
	public static void setCurrent(int current1) {
		current = current1;
	}
	public static File getFile() {
		return file;
	}
	public static void setFile(File file1) {
		file = file1;
	}
	public static Connection getConn() {
		return conn;
	}
	public static void setConn(Connection conn1) {
		conn = conn1;
	}
	public static String getSplitchar() {
		return splitchar;
	}
	public static void setSplitchar(String splitchar1) {
		splitchar = splitchar1;
	}
	public static Properties getProperties() {
		return properties;
	}
	public static void setProperties(Properties properties1) {
		properties = properties1;
	}
	public static boolean isHastotal() {
		return hastotal;
	}
	public static void setHastotal(boolean hastotal1) {
		hastotal = hastotal1;
	}
	public static String getTablename() {
		return tablename;
	}
	public static void setTablename(String tablename1) {
		tablename = tablename1;
		temptablename=temptablename==null||temptablename.isEmpty()?tablename+"_copy":temptablename;
		errtablename=errtablename==null||errtablename.isEmpty()?tablename+"_err":errtablename;
	}
	public static String getErrtablename() {
		return errtablename;
	}
	public static void setErrtablename(String errtablename1) {
		errtablename = errtablename1;
	}
	public static LinkedHashMap<String, Object> getRecord() {
		return record;
	}
	public static List<String> getRecords() {
		return records;
	}
	public static void setRecords(List<String> records1) {
		records = records1;
	}
	public static LinkedHashMap<Integer, String> getIndexColumnMap() {
		indexColumnMap=indexColumnMap==null?new LinkedHashMap<Integer, String>():indexColumnMap;
		return indexColumnMap;
	}
	public static void setIndexColumnMap(LinkedHashMap<Integer, String> indexColumnMap1) {
		indexColumnMap = indexColumnMap1;
	}
	public static ArrayList<Integer> getUniqueIndexIndex() {
		uniqueIndexIndex=uniqueIndexIndex==null?new ArrayList<Integer>():uniqueIndexIndex;
		return uniqueIndexIndex;
	}
	public static void setUniqueIndexIndex(ArrayList<Integer> uniqueIndexIndex1) {
		uniqueIndexIndex = uniqueIndexIndex1;
	}
	public static ArrayList<Integer> getValueColumn() {
		valueColumn=valueColumn==null?new ArrayList<Integer>():valueColumn;
		return valueColumn;
	}
	public static void setValueColumn(ArrayList<Integer> valueColumn1) {
		valueColumn = valueColumn1;
	}
	public static Map<String, Object> getMoreColumnValue() {
		moreColumnValue=moreColumnValue==null?new HashMap<String, Object>():moreColumnValue;
		return moreColumnValue;
	}
	public static void setMoreColumnValue(Map<String, Object> moreColumnValue1) {
		moreColumnValue = moreColumnValue1;
	}
	public static void setDbRecord(Map<String, Object> dbRecord1) {
		dbRecord = dbRecord1;
	}	
	public static String getTemptablename() {
		temptablename=temptablename==null||temptablename.isEmpty()?tablename+"_copy":temptablename;
		return temptablename;
	}
	public static void setTemptablename(String temptablename1) {
		temptablename = temptablename1;
	}
	public static LinkedHashMap<String, Object>  getMultiColumnValue() {
		multiColumnValue=multiColumnValue==null?new LinkedHashMap<String, Object> ():multiColumnValue;
		return multiColumnValue;
	}
	public static void setMultiColumnValuex(LinkedHashMap<String, Object>  multiColumnValue1) {
		multiColumnValue = multiColumnValue1;
	}
	/**
	 * 该方法用于选择对账文件---
	 */
	public static void loadFile(){
		file=loadFileInClassPath(".txt");
		log.info("加载对账文件"+file.toURI().getPath());
	}
	/**
	 * 该方法用于选择加载jdbc配置文件
	 */
	public static void loadJdbc(){
		try{
			properties=new Properties();
			properties.load(new FileInputStream(loadFileInClassPath(".properties")));
			conn=getConnection(properties);
			log.info("加载jdbc配置文件"+properties);
		}catch (Exception e) {
			throw new RuntimeException("加载jdbc配置文件异常");
		}
	}
	/**
	 * 从文件中加载指定条数的流水记录
	 * 	若有总账 从2开始 | 无总账 1开始
	 */
	public static void loadRecords(){
		log.info("加载多条对账记录");
		if(position==0)
			position=hastotal?2:1;
		records=readFileByLineStartAndStop(file, position,position+step,"utf-8");
		if(records.size()<step){
			hasNext=false;
			log.info("对账文件读取结束");
		}
		position=position+step;
	}
	/**
	 * 获取缓存的下一条流水记录
	 * @return
	 */
	public static Map<String, Object> next(){
		log.info("处理下一条对账记录");
		if(records==null)
			loadRecords();
		String str=records.get(current);
		String[] strs=str.split(splitchar);
		record=new LinkedHashMap<>();
		for(int i=0;i<strs.length;i++)
			record.put(indexColumnMap.get(i), strs[i]);
		current++;
		current=current==records.size()?0:current;
		if(current==0&hasNext)
			loadRecords();
		return record;
	}
	/**
	 * 构建唯一索引 preparedstatement
	 * @param record
	 * @return
	 */
	private static PreparedStatement makeStatement(Map<String, Object> record){
		try {
			log.info("构建 唯一索引选择 preparedstatement");
			StringBuffer sql=new StringBuffer("SELECT ");
			for(Integer each:valueColumn)
				sql.append(indexColumnMap.get(each)+",");
			sql.replace(sql.length()-1, sql.length(), " ");
			sql.append(" FROM "+tablename+" WHERE ");
			for(Integer each:uniqueIndexIndex)
				sql.append(" "+indexColumnMap.get(each)+"=? AND");
			for(Map.Entry<String, Object> each:moreColumnValue.entrySet())
				sql.append(" "+each.getKey()+"=? AND");
			sql.replace(sql.length()-3, sql.length(), " ");
			PreparedStatement statement=conn.prepareStatement(sql.toString());
			for(int i=1,j=uniqueIndexIndex.size()+1;i<j;i++)
				statement.setObject(i, record.get(indexColumnMap.get(i)));
			int i=uniqueIndexIndex.size()+1;
			for(Map.Entry<String, Object> each:moreColumnValue.entrySet()){
				statement.setObject(i, each.getValue());
				i++;
			}
			return statement;
		}catch (Exception e) {
			throw new RuntimeException("组建preparestatement异常",e);
		}
	}
	/**
	 * 根据对账文件中的流水记录的解析情况 获取 数据库中的流水记录
	 * @param record
	 * @param statement
	 * @return
	 */
	public static Map<String, Object> getDbRecord(Map<String, Object> record,PreparedStatement statement){
		try {
			log.info("从数据库中获取对应的对账记录");
			ResultSet resultSet=statement.executeQuery();
			ResultSetMetaData metaData=resultSet.getMetaData();
			int count=metaData.getColumnCount();
			boolean uniqueFlag=true;
			dbRecord.clear();
			int i=0;
			while(resultSet.next()){
				if(!uniqueFlag){
					log.info("唯一索引设定错误,当前唯一索引条件下发现多条记录");
					throw new RuntimeException("数据库中获取的数据不唯一,请检查唯一索引字段是否正确!");
				}
				for(i=0;i<count;i++){
					dbRecord.put(indexColumnMap.get(valueColumn.get(i)), resultSet.getObject(i+1));
				}
				uniqueFlag=false;
			}
			return dbRecord;
		} catch (Exception e) {
			throw new RuntimeException("获取数据库对应流水记录异常",e);
		}
	}
	/**
	 * 创建临时表并根据多选条件以及附加条件来复制数据库中的数据到临时表中
	 */
	public static void copyTable(){
		boolean containData=true;
		try {
			log.info("尝试复制数据库表");
			if(isExist(conn, getTemptablename())){
				log.info("表已存在,仅复制表中数据");
				copyData();
				return ;
			}
			StringBuffer sql=new StringBuffer();
			sql.append("CREATE TABLE "+getTemptablename()+" SELECT ");
			for(Entry<Integer, String> each:indexColumnMap.entrySet())
				sql.append(each.getValue()+",");
			sql.replace(sql.length()-1, sql.length(), " ");
			sql.append(" FROM "+tablename+" WHERE ");
			for(Map.Entry<String, Object> each:multiColumnValue.entrySet())
				sql.append(" "+each.getKey()+"=? AND");
			for(Map.Entry<String, Object> each:moreColumnValue.entrySet())
				sql.append(" "+each.getKey()+"=? AND");
			sql.append(" "+(containData?" '1'='1'":" '1'='2'"));
			log.info("sql:"+sql);
			PreparedStatement statement = conn.prepareStatement(sql.toString());
			int i=1;
			for(Map.Entry<String, Object> each:multiColumnValue.entrySet())
				statement.setObject(i++, each.getValue());
			for(Map.Entry<String, Object> each:moreColumnValue.entrySet())
				statement.setObject(i++, each.getValue());
			statement.execute();
			log.info("创建并复制表中数据完成");
		} catch (Exception e) {
			throw new RuntimeException("复制表数据及结构异常",e);
		}
	}
	/**
	 * 从临时表中删除指定的数据
	 * @param detail
	 */
	public static void deleteFromTemp(Map<String, Object> detail){
		try {
			log.info("尝试从临时表中删除数据");
			StringBuffer sql=new StringBuffer();
			sql.append("DELETE FROM "+getTemptablename()+" WHERE ");
			for(Map.Entry<String, Object> each:detail.entrySet())
				sql.append(" "+each.getKey()+"=? AND");
			sql.replace(sql.length() - 3, sql.length(), " ");
			log.info("sql:"+sql);
			PreparedStatement statement = conn.prepareStatement(sql.toString());
			int i=1;
			for(Map.Entry<String, Object> each:detail.entrySet())
				statement.setObject(i++, each.getValue());
			statement.execute();
		} catch (Exception e) {
			throw new RuntimeException("删除临时表中数据时发生异常",e);
		}
	}
	/**
	 * 复制数据
	 */
	public static void copyData(){
		try {
			log.info("尝试复制数据库中数据");
			StringBuffer sql = new StringBuffer();
			sql.append("INSERT INTO " + getTemptablename() + " SELECT ");
			for(Map.Entry<Integer, String> each:indexColumnMap.entrySet())
				sql.append(" "+each.getValue()+",");
			sql.replace(sql.length() - 1, sql.length(), " ");
			sql.append(" FROM " + tablename);
			sql.append(" WHERE ");
			for(Map.Entry<String, Object> each:multiColumnValue.entrySet())
				sql.append(" "+each.getKey()+"=? AND");
			for(Map.Entry<String, Object> each:moreColumnValue.entrySet())
				sql.append(" "+each.getKey()+"=? AND");
			sql.replace(sql.length() - 3, sql.length(), " ");
			log.info("sql:"+sql);
			PreparedStatement statement = conn.prepareStatement(sql.toString());
			int i=1;
			for(Map.Entry<String, Object> each:multiColumnValue.entrySet())
				statement.setObject(i++, each.getValue());
			for(Map.Entry<String, Object> each:moreColumnValue.entrySet())
				statement.setObject(i++, each.getValue());
			statement.execute();
			log.info("复制数据完成");
		} catch (Exception e) {
			throw new RuntimeException("复制数据异常",e);
		}
	}
	/**
	 * 单条记录比较
	 * @param record
	 * @param dbRecord
	 * @return 
	 * 			false 金额差错
	 * 			null  我方数据缺失
	 * 			true  对账通过
	 */
	public static Boolean compare(Map<String, Object> record,Map<String, Object> dbRecord){
		log.info("尝试对比数据obj1:"+record+"obj2:"+dbRecord);
		Boolean flag=true;
		if(dbRecord.isEmpty()){
			log.info("我方数据丢失,对比结束,对比结果:null");
			return null;
		}
		for(Integer each:valueColumn)
			flag&=(record.get(indexColumnMap.get(each)).equals(dbRecord.get(indexColumnMap.get(each))));
		log.info("对比结果:"+flag);
		return flag;
	}
	/**
	 * 获取我方存在而对方缺失的数据
	 * @return
	 */
	public static List<Map<String, Object>> getSurplus(){
		try {
			log.info("尝试获取临时表中剩余数据");
			List<Map<String, Object>> list=new ArrayList<>();
			StringBuffer sql=new StringBuffer("SELECT ");
			for(Map.Entry<Integer,String> each:indexColumnMap.entrySet())
				sql.append(each.getValue()+",");
			sql.replace(sql.length()-1, sql.length(), " ");
			sql.append(" FROM "+getTemptablename());
			log.info("sql:"+sql);
			PreparedStatement statement=conn.prepareStatement(sql.toString());
			ResultSet resultSet=statement.executeQuery();
			while(resultSet.next()){
				int i=1;
				Map<String, Object> temp=new LinkedHashMap<>();
				for(Entry<Integer, String> each:indexColumnMap.entrySet())
					temp.put(each.getValue(),resultSet.getObject(i++)); 
				list.add(temp);
				deleteFromTemp(temp);
			}
			log.info("剩余数据:"+list);
			return list;
		} catch (Exception e) {
			throw new RuntimeException("获取临时表中剩余数据异常", e);
		}
	}
	/**
	 * 对账 | 处理对账文件中的所有记录
	 */
	public static void compareAll(){
		log.info("尝试对账");
		copyTable();
		while(hasNext){
			{
				while(current<step){
					Map<String, Object> temp=next();
					deleteFromTemp(temp);
					PreparedStatement statement=makeStatement(temp);
					Boolean tempResult=compare(temp, getDbRecord(temp,statement));
					if(tempResult==null||tempResult==false){
						ErrAccount errAccount=new ErrAccount();
						errAccount.setValue(temp);
						errAccount.setTime(TimeUtil.getDateTime());
						if(tempResult==null)
							errAccount.setResult(AccountCompareResult.MORERECORD);
						if(tempResult!=null&&tempResult==false)
							errAccount.setResult(AccountCompareResult.COSTERR);
						errAccount.insertIntoDataBase();
						log.info("发现错账:"+errAccount);
					}
					if(current==0){
						log.info("当前缓存记录对比结束");
						break;	
					}
				}
				
			}
		}
		log.info("开始处理我方剩余数据");
		List<Map<String, Object>> list=getSurplus();
		for(Map<String, Object> each:list){
			ErrAccount errAccount=new ErrAccount();
			errAccount.setValue(each);
			errAccount.setTime(TimeUtil.getDateTime());
			errAccount.setResult(AccountCompareResult.NORECORD);
			errAccount.insertIntoDataBase();
			log.info("发现错账:"+errAccount);
		}
		log.info("对账完成");
	}
	/**
	 * 对账结果
	 * 0-正常
	 * 1-我方没有该笔记录
	 * 2-对方没有该笔记录
	 * 9-金额存在差错
	 * @author 1
	 *
	 */
	public static enum AccountCompareResult{
		NORMAL("0","正常"),COSTERR("9","金额存在差错"),NORECORD("2","对方没有记录"),MORERECORD("1","我方没有记录");
		private String code;
		private String message;
		private AccountCompareResult(String code,String message) {
			this.code=code;
			this.message=message;
		}
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		@Override
		public String toString() {
			return BeanUtil.toString(this);
		}
	}
	/**
	 * 错账类
	 * @author 1
	 *
	 */
	public static class ErrAccount{
		/**错账详情*/
		Map<String, Object>  value;
		/**对账结果*/
		AccountCompareResult result;
		/**对账时间*/
		String 				 time;
		public Map<String, Object> getValue() {
			return value;
		}
		public void setValue(Map<String, Object> value) {
			this.value = value;
		}
		public AccountCompareResult getResult() {
			return result;
		}
		public void setResult(AccountCompareResult result) {
			this.result = result;
		}
		public String getTime() {
			return time;
		}
		public void setTime(String time) {
			this.time = time;
		}
		/**
		 * 将错账记录插入错账表
		 */
		public void insertIntoDataBase(){
			try {
				log.info("尝试插入错账记录");
				if(!isExist(conn, errtablename)){
					log.info("错账表不存在");
					makeErrTable();
				}
				StringBuffer sql=new StringBuffer();
				sql.append("INSERT INTO " +getErrtablename()+" ( ");
				for(Map.Entry<String, Object> each:value.entrySet())
					sql.append(each.getKey()+",");
				sql.append("message,time) ");
				sql.append("values (");
				for(int i=0;i<value.size();i++)
					sql.append("?,");
				sql.append("?,?)");
				log.info("sql:"+sql);
				PreparedStatement statement=conn.prepareStatement(sql.toString());
				int i=1;
				for(Map.Entry<String, Object> each:value.entrySet())
					statement.setObject(i++,each.getValue());
				statement.setObject(i++, result.getMessage());
				statement.setObject(i++, time);
				boolean result=statement.execute();
				log.info("插入错账记录完成"+result);
			} catch (Exception e) {
				throw new RuntimeException("插入错账记录到数据库异常",e);
			}
		}
		/**
		 * 创建错账表
		 */
		public void makeErrTable(){
			try {
				log.info("尝试创建错账表");
				StringBuffer sql=new StringBuffer();
				sql.append("CREATE TABLE "+getErrtablename()+" SELECT ");
				for(Map.Entry<Integer, String> each:indexColumnMap.entrySet())
					sql.append(each.getValue()+",");
				sql.replace(sql.length()-1, sql.length(), " ");
				sql.append(" FROM "+tablename+" WHERE ");
//				for(Map.Entry<String, Object> each:multiColumnValue.entrySet())
//					sql.append(" "+each.getKey()+"=?  AND");
				sql.append(" '1'='2'");
				log.info("sql:"+sql);
				PreparedStatement statement=conn.prepareStatement(sql.toString());
//				int i=1;
//				for(Map.Entry<String, Object> each:multiColumnValue.entrySet())
//					statement.setObject(i, each.getValue());
				statement.execute();
				sql.replace(0, sql.length(), "");
				sql.append("ALTER TABLE "+getErrtablename()+" ADD ");
				sql.append(" message varchar(800)");
				log.info("sql:"+sql);
				statement.execute(sql.toString());
				sql.replace(0, sql.length(), "");
				sql.append("ALTER TABLE "+getErrtablename()+" ADD ");
				sql.append(" time char(16)");
				log.info("sql:"+sql);
				boolean result=statement.execute(sql.toString());
				log.info("创建错账表完成"+result);
			} catch (Exception e) {
				throw new RuntimeException("插入错账记录到数据库异常",e);
			}
		}
		@Override
		public String toString() {
			return BeanUtil.toString(this);
		}
	}
	/**
	 * 测试用main方法
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		AccountCompareUtil.loadFile();
		AccountCompareUtil.loadJdbc();
		Map< Integer, String> columns=AccountCompareUtil.getIndexColumnMap();
		columns.put(0, "car_plate_no");
		columns.put(1, "customer_name");
		columns.put(2, "id_card");
		List<Integer> indexs=AccountCompareUtil.getUniqueIndexIndex();
		indexs.add(0);
		indexs.add(1);
		List<Integer> values=AccountCompareUtil.getValueColumn();
		values.add(2);
		Map<String, Object> multiselect=AccountCompareUtil.getMultiColumnValue();
		multiselect.put("customer_name","老司机");
		Map<String, Object> more=AccountCompareUtil.getMoreColumnValue();
//		more.put("is_secondhand","1");
		AccountCompareUtil.setTablename("t_car_owner");
//		Map<String, Object> temp=acu.next();
//		acu.deleteFromTemp(temp);
//		PreparedStatement statement=acu.makeStatement(temp);
		AccountCompareUtil.compareAll();
//		Boolean tempResult=acu.compare(temp, acu.getDbRecord(temp,statement));
//		if(tempResult==null||tempResult==false){
//			ErrAccount errAccount=acu.new ErrAccount();
//			errAccount.setValue(temp);
//			errAccount.setTime(TimeUtil.getDateTime());
//			if(tempResult==null)
//				errAccount.setResult(AccountCompareResult.NORECORD);
//			if(tempResult!=null&&false==tempResult)
//				errAccount.setResult(AccountCompareResult.COSTERR);
//			errAccount.insertIntoDataBase();
//			System.out.println(errAccount);
//		}else {
//			System.out.println("pase!");
//		}
	}
}

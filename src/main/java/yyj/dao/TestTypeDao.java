package yyj.dao;

import yyj.entity.TestType;
import org.apache.ibatis.annotations.Param;
import java.util.*;
/**
*no comment
*@author yyj | auto generator
*@version 1.0.0 2017-02-28 16:00:32
*/

public interface TestTypeDao{
	
	
	/**插入TestType到数据库
	 * @paramtestType 待插入的对象
	 */
	public void addTestType(TestType testType);
	
	public void deleteTestTypeByClass(TestType testType);
	public void deleteTestTypeByPk(@Param(value="tInt")Integer tInt,@Param(value="tMediumint")Integer tMediumint,@Param(value="tSmallint")Integer tSmallint,@Param(value="tTinyint")Integer tTinyint);
	public void deleteTestTypeByPkTInt(Integer tInt);
	public void deleteTestTypeByPkTMediumint(Integer tMediumint);
	public void deleteTestTypeByPkTSmallint(Integer tSmallint);
	public void deleteTestTypeByPkTTinyint(Integer tTinyint);
	
	public void updateTestTypeByClass(@Param(value="testType")TestType testType,@Param(value="param")TestType param);
	public void updateTestTypeByPk(@Param(value="testType")TestType testType,@Param(value="tInt")Integer tInt,@Param(value="tMediumint")Integer tMediumint,@Param(value="tSmallint")Integer tSmallint,@Param(value="tTinyint")Integer tTinyint);
	public void updateTestTypeByPkTInt(@Param(value="testType")TestType testType,@Param(value="tInt")Integer tInt);
	public void updateTestTypeByPkTMediumint(@Param(value="testType")TestType testType,@Param(value="tMediumint")Integer tMediumint);
	public void updateTestTypeByPkTSmallint(@Param(value="testType")TestType testType,@Param(value="tSmallint")Integer tSmallint);
	public void updateTestTypeByPkTTinyint(@Param(value="testType")TestType testType,@Param(value="tTinyint")Integer tTinyint);
	
	public List<TestType> selectTestTypeByClass(TestType testType);
	public TestType selectTestTypeByPk(@Param(value="tInt")Integer tInt,@Param(value="tMediumint")Integer tMediumint,@Param(value="tSmallint")Integer tSmallint,@Param(value="tTinyint")Integer tTinyint);
	public List<TestType> selectTestTypeByPkTInt(Integer tInt);
	public List<TestType> selectTestTypeByPkTMediumint(Integer tMediumint);
	public List<TestType> selectTestTypeByPkTSmallint(Integer tSmallint);
	public List<TestType> selectTestTypeByPkTTinyint(Integer tTinyint);
	
}

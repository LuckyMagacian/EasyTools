package yyj.dao;

import yyj.entity.TestType;
import org.apache.ibatis.annotations.Param;
import java.util.*;/**
*no comment
*@author yyj | auto generator
*@version 1.0.0 2017-02-20 15:54:38
*/

public interface TestTypeDao{
	
	
	public void addTestType(TestType testType);
	
	public void deleteTestTypeByClass(TestType testType);
	public void deleteTestTypeByPkTInt(Integer tInt);
	public void deleteTestTypeByPkTMediumint(Integer tMediumint);
	public void deleteTestTypeByPkTSmallint(Integer tSmallint);
	public void deleteTestTypeByPkTTinyint(Integer tTinyint);
	
	public void updateTestTypeByClass(@Param(value="testType")TestType testType,@Param(value="param")TestType param);
	public void updateTestTypeByPkTInt(@Param(value="testType")TestType testType,Integer tInt);
	public void updateTestTypeByPkTMediumint(@Param(value="testType")TestType testType,Integer tMediumint);
	public void updateTestTypeByPkTSmallint(@Param(value="testType")TestType testType,Integer tSmallint);
	public void updateTestTypeByPkTTinyint(@Param(value="testType")TestType testType,Integer tTinyint);
	
	public List<TestType> selectTestTypeByClass(TestType testType);
	public TestType selectTestTypeByPkTInt(Integer tInt);
	public TestType selectTestTypeByPkTMediumint(Integer tMediumint);
	public TestType selectTestTypeByPkTSmallint(Integer tSmallint);
	public TestType selectTestTypeByPkTTinyint(Integer tTinyint);
	
}

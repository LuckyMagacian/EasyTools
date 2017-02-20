package yyj.entity;

import java.sql.Timestamp;
import java.lang.String;

/**
*no comment
*@author yyj | auto generator
*@version 1.0.0 2017-02-20 15:54:38
*/

public class CityidTransfor{
	/**蓝喜投保城市*/
	private String lxCityid;
	
	/**保险公司投保城市id*/
	private String icCityid;
	
	/**保险公司*/
	private String insuranceCompany;
	
	/**创建时间*/
	private Timestamp createTime;
	
	/**获取蓝喜投保城市*/
	public String getLxCityid(){
		return this.lxCityid;
	}
	
	/**设置蓝喜投保城市*/
	public void setLxCityid(String lxCityid){
		this.lxCityid=lxCityid;
		
	}
	/**获取保险公司投保城市id*/
	public String getIcCityid(){
		return this.icCityid;
	}
	
	/**设置保险公司投保城市id*/
	public void setIcCityid(String icCityid){
		this.icCityid=icCityid;
		
	}
	/**获取保险公司*/
	public String getInsuranceCompany(){
		return this.insuranceCompany;
	}
	
	/**设置保险公司*/
	public void setInsuranceCompany(String insuranceCompany){
		this.insuranceCompany=insuranceCompany;
		
	}
	/**获取创建时间*/
	public Timestamp getCreateTime(){
		return this.createTime;
	}
	
	/**设置创建时间*/
	public void setCreateTime(Timestamp createTime){
		this.createTime=createTime;
		
	}
}

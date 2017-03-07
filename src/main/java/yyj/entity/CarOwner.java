package yyj.entity;

import java.lang.String;
import java.sql.Date;

/**
*no comment
*@author yyj | auto generator
*@version 1.0.0 2017-02-28 16:00:32
*/

public class CarOwner{
	/**车牌号*/
	private String carPlateNo;
	
	/**车主姓名*/
	private String customerName;
	
	/**身份证号*/
	private String idCard;
	
	/**车架号*/
	private String vehicleIdNo;
	
	/**发动机号*/
	private String engineNo;
	
	/**品牌型号*/
	private String brandModal;
	
	/**品牌型号_中文*/
	private String brandModalCh;
	
	/**注册日期*/
	private Date registerDate;
	
	/**是否二手车*/
	private String isSecondhand;
	
	/**二手车过户日期*/
	private Date transforDate;
	
	/**获取车牌号*/
	public String getCarPlateNo(){
		return this.carPlateNo;
	}
	
	/**设置车牌号*/
	public void setCarPlateNo(String carPlateNo){
		this.carPlateNo=carPlateNo;
		
	}
	/**获取车主姓名*/
	public String getCustomerName(){
		return this.customerName;
	}
	
	/**设置车主姓名*/
	public void setCustomerName(String customerName){
		this.customerName=customerName;
		
	}
	/**获取身份证号*/
	public String getIdCard(){
		return this.idCard;
	}
	
	/**设置身份证号*/
	public void setIdCard(String idCard){
		this.idCard=idCard;
		
	}
	/**获取车架号*/
	public String getVehicleIdNo(){
		return this.vehicleIdNo;
	}
	
	/**设置车架号*/
	public void setVehicleIdNo(String vehicleIdNo){
		this.vehicleIdNo=vehicleIdNo;
		
	}
	/**获取发动机号*/
	public String getEngineNo(){
		return this.engineNo;
	}
	
	/**设置发动机号*/
	public void setEngineNo(String engineNo){
		this.engineNo=engineNo;
		
	}
	/**获取品牌型号*/
	public String getBrandModal(){
		return this.brandModal;
	}
	
	/**设置品牌型号*/
	public void setBrandModal(String brandModal){
		this.brandModal=brandModal;
		
	}
	/**获取品牌型号_中文*/
	public String getBrandModalCh(){
		return this.brandModalCh;
	}
	
	/**设置品牌型号_中文*/
	public void setBrandModalCh(String brandModalCh){
		this.brandModalCh=brandModalCh;
		
	}
	/**获取注册日期*/
	public Date getRegisterDate(){
		return this.registerDate;
	}
	
	/**设置注册日期*/
	public void setRegisterDate(Date registerDate){
		this.registerDate=registerDate;
		
	}
	/**获取是否二手车*/
	public String getIsSecondhand(){
		return this.isSecondhand;
	}
	
	/**设置是否二手车*/
	public void setIsSecondhand(String isSecondhand){
		this.isSecondhand=isSecondhand;
		
	}
	/**获取二手车过户日期*/
	public Date getTransforDate(){
		return this.transforDate;
	}
	
	/**设置二手车过户日期*/
	public void setTransforDate(Date transforDate){
		this.transforDate=transforDate;
		
	}
	@Override
	public String toString(){
		return "yyj.entity.CarOwner:["+"carPlateNo="+carPlateNo+","+"customerName="+customerName+","+"idCard="+idCard+","+"vehicleIdNo="+vehicleIdNo+","+"engineNo="+engineNo+","+"brandModal="+brandModal+","+"brandModalCh="+brandModalCh+","+"registerDate="+registerDate+","+"isSecondhand="+isSecondhand+","+"transforDate="+transforDate+"]";
		
	}
}

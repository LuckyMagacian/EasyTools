package yyj.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.lang.String;
import java.sql.Date;

/**
*no comment
*@author yyj | auto generator
*@version 1.0.0 2017-02-21 10:53:03
*/

public class OrderDetail{
	/**订单id*/
	private String orderId;
	
	/**投保城市*/
	private String insureCity;
	
	/**车牌号*/
	private String carPlateNo;
	
	/**新车未上牌*/
	private String hasPlate;
	
	/**车主姓名*/
	private String customerName;
	
	/**身份证号*/
	private String idCard;
	
	/**手机号*/
	private String phoneNo;
	
	/**车主email*/
	private String email;
	
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
	
	/**保单总价*/
	private BigDecimal totalPrice;
	
	/**交强险总价格*/
	private BigDecimal priceTotalCompulsory;
	
	/**商业险总价格*/
	private BigDecimal priceTotalBusiness;
	
	/**交强险价格*/
	private BigDecimal priceCompulsory;
	
	/**车船税价格*/
	private BigDecimal priceTax;
	
	/**交强险生效日期*/
	private Date dateCompulsory;
	
	/**商业险生效日期*/
	private Date dateBusiness;
	
	/**投保人姓名*/
	private String applicantName;
	
	/**投保人身份证号*/
	private String applicantIdNo;
	
	/**投保人手机号*/
	private String applicantPhoneNo;
	
	/**投保人email*/
	private String applicantEmail;
	
	/**被保险人姓名*/
	private String insuredName;
	
	/**被保险人身份证号*/
	private String insuredIdNo;
	
	/**被保险人手机号*/
	private String insuredPhoneNo;
	
	/**被保险人email*/
	private String insuredEmail;
	
	/**收件人姓名*/
	private String addresseeName;
	
	/**收件人手机号*/
	private String addresseeMobile;
	
	/**配送时间*/
	private Date sendDate;
	
	/**省份代码*/
	private String addresseeProvince;
	
	/**城市代码*/
	private String addresseeCity;
	
	/**区县代码*/
	private String addresseeTown;
	
	/**收件地址*/
	private String addresseeDetails;
	
	/**被保人身份证地址*/
	private String insuredAddresseeDetails;
	
	/**保单详细信息, 投保项, 价格*/
	private String insureInfo;
	
	/**创建时间*/
	private Timestamp createTime;
	
	/**更新时间*/
	private Timestamp updateTime;
	
	/**获取订单id*/
	public String getOrderId(){
		return this.orderId;
	}
	
	/**设置订单id*/
	public void setOrderId(String orderId){
		this.orderId=orderId;
		
	}
	/**获取投保城市*/
	public String getInsureCity(){
		return this.insureCity;
	}
	
	/**设置投保城市*/
	public void setInsureCity(String insureCity){
		this.insureCity=insureCity;
		
	}
	/**获取车牌号*/
	public String getCarPlateNo(){
		return this.carPlateNo;
	}
	
	/**设置车牌号*/
	public void setCarPlateNo(String carPlateNo){
		this.carPlateNo=carPlateNo;
		
	}
	/**获取新车未上牌*/
	public String getHasPlate(){
		return this.hasPlate;
	}
	
	/**设置新车未上牌*/
	public void setHasPlate(String hasPlate){
		this.hasPlate=hasPlate;
		
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
	/**获取手机号*/
	public String getPhoneNo(){
		return this.phoneNo;
	}
	
	/**设置手机号*/
	public void setPhoneNo(String phoneNo){
		this.phoneNo=phoneNo;
		
	}
	/**获取车主email*/
	public String getEmail(){
		return this.email;
	}
	
	/**设置车主email*/
	public void setEmail(String email){
		this.email=email;
		
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
	/**获取保单总价*/
	public BigDecimal getTotalPrice(){
		return this.totalPrice;
	}
	
	/**设置保单总价*/
	public void setTotalPrice(BigDecimal totalPrice){
		this.totalPrice=totalPrice;
		
	}
	/**获取交强险总价格*/
	public BigDecimal getPriceTotalCompulsory(){
		return this.priceTotalCompulsory;
	}
	
	/**设置交强险总价格*/
	public void setPriceTotalCompulsory(BigDecimal priceTotalCompulsory){
		this.priceTotalCompulsory=priceTotalCompulsory;
		
	}
	/**获取商业险总价格*/
	public BigDecimal getPriceTotalBusiness(){
		return this.priceTotalBusiness;
	}
	
	/**设置商业险总价格*/
	public void setPriceTotalBusiness(BigDecimal priceTotalBusiness){
		this.priceTotalBusiness=priceTotalBusiness;
		
	}
	/**获取交强险价格*/
	public BigDecimal getPriceCompulsory(){
		return this.priceCompulsory;
	}
	
	/**设置交强险价格*/
	public void setPriceCompulsory(BigDecimal priceCompulsory){
		this.priceCompulsory=priceCompulsory;
		
	}
	/**获取车船税价格*/
	public BigDecimal getPriceTax(){
		return this.priceTax;
	}
	
	/**设置车船税价格*/
	public void setPriceTax(BigDecimal priceTax){
		this.priceTax=priceTax;
		
	}
	/**获取交强险生效日期*/
	public Date getDateCompulsory(){
		return this.dateCompulsory;
	}
	
	/**设置交强险生效日期*/
	public void setDateCompulsory(Date dateCompulsory){
		this.dateCompulsory=dateCompulsory;
		
	}
	/**获取商业险生效日期*/
	public Date getDateBusiness(){
		return this.dateBusiness;
	}
	
	/**设置商业险生效日期*/
	public void setDateBusiness(Date dateBusiness){
		this.dateBusiness=dateBusiness;
		
	}
	/**获取投保人姓名*/
	public String getApplicantName(){
		return this.applicantName;
	}
	
	/**设置投保人姓名*/
	public void setApplicantName(String applicantName){
		this.applicantName=applicantName;
		
	}
	/**获取投保人身份证号*/
	public String getApplicantIdNo(){
		return this.applicantIdNo;
	}
	
	/**设置投保人身份证号*/
	public void setApplicantIdNo(String applicantIdNo){
		this.applicantIdNo=applicantIdNo;
		
	}
	/**获取投保人手机号*/
	public String getApplicantPhoneNo(){
		return this.applicantPhoneNo;
	}
	
	/**设置投保人手机号*/
	public void setApplicantPhoneNo(String applicantPhoneNo){
		this.applicantPhoneNo=applicantPhoneNo;
		
	}
	/**获取投保人email*/
	public String getApplicantEmail(){
		return this.applicantEmail;
	}
	
	/**设置投保人email*/
	public void setApplicantEmail(String applicantEmail){
		this.applicantEmail=applicantEmail;
		
	}
	/**获取被保险人姓名*/
	public String getInsuredName(){
		return this.insuredName;
	}
	
	/**设置被保险人姓名*/
	public void setInsuredName(String insuredName){
		this.insuredName=insuredName;
		
	}
	/**获取被保险人身份证号*/
	public String getInsuredIdNo(){
		return this.insuredIdNo;
	}
	
	/**设置被保险人身份证号*/
	public void setInsuredIdNo(String insuredIdNo){
		this.insuredIdNo=insuredIdNo;
		
	}
	/**获取被保险人手机号*/
	public String getInsuredPhoneNo(){
		return this.insuredPhoneNo;
	}
	
	/**设置被保险人手机号*/
	public void setInsuredPhoneNo(String insuredPhoneNo){
		this.insuredPhoneNo=insuredPhoneNo;
		
	}
	/**获取被保险人email*/
	public String getInsuredEmail(){
		return this.insuredEmail;
	}
	
	/**设置被保险人email*/
	public void setInsuredEmail(String insuredEmail){
		this.insuredEmail=insuredEmail;
		
	}
	/**获取收件人姓名*/
	public String getAddresseeName(){
		return this.addresseeName;
	}
	
	/**设置收件人姓名*/
	public void setAddresseeName(String addresseeName){
		this.addresseeName=addresseeName;
		
	}
	/**获取收件人手机号*/
	public String getAddresseeMobile(){
		return this.addresseeMobile;
	}
	
	/**设置收件人手机号*/
	public void setAddresseeMobile(String addresseeMobile){
		this.addresseeMobile=addresseeMobile;
		
	}
	/**获取配送时间*/
	public Date getSendDate(){
		return this.sendDate;
	}
	
	/**设置配送时间*/
	public void setSendDate(Date sendDate){
		this.sendDate=sendDate;
		
	}
	/**获取省份代码*/
	public String getAddresseeProvince(){
		return this.addresseeProvince;
	}
	
	/**设置省份代码*/
	public void setAddresseeProvince(String addresseeProvince){
		this.addresseeProvince=addresseeProvince;
		
	}
	/**获取城市代码*/
	public String getAddresseeCity(){
		return this.addresseeCity;
	}
	
	/**设置城市代码*/
	public void setAddresseeCity(String addresseeCity){
		this.addresseeCity=addresseeCity;
		
	}
	/**获取区县代码*/
	public String getAddresseeTown(){
		return this.addresseeTown;
	}
	
	/**设置区县代码*/
	public void setAddresseeTown(String addresseeTown){
		this.addresseeTown=addresseeTown;
		
	}
	/**获取收件地址*/
	public String getAddresseeDetails(){
		return this.addresseeDetails;
	}
	
	/**设置收件地址*/
	public void setAddresseeDetails(String addresseeDetails){
		this.addresseeDetails=addresseeDetails;
		
	}
	/**获取被保人身份证地址*/
	public String getInsuredAddresseeDetails(){
		return this.insuredAddresseeDetails;
	}
	
	/**设置被保人身份证地址*/
	public void setInsuredAddresseeDetails(String insuredAddresseeDetails){
		this.insuredAddresseeDetails=insuredAddresseeDetails;
		
	}
	/**获取保单详细信息, 投保项, 价格*/
	public String getInsureInfo(){
		return this.insureInfo;
	}
	
	/**设置保单详细信息, 投保项, 价格*/
	public void setInsureInfo(String insureInfo){
		this.insureInfo=insureInfo;
		
	}
	/**获取创建时间*/
	public Timestamp getCreateTime(){
		return this.createTime;
	}
	
	/**设置创建时间*/
	public void setCreateTime(Timestamp createTime){
		this.createTime=createTime;
		
	}
	/**获取更新时间*/
	public Timestamp getUpdateTime(){
		return this.updateTime;
	}
	
	/**设置更新时间*/
	public void setUpdateTime(Timestamp updateTime){
		this.updateTime=updateTime;
		
	}
	@Override
	public String toString(){
		return "yyj.entity.OrderDetail:["+"orderId="+orderId+","+"insureCity="+insureCity+","+"carPlateNo="+carPlateNo+","+"hasPlate="+hasPlate+","+"customerName="+customerName+","+"idCard="+idCard+","+"phoneNo="+phoneNo+","+"email="+email+","+"vehicleIdNo="+vehicleIdNo+","+"engineNo="+engineNo+","+"brandModal="+brandModal+","+"brandModalCh="+brandModalCh+","+"registerDate="+registerDate+","+"isSecondhand="+isSecondhand+","+"transforDate="+transforDate+","+"totalPrice="+totalPrice+","+"priceTotalCompulsory="+priceTotalCompulsory+","+"priceTotalBusiness="+priceTotalBusiness+","+"priceCompulsory="+priceCompulsory+","+"priceTax="+priceTax+","+"dateCompulsory="+dateCompulsory+","+"dateBusiness="+dateBusiness+","+"applicantName="+applicantName+","+"applicantIdNo="+applicantIdNo+","+"applicantPhoneNo="+applicantPhoneNo+","+"applicantEmail="+applicantEmail+","+"insuredName="+insuredName+","+"insuredIdNo="+insuredIdNo+","+"insuredPhoneNo="+insuredPhoneNo+","+"insuredEmail="+insuredEmail+","+"addresseeName="+addresseeName+","+"addresseeMobile="+addresseeMobile+","+"sendDate="+sendDate+","+"addresseeProvince="+addresseeProvince+","+"addresseeCity="+addresseeCity+","+"addresseeTown="+addresseeTown+","+"addresseeDetails="+addresseeDetails+","+"insuredAddresseeDetails="+insuredAddresseeDetails+","+"insureInfo="+insureInfo+","+"createTime="+createTime+","+"updateTime="+updateTime+"]";
		
	}
}

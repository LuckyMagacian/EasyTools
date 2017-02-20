package yyj.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.lang.String;

/**
*no comment
*@author yyj | auto generator
*@version 1.0.0 2017-02-20 15:54:38
*/

public class OrderList{
	/**订单id*/
	private String orderId;
	
	/**第三方订单id*/
	private String thirdpartOrderId;
	
	/**保险公司*/
	private String insuranceCompany;
	
	/**请求机构*/
	private String reqBranch;
	
	/**交强投保单号*/
	private String compulsoryPropNo;
	
	/**商业投保单号*/
	private String businessPropNo;
	
	/**交强保单号*/
	private String compulsoryPolicyNo;
	
	/**商业保单号*/
	private String businessPolicyNo;
	
	/**投保城市*/
	private String insureCity;
	
	/**车牌号*/
	private String carPlateNo;
	
	/**新车未上牌*/
	private String hasPlate;
	
	/**保单总价*/
	private BigDecimal totalPrice;
	
	/**订单状态*/
	private String orderStatus;
	
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
	/**获取第三方订单id*/
	public String getThirdpartOrderId(){
		return this.thirdpartOrderId;
	}
	
	/**设置第三方订单id*/
	public void setThirdpartOrderId(String thirdpartOrderId){
		this.thirdpartOrderId=thirdpartOrderId;
		
	}
	/**获取保险公司*/
	public String getInsuranceCompany(){
		return this.insuranceCompany;
	}
	
	/**设置保险公司*/
	public void setInsuranceCompany(String insuranceCompany){
		this.insuranceCompany=insuranceCompany;
		
	}
	/**获取请求机构*/
	public String getReqBranch(){
		return this.reqBranch;
	}
	
	/**设置请求机构*/
	public void setReqBranch(String reqBranch){
		this.reqBranch=reqBranch;
		
	}
	/**获取交强投保单号*/
	public String getCompulsoryPropNo(){
		return this.compulsoryPropNo;
	}
	
	/**设置交强投保单号*/
	public void setCompulsoryPropNo(String compulsoryPropNo){
		this.compulsoryPropNo=compulsoryPropNo;
		
	}
	/**获取商业投保单号*/
	public String getBusinessPropNo(){
		return this.businessPropNo;
	}
	
	/**设置商业投保单号*/
	public void setBusinessPropNo(String businessPropNo){
		this.businessPropNo=businessPropNo;
		
	}
	/**获取交强保单号*/
	public String getCompulsoryPolicyNo(){
		return this.compulsoryPolicyNo;
	}
	
	/**设置交强保单号*/
	public void setCompulsoryPolicyNo(String compulsoryPolicyNo){
		this.compulsoryPolicyNo=compulsoryPolicyNo;
		
	}
	/**获取商业保单号*/
	public String getBusinessPolicyNo(){
		return this.businessPolicyNo;
	}
	
	/**设置商业保单号*/
	public void setBusinessPolicyNo(String businessPolicyNo){
		this.businessPolicyNo=businessPolicyNo;
		
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
	/**获取保单总价*/
	public BigDecimal getTotalPrice(){
		return this.totalPrice;
	}
	
	/**设置保单总价*/
	public void setTotalPrice(BigDecimal totalPrice){
		this.totalPrice=totalPrice;
		
	}
	/**获取订单状态*/
	public String getOrderStatus(){
		return this.orderStatus;
	}
	
	/**设置订单状态*/
	public void setOrderStatus(String orderStatus){
		this.orderStatus=orderStatus;
		
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
}

package yyj.entity;

import java.sql.Timestamp;
import java.lang.String;

/**
*no comment
*@author yyj | auto generator
*@version 1.0.0 2017-02-28 16:00:32
*/

public class UserInfo{
	/**id*/
	private String userId;
	
	/**角色id*/
	private String roleId;
	
	/**姓名*/
	private String name;
	
	/**机构*/
	private String organization;
	
	/**密码*/
	private String passwd;
	
	/**手机*/
	private String phone;
	
	/**创建时间*/
	private Timestamp createTime;
	
	/**更新时间*/
	private Timestamp updateTime;
	
	/**获取id*/
	public String getUserId(){
		return this.userId;
	}
	
	/**设置id*/
	public void setUserId(String userId){
		this.userId=userId;
		
	}
	/**获取角色id*/
	public String getRoleId(){
		return this.roleId;
	}
	
	/**设置角色id*/
	public void setRoleId(String roleId){
		this.roleId=roleId;
		
	}
	/**获取姓名*/
	public String getName(){
		return this.name;
	}
	
	/**设置姓名*/
	public void setName(String name){
		this.name=name;
		
	}
	/**获取机构*/
	public String getOrganization(){
		return this.organization;
	}
	
	/**设置机构*/
	public void setOrganization(String organization){
		this.organization=organization;
		
	}
	/**获取密码*/
	public String getPasswd(){
		return this.passwd;
	}
	
	/**设置密码*/
	public void setPasswd(String passwd){
		this.passwd=passwd;
		
	}
	/**获取手机*/
	public String getPhone(){
		return this.phone;
	}
	
	/**设置手机*/
	public void setPhone(String phone){
		this.phone=phone;
		
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
		return "yyj.entity.UserInfo:["+"userId="+userId+","+"roleId="+roleId+","+"name="+name+","+"organization="+organization+","+"passwd="+passwd+","+"phone="+phone+","+"createTime="+createTime+","+"updateTime="+updateTime+"]";
		
	}
}

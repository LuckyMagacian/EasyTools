package yyj.dao;

import yyj.entity.UserInfo;
import org.apache.ibatis.annotations.Param;
import java.util.*;
/**
*no comment
*@author yyj | auto generator
*@version 1.0.0 2017-02-28 16:00:32
*/

public interface UserInfoDao{
	
	
	/**插入UserInfo到数据库
	 * @paramuserInfo 待插入的对象
	 */
	public void addUserInfo(UserInfo userInfo);
	
	public void deleteUserInfoByClass(UserInfo userInfo);
	public void deleteUserInfoByUniqueIndexOnUserId(@Param(value="userId")String userId);
	
	public void updateUserInfoByClass(@Param(value="userInfo")UserInfo userInfo,@Param(value="param")UserInfo param);
	
	public List<UserInfo> selectUserInfoByClass(UserInfo userInfo);
	public UserInfo selectUserInfoByUniqueIndexOnUserId(@Param(value="userId")String userId);
	
}

package yyj.dao;

import yyj.entity.CityidTransfor;
import org.apache.ibatis.annotations.Param;
import java.util.*;
/**
*no comment
*@author yyj | auto generator
*@version 1.0.0 2017-02-28 16:00:32
*/

public interface CityidTransforDao{
	
	
	/**插入CityidTransfor到数据库
	 * @paramcityidTransfor 待插入的对象
	 */
	public void addCityidTransfor(CityidTransfor cityidTransfor);
	
	public void deleteCityidTransforByClass(CityidTransfor cityidTransfor);
	public void deleteCityidTransforByUniqueIndexOnLxCityidAndInsuranceCompany(@Param(value="lxCityid")String lxCityid,@Param(value="insuranceCompany")String insuranceCompany);
	
	public void updateCityidTransforByClass(@Param(value="cityidTransfor")CityidTransfor cityidTransfor,@Param(value="param")CityidTransfor param);
	
	public List<CityidTransfor> selectCityidTransforByClass(CityidTransfor cityidTransfor);
	public CityidTransfor selectCityidTransforByUniqueIndexOnLxCityidAndInsuranceCompany(@Param(value="lxCityid")String lxCityid,@Param(value="insuranceCompany")String insuranceCompany);
	
}

package yyj.dao;

import yyj.entity.CityidTransfor;
import org.apache.ibatis.annotations.Param;
import java.util.*;
/**
*no comment
*@author yyj | auto generator
*@version 1.0.0 2017-02-21 10:53:03
*/

public interface CityidTransforDao{
	
	
	public void addCityidTransfor(CityidTransfor cityidTransfor);
	
	public void deleteCityidTransforByClass(CityidTransfor cityidTransfor);
	public void deleteCityidTransforByUniqueIndexOnLxCityidAndInsuranceCompany(@Param(value="lxCityid")String lxCityid,@Param(value="insuranceCompany")String insuranceCompany);
	
	public void updateCityidTransforByClass(@Param(value="cityidTransfor")CityidTransfor cityidTransfor,@Param(value="param")CityidTransfor param);
	
	public List<CityidTransfor> selectCityidTransforByClass(CityidTransfor cityidTransfor);
	public CityidTransfor selectCityidTransforByUniqueIndexOnLxCityidAndInsuranceCompany(@Param(value="lxCityid")String lxCityid,@Param(value="insuranceCompany")String insuranceCompany);
	
}

package yyj.dao;

import yyj.entity.CarOwner;
import org.apache.ibatis.annotations.Param;
import java.util.*;
/**
*no comment
*@author yyj | auto generator
*@version 1.0.0 2017-02-21 10:43:36
*/

public interface CarOwnerDao{
	
	
	public void addCarOwner(CarOwner carOwner);
	
	public void deleteCarOwnerByClass(CarOwner carOwner);
	public void deleteCarOwnerByPk(@Param(value="carPlateNo")String carPlateNo,@Param(value="customerName")String customerName);
	public void deleteCarOwnerByPkCarPlateNo(String carPlateNo);
	public void deleteCarOwnerByPkCustomerName(String customerName);
	public void deleteCarOwnerByUniqueIndexOnCarPlateNoAndCustomerName(@Param(value="carPlateNo")String carPlateNo,@Param(value="customerName")String customerName);
	public void deleteCarOwnerByUniqueIndexOnCarPlateNo(@Param(value="carPlateNo")String carPlateNo);
	
	public void updateCarOwnerByClass(@Param(value="carOwner")CarOwner carOwner,@Param(value="param")CarOwner param);
	public void updateCarOwnerByPk(@Param(value="carOwner")CarOwner carOwner,@Param(value="carPlateNo")String carPlateNo,@Param(value="customerName")String customerName);
	public void updateCarOwnerByPkCarPlateNo(@Param(value="carOwner")CarOwner carOwner,@Param(value="carPlateNo")String carPlateNo);
	public void updateCarOwnerByPkCustomerName(@Param(value="carOwner")CarOwner carOwner,@Param(value="customerName")String customerName);
	public void updateCarOwnerByUniqueIndexOnCarPlateNoAndCustomerName(@Param(value="carOwner")CarOwner carOwner,@Param(value="carPlateNo")String carPlateNo,@Param(value="customerName")String customerName);
	public void updateCarOwnerByUniqueIndexOnCarPlateNo(@Param(value="carOwner")CarOwner carOwner,@Param(value="carPlateNo")String carPlateNo);
	
	public List<CarOwner> selectCarOwnerByClass(CarOwner carOwner);
	public CarOwner selectCarOwnerByPk(@Param(value="carPlateNo")String carPlateNo,@Param(value="customerName")String customerName);
	public List<CarOwner> selectCarOwnerByPkCarPlateNo(String carPlateNo);
	public List<CarOwner> selectCarOwnerByPkCustomerName(String customerName);
	public CarOwner selectCarOwnerByUniqueIndexOnCarPlateNoAndCustomerName(@Param(value="carPlateNo")String carPlateNo,@Param(value="customerName")String customerName);
	public CarOwner selectCarOwnerByUniqueIndexOnCarPlateNo(@Param(value="carPlateNo")String carPlateNo);
	
}

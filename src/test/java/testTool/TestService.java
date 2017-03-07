package testTool;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


import yyj.dao.CarOwnerDao;
import yyj.entity.CarOwner;
import yyj.tools.BeanUtil;
import static yyj.tools.SqlUtilForDB.*;

public class TestService {
	private ApplicationContext ac;
//	@Before
	public void Init(){
		ac=new ClassPathXmlApplicationContext("applicationContext.xml");
	}
	
	@Test
	public void test1() throws Exception{
//		CarOwnerDao dao=(CarOwnerDao) ac.getBean("carOwnerDao");
		CarOwner carOwner=new CarOwner();
		List<Method> setters=BeanUtil.getSetters(carOwner); 
		for(Method each:setters){
			Parameter[] parameters=each.getParameters();
			if(parameters.length!=1)
				continue;
			Parameter param=parameters[0];
			if(param.getType().equals(String.class))
				each.invoke(carOwner, "6");
			if(param.getType().equals(Date.class))
				each.invoke(carOwner, new Date(System.currentTimeMillis()));
		}
		add(getConnection(), carOwner, true, "t_","t_car_owner");
//		dao.addCarOwner(carOwner);
//		dao.deleteCarOwnerByClass(carOwner);
//		dao.deleteCarOwnerByPk(carOwner.getCarPlateNo(), carOwner.getCustomerName());
//		dao.deleteCarOwnerByPkCarPlateNo(carOwner.getCarPlateNo());
//		dao.deleteCarOwnerByPkCustomerName(carOwner.getCustomerName());
//		dao.deleteCarOwnerByUniqueIndexOnCarPlateNo(carOwner.getCarPlateNo());
//		dao.deleteCarOwnerByUniqueIndexOnCarPlateNoAndCustomerName(carOwner.getCarPlateNo(), carOwner.getCustomerName());
//		CarOwner param=new CarOwner();
//		BeanUtil.copy(carOwner, param);
//		param.setCustomerName("隔壁老王");
//		dao.updateCarOwnerByClass(carOwner,param);
//		dao.updateCarOwnerByPk(param, carOwner.getCarPlateNo(), carOwner.getCustomerName());
//		dao.updateCarOwnerByPkCarPlateNo(carOwner, carOwner.getCarPlateNo());
//		dao.updateCarOwnerByPkCustomerName(param, carOwner.getCustomerName());
//		dao.updateCarOwnerByUniqueIndexOnCarPlateNo(carOwner, param.getCarPlateNo());
//		dao.updateCarOwnerByUniqueIndexOnCarPlateNoAndCustomerName(param, carOwner.getCarPlateNo(), carOwner.getCustomerName());
//		System.out.println(dao.selectCarOwnerByClass(carOwner));
//		System.out.println(dao.selectCarOwnerByPk(carOwner.getCarPlateNo(),carOwner.getCustomerName()));
//		System.out.println(dao.selectCarOwnerByPkCarPlateNo(carOwner.getCarPlateNo()));
//		System.out.println(dao.selectCarOwnerByPkCustomerName(carOwner.getCustomerName()));
//		System.out.println(dao.selectCarOwnerByUniqueIndexOnCarPlateNo(carOwner.getCarPlateNo()));
//		System.out.println(dao.selectCarOwnerByUniqueIndexOnCarPlateNoAndCustomerName(carOwner.getCarPlateNo(),carOwner.getCustomerName()));
	}
	
}

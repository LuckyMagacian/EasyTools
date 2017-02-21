package yyj.dao;

import yyj.entity.OrderDetail;
import org.apache.ibatis.annotations.Param;
import java.util.*;
/**
*no comment
*@author yyj | auto generator
*@version 1.0.0 2017-02-21 10:53:03
*/

public interface OrderDetailDao{
	
	
	public void addOrderDetail(OrderDetail orderDetail);
	
	public void deleteOrderDetailByClass(OrderDetail orderDetail);
	public void deleteOrderDetailByUniqueIndexOnOrderId(@Param(value="orderId")String orderId);
	
	public void updateOrderDetailByClass(@Param(value="orderDetail")OrderDetail orderDetail,@Param(value="param")OrderDetail param);
	
	public List<OrderDetail> selectOrderDetailByClass(OrderDetail orderDetail);
	public OrderDetail selectOrderDetailByUniqueIndexOnOrderId(@Param(value="orderId")String orderId);
	
}

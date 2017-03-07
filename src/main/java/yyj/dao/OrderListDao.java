package yyj.dao;

import yyj.entity.OrderList;
import org.apache.ibatis.annotations.Param;
import java.util.*;
/**
*no comment
*@author yyj | auto generator
*@version 1.0.0 2017-02-28 16:00:32
*/

public interface OrderListDao{
	
	
	/**插入OrderList到数据库
	 * @paramorderList 待插入的对象
	 */
	public void addOrderList(OrderList orderList);
	
	public void deleteOrderListByClass(OrderList orderList);
	public void deleteOrderListByUniqueIndexOnOrderId(@Param(value="orderId")String orderId);
	
	public void updateOrderListByClass(@Param(value="orderList")OrderList orderList,@Param(value="param")OrderList param);
	
	public List<OrderList> selectOrderListByClass(OrderList orderList);
	public OrderList selectOrderListByUniqueIndexOnOrderId(@Param(value="orderId")String orderId);
	
}

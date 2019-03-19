package com.wukong.order.service;

import java.util.List;

import com.wukong.common.pojo.EasyUIDataGridResult;
import com.wukong.common.pojo.WukongResult;
import com.wukong.order.pojo.OrderInfo;
import com.wukong.pojo.TbOrder;
import com.wukong.pojo.TbOrderItem;
import com.wukong.pojo.TbReceiver;

public interface OrderService {

	WukongResult createOrder(OrderInfo orderInfo,long userId);
	WukongResult addAddress(TbReceiver receiver);
	List<TbReceiver> getTbReceiverList(long userId);
	TbReceiver getTbReceiverById(long id);
	WukongResult deleteReceiver(long id,long userId);
	List<TbOrder> getTbOrderListByUserId(long userId);
	List<TbOrderItem> geTbOrderItemListByOrderId(long orderId);
	EasyUIDataGridResult getOrderList(int page, int rows);
	WukongResult updateOrderStatus4ByOrderId(long orderId);
	WukongResult updateOrderStatus5ByOrderId(long orderId);
	WukongResult updateOrderStatus6ByOrderId(long orderId);
}

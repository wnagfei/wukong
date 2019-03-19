package com.wukong.controller;
/**
 * 订单管理
 * @author yuanbao
 *
 */


import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wukong.common.pojo.EasyUIDataGridResult;
import com.wukong.common.pojo.WukongResult;
import com.wukong.order.service.OrderService;
import com.wukong.pojo.TbOrderItem;
@Controller
public class OrderController {

	@Resource
	private OrderService orderService;

	@RequestMapping("/item/order-list")
	@ResponseBody
	public EasyUIDataGridResult showOrderList(Integer page,Integer rows){
		EasyUIDataGridResult result = orderService.getOrderList( page, rows);
		return result;
	}
	
	@RequestMapping("/item/orderItemList/{orderId}")
	@ResponseBody
	public WukongResult showOrderItem(@PathVariable Long orderId){
		List<TbOrderItem> geTbOrderItemListByOrderId = orderService.geTbOrderItemListByOrderId(orderId);
		return WukongResult.ok(geTbOrderItemListByOrderId);
	}
	
	@RequestMapping("/item/orderUpdate")
	@ResponseBody
	public WukongResult orderUpdate(Long orderId){
		orderService.updateOrderStatus4ByOrderId(orderId);
		return WukongResult.ok();
	}
	
	@RequestMapping("/item/closeOrder")
	@ResponseBody
	public WukongResult closeOrder(Long orderId){
		orderService.updateOrderStatus6ByOrderId(orderId);
		return WukongResult.ok();
	}
	
}

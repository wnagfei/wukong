package com.wukong.order.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wukong.common.pojo.EasyUIDataGridResult;
import com.wukong.common.pojo.WukongResult;
import com.wukong.jedis.JedisClient;
import com.wukong.mapper.TbOrderItemMapper;
import com.wukong.mapper.TbOrderMapper;
import com.wukong.mapper.TbOrderShippingMapper;
import com.wukong.mapper.TbReceiverMapper;
import com.wukong.mapper.TbUserMapper;
import com.wukong.order.pojo.OrderInfo;
import com.wukong.order.service.OrderService;
import com.wukong.pojo.TbOrder;
import com.wukong.pojo.TbOrderExample;
import com.wukong.pojo.TbOrderItem;
import com.wukong.pojo.TbOrderItemExample;
import com.wukong.pojo.TbOrderShipping;
import com.wukong.pojo.TbReceiver;
import com.wukong.pojo.TbReceiverExample;
import com.wukong.pojo.TbReceiverExample.Criteria;
/**
 * 订单处理
 *
 */
@Service
public class OrderServiceImpl implements OrderService {

	@Resource 
	private TbOrderMapper orderMapper;
	@Resource
	private TbOrderItemMapper orderItemMapper;
	@Resource
	private TbOrderShippingMapper orderShippingMapper;
	@Resource
	private TbReceiverMapper receiverMapper;
	@Resource
	private TbUserMapper userMapper;
	@Resource
	private JedisClient jedisClient;
	@Value("${ORDER_ID_GEN_KEY}")
	private String ORDER_ID_GEN_KEY;
	@Value("${ORDER_ID_BEGIN_VALUE}")
	private String ORDER_ID_BEGIN_VALUE;
	@Value("${ORDER_ITEM_ID_GEN_KEY}")
	private String ORDER_ITEM_ID_GEN_KEY;
	
	@Override
	public WukongResult createOrder(OrderInfo orderInfo,long userId) {
		// TODO Auto-generated method stub
		//生成订单号，用redis 的 incr
		if(!jedisClient.exists(ORDER_ID_GEN_KEY)){
			//设置初始值
			jedisClient.set(ORDER_ID_GEN_KEY, ORDER_ID_BEGIN_VALUE);
		}
		String  orderId = jedisClient.incr(ORDER_ID_GEN_KEY).toString();
		//向订单插入数据
		orderInfo.setOrderId(orderId);
		orderInfo.setPostFee("0");
		//1 未付款，2已付款，3未发货，4已发货，5交易成功，6关闭
		orderInfo.setStatus(1);
		orderInfo.setCreateTime(new Date());
		orderInfo.setUpdateTime(new Date());
		orderInfo.setUserId(userId);
		orderInfo.setBuyerNick(userMapper.selectByPrimaryKey(userId).getUsername());
		long totalprice = 0;
		
		
		
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			//获得明细主键
			String oid = jedisClient.incr(ORDER_ITEM_ID_GEN_KEY).toString();
			tbOrderItem.setId(oid);
			tbOrderItem.setOrderId(orderId);
			totalprice = totalprice + tbOrderItem.getPrice() * tbOrderItem.getNum();
			//插入明细数据
			orderItemMapper.insert(tbOrderItem);
		}
		orderInfo.setPayment(String.valueOf(totalprice));
		
		
		//向订单明细表插入数据
		orderMapper.insert(orderInfo);
		
		//想订单物流表插入数据
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(new Date());
		orderShipping.setUpdated(new Date());
		orderShippingMapper.insert(orderShipping);
		//返回订单号
		return WukongResult.ok(orderId);
	}

	@Override
	public WukongResult addAddress(TbReceiver receiver) {
		// TODO Auto-generated method stub
		receiver.setCreated(new Date());
		receiver.setUpdated(new Date());
		receiverMapper.insert(receiver);
		return WukongResult.ok();
	}

	@Override
	public List<TbReceiver> getTbReceiverList(long userId) {
		// TODO Auto-generated method stub
		TbReceiverExample example = new TbReceiverExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUseridEqualTo(userId);
		List<TbReceiver> selectByExample = receiverMapper.selectByExample(example);
		return selectByExample;
	}

	@Override
	public TbReceiver getTbReceiverById(long id) {
		// TODO Auto-generated method stub
		TbReceiver selectByPrimaryKey = receiverMapper.selectByPrimaryKey(id);
		return selectByPrimaryKey;
	}

	@Override
	public WukongResult deleteReceiver(long id, long userId) {
		// TODO Auto-generated method stub
		TbReceiverExample example = new TbReceiverExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andUseridEqualTo(userId);
		createCriteria.andIdEqualTo(id);
		receiverMapper.deleteByExample(example);
		return WukongResult.ok();
	}

	@Override
	public List<TbOrder> getTbOrderListByUserId(long userId) {
		// TODO Auto-generated method stub
		TbOrderExample example = new TbOrderExample();
		com.wukong.pojo.TbOrderExample.Criteria createCriteria = example.createCriteria();
		createCriteria.andUserIdEqualTo(userId);
		List<TbOrder> selectByExample = orderMapper.selectByExample(example);
		return selectByExample;
	}

	@Override
	public List<TbOrderItem> geTbOrderItemListByOrderId(long orderId) {
		// TODO Auto-generated method stub
		TbOrderItemExample example = new TbOrderItemExample();
		com.wukong.pojo.TbOrderItemExample.Criteria createCriteria = example.createCriteria();
		createCriteria.andOrderIdEqualTo(String.valueOf(orderId));
		List<TbOrderItem> selectByExample = orderItemMapper.selectByExample(example);
		for (TbOrderItem tbOrderItem : selectByExample) {
			tbOrderItem.setTitle(tbOrderItem.getTitle().substring(0, 6) + "...");
		}
		return selectByExample;
	}

	@Override
	public EasyUIDataGridResult getOrderList(int page, int rows) {
		// TODO Auto-generated method stub
		//分页信息
		PageHelper.startPage(page,rows);
		TbOrderExample example = new TbOrderExample();
		List<TbOrder> selectByExample = orderMapper.selectByExample(example);
		PageInfo<TbOrder> pageInfo = new PageInfo<>(selectByExample);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(selectByExample);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public WukongResult updateOrderStatus4ByOrderId(long orderId) {
		// TODO Auto-generated method stub
		TbOrder selectByPrimaryKey = orderMapper.selectByPrimaryKey(String.valueOf(orderId));
		selectByPrimaryKey.setStatus(4);
		selectByPrimaryKey.setUpdateTime(new Date());
		orderMapper.updateByPrimaryKey(selectByPrimaryKey);
		return WukongResult.ok();
	}

	@Override
	public WukongResult updateOrderStatus5ByOrderId(long orderId) {
		// TODO Auto-generated method stub
		TbOrder selectByPrimaryKey = orderMapper.selectByPrimaryKey(String.valueOf(orderId));
		selectByPrimaryKey.setStatus(5);
		selectByPrimaryKey.setUpdateTime(new Date());
		orderMapper.updateByPrimaryKey(selectByPrimaryKey);
		return WukongResult.ok();
	}

	@Override
	public WukongResult updateOrderStatus6ByOrderId(long orderId) {
		// TODO Auto-generated method stub
		TbOrder selectByPrimaryKey = orderMapper.selectByPrimaryKey(String.valueOf(orderId));
		selectByPrimaryKey.setStatus(6);
		selectByPrimaryKey.setUpdateTime(new Date());
		orderMapper.updateByPrimaryKey(selectByPrimaryKey);
		return WukongResult.ok();
	}
	
	

}

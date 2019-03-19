package com.wukong.order.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wukong.cart.service.CartService;
import com.wukong.common.pojo.WukongResult;
import com.wukong.common.utils.CookieUtils;
import com.wukong.common.utils.JsonUtils;
import com.wukong.order.pojo.OrderInfo;
import com.wukong.order.service.OrderService;
import com.wukong.pojo.TbCart;
import com.wukong.pojo.TbItem;
import com.wukong.pojo.TbOrder;
import com.wukong.pojo.TbOrderItem;
import com.wukong.pojo.TbReceiver;
import com.wukong.pojo.TbUser;
import com.wukong.service.ItemService;
import com.wukong.sso.service.UserService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 订单页面处理
 * 
 * @author yuanbao
 *
 */
@Controller
public class OrderCartController {

	@Value("${CART_KEY}")
	private String CART_KEY;
	@Value("${CART_EXPIER}")
	private Integer CART_EXPIER;
	@Value("${WU_KONG}")
	private String WU_KONG;
	@Resource
	private OrderService orderService;
	@Resource
	private CartService cartService;
	@Resource
	private UserService userService;
	@Resource
	private ItemService itemService;

	@RequestMapping("/order/order-cart")
	public String showOrderCart(HttpServletRequest request, HttpServletResponse response,String items) {

		/************************** 分出itemID ******************************/
		// 把id于num分割
		String[] itemList = items.split(",");
	
		//取中间值，你前面是物品id，n后面是数量
		int  n = itemList.length / 2;
		
		
		
 		List<TbItem> tbItems = new ArrayList<>();
		// 取出所选id,并根据商品id查询商品
		for (int i = 0; i < n; i++) {
			// 取出商品ID
			long itemId = Long.valueOf(itemList[i]);
			// 取出商品数量
			int num = Integer.valueOf(itemList[i + n]);
			TbItem item = new TbItem();
			item = itemService.geItemById(itemId);
			item.setNum(num);

			// 取图片
			String image = item.getImage();
			if (StringUtils.isNotBlank(image)) {
				String[] images = image.split(",");
				item.setImage(images[0]);
			}
			tbItems.add(item);
		}

		// 取出商品数量
		/********************************************************/
		// 用户必须是登陆状态
		// 取用户id
		// TbUser user = (TbUser) request.getAttribute("user");
		// 根据用户去收货地址列表
		// 收货地址列表展示到页面

		// 获取token，获取id

		
		// 从数据库中取商品列表
		//List<TbCart> cartItemList = cartService.getCartByUserId(userId);

		/*
		 * // 从cookie中取出购物车商品 合并到数据库 List<TbItem> itemsList =
		 * getCartItemList(request);
		 * 
		 * if (itemsList != null) { for (TbItem item : itemsList) { // 判断物品书否存在
		 * boolean flag = false; for (TbCart cart : cartItemList) { if
		 * (cart.getItemId().longValue() == item.getId().longValue()) {
		 * cart.setNum(cart.getNum() + item.getNum());
		 * cartService.updateCart(cart); flag = true; break; } } // 不存在 添加商品 if
		 * (!flag) { TbCart cart = new TbCart(); TbItem tbItem =
		 * itemService.geItemById(item.getId()); cart.setUserId(userId);
		 * cart.setItemId(item.getId()); cart.setItemTitle(tbItem.getTitle());
		 * // 取图片 String image = tbItem.getImage(); if
		 * (StringUtils.isNotBlank(image)) { String[] images = image.split(",");
		 * cart.setItemImage(images[0]); } cart.setItemPrice(tbItem.getPrice());
		 * cart.setNum(item.getNum()); cart.setCreated(new Date());
		 * cart.setUpdated(new Date()); // 把物品添加到购物车 cartService.addCart(cart);
		 * } } cartItemList = cartService.getCartByUserId(userId); // 清空cookie
		 * CookieUtils.deleteCookie(request, response, CART_KEY); }
		 */
		// 转换为tbtem

		// 在购物界面选择收货地址 else没有选择收货地址
		/*
		 * if (id != null) { receiver = orderService.getTbReceiverById(id);
		 * List<TbReceiver> tbReceiverList = new ArrayList<>();
		 * tbReceiverList.add(receiver); request.setAttribute("receiverInfo",
		 * receiver); request.setAttribute("receiver", tbReceiverList);
		 * request.setAttribute("cartList", tbItems); return "order-cart"; }
		 */
		Long userId = getUserId(request);
		List<TbReceiver> tbReceiverList = orderService.getTbReceiverList(userId);
		request.setAttribute("receiver", tbReceiverList);
		request.setAttribute("cartList", tbItems);
		return "order-cart";

	}

	private List<TbItem> getCartItemList(HttpServletRequest request) {
		// 从cookie中去购物车商品列表
		String json = CookieUtils.getCookieValue(request, CART_KEY, true);
		if (StringUtils.isBlank(json)) {
			return new ArrayList<>();
		}
		List<TbItem> list = JsonUtils.jsonToList(json, TbItem.class);
		return list;
	}

	// 生成订单
	@RequestMapping(value = "/order/create", method = RequestMethod.POST)
	public String createOrder(OrderInfo orderInfo, Model model, HttpServletRequest request) {
		Long userId = getUserId(request);

		// 生成订单
		WukongResult createOrder = orderService.createOrder(orderInfo, userId);
		
		model.addAttribute("orderId", createOrder.getData().toString());
		model.addAttribute("payment", orderInfo.getPayment());
		// 预计三天后送达
		DateTime dateTime = new DateTime();
		dateTime = dateTime.plusDays(3);
		model.addAttribute("date", dateTime.toString("yyyy-MM-dd"));
		// 删除购物车内容
		List<TbOrderItem> orderItems = orderInfo.getOrderItems();
		for (TbOrderItem tbOrderItem : orderItems) {
			cartService.deleteCartByUserIdANDItemId(userId, Long.valueOf(tbOrderItem.getItemId()));
		}

		return "success";
	}

	@RequestMapping(value = "/order/addAddress/add", method = RequestMethod.POST)
	@ResponseBody
	public WukongResult addAddress(HttpServletRequest request, HttpServletResponse response, TbReceiver receiver) {
		// 获取token，获取id
		Long userId = getUserId(request);
		receiver.setUserid(userId);
		int i = orderService.getTbReceiverList(userId).size();
		if (i >= 4) {
			return WukongResult.build(400, "请先删掉多余地址再添加");
		}
		return orderService.addAddress(receiver);
	}

	@RequestMapping("/order/addAddress")
	public String pageAddress() {
		return "addAddress";
	}

	@RequestMapping("/order/checkAddress")
	@ResponseBody
	public WukongResult checkAddress(HttpServletRequest request) {
		List<TbReceiver> list = orderService.getTbReceiverList(getUserId(request));
		if (list.size() >= 4) {
			return WukongResult.build(400, "收货地址过多，无法添加");
		} else {
			return WukongResult.ok();
		}
	}

	@RequestMapping("/order/deleteAddress")
	@ResponseBody
	public WukongResult deleteAddress(HttpServletRequest request, long id) {
		WukongResult result = orderService.deleteReceiver(id, getUserId(request));
		return result;

	}

	@RequestMapping("/order/order-list")
	public String showOrderList(HttpServletRequest request, HttpServletResponse response, Model model)
			throws ParseException {
		long userId = getUserId(request);
		List<TbOrder> getTbOrderList = orderService.getTbOrderListByUserId(userId);
		int count = getTbOrderList.size();
		int totalPrice = 0;
		for (TbOrder tbOrder : getTbOrderList) {
			totalPrice += Integer.valueOf(tbOrder.getPayment());
		}
		request.setAttribute("count", count);
		request.setAttribute("orderList", getTbOrderList);
		request.setAttribute("totalPrice", totalPrice);
		return "order-list";
	}

	@RequestMapping("/order/order-item-list/{orderId}")
	public String showOrderItemList(@PathVariable Long orderId, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {
		List<TbOrderItem> geTbOrderItemListByOrderId = orderService.geTbOrderItemListByOrderId(orderId);
		int count = 0;
		int totalPrice = 0;
		for (TbOrderItem tbOrderItem : geTbOrderItemListByOrderId) {
			count += tbOrderItem.getNum();
			totalPrice += tbOrderItem.getNum() * tbOrderItem.getPrice();
		}
		request.setAttribute("totalPrice", totalPrice);
		request.setAttribute("count", count);
		request.setAttribute("orderItem", geTbOrderItemListByOrderId);
		return "order-item-list";
	}

	@RequestMapping("/order/orderUpdate")
	@ResponseBody
	public WukongResult orderUpdate(Long orderId) {
		orderService.updateOrderStatus5ByOrderId(orderId);
		return WukongResult.ok();
	}

	private Long getUserId(HttpServletRequest request) {
		// 获取token，获取id
		String token = CookieUtils.getCookieValue(request, WU_KONG);
		WukongResult wukongResult = userService.getUserByToken(token);
		TbUser user = (TbUser) wukongResult.getData();
		Long userId = user.getId();
		return userId;
	}
}

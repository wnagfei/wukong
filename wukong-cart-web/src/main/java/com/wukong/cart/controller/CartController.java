package com.wukong.cart.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wukong.cart.service.CartService;
import com.wukong.common.pojo.WukongResult;
import com.wukong.common.utils.CookieUtils;
import com.wukong.common.utils.JsonUtils;
import com.wukong.pojo.TbCart;
import com.wukong.pojo.TbItem;
import com.wukong.pojo.TbUser;
import com.wukong.service.ItemService;
import com.wukong.sso.service.UserService;

/**
 * 购物车管理Controller
 *
 */
@Controller
public class CartController {

	@Value("${CART_KEY}")
	private String CART_KEY;
	@Value("${CART_EXPIER}")
	private Integer CART_EXPIER;
	@Value("WU_KONG")
	private String WU_KONG;
	@Resource
	private ItemService itemService;
	@Resource
	private UserService userService;
	@Resource
	private CartService cartService;

	@RequestMapping("/cart/add/{itemId}")
	public String addItemCart(@PathVariable Long itemId, @RequestParam(defaultValue = "1") Integer num,
			HttpServletRequest request, HttpServletResponse response) {
		// 判断用户是否登陆， 从cookie去token信息
		String token = CookieUtils.getCookieValue(request, WU_KONG);

		if (StringUtils.isBlank(token)) {
			// 没有登录
			// 没登录添加到cookie
			// 取购物车商品列表
			List<TbItem> cartItemList = getCartItemList(request);
			// 判断商品在购物车是否存在
			boolean flag = false;
			for (TbItem tbItem : cartItemList) {
				if (tbItem.getId() == itemId.longValue()) {
					// 如果存在数量相加
					tbItem.setNum(tbItem.getNum() + num);
					flag = true;
					break;
				}
			}
			// 没有就添加一个商品
			if (!flag) {
				// 调用服务区商品
				TbItem tbItem = itemService.geItemById(itemId);
				// 设置购买数量
				tbItem.setNum(num);
				// 取图片
				String image = tbItem.getImage();
				if (StringUtils.isNotBlank(image)) {
					String[] images = image.split(",");
					tbItem.setImage(images[0]);
				}
				// 把商品添加到购物车
				cartItemList.add(tbItem);

			}
			// 把购物车列表写入cookie
			CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartItemList), CART_EXPIER, true);
			// 返回添加成功页面
			return "cartSuccess";

		}
		// 取到token，调用sso判断是否登陆
		WukongResult wukongResult = userService.getUserByToken(token);
		if (wukongResult.getStatus() != 200) {
			// 没有登录
			// 没登录添加到cookie
			// 取购物车商品列表
			List<TbItem> cartItemList = getCartItemList(request);
			// 判断商品在购物车是否存在
			boolean flag = false;
			for (TbItem tbItem : cartItemList) {
				if (tbItem.getId() == itemId.longValue()) {
					// 如果存在数量相加
					tbItem.setNum(tbItem.getNum() + num);
					flag = true;
					break;
				}
			}
			// 没有就添加一个商品
			if (!flag) {
				// 调用服务区商品
				TbItem tbItem = itemService.geItemById(itemId);
				// 设置购买数量
				tbItem.setNum(num);
				// 取图片
				String image = tbItem.getImage();
				if (StringUtils.isNotBlank(image)) {
					String[] images = image.split(",");
					tbItem.setImage(images[0]);
				}
				// 把商品添加到购物车
				cartItemList.add(tbItem);

			}
			// 把购物车列表写入cookie
			CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartItemList), CART_EXPIER, true);
			// 返回添加成功页面
			return "cartSuccess";
		}
		// 从token取userid
		TbUser user = (TbUser) wukongResult.getData();
		Long userId = user.getId();
		// 取数据库中购物车商品列表
		List<TbCart> cartItemList = cartService.getCartByUserId(userId);
		// 判断物品书否存在
		boolean flag = false;
		for (TbCart tbCart : cartItemList) {
			if (tbCart.getItemId() == itemId.longValue()) {
				// 存在增加数量
				tbCart.setNum(tbCart.getNum() + num);
				// 跟新到数据库
				cartService.updateCart(tbCart);
				flag = true;
				break;
			}

		}
		// 不存在新增加商品
		if (!flag) {
			TbCart cart = new TbCart();
			TbItem tbItem = itemService.geItemById(itemId);
			cart.setUserId(userId);
			cart.setItemId(itemId);
			cart.setItemTitle(tbItem.getTitle());
			// 取图片
			String image = tbItem.getImage();
			if (StringUtils.isNotBlank(image)) {
				String[] images = image.split(",");
				cart.setItemImage(images[0]);
			}
			cart.setItemPrice(tbItem.getPrice());
			cart.setNum(num);
			cart.setCreated(new Date());
			cart.setUpdated(new Date());
			// 把物品添加到购物车
			cartService.addCart(cart);
		}
		// ，清空cookie
		CookieUtils.deleteCookie(request, response, CART_KEY);
		return "cartSuccess";
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

	@RequestMapping("/cart/cart")
	public String showCartList(HttpServletRequest request, HttpServletResponse response) {

		// 判断用户是否登陆， 从cookie去token信息
		String token = CookieUtils.getCookieValue(request, WU_KONG);

		if (StringUtils.isBlank(token)) {
			// 没有登录
			// 从cookie中去购物车列表
			List<TbItem> cartItemList = getCartItemList(request);
			// 把购物车列表传递给jsp
			request.setAttribute("cartList", cartItemList);
			return "cart";

		}
		// 取到token，调用sso判断是否登陆
		WukongResult wukongResult = userService.getUserByToken(token);
		if (wukongResult.getStatus() != 200) {
			// 没有登录
			// 从cookie中去购物车列表
			List<TbItem> cartItemList = getCartItemList(request);
			// 把购物车列表传递给jsp
			request.setAttribute("cartList", cartItemList);
			return "cart";
		}
		// 把cookie物品取出并且更新到数据库
		List<TbItem> cookieList = getCartItemList(request);

		// 从token取userid
		TbUser user = (TbUser) wukongResult.getData();
		Long userId = user.getId();
		// 取数据库中购物车商品列表
		List<TbCart> cartItemList = cartService.getCartByUserId(userId);

		// 循环遍历，如果数据库中有商品，添加数量，如果没有，添加新商品
		for (TbItem tbItem : cookieList) {
			boolean flag = true;
			for (TbCart tbCart : cartItemList) {
				// 判断是否相等
				if (tbItem.getId().longValue() == tbCart.getItemId().longValue()) {
					tbCart.setNum(tbCart.getNum() + tbItem.getNum());
					flag = false;
					// 跟新到数据库
					cartService.updateCart(tbCart);
					break;
				}
			}
			if (flag) {
				TbCart tbCart = new TbCart();
				tbCart.setUserId(userId);
				tbCart.setItemId(tbItem.getId());
				tbCart.setItemTitle(tbItem.getTitle());
				// 取图片
				String image = tbItem.getImage();
				if (StringUtils.isNotBlank(image)) {
					String[] images = image.split(",");
					tbCart.setItemImage(images[0]);
				}
				tbCart.setItemPrice(tbItem.getPrice());
				tbCart.setNum(tbItem.getNum());
				tbCart.setCreated(new Date());
				tbCart.setUpdated(new Date());
				// 把物品添加到购物车
				cartService.addCart(tbCart);
			}
		}
		// 清空cookie
		CookieUtils.deleteCookie(request, response, CART_KEY);
		// 重新取出数据库商品
		cartItemList = cartService.getCartByUserId(userId);
		
		//转换为TbItem
		List<TbItem> tbItems = new ArrayList<>();
		for (TbCart tbCart : cartItemList) {
			TbItem item = new TbItem();
			item = itemService.geItemById(tbCart.getItemId());
			item.setNum(tbCart.getNum());
			
			// 取图片
			String image = item.getImage();
			if (StringUtils.isNotBlank(image)) {
				String[] images = image.split(",");
				item.setImage(images[0]);
			}
			
			
			tbItems.add(item);
		}
		request.setAttribute("cartList", tbItems);
		return "cart";
	}

	@RequestMapping("/cart/update/num/{itemId}/{num}")
	@ResponseBody
	public WukongResult updateItemNum(@PathVariable Long itemId, @PathVariable Integer num, HttpServletRequest request,
			HttpServletResponse response) {

		// 判断用户是否登陆， 从cookie去token信息
		String token = CookieUtils.getCookieValue(request, WU_KONG);

		if (StringUtils.isBlank(token)) {
			// 没有登录
			// 从购物车去购物车列表
			List<TbItem> cartItemList = getCartItemList(request);
			// 查询对应商品
			for (TbItem tbItem : cartItemList) {
				if (tbItem.getId() == itemId.longValue()) {
					// 更新商品数量
					tbItem.setNum(num);
					break;
				}
			}
			// 写入cookie
			CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartItemList), CART_EXPIER, true);
			return WukongResult.ok();
		}
		// 取到token，调用sso判断是否登陆
		WukongResult wukongResult = userService.getUserByToken(token);
		if (wukongResult.getStatus() != 200) {
			// 没有登录
			// 从购物车去购物车列表
			List<TbItem> cartItemList = getCartItemList(request);
			// 查询对应商品
			for (TbItem tbItem : cartItemList) {
				if (tbItem.getId() == itemId.longValue()) {
					// 更新商品数量
					tbItem.setNum(num);
					break;
				}
			}
			// 写入cookie
			CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartItemList), CART_EXPIER, true);
			return WukongResult.ok();
		}
		// 从token取userid
		TbUser user = (TbUser) wukongResult.getData();
		Long userId = user.getId();
		// 取数据库中购物车商品列表
		List<TbCart> cartItemList = cartService.getCartByUserId(userId);
		for (TbCart tbCart : cartItemList) {
			if (tbCart.getItemId() == itemId.longValue()) {
				// 更新商品数量
				tbCart.setNum(num);
				cartService.updateCart(tbCart);
				break;
			}
		}
		// 清空cookie
		CookieUtils.deleteCookie(request, response, CART_KEY);
		// 返回成功
		return WukongResult.ok();
	}

	@RequestMapping("/cart/delete/{itemId}")
	public String deleteCartItem(@PathVariable Long itemId, HttpServletRequest request, HttpServletResponse response) {
		// 判断用户是否登陆， 从cookie去token信息
		String token = CookieUtils.getCookieValue(request, WU_KONG);
		if (StringUtils.isBlank(token)) {
			// 没有登录
			// 从cookie去购物车列表
			List<TbItem> cartItemList = getCartItemList(request);
			// 找到对应商品
			for (TbItem tbItem : cartItemList) {
				if (tbItem.getId() == itemId.longValue()) {
					// 删除商品
					cartItemList.remove(tbItem);
					break;
				}
			}
			// 写入购物车列表
			CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartItemList), CART_EXPIER, true);
			// 重定向购物车列表
			return "redirect:/cart/cart.html";
		}
		// 取到token，调用sso判断是否登陆
		WukongResult wukongResult = userService.getUserByToken(token);
		if (wukongResult.getStatus() != 200) {
			// 没有登录
			// 从cookie去购物车列表
			List<TbItem> cartItemList = getCartItemList(request);
			// 找到对应商品
			for (TbItem tbItem : cartItemList) {
				if (tbItem.getId() == itemId.longValue()) {
					// 删除商品
					cartItemList.remove(tbItem);
					break;
				}
			}
			// 写入购物车列表
			CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartItemList), CART_EXPIER, true);
			// 重定向购物车列表
			return "redirect:/cart/cart.html";
		}
		// 从token取userid
		TbUser user = (TbUser) wukongResult.getData();
		Long userId = user.getId();
		// 从数据库中去购物车列表
		List<TbCart> cartItemList = cartService.getCartByUserId(userId);
		for (TbCart tbCart : cartItemList) {
			if(tbCart.getItemId() == itemId.longValue()){
				//从数据库中删除
				cartService.deleteById(tbCart.getId());
				break;
			}
		}
		// 清空cookie
		CookieUtils.deleteCookie(request, response, CART_KEY);
		// 重定向购物车列表
		return "redirect:/cart/cart.html";
	}

}

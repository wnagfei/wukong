package com.wukong.sso.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
 * 用户处理
 *
 */
@Controller
public class UserController {

	@Resource
	private UserService userService;
	@Resource
	private CartService cartService;
	@Resource
	private ItemService itemService;
	@Value("${TOKEN_KEY}")
	private String TOKEN_KEY;
	@Value("${CART_KEY}")
	private String CART_KEY;
	@Value("WU_KONG")
	private String WU_KONG;

	@RequestMapping("/user/check/{param}/{type}")
	@ResponseBody
	public WukongResult checkUserData(@PathVariable String param, @PathVariable Integer type) {
		return userService.checkDate(param, type);
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	@ResponseBody
	public WukongResult register(TbUser user) {
		return userService.register(user);
	}

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	@ResponseBody
	public WukongResult login(HttpServletRequest request, HttpServletResponse response, String username,
			String password) {
		WukongResult login = userService.login(username, password);
		// 登陆成功把token写入cookie
		if (login.getStatus() == 200) {
			CookieUtils.setCookie(request, response, TOKEN_KEY, login.getData().toString());
			System.out.println(login.getData().toString());
			TbUser user = userService.geTbUser(login.getData().toString());
			/************************** 把cookie中购物车信息同步到数据库 *********************************/
			
			List<TbCart> cartItemList = cartService.getCartByUserId(user.getId());
			// 从cookie中取出购物车商品 合并到数据库
			List<TbItem> itemsList = getCartItemList(request);

			if (itemsList != null) {
				for (TbItem item : itemsList) {
					// 判断物品书否存在
					boolean flag = false;
					for (TbCart cart : cartItemList) {
						if (cart.getItemId().longValue() == item.getId().longValue()) {

							cart.setNum(cart.getNum() + item.getNum());
							cartService.updateCart(cart);
							flag = true;
							break;
						}
					}
					// 不存在 添加商品
					if (!flag) {
						TbCart cart = new TbCart();
						TbItem tbItem = itemService.geItemById(item.getId());
						cart.setUserId(user.getId());
						cart.setItemId(item.getId());
						cart.setItemTitle(tbItem.getTitle());
						// 取图片
						String image = tbItem.getImage();
						if (StringUtils.isNotBlank(image)) {
							String[] images = image.split(",");
							cart.setItemImage(images[0]);
						}
						cart.setItemPrice(tbItem.getPrice());
						cart.setNum(item.getNum());
						cart.setCreated(new Date());
						cart.setUpdated(new Date()); // 把物品添加到购物车
						cartService.addCart(cart);
					}
				}
				// 清空cookie
				CookieUtils.deleteCookie(request, response, CART_KEY);

			}
			/***********************************************************/
		}

		return login;
	}

	/*
	 * @RequestMapping(value = "/user/token/{token}", method =
	 * RequestMethod.POST, //指定返回响应数据的content-type produces =
	 * MediaType.APPLICATION_JSON_UTF8_VALUE)
	 * 
	 * @ResponseBody public String getUserByToken(@PathVariable String token,
	 * String callback) { WukongResult userByToken =
	 * userService.getUserByToken(token); // 判断是否为jsonp请求 if
	 * (StringUtils.isNotBlank(callback)){ return callback + "(" +
	 * JsonUtils.objectToJson(userByToken) + ");"; } return
	 * JsonUtils.objectToJson(userByToken); }
	 */
	@RequestMapping(value = "/user/token/{token}", method = RequestMethod.GET)
	@ResponseBody
	public Object getUserByToken(@PathVariable String token, String callback) {
		WukongResult userByToken = userService.getUserByToken(token);
		// 判断是否为jsonp请求
		if (StringUtils.isNotBlank(callback)) {
			MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(userByToken);
			// 设置回调方法
			mappingJacksonValue.setJsonpFunction(callback);
			return mappingJacksonValue;
		}
		return userByToken;
	}

	@RequestMapping("/user/logout")
	public String logout(HttpServletRequest request, HttpServletResponse response) {
		String token = CookieUtils.getCookieValue(request, "WU_KONG");

		userService.logout(token);

		CookieUtils.deleteCookie(request, response, "WU_KONG");
		
		/*
		 * Cookie[] cookies = request.getCookies(); for (Cookie cookie :
		 * cookies) { System.out.println(cookie.getName());
		 * 
		 * if("WU_KONG".equals(cookie.getName())){
		 * 
		 * System.out.println(cookie.getMaxAge());
		 * 
		 * System.out.println(cookie.getName());
		 * 
		 * cookie.setMaxAge(100);// 如果0，就说明立即删除
		 * 
		 * System.out.println("sdfsf");
		 * 
		 * System.out.println(cookie.getMaxAge());
		 * 
		 * cookie.setPath("/");// 不要漏掉 break; } } cookies =
		 * request.getCookies(); for (Cookie cookie : cookies) {
		 * System.out.println(cookie.getName()); }
		 */
		return "redirect:/page/login";
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


}

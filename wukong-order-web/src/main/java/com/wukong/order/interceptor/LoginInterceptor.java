package com.wukong.order.interceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.wukong.common.pojo.WukongResult;
import com.wukong.common.utils.CookieUtils;
import com.wukong.pojo.TbUser;
import com.wukong.sso.service.UserService;

/**
 * 判断用户是否登陆
 *
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Value("${WU_KONG}")
	private String WU_KONG;
	@Value("${SSO_URL}")
	private String SSO_URL;
	@Value("${ORDER_URL}")
	private String ORDER_URL;
	@Resource
	private UserService userService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		// 从cookie取token信息
		String token = CookieUtils.getCookieValue(request, WU_KONG);
		// 取当前请求的url
		String requestUrl = request.getRequestURL().toString();
		//判断有没有参数
		String queryUrl = request.getQueryString();
		System.out.println(queryUrl);
		if(queryUrl != null && !"".equals(queryUrl)){
			requestUrl = requestUrl+ "?" + queryUrl;
			System.out.println(requestUrl);
		}
		// 取不到token 跳转到登陆页面，需要把当前请求url作为蚕食传给sso，sso登陆成功后跳转回请求页面
		if (StringUtils.isBlank(token)) {
			
			// 跳转到登陆页面
			response.sendRedirect(SSO_URL + "/page/login?url=" + requestUrl);
			// 拦截
			return false;
		}
		// 取到token，调用sso判断是否登陆
		WukongResult wukongResult = userService.getUserByToken(token);
		// 未登录 跳转sso登陆
		if (wukongResult.getStatus() != 200) {
			response.sendRedirect(SSO_URL + "/page/login?url=" + requestUrl);
			return false;
		}
		//把用户信息放到request中
		TbUser user = (TbUser)wukongResult.getData();
		request.setAttribute("user", user);
		return true;

	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub 

	}

}

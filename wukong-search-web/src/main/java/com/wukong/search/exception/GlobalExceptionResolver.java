package com.wukong.search.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class GlobalExceptionResolver implements HandlerExceptionResolver {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception e) {
		// TODO Auto-generated method stub
		logger.info("");
		
		//想日志文件中写入日常
		logger.error("系统错误 ", e);
		
		//
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("message", "系统错误");
		modelAndView.setViewName("error/exception");
		return null;
	}

}

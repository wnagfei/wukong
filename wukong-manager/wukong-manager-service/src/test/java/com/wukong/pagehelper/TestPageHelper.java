package com.wukong.pagehelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wukong.mapper.TbItemMapper;
import com.wukong.pojo.TbItem;
import com.wukong.pojo.TbItemExample;

public class TestPageHelper {
	
	@Test
	public void testPageHelper() throws Exception{
		PageHelper.startPage(1,10);
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		TbItemMapper itemMapper = applicationContext.getBean(TbItemMapper.class);
		
		TbItemExample example = new TbItemExample();
		
		List<TbItem> list = itemMapper.selectByExample(example);
		
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		System.out.println(pageInfo.getTotal());
		System.out.println(pageInfo.getPages());
	}
}

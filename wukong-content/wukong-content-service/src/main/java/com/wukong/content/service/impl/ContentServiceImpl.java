package com.wukong.content.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wukong.common.pojo.EasyUIDataGridResult;
import com.wukong.common.pojo.WukongResult;
import com.wukong.common.utils.JsonUtils;
import com.wukong.content.service.ContentService;
import com.wukong.jedis.JedisClient;
import com.wukong.mapper.TbContentMapper;
import com.wukong.pojo.TbContent;
import com.wukong.pojo.TbContentExample;
import com.wukong.pojo.TbContentExample.Criteria;

@Service
public class ContentServiceImpl implements ContentService {

	@Resource
	private TbContentMapper contentMapper;
	@Resource
	private JedisClient jedisClient;
	@Value("${INDEX_CONTENT}")
	private String INDEX_CONTENT;
	
	@Override
	public WukongResult addContent(TbContent content) {
		// TODO Auto-generated method stub
		content.setCreated(new Date());
		content.setUpdated(new Date());
		contentMapper.insert(content);
		
		//同步redis
		//删除redis
		jedisClient.hdel(INDEX_CONTENT, content.getCategoryId().toString());
		return WukongResult.ok();
	}

	@Override
	public EasyUIDataGridResult getContents(int page, int rows) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		List<TbContent> selectByExample = contentMapper.selectByExample(example);

		PageInfo<TbContent> pageInfo = new PageInfo<>(selectByExample);

		EasyUIDataGridResult result = new EasyUIDataGridResult();

		result.setRows(selectByExample);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public EasyUIDataGridResult getContentsByCategoryId(long categoryId, int page, int rows) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> selectByExample = contentMapper.selectByExample(example);
		PageInfo<TbContent> pageInfo = new PageInfo<>(selectByExample);

		EasyUIDataGridResult result = new EasyUIDataGridResult();

		result.setRows(selectByExample);
		result.setTotal(pageInfo.getTotal());
		return result;
	}

	@Override
	public List<TbContent> getContentsByCId(long cid) {
		// TODO Auto-generated method stub
		
		//先查询redis
		//添加缓存不能影响业务逻辑
		try{
			
			String json = jedisClient.hget(INDEX_CONTENT, cid+"");
			
			//查询到把json转换成list
			if(StringUtils.isNotBlank(json)){
				List<TbContent> jsonToList = JsonUtils.jsonToList(json, TbContent.class);
				return jsonToList;
			}
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//redis没有查询缓存
		TbContentExample example = new TbContentExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andCategoryIdEqualTo(cid);
		List<TbContent> selectByExample = contentMapper.selectByExample(example);
		
		//把结果添加到redis
		try{
			jedisClient.hset(INDEX_CONTENT, cid+"", JsonUtils.objectToJson(selectByExample));
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return selectByExample;
	}

	@Override
	public WukongResult deleteContents(List<Long> list) {
		// TODO Auto-generated method stub
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(list);
		//根据id查询出该商品的cid，因为根据列表数据显示，同一次删除的是同一类型的
		TbContent tbContent = contentMapper.selectByPrimaryKey(list.get(0));
		Long cid = tbContent.getCategoryId();
		contentMapper.deleteByExample(example);
		
		try {
			//根据cid删除商品缓存
			jedisClient.hdel(INDEX_CONTENT, cid + "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return WukongResult.ok();
	}

	@Override
	public WukongResult updateContent(TbContent content) {
		// TODO Auto-generated method stub
		content.setUpdated(new Date());
		contentMapper.updateByPrimaryKeySelective(content);
		return WukongResult.ok();
	}

	@Override
	public WukongResult deleteContentByCid(long cid) {
		// TODO Auto-generated method stub
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		contentMapper.deleteByExample(example);
		return WukongResult.ok();
	}

}

package com.wukong.service.impl;


import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wukong.common.pojo.EasyUIDataGridResult;
import com.wukong.common.pojo.WukongResult;
import com.wukong.common.utils.IDUtils;
import com.wukong.mapper.TbItemDescMapper;
import com.wukong.mapper.TbItemMapper;
import com.wukong.pojo.TbItem;
import com.wukong.pojo.TbItemDesc;
import com.wukong.pojo.TbItemDescExample;
import com.wukong.pojo.TbItemExample;
import com.wukong.pojo.TbItemExample.Criteria;
import com.wukong.service.ItemService;

/**
 * 商品管理Service
 * 
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Resource
	private TbItemMapper itemMapper;
	@Resource
	private TbItemDescMapper itemDescMapper;
	@Resource
	private JmsTemplate jmsTemplate;
	@Resource(name="itemAddtopic")
	private Destination destination;
	@Resource(name="itemDeleteTopic")
	private Destination deleteTopic;
	
	@Override
	public TbItem geItemById(long itemId) {
		// TODO Auto-generated method stub
		TbItem item = itemMapper.selectByPrimaryKey(itemId);
		return item;
	}
	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		//分页信息
		PageHelper.startPage(page,rows);
		//查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		
		//结果
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(pageInfo.getTotal());
		return result;
	}
	@Override
	public WukongResult addItem(TbItem item, String desc) {
		//生成商品id
		 long itemId = IDUtils.genItemId();
		item.setId(itemId);
		//商品状态1正常 2下架 3删除
		item.setStatus((byte) 1);
		
		item.setCreated(new Date());
		item.setUpdated(new Date());
		
		itemMapper.insert(item);
		
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(new Date());
		itemDesc.setUpdated(new Date());
		
		itemDescMapper.insert(itemDesc);
		
		//向activemp发送消息
		jmsTemplate.send(destination,new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				// TODO Auto-generated method stub
				//发送商品id
				TextMessage textMessage = session.createTextMessage(itemId + "");
				return textMessage;
			}
		});
        
		return WukongResult.ok();
	}
	@Override
	public TbItemDesc getTbItemDescById(long itemId) {
		// TODO Auto-generated method stub
		TbItemDesc selectByPrimaryKey = itemDescMapper.selectByPrimaryKey(itemId);
		return selectByPrimaryKey;
	}
	@Override
	public WukongResult deleteItems(List<Long> list) {
		// TODO Auto-generated method stub
		TbItemExample example = new TbItemExample();
        Criteria criteria = example.createCriteria();
		criteria.andIdIn(list);
		itemMapper.deleteByExample(example);
		for(Long itemId : list) {
			// 向activemp发送消息
			jmsTemplate.send(deleteTopic, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				//发送商品id
				TextMessage textMessage = session.createTextMessage(itemId + "");
					return textMessage;
				}
			});
		}

		return WukongResult.ok();
	}
	@Override
	public WukongResult instockItems(List<Long> list) {
		// TODO Auto-generated method stub
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(list);
		
		List<TbItem> itemList = itemMapper.selectByExample(example);
		for(TbItem item:itemList) {
			item.setStatus((byte) 2);
			itemMapper.updateByPrimaryKey(item);
		}
		return WukongResult.ok();
	}
	@Override
	public WukongResult reshelfItems(List<Long> list) {
		// TODO Auto-generated method stub
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andIdIn(list);
		
		List<TbItem> itemList = itemMapper.selectByExample(example);
		for(TbItem item:itemList) {
			item.setStatus((byte) 1);
			itemMapper.updateByPrimaryKey(item);
		}
		return WukongResult.ok();
	}
	@Override
	public WukongResult updateItemAndDesc(TbItem item, String desc) {
		// TODO Auto-generated method stub
		item.setUpdated(new Date());
		itemMapper.updateByPrimaryKeySelective(item);
		TbItemDescExample example = new TbItemDescExample();
		TbItemDescExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(item.getId());
		List<TbItemDesc> list = itemDescMapper.selectByExample(example);
		TbItemDesc itemDesc = list.get(0);
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(new Date());
		itemDescMapper.updateByPrimaryKeySelective(itemDesc);
		return WukongResult.ok();
	}

}

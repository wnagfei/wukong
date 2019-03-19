package com.wukong.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wukong.common.pojo.EasyUITreeNode;
import com.wukong.mapper.TbItemCatMapper;
import com.wukong.pojo.TbItemCat;
import com.wukong.pojo.TbItemCatExample;
import com.wukong.pojo.TbItemCatExample.Criteria;
import com.wukong.service.ItemCatService;
/**
 * 商品分类管理
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Resource
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public List<EasyUITreeNode> getItemCatList(long parentId) {
		//根据父节点id 查询子节点列表
		TbItemCatExample example = new TbItemCatExample();
		
		Criteria criteria = example.createCriteria();
		
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		
		//把结果转化
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for(TbItemCat tbItemCat : list){
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			
			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public TbItemCat getItemCat(long cid) {
		TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(cid);
		return itemCat;
	}
}

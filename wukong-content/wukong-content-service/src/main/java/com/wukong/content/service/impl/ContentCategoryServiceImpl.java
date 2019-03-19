package com.wukong.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.wukong.common.pojo.EasyUITreeNode;
import com.wukong.common.pojo.WukongResult;
import com.wukong.content.service.ContentCategoryService;
import com.wukong.content.service.ContentService;
import com.wukong.mapper.TbContentCategoryMapper;
import com.wukong.pojo.TbContentCategory;
import com.wukong.pojo.TbContentCategoryExample;
import com.wukong.pojo.TbContentCategoryExample.Criteria;

import javassist.expr.NewArray;

/**
 * 内容分类管理
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Resource
	private TbContentCategoryMapper contentCategoryMapper;
	@Resource
	private ContentService contentService;

	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
		// TODO Auto-generated method stub
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria citeria = example.createCriteria();
		contentCategoryMapper.selectByExample(example);
		citeria.andParentIdEqualTo(parentId);
		citeria.andStatusEqualTo(1);
		List<EasyUITreeNode> rEasyUITreeNodes = new ArrayList<>();
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent() ? "closed" : "open");

			rEasyUITreeNodes.add(node);
		}

		return rEasyUITreeNodes;
	}

	@Override
	public WukongResult addContentCateGory(long parentId, String name) {
		// TODO Auto-generated method stub
		TbContentCategory category = new TbContentCategory();

		category.setParentId(parentId);
		category.setName(name);
		//状态。可选值:1(正常),2(删除)
		category.setStatus(1);
		//排序，默认为1
		category.setSortOrder(1);
		category.setIsParent(false);
		category.setCreated(new Date());
		category.setUpdated(new Date());
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parent.getIsParent()) {
			// 如果是叶子节点，改为父节点
			parent.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parent);
		}

		contentCategoryMapper.insert(category);
		return WukongResult.ok(category);
	}

	@Override
	public WukongResult updateContentCateGory(long id, String name) {
		// TODO Auto-generated method stub
		TbContentCategory category = new TbContentCategory();
		category.setId(id);
		category.setName(name);
		category.setUpdated(new Date());
		contentCategoryMapper.updateByPrimaryKeySelective(category);
		return WukongResult.ok();
	}

	@Override
	public WukongResult deleteContentCategory(Long id) {
		// TODO Auto-generated method stub
		// 获取要删除的节点
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);

		// 判断该节点是否为父节点，若是，级联删除子节点
		if (contentCategory.getIsParent()) {
			deleteNode(contentCategory.getId());
			contentCategory.setStatus(2);
			contentCategory.setUpdated(new Date());
			// 设置为叶子节点
			contentCategory.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		}

		// 如果不是父节点要删除该节点下的商品信息
		// 软删除
		contentCategory.setStatus(2);
		contentCategory.setUpdated(new Date());
		// 设置为叶子节点
		contentCategory.setIsParent(false);

		contentService.deleteContentByCid(contentCategory.getId());
		contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		
		return WukongResult.ok();
	}

	@Override
	public WukongResult getContentCategoryById(long id) {
		// TODO Auto-generated method stub
		TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);
		return WukongResult.build(200, null, contentCategory);
	}

	// 查询父节点下的所有子节点，抽取出的公共方法
	private List<TbContentCategory> getContentCategoryListByParentId(long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		return list;
	}

	// 递归删除节点
	private void deleteNode(long parentId) {
		List<TbContentCategory> list = getContentCategoryListByParentId(parentId);
		for (TbContentCategory contentCategory : list) {
			// 如果是父节点递归删除
			if (contentCategory.getIsParent()) {
				deleteNode(contentCategory.getId());
			}

			// 不使用硬删除，只更改状态
			contentCategory.setStatus(2);
			// 修改该节点为叶子节点
			contentCategory.setIsParent(false);
			// 删除该节点下的商品
			contentService.deleteContentByCid(contentCategory.getId());
			contentCategoryMapper.updateByPrimaryKeySelective(contentCategory);
		}
	}

}

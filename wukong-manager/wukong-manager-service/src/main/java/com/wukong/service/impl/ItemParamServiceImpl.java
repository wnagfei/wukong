package com.wukong.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wukong.common.pojo.EasyUIDataGridResult;
import com.wukong.common.pojo.ParamResult;
import com.wukong.common.pojo.WukongResult;
import com.wukong.mapper.TbItemCatMapper;
import com.wukong.mapper.TbItemParamItemMapper;
import com.wukong.mapper.TbItemParamMapper;

import com.wukong.pojo.TbItemCat;
import com.wukong.pojo.TbItemParam;
import com.wukong.pojo.TbItemParamExample;
import com.wukong.pojo.TbItemParamItem;
import com.wukong.pojo.TbItemParamItemExample;
import com.wukong.pojo.TbItemParamItemExample.Criteria;
import com.wukong.service.ItemParamService;

@Service
public class ItemParamServiceImpl implements ItemParamService {
	@Resource
	private TbItemParamMapper paramMepper;
	@Resource
	private TbItemCatMapper itemCatMapper;
	@Resource
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public EasyUIDataGridResult getItemParamList(int page, int rows) {

		PageHelper.startPage(page, rows);
		TbItemParamExample example = new TbItemParamExample();
		List<TbItemParam> list = paramMepper.selectByExampleWithBLOBs(example);

		List<ParamResult> LResults = new ArrayList<>();
		for (TbItemParam param : list) {
			TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(param.getItemCatId());

			ParamResult paramResult = new ParamResult();

			paramResult.setId(param.getId());
			paramResult.setItemCatId(param.getItemCatId());
			paramResult.setItemCatName(itemCat.getName());
			paramResult.setParamData(param.getParamData());
			paramResult.setCreated(param.getCreated());
			paramResult.setUpdated(param.getUpdated());

			LResults.add(paramResult);
		}
		PageInfo<TbItemParam> pageInfo = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(LResults);
		return result;

	}

	@Override
	public WukongResult getItemParamItemByItemId(long itemId) {
		// TODO Auto-generated method stub
		WukongResult result = WukongResult.ok();
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = itemParamItemMapper.selectByExample(example);
		if(list.size()>0) {
			result = WukongResult.ok(list);
		}
 		return result;
	}

	@Override
	public TbItemParamItem getItemParamItemByItemId2(long itemId) {
		// TODO Auto-generated method stub
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
		if(list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public WukongResult deleteItemParamByItemIdList(ArrayList<Long> list) {
		// TODO Auto-generated method stub
		TbItemParamItemExample example = new TbItemParamItemExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdIn(list);
		itemParamItemMapper.deleteByExample(example);
		return WukongResult.ok();
	}
}

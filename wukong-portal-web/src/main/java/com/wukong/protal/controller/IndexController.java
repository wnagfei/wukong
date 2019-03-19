package com.wukong.protal.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
/**
 * 
 * 首页
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;

import com.wukong.common.utils.JsonUtils;
import com.wukong.content.service.ContentService;
import com.wukong.pojo.TbContent;
import com.wukong.protal.pojo.AD1Node;
import com.wukong.protal.pojo.RNode;
import com.wukong.protal.pojo.SlideNode;
@Controller
@RequestMapping("/")
public class IndexController {
	
	@Value("${AD1_CATEGORY_ID}")
	private long AD1_CATEGORY_ID;
	@Value("${AD1_WIDTH}")
	private Integer AD1_WIDTH;
	@Value("${AD1_WIDTH_B}")
	private Integer AD1_WIDTH_B;
	@Value("${AD1_HEIGHT}")
	private Integer AD1_HEIGHT;
	@Value("${AD1_HEIGHT_B}")
	private Integer AD1_HEIGHT_B;
	@Value("${SLI_CATEFORY_ID}")
	private Long SLI_CATEFORY_ID;
	@Value("${R_CATEFORY_ID}")
	private Long R_CATEFORY_ID;
	@Value("${R_WIDTH}")
	private Integer R_WIDTH;
	@Value("${R_WIDTH_B}")
	private Integer R_WIDTH_B;
	@Value("${R_HEIGHT}")
	private Integer R_HEIGHT;
	@Value("${R_HEIGHT_B}")
	private Integer R_HEIGHT_B;
	
	@Resource
	private ContentService contentService;
	
	@RequestMapping("index")
	public String showIndex(Model model){
		//右侧广告位
		List<TbContent> rList = contentService.getContentsByCId(R_CATEFORY_ID);
		TbContent tbContent2 = rList.get(0);
		RNode rnode = new RNode();
		rnode.setAlt(tbContent2.getTitle());
		rnode.setHeight(R_HEIGHT);
		rnode.setHeightB(R_HEIGHT_B);
		rnode.setWidth(R_WIDTH);
		rnode.setWidthB(R_WIDTH_B);
		rnode.setSrc(tbContent2.getPic());
		rnode.setSrcB(tbContent2.getPic2());
		rnode.setHref(tbContent2.getUrl());
		//中央大广告
		List<TbContent> adList = contentService.getContentsByCId(AD1_CATEGORY_ID);
		List<AD1Node> ad1Nodes = new ArrayList<>();
		for(TbContent tbContent:adList) {
			AD1Node node = new AD1Node();
			node.setAlt(tbContent.getTitle());
			node.setHeight(AD1_HEIGHT);
			node.setHeightB(AD1_HEIGHT_B);
			node.setWidth(AD1_WIDTH);
			node.setWidthB(AD1_WIDTH_B);
			node.setSrc(tbContent.getPic());
			node.setSrcB(tbContent.getPic2());
			node.setHref(tbContent.getUrl());
			ad1Nodes.add(node);
		}
		
		//滑动广告位
		List<TbContent> slideList = contentService.getContentsByCId(SLI_CATEFORY_ID);
		List<SlideNode> slideNodes = new ArrayList<>();
		int count = 0;
		for(TbContent sli:slideList) {
			SlideNode snode = new SlideNode();
			snode.setAlt(sli.getTitle());
			snode.setHref(sli.getUrl());
			snode.setIndex(count);
			snode.setSrc(sli.getPic());
			snode.setExt(null);
			count++;
			slideNodes.add(snode);
		}
		
		List<String> list = new ArrayList<>();
		String ad1Json = JsonUtils.objectToJson(ad1Nodes);
		list.add(ad1Json);
		String slideJson = JsonUtils.objectToJson(slideNodes);
		//System.out.println(slideJson + ",==========================");
		list.add(slideJson);
		
		//右上侧广告，前端要求一个list（json化的），所以虽然只有一条数据也要放到list中
		ArrayList<Object> r = new ArrayList<>();
		r.add(rnode);
		String rJson = JsonUtils.objectToJson(r);
		list.add(rJson);

		model.addAttribute("result", list);
		return "index";
	}
	
	
}

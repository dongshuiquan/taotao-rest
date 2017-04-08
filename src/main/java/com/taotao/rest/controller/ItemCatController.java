package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.utils.JsonUtils;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
/**
 * 商品分类列表控制器
 * @author dong
 *
 */
@Controller
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping(value="itemcat/list", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getItemCatList(String callback){
		CatResult catResult = itemCatService.getItemResult();
		//把pojo转换成字符串
		String json = JsonUtils.objectToJson(catResult);
		String result = callback + "(" + json + ");";
		return result;		 
	}
	
}

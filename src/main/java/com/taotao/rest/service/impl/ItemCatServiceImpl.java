package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
/**
 * 商品分类服务
 * @author dong
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService{

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public CatResult getItemResult() {
		CatResult catResult = new CatResult();
		catResult.setData(getCatList(0));
		return catResult;
	}

	private List<?> getCatList(long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		List<Object> resultList = new ArrayList<>();
		criteria.andParentIdEqualTo(parentId);
		CatNode catNode;
		int count = 0;
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		for(TbItemCat tbItemCat : list){
			//判断是否为父节点
			if(tbItemCat.getIsParent()){
				catNode = new CatNode();
				if(parentId == 0){
					catNode.setName("<a href='/products/"+ tbItemCat.getId()+".html'>"+ tbItemCat.getName()+"</a>");
				}else{
					catNode.setName(tbItemCat.getName());
				}
				catNode.setUrl("/products/" + tbItemCat.getId() + ".html");
				catNode.setItem(getCatList(tbItemCat.getId()));
				resultList.add(catNode);
				count++;
				if(parentId == 0 && count >= 14)
					break;
			//如果是叶子节点	
			}else{
				resultList.add("/products/" + tbItemCat.getId() + ".html|" + tbItemCat.getName());
			}
		}
		return resultList;
	}

}

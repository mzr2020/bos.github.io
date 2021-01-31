package com.itheima.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Function;

import com.itheima.bos.service.FunctionService;
import com.itheima.bos.web.action.base.BaseAction;


/*
	权限管理

*/
@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function> {

	@Autowired
	private	FunctionService	functionService;
	
	/*
		查询所有数据，返回json数据

	 */
	/**
	 * 查询所有权限，返回json数据
	 */
	public String listajax(){
		List<Function> list = functionService.findAll();
		this.java2Json(list, new String[]{"parentFunction","roles"});
		return NONE;
	}
	/*
	 * 	添加权限
	*/
	public String	add() {
			
		
		functionService.save(model);
		
		return LIST;
		
	}
	/*
	 * 分页
	*/
	public String	pageQuery() {
		
		String page = model.getPage();
		pageBean.setCurrentPage(Integer.parseInt(page));
		
		functionService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"parentFunction","roles","children"});
		
		return NONE;
		
	}	
	/*
	 * 根据当前登录人查询对应的菜单数据，返回json
	*/
	public String	findMenu() {
		
		List<Function> list=functionService.findMenu();
		this.java2Json(list, new String[]{"parentFunction","roles","children"});
		return NONE;
		
	}
	
}

package com.itheima.bos.web.action;


import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IStaffSerivice;

import com.itheima.bos.web.action.base.BaseAction;


/*
 * 取派员管理
 * 
*/
@Controller
@Scope("prototype")
public class StaffAction	extends	BaseAction<Staff> {
	
	@Autowired	
	protected	IStaffSerivice	staffSerivice;
	/*
	*	取派员信息添加
	*/
	public	String	add() {
		
		staffSerivice.save(model);		
		return LIST;		
	}
	/*
	*	取派员信息修改
	*/
	public	String	edit() {
		//先查询数据库，根据id查询原始数据
		Staff staff = staffSerivice.findById(model.getId());
		
		//使用页面提交的数据进行覆盖
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setHaspda(model.getHaspda());		
		staff.setStandard(model.getStandard());
		staff.setStation(model.getStation());
		
		staffSerivice.update(staff);
		return LIST;
		
	}
	
	//属性驱动，接收页面提交的id参数
	private	String	ids;
	public String getIds() {
		return ids;
	}
	
	public void setIds(String ids) {
		this.ids = ids;
	}
	/*
	*	取派员批量删除
	*/	
	@RequiresPermissions("staff-delete") //执行这个方法，需要当前用户具有staff-delete这个权限
	public	String	deleteBath() {
		
		staffSerivice.deleteBath(ids);
		return LIST;		
	}
	
	/*
	*	分页查询方法
	*/
	

	public	String	pageQuery() throws Exception{			
				
		staffSerivice.pageQuery(pageBean);
		
		
		//使用json-lib将PageBean对象转为json，通过输入流写回页面
		//jsonObject--将单一对象转为就送
		//jsonArray--将数组或者集合对象转为json
		
		/*{"currentPage":0,
		"detachedCriteria":{"alias":"this"},
		"pageSize":0,
		"rows":[{"decidedzones":[],"deltag":"","haspda":"1","id":"40285581771f4a2e01771f4abb380000","name":"lisi","standard":"合格","station":"北京","telephone":"13539627725"},{"decidedzones":[],"deltag":"","haspda":"0","id":"402855817720553f017720567bc10000","name":"测试1","standard":"不合格","station":"未知","telephone":"13564785555"}],
		"total":2}
		其中有不需要的对象
		*/
		this.java2Json(pageBean, new String[]{"decidedzones","currentPage","detachedCriteria","pageSize"});
		return NONE;
		
	}
	/*
		查询所以未查询的取派员，并返回json对象
	
	*/
	public String	listajax() {
		
		List<Staff>	list=staffSerivice.findListNotDelete();
		this.java2Json(list,new	String[]{"decidedzones"});
		
		return NONE;
		
	}
	
	
}

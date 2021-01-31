package com.itheima.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.service.DecidedzoneService;
import com.itheima.bos.web.action.base.BaseAction;
import com.itheima.crm.service.Customer;
import com.itheima.crm.service.ICustomerService;

	/*
		定区管理
	
	*/
@Controller
@Scope("prototype")
public class DecidedzoneAction extends	BaseAction<Decidedzone>{
	@Autowired
	private	DecidedzoneService	decidedzoneService;
	
	//属性驱动，接受多个分区id
	private	String[]	subareaid;
	
	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}
	/*
		添加定区

	*/

	public	String	add() {
		
		decidedzoneService.save(model,subareaid);
		
		return NONE;
		
	}
	/**
	 * 分页查询方法
	 */
	public String pageQuery() throws Exception{
		decidedzoneService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"currentPage","detachedCriteria",
						"pageSize","subareas","decidedzones"});
		return NONE;
	}
	//注入代理对象
	@Autowired
	private ICustomerService	proxy;
	/*
		远程调用crm服务，获取未关联到定区的客户
		
	*/
	public String	findListNotAssociation() {
		
		List<Customer> list = proxy.findListNotAssociation();
		this.java2Json(list, new String[]{});
		return NONE;
		
	}
	/*
	远程调用crm服务，获取关联到定区的客户
	
	 */
	public String	findListAssociation() {
		
		String id = model.getId();
		List<Customer> list = proxy.findListAssociation(id);
		this.java2Json(list, new String[]{});
		return NONE;
		
	}
		//属性驱动。接收页面提交的多个客户id
		private List<Integer>	customerIds;
		
	public List<Integer> getCustomerIds() {
			return customerIds;
		}

		public void setCustomerIds(List<Integer> customerIds) {
			this.customerIds = customerIds;
		}

	/*
		远程调用crm服务，将客户关联到定区
	
	 */
	public	String	assigncustomerstodecidedzone() {
	
		proxy.assigncustomerstodecidedzone(model.getId(), customerIds);
		return "add";
		
	}
}

package com.itheima.bos.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.service.NoticebillService;
import com.itheima.bos.web.action.base.BaseAction;
import com.itheima.crm.service.Customer;
import com.itheima.crm.service.ICustomerService;
/*
	业务通知管理

*/

@Controller
@Scope("prototype")
public class NoticebillAction extends	BaseAction<Noticebill>{
	
	@Autowired
	private	ICustomerService	iCustomerService;
	/*
		远程调用crm服务，根据手机号查询客户信息
	*/
	public	String findCustomerByTelephone(){
		
		String telephone = model.getTelephone();
		Customer customer = iCustomerService.findCustomerByTelephone(telephone);
		this.java2Json(customer,new String[]{});
		return NONE;
		
	}
	/*
		保存一个业务通知单，并尝试自动分担
	 */
	@Autowired
	private	NoticebillService noticebillService;
	public	String	add() {
		noticebillService.save(model);
		return "noticebill_add";
		
	}
}

package com.itheima.crm.service;

import java.util.List;

import javax.jws.WebService;

import com.itheima.crm.domain.Customer;
@WebService
public interface ICustomerService {
	public List<Customer> findAll();
	//查询未关联到定区的客户
	public List<Customer> findListNotAssociation();
	//查询关联到定区的客户
	public  List<Customer> findListAssociation(String ecidedzone_id);
	//定区关联客户
	public	void	assigncustomerstodecidedzone(String ecidedzone_id,Integer[]	customerIds);
	//根据客户手机号查询客户信息
	public	Customer	findCustomerByTelephone(String	telephone);
	//根据客户的地址查询定区id
	public  String		findDecidedzoneIdByAddress(String	address);
}

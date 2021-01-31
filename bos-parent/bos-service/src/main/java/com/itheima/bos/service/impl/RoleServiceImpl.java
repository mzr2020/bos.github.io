package com.itheima.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.RoleDao;
import com.itheima.bos.domain.Function;
import com.itheima.bos.domain.Role;
import com.itheima.bos.service.RoleService;
import com.itheima.bos.utils.PageBean;



@Service
@Transactional
public class RoleServiceImpl implements RoleService{

	@Autowired
	private RoleDao roledao;
	/*
		保存一个角色，并且还需要关联权限
	*/	
	public void save(Role role, String functionIds) {
		//保存一个角色
		roledao.save(role);
		//判断不为空
		if (StringUtils.isNotBlank(functionIds)) {
			//取出，并去除“”
			String[] split = functionIds.split(",");
			for (String functionId: split) {							
				
				Function function=new Function(functionId); //手动构造一个权限对象，设置id，对象状态为游离
				//角色关联权限	
				role.getFunctions().add(function);
			}
		}
	}
	
	public void pageQuery(PageBean pageBean) {
		roledao.pageQuery(pageBean);
	}

	public List<Role> findAll() {
		return roledao.findAll();
	}

}

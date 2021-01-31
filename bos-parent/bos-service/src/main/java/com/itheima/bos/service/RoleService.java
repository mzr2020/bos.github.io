package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Role;
import com.itheima.bos.utils.PageBean;

public interface RoleService {

	void save(Role role, String functionIds);

	void pageQuery(PageBean pageBean);

	List<Role> findAll();

	

}

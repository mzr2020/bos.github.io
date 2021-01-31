package com.itheima.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IUserDao;
import com.itheima.bos.domain.Role;
import com.itheima.bos.domain.User;
import com.itheima.bos.service.IUserService;
import com.itheima.bos.utils.MD5Utils;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class UserServiceImpl implements IUserService{
	@Autowired
	private IUserDao userDao;
	/***
	 * 用户登录
	 */
	public User login(User user) {
		//使用MD5加密密码
		String password = MD5Utils.md5(user.getPassword());
		return userDao.findUserByUsernameAndPassword(user.getUsername(),password);
	}
	/*
	 *根据用户id密码修改 
	*/
	public void editPassword(String id, String password) {
		//使用MD5加密密码
		password = MD5Utils.md5(password);
		userDao.executeUpdate("user.editpassword", password,id);

		
	}
	/*
	 * 添加一个用户，同时关联角色
	*/
	public void save(User user, String[] roleTds) {
		// 添加一个用户
		userDao.save(user);
		//关联角色
		if (roleTds !=null && roleTds.length >0) {
			//遍历
			for (String roleTd : roleTds) {
				//手动构造托管对象
				Role	role=new Role(roleTd);
				//用户对象关联角色
				user.getRoles().add(role);
			}
		}
		
	}
	
	public void pageQuery(PageBean pageBean) {
		userDao.pageQuery(pageBean);
	}
}

package com.itheima.bos.dao;

import com.itheima.bos.dao.base.BaseDao;
import com.itheima.bos.domain.User;

public interface IUserDao extends BaseDao<User> {

	public User findUserByUsernameAndPassword(String username, String password);

	public User findUserByUsername(String username);

}

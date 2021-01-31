package com.itheima.bos.dao;

import java.util.List;

import com.itheima.bos.dao.base.BaseDao;
import com.itheima.bos.domain.Function;

public interface FunctionDao extends BaseDao<Function> {

	List<Function> findFunctionListByUserId(String id);

	List<Function> findAllMenu();

	List<Function> findAllMenuByUserId(String id);

}

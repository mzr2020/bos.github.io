package com.itheima.bos.dao;

import java.util.List;

import com.itheima.bos.dao.base.BaseDao;
import com.itheima.bos.domain.Region;

public interface RegionDao extends	BaseDao<Region>{

	

	List<Region> findListByQ(String q);

	

}

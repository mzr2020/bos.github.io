package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Region;
import com.itheima.bos.utils.PageBean;

public interface RegionService {

	void saveBatch(List<Region> regionList);

	void pageQuery(PageBean pageBean);

	public List<Region> findAll();

	public List<Region> findListByQ(String q);

	

	

}

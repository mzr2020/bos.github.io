package com.itheima.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.RegionDao;
import com.itheima.bos.domain.Region;
import com.itheima.bos.service.RegionService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional
public class RegionServiceImpl implements RegionService {
	
	@Autowired
	private	RegionDao	regionDao;
	/*
	*	区域数据批量保存
	*/
	public void saveBatch(List<Region> regionList) {
		
		for (Region region : regionList) {
			regionDao.saveorUpdate(region);
		}
		
	}

	public void pageQuery(PageBean pageBean) {
		
		regionDao.pageQuery(pageBean);
	}

	public List<Region> findAll() {
		
		return regionDao.findAll();
	}

	
	public  List<Region> findListByQ(String q) {
		return regionDao.findListByQ(q);
	
	}

	

}

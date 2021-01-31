package com.itheima.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.DecidedzoneDao;
import com.itheima.bos.dao.SubareaDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.DecidedzoneService;
import com.itheima.bos.utils.PageBean;
@Service
@Transactional  //事务注解
public class DecidedzoneServiceImpl implements	DecidedzoneService{

	@Autowired
	private	DecidedzoneDao	decidedzoneDao;
	@Autowired
	private	SubareaDao	subareaDao;
	/*
		添加定区，同时关联定区
	
	*/
	public void save(Decidedzone model, String[] subareaid) {
		
		decidedzoneDao.save(model);
		
		for (String id : subareaid) {
			Subarea subarea = subareaDao.findById(id);
			//model.getSubareas().add(subarea);一方（定区）已经放弃维护外键权利，只能由多方（分区）负责维护
			subarea.setDecidedzone(model);//分区关联定区
		}
		
	}
	public void pageQuery(PageBean pageBean) {
		decidedzoneDao.pageQuery(pageBean);
		
	}
	
}

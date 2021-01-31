package com.itheima.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.SubareaDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Region;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.SubareaService;
import com.itheima.bos.utils.PageBean;

@Service
@Transactional
public class SubareaServiceImpl implements	SubareaService{
	@Autowired
	private	SubareaDao subareaDao;


	public void save(Subarea model) {
	
		subareaDao.save(model);
	}


	
	public void pageQuery(PageBean pageBean) {
	
		subareaDao.pageQuery(pageBean);
	}



	
	public List<Subarea> findAll() {
		
		return subareaDao.findAll();
	}
	/*
	查询所有未关联到定区的分区

	 */	
	public List<Subarea> findListNotAssociation() {
		
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Subarea.class);
		//添加过滤条件--decidedzone定区属性为空
		detachedCriteria.add(Restrictions.isNull("decidedzone"));
		
		return subareaDao.findByCriteria(detachedCriteria);
	}
	/**
	 * 根据定区id查询关联的分区
	 */
	public List<Subarea> findListByDecidedzoneId(String decidedzoneId) {
		DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Subarea.class);
		//添加过滤条件
		detachedCriteria.add(Restrictions.eq("decidedzone.id", decidedzoneId));
		return subareaDao.findByCriteria(detachedCriteria );
	}
}

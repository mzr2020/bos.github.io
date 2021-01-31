package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Decidedzone;

import com.itheima.bos.domain.Subarea;
import com.itheima.bos.utils.PageBean;

public interface SubareaService {

	void save(Subarea model);

	void pageQuery(PageBean pageBean);

	List<Subarea> findAll();

	List<Subarea> findListNotAssociation();	
	//根据定区id查询关联的分区
	public List<Subarea> findListByDecidedzoneId(String decidedzoneId);
	

	

}

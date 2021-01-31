	package com.itheima.bos.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.IStaffDao;
import com.itheima.bos.domain.Region;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.service.IStaffSerivice;
import com.itheima.bos.utils.PageBean;



@Service
@Transactional
public class IStaffSeriviceImpl implements IStaffSerivice {

	@Autowired
	private	IStaffDao	iStaffDao;

	/**
	 * 添加取派员
	 */
	public void save(Staff model) {
		
		iStaffDao.save(model);
		
	}
	/**
	 * 分页查询方法
	 * @throws IOException 
	 */
	public void  pageQuery(PageBean pageBean) {

		iStaffDao.pageQuery(pageBean);
		
	}
	/*
	*	取派员批量删除
	*	逻辑删除，将deltag改为1
	*/
	public void deleteBath(String ids) {
		//传过来的id为 1,2,3这种格式
		//更改格式
		if (StringUtils.isNotBlank(ids)) {
			
			String[] staffIds = ids.split(",");
			for (String id : staffIds) {
				
				iStaffDao.executeUpdate("staff.delete", id);
			}
		}
		
	}

	
	public Staff findById(String id) {
	
	return	iStaffDao.findById(id);	
	}
	/*
	*	根据id查询取派员
	*
	*/
	
	public void update(Staff staff) {
			iStaffDao.update(staff);
	}
	/*
	查询所以未查询的取派员

	 */

	public List<Staff> findListNotDelete() {
		
		DetachedCriteria detachedCriteria=DetachedCriteria.forClass(Staff.class);
		//添加过滤条件--- deltag="0";	//删除标识符--- 1：已删除----0：未删除
		detachedCriteria.add(Restrictions.eq("deltag", "0"));
		
		return iStaffDao.findByCriteria(detachedCriteria);
	}
	

}

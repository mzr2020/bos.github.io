package com.itheima.bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.itheima.bos.dao.RegionDao;
import com.itheima.bos.dao.base.impl.BaseDaoImpl;
import com.itheima.bos.domain.Region;
import com.itheima.bos.utils.PageBean;
@Repository
public class RegionDaoImpl extends BaseDaoImpl<Region> implements RegionDao {

	/**
	 * 根据q参数进行模糊查询
	 */
	public List<Region> findListByQ(String q) {
		
		String hql = "FROM Region r WHERE r.shortcode LIKE ? "
				+ "	OR r.citycode LIKE ? OR r.province LIKE ? "
				+ "OR r.city LIKE ? OR r.district LIKE ?";
	List<Region> list = (List<Region>) this.getHibernateTemplate().
			find(hql, "%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%","%"+q+"%");
	return list;
	
	}
}

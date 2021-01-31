package com.itheima.bos.service;

import java.util.List;

import com.itheima.bos.domain.Staff;
import com.itheima.bos.utils.PageBean;

public interface IStaffSerivice {

	void save(Staff model);

	void pageQuery(PageBean pageBean);

	void deleteBath(String ids);

	Staff findById(String id);

	void update(Staff staff);

	List<Staff> findListNotDelete();


}

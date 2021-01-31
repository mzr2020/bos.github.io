package com.itheima.bos.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.bos.dao.DecidedzoneDao;
import com.itheima.bos.dao.NoticebillDao;
import com.itheima.bos.dao.WorkbillDao;
import com.itheima.bos.domain.Decidedzone;
import com.itheima.bos.domain.Noticebill;
import com.itheima.bos.domain.Staff;
import com.itheima.bos.domain.User;
import com.itheima.bos.domain.Workbill;
import com.itheima.bos.service.NoticebillService;
import com.itheima.bos.utils.BOSUtils;
import com.itheima.crm.service.ICustomerService;

@Service
@Transactional
public class NoticebillServiceImpl implements NoticebillService {
	@Autowired
	private NoticebillDao	noticebillDao;
	@Autowired
	private	ICustomerService	icustomerService;
	@Autowired
	private	DecidedzoneDao	decidedzoneDao;
	@Autowired
	private	WorkbillDao	workbillDao;
	/*
	保存一个业务通知单，并尝试自动分担
	 */
	public void save(Noticebill model) {
		//设置当前登录用户
		User	user=BOSUtils.getLoginUser();
		model.setUser(user);
		noticebillDao.save(model);
		//-----分单------
		//获取客户取件地址
		String pickaddress = model.getPickaddress();
		//远程调用crm服务，根据取件地址查询定区id
		String decidedzoneId	= icustomerService.findDecidedzoneIdByAddress(pickaddress);
		//查询是否存在定区id，完成分单
		if (decidedzoneId !=null) {
			//自动分单
			Decidedzone decidedzone = decidedzoneDao.findById(decidedzoneId);
			Staff staff = decidedzone.getStaff();
			//业务通知单关联取派员对象
			model.setStaff(staff);
			//设置分单类型为：自动分单
			model.setOrdertype(Noticebill.ORDERTYPE_AUTO);
			//----产生工单---------
			//为取派员产生工单
			Workbill	workbill=new	Workbill();
			workbill.setAttachbilltimes(0);//追单次数
			workbill.setBuildtime(new	Timestamp(System.currentTimeMillis()));//创建数据，当前系统时间
			workbill.setNoticebill(model);;//工单关联页面通知单
			workbill.setRemark(model.getRemark());//备注信息
			workbill.setStaff(staff);//工单关联取派员
			workbill.setPickstate(workbill.PICKSTATE_NO);//取件状态
			workbill.setType(workbill.TYPE_1);//工单类型
			workbillDao.save(workbill);
		}else {
			//人工分单
			//设置分单类型为：人工分单
			model.setOrdertype(Noticebill.ORDERTYPE_MAN);
		}
	}

}

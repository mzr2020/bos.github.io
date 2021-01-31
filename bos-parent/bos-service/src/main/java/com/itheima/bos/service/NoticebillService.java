package com.itheima.bos.service;

import com.itheima.bos.domain.Noticebill;

public interface NoticebillService {
	
	//保存一个业务通知单，并尝试自动分担
 
	void save(Noticebill model);

}

package com.itheima.bos.web.interceptor;

import org.apache.struts2.components.If;

import com.itheima.bos.domain.User;
import com.itheima.bos.utils.BOSUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class BOSLoginInterceptor	extends	MethodFilterInterceptor {

	

	//拦截方法
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		
		//从session中获取用户对象
		User	user=BOSUtils.getLoginUser();
		
		//判断有没有登录
		if (user==null) {
			//跳转到登录页面
			return	"login";			
		}
		return invocation.invoke();
		
	}
	

	
}

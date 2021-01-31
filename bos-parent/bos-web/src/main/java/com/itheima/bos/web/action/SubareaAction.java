package com.itheima.bos.web.action;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.itheima.bos.domain.Region;
import com.itheima.bos.domain.Subarea;
import com.itheima.bos.service.SubareaService;
import com.itheima.bos.utils.FileUtils;
import com.itheima.bos.web.action.base.BaseAction;


@Controller
@Scope("prototype")
public class SubareaAction extends	BaseAction<Subarea>{
	@Autowired
	private	SubareaService subareaService;
	/*
		添加分区
	*/
	public	String	add(){
		
		subareaService.save(model);
		
		return LIST;
		
	}
	/*
		查询所有未关联到定区的分区，返回json
	
	*/
	public	String	listajax(){
		List<Subarea>	list=subareaService.findListNotAssociation();
		this.java2Json(list, new String[]{"decidedzone","region"}); //防止死循环
		return NONE;
		
	}
	/*
		分页查询
	*/
	
	public	String	pageQuery() {
		//开启离线查询
		DetachedCriteria	dc=pageBean.getDetachedCriteria();
		//动态添加查询条件
		//从传递过来的对象addresskey
		String addresskey = model.getAddresskey();
		/** 判断参数是否不为空.
		 * 1.如果不为空返回true。
		 * 2.如果为空返回false。*/
		if (StringUtils.isNotBlank(addresskey)) {
			//添加条件，根据地址关键词查询
			dc.add(Restrictions.like("addresskey", "%"+addresskey+"%"));
		}
		
		//从传递过来的对象取省市区
		Region region = model.getRegion();
		if (region != null) {
			//省市区
			String province = region.getProvince();
			String city = region.getCity();
			String district = region.getDistrict();
			dc.createAlias("region", "r");
			if (StringUtils.isNotBlank(province)) {
				//添加查询条件，根据省份模糊查询----多表关联查询，使用别名实现
				//参数一：分区对象中关联的区域对象属性名称
				//参数二:别名，可以任意
				
				dc.add(Restrictions.like("r.province","%"+province+"%"));
			}
			if (StringUtils.isNotBlank(city)) {
				//添加查询条件，根据市模糊查询----多表关联查询，使用别名实现
				//参数一：分区对象中关联的区域对象属性名称
				//参数二:别名，可以任意
			
				dc.add(Restrictions.like("r.city","%"+city+"%"));
			}
			if (StringUtils.isNotBlank(district)) {
				//添加查询条件，根据区模糊查询----多表关联查询，使用别名实现
				//参数一：分区对象中关联的区域对象属性名称
				//参数二:别名，可以任意
				
				dc.add(Restrictions.like("r.district","%"+district+"%"));
			}
		}
		
		subareaService.pageQuery(pageBean);
		this.java2Json(pageBean, new String[]{"currentPage","detachedCriteria","pageSize",
												"decidedzone","subareas"});
		return NONE;		
	}
	/* 
		分区数据导出功能
	*/
	public	String	exportXls() throws IOException {
		//第一步：查询所有的分区数据
		List<Subarea>	list=subareaService.findAll();
		
		//第二步：使用poi将数据写入Excel文件
		//在标签创建一个Excel文件
		HSSFWorkbook	workbook=new	HSSFWorkbook();
		//创建一个标签页
		HSSFSheet	sheet=workbook.createSheet("分区数据");
		//创建标题行
		HSSFRow	headRow=sheet.createRow(0);
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("开始编号");
		headRow.createCell(2).setCellValue("结束编号");
		headRow.createCell(3).setCellValue("位置信息");
		headRow.createCell(4).setCellValue("省市区");
		
		//遍历并传入数据
		for (Subarea subarea : list) {
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getStartnum());
			dataRow.createCell(2).setCellValue(subarea.getEndnum());
			dataRow.createCell(3).setCellValue(subarea.getPosition());
			dataRow.createCell(4).setCellValue(subarea.getRegion().getName());
			
		}
		
		//第三步：使用输出流进行文件下载（一个流、两个头）
		
				String filename = "分区数据.xls";
				String contentType = ServletActionContext.getServletContext().getMimeType(filename);
				ServletOutputStream out = ServletActionContext.getResponse().getOutputStream();
				ServletActionContext.getResponse().setContentType(contentType);
				
				//获取客户端浏览器类型
				String agent = ServletActionContext.getRequest().getHeader("User-Agent");
				filename = FileUtils.encodeDownloadFilename(filename, agent);
				ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename="+filename);
				workbook.write(out);
				return NONE;
		
	}
	/*
		根据定区id查询关联的分区
	*/	
	private	String	decidedzoneId;
	
	
	public String getDecidedzoneId() {
		return decidedzoneId;
	}
	public void setDecidedzoneId(String decidedzoneId) {
		this.decidedzoneId = decidedzoneId;
	}
	public String findListByDecidedzoneId(){
		List<Subarea> list = subareaService.findListByDecidedzoneId(decidedzoneId);
		this.java2Json(list, new String[]{"decidedzone","subareas"});
		return NONE;
	}
}

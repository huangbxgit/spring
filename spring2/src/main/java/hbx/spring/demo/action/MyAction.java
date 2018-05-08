package hbx.spring.demo.action;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hbx.spring.demo.service.IModifyService;
import hbx.spring.demo.service.IQueryService;
import hbx.spring.formework.annotation.MyAutowired;
import hbx.spring.formework.annotation.MyController;
import hbx.spring.formework.annotation.MyRequestMapping;
import hbx.spring.formework.annotation.MyRequestParam;
import hbx.spring.formework.webmvc.MyModelAndView;

/**
 * 公布接口url
 * @author Tom
 *
 */
@MyController
@MyRequestMapping("/web")
public class MyAction {

	@MyAutowired
	IQueryService queryService;
	@MyAutowired
	IModifyService modifyService;
	
	@MyRequestMapping("/query.json")
	public MyModelAndView query(HttpServletRequest request, HttpServletResponse response,
								@MyRequestParam("name") String name){
		String result = queryService.query(name);
		System.out.println(result);
		return out(response,result);
	}
	
	@MyRequestMapping("/add*.json")
	public MyModelAndView add(HttpServletRequest request, HttpServletResponse response,
							  @MyRequestParam("name") String name, @MyRequestParam("addr") String addr){
		String result = modifyService.add(name,addr);
		return out(response,result);
	}
	
	@MyRequestMapping("/remove.json")
	public MyModelAndView remove(HttpServletRequest request,HttpServletResponse response,
		   @MyRequestParam("id") Integer id){
		String result = modifyService.remove(id);
		return out(response,result);
	}
	
	@MyRequestMapping("/edit.json")
	public MyModelAndView edit(HttpServletRequest request,HttpServletResponse response,
			@MyRequestParam("id") Integer id,
			@MyRequestParam("name") String name){
		String result = modifyService.edit(id,name);
		return out(response,result);
	}
	
	
	
	private MyModelAndView out(HttpServletResponse resp,String str){
		try {
			resp.getWriter().write(str);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}

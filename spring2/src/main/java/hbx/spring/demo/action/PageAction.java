package hbx.spring.demo.action;

import java.util.HashMap;
import java.util.Map;

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
@MyRequestMapping("/")
public class PageAction {

	@MyAutowired
	IQueryService queryService;
	
	@MyRequestMapping("/first.html")
	public MyModelAndView query(@MyRequestParam("teacher") String teacher){
		String result = queryService.query(teacher);
		Map<String,Object> model = new HashMap<String,Object>();
		model.put("teacher", teacher);
		model.put("data", result);
		model.put("token", "123456");
		return new MyModelAndView("first.html",model);
	}
	
}

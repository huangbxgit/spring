package hbx.spring.demo.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import hbx.spring.demo.service.IQueryService;
import hbx.spring.formework.annotation.MyService;

/**
 * 查询业务
 * @author Tom
 *
 */
@MyService
public class QueryService implements IQueryService {

	/**
	 * 查询
	 */
	public String query(String name) {
		System.out.println("query=============");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = sdf.format(new Date());
		String json = "{name:\"" + name + "\",time:\"" + time + "\"}";
		return json;
	}

}

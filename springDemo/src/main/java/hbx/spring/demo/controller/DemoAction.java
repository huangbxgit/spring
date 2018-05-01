package hbx.spring.demo.controller;

import hbx.spring.annotation.AutoWrier;
import hbx.spring.annotation.Controller;
import hbx.spring.annotation.MapperRequest;
import hbx.spring.annotation.ParamRequest;
import hbx.spring.demo.services.IDemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@MapperRequest("/test")
public class DemoAction {
    @AutoWrier
    private IDemoService demoService;

    @ParamRequest("/login")
    public String testLogin(HttpServletRequest request, HttpServletResponse response,String name){
      //  String name = request.getParameter("name");
        System.out.println(demoService.getName(name));
        System.out.println("执行完毕");
        return "";
    }

    public IDemoService getDemoService() {
        return demoService;
    }

    public void setDemoService(IDemoService demoService) {
        System.out.println("ssssssssssssssssss");
        this.demoService = demoService;
    }
}

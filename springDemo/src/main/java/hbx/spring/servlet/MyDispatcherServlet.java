package hbx.spring.servlet;

import hbx.spring.annotation.AutoWrier;
import hbx.spring.annotation.Controller;
import hbx.spring.annotation.Services;
import hbx.spring.demo.controller.DemoAction;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public class MyDispatcherServlet extends HttpServlet {

    private Properties contextConfig = new Properties();

    private Map<String,Object> beanMap = new ConcurrentHashMap<>();

    private List<String> classNames = new ArrayList<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        //开始初始化进程

        //定位
        doConfig(config.getInitParameter("contextConfigLocation"));
        //加载
        doLoad();
        //注册
        doRegister();
        //依赖注册
        doAutoWired();

        DemoAction demo = (DemoAction) beanMap.get("demoAction");
        System.out.println(demo.testLogin(null,null,"ddddd"));
    }

    private void doAutoWired() {
        if(beanMap.isEmpty()){
            return;
        }
        for(Map.Entry<String,Object> entry:beanMap.entrySet()){
           Field[] fields =  entry.getValue().getClass().getDeclaredFields();
           for(Field field : fields){
               if(!field.isAnnotationPresent(AutoWrier.class)){
                   continue;
               }
               AutoWrier autoWrier = field.getAnnotation(AutoWrier.class);
               String beanName = field.getName();
               if(!"".equals(autoWrier.value().trim())){
                   beanName = autoWrier.value().trim();
               }

               field.setAccessible(true);
               try {
                   Object o = beanMap.get(beanName);
                   System.out.println(o);
                   field.set(entry.getValue(),o);
               } catch (IllegalAccessException e) {
                   e.printStackTrace();
               }
           }
        }
    }

    private void doRegister() {
        if(classNames.size()<1){
            return;
        }
        for(String className:classNames){
            try {
                if(className == null||"".equals(className)){
                    continue;
                }
                Class myClass = Class.forName(className);
                if(myClass.isAnnotationPresent(Controller.class)){
                    String beanName = lowFirstBeanName(myClass.getSimpleName());
                    beanMap.put(beanName,myClass.newInstance());
                }else if(myClass.isAnnotationPresent(Services.class)){
                   Services myService = (Services) myClass.getAnnotation(Services.class);
                    String beanName ;
                   //如果是自己的名称用自己的，如果不是，用默认首字母小写
                    if("".equals(myService.value())){
                        beanName = lowFirstBeanName(myClass.getSimpleName());
                    }else{
                        beanName = myService.value();
                    }
                    Object instances = myClass.newInstance();
                    beanMap.put(beanName,instances);

                    Class [] is = myClass.getInterfaces();
                    for (Class cl:is){
                        beanMap.put(cl.getName(),instances);
                    }
                }else{
                    continue;
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        System.out.println(beanMap);
    }

    private void doLoad() {
        String packageName = contextConfig.getProperty("loadPackage");
        System.out.println(packageName);
        URL url = this.getClass().getClassLoader().getResource(packageName.replaceAll("\\.","/"));
        File fileAll = new File(url.getFile());
        initClassName(fileAll,packageName);
        System.out.println(classNames);
    }

    private void initClassName(File fileAll, String packageName) {
        for(File file:fileAll.listFiles()){
            if(file.isDirectory()){
                initClassName(file, packageName+"."+file.getName());
            }else{
                classNames.add(packageName+"."+file.getName().replace(".class",""));
            }
        }
    }

    private void doConfig(String location) {

        InputStream is = this.getClass().getClassLoader().getResourceAsStream(location.replace("classpath:",""));
        try{
            contextConfig.load(is);
            System.out.println(contextConfig);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(is!=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private String lowFirstBeanName(String beanName){
        if(beanName == null ||"".equals(beanName)){
            return "";
        }
        char [] chars = beanName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}

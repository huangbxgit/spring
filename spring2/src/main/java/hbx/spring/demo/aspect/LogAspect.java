package hbx.spring.demo.aspect;

public class LogAspect {
    public void before(){
        System.out.println("before invoke ==================");
    }

    public void after(){
        System.out.println("after invoke ==================");
    }
}

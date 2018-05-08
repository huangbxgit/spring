package hbx.spring.formework.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyAopProxy implements InvocationHandler {

    private MyAopConfig config;

    private Object target;

    public void setConfig(MyAopConfig config){
        this.config = config;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method after = null;
        MyAopConfig.Aspect aspect = null;
        Method m = this.target.getClass().getMethod(method.getName(),method.getParameterTypes());
        if(config.contains(m)){
            aspect = config.getPoints(m);
            Method before = aspect.getMethods()[0];
            before.invoke(aspect.getAspect());
            after = aspect.getMethods()[1];
        }

        Object o =  m.invoke(target,args);

        if(after!=null){
            after.invoke(aspect.getAspect());
        }
        return o;
    }

    public Object getProxy(Object target){
        this.target = target;
        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(),target.getClass().getInterfaces(),this);
    }
}

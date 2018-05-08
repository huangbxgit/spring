package hbx.spring.formework.aop;

import java.lang.reflect.Field;
import java.lang.reflect.Proxy;

public class MyProxyUtils {

    public static Object getTargetObject(Object obj) throws Exception{
        if(!isProxyObject(obj)){
            return obj;
        }
        Field h = obj.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        MyAopProxy proxy = (MyAopProxy)h.get(obj);
        Field target = proxy.getClass().getDeclaredField("target");
        target.setAccessible(true);
        return target.get(proxy);
    }

    public static boolean isProxyObject(Object object){
        return Proxy.isProxyClass(object.getClass());
    }
}

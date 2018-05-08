package hbx.spring.formework.aop;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class MyAopConfig {

    private Map<Method,Aspect> points = new HashMap<>();

    public void put(Method method,Object aspect,Method[] methods){
        this.points.put(method,new Aspect(aspect,methods));
    }

    public Aspect getPoints(Method method) {
        return points.get(method);
    }

    public boolean contains(Method method){
        return points.containsKey(method);
    }

    public class Aspect{
        private Object aspect;
        private Method[]methods;

        public Aspect(Object aspect, Method[] methods) {
            this.aspect = aspect;
            this.methods = methods;
        }

        public Object getAspect() {
            return aspect;
        }

        public Method[] getMethods() {
            return methods;
        }
    }
}

package hbx.spring.formework.context;

import hbx.spring.formework.beans.BeanDefinition;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MyDefaultListableBeanFactory extends MyAbstractApplicationContext {


    //beanDefinitionMap用来保存配置信息
    protected Map<String,BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String,BeanDefinition>();

    @Override
    protected void onRefresh() {
        super.onRefresh();
    }

    @Override
    protected void refreshBeanFactory() {
        super.refreshBeanFactory();
    }
}

package test.chao.sqlsession;

import java.lang.reflect.Method;

import org.springframework.aop.support.AopUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import test.chao.base.DataSourceSelector;
import test.chao.base.SwitchDataSource;
import test.chao.sqlsession.dao.BaseDao;

/**
 * 为容器中的Dao生成代理
 * 
 * @author xiezhengchao
 * @since 2018/5/20 18:56
 */
@Component
public class BaseDaoBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (BaseDao.class.isAssignableFrom(AopUtils.getTargetClass(bean))) {
            // 采用cglib做代理,子类更灵活
            return CglibProxy.newInstance(bean);
        }
        return bean;
    }

    private static class CglibProxy implements MethodInterceptor {
        private Object target;

        CglibProxy(Object target) {
            this.target = target;
        }

        static Object newInstance(Object target) {
            CglibProxy cglibProxy = new CglibProxy(target);
            Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(target.getClass());
            enhancer.setCallback(cglibProxy);
            return enhancer.create();
        }

        @Override
        public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            SwitchDataSource switchDataSource = method.getAnnotation(SwitchDataSource.class);
            if (switchDataSource != null) {
                DataSourceSelector.set(switchDataSource.value());
            }
            return method.invoke(target, args);
        }

    }
}

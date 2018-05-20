package test.chao.mapper;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import org.mybatis.spring.mapper.MapperFactoryBean;

import test.chao.base.DataSourceSelector;

/**
 * @author xiezhengchao
 * @since 2018/5/20 15:52
 */
public class MapperFactoryBeanExt<T> extends MapperFactoryBean<T> {

    public MapperFactoryBeanExt() {
    }

    public MapperFactoryBeanExt(Class<T> mapperInterface) {
        super(mapperInterface);
    }

    @Override
    @SuppressWarnings("all")
    public T getObject() throws Exception {
        T proxy = super.getObject();
        return (T) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[] { getObjectType() },
                new MapperHandle(proxy));
    }

    private class MapperHandle implements InvocationHandler {

        private T originProxy;

        MapperHandle(T originProxy) {
            this.originProxy = originProxy;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            SwitchDataSource switchDataSource = method.getAnnotation(SwitchDataSource.class);
            if (switchDataSource != null) {
                DataSourceSelector.set(switchDataSource.value());
            }
            return method.invoke(originProxy, args);
        }
    }

}

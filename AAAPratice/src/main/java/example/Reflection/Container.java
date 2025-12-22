package example.Reflection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-0814:14
 */

public class Container {
    private Map<Class<?>, Method> methodMap;
    private Object config; //放实例
    private Map<Class<?>,Object> services; //避免一次又一次的创建对象

    public void init() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        this.methodMap = new HashMap<>();
        Class<?> clazz = Class.forName("example.Reflection.config");
        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods){
            if (method.getDeclaredAnnotation(Bean.class) != null) {
                this.methodMap.put(method.getReturnType(),method);
            }
        }
        this.config = clazz.getConstructor().newInstance();
    }

    /**
     * 获取实例
     * @param clazz
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public Object getServiceInstanceByClass(Class<?> clazz) throws InvocationTargetException, IllegalAccessException {
        this.services = new HashMap<>();
        if(this.services.containsKey(clazz)){
            return this.services.get(clazz);
        }else {
            if(this.methodMap.containsKey(clazz)){
                Method method = this.methodMap.get(clazz);
                Object obj = method.invoke(this.config);
                this.services.put(clazz,obj);
                return obj;
            }
        }
        return null;
    }

    /**
     * 通过构造器自动注入
     * @param clazz
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public Object createInstance(Class<?> clazz) throws InvocationTargetException, IllegalAccessException, InstantiationException, NoSuchMethodException {
        Constructor<?>[] constructors = clazz.getDeclaredConstructors();
        for (Constructor constructor: constructors) {
            if (constructor.getDeclaredAnnotation(Autowired.class) != null){
                Class[] parameterTypes = constructor.getParameterTypes();
                Object[] arguments = new Object[parameterTypes.length];
                for (int i = 0; i < parameterTypes.length; i++) {
                    arguments[i] = getServiceInstanceByClass(parameterTypes[i]);
                }
                return constructor.newInstance(arguments);
            }
        }
        return clazz.getConstructor().newInstance();
    }

}

package example.Reflection;

import example.entity.Printable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 功能：
 * 作者：yml
 * 日期：2024-12-0716:36
 */
public class main {

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        Container container = new Container();
        container.init();
        Class<?> clazz = Class.forName("example.entity.Order");
        Object obj = container.createInstance(clazz);
        Field field = clazz.getDeclaredField("customer");
        field.setAccessible(true);
        Object fieldValue = field.get(obj);;
        Method[] methods = fieldValue.getClass().getDeclaredMethods();
        for(Method method : methods) {
            if(method.getDeclaredAnnotation(Printable.class) != null){
                method.invoke(fieldValue);
            }
        }

    }
}

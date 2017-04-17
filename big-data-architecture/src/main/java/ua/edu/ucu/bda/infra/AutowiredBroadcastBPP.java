package ua.edu.ucu.bda.infra;

import lombok.SneakyThrows;
import org.apache.spark.api.java.JavaSparkContext;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;

/**
 * Created by Evegeny on 21/04/2016.
 */
@Component
public class AutowiredBroadcastBPP implements BeanPostProcessor {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private JavaSparkContext sparkContext;

    @Override
    @SneakyThrows
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(AutowiredBroadcast.class)) {
                ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                Class<?> typeOfBeanToInject = (Class<?>) genericType.getActualTypeArguments()[0];
                field.setAccessible(true);
                Object beanToInject = context.getBean(typeOfBeanToInject);
                field.set(bean, sparkContext.broadcast(beanToInject));
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}

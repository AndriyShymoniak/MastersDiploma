package com.nulp.shymoniak.mastersproject.spring;

import com.nulp.shymoniak.mastersproject.annotations.CrudService;
import com.nulp.shymoniak.mastersproject.mapping.AbstractMapper;
import com.nulp.shymoniak.mastersproject.repository.AbstractRepository;
import com.nulp.shymoniak.mastersproject.validation.Validator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class CrudServiceAnnotationBeanPostProcessor implements BeanPostProcessor {
    @Autowired
    private ApplicationContext context;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Class<?> beanClass = bean.getClass();
        CrudService annotation = beanClass.getAnnotation(CrudService.class);
        if (annotation != null) {
            Class<? extends Validator> validatorCls = annotation.validator();
            Class<? extends AbstractRepository> repositoryCls = annotation.repository();
            Class<? extends AbstractMapper> mapperCls = annotation.mapper();

            Object validatorBean = context.getBean(validatorCls);
            Object repositoryBean = context.getBean(repositoryCls);
            Object mapperBean = context.getBean(mapperCls);

            List<Field> fields = getAllFields(bean.getClass());
            for (Field field : fields) {
                if (field.getName().equals("validator")) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, bean, validatorBean);
                } else if (field.getName().equals("repository")) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, bean, repositoryBean);
                } else if (field.getName().equals("mapper")) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, bean, mapperBean);
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    private List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }
}

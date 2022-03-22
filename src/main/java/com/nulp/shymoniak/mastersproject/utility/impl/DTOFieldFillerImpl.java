package com.nulp.shymoniak.mastersproject.utility.impl;

import com.nulp.shymoniak.mastersproject.utility.DTOFieldFiller;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

@Component
public class DTOFieldFillerImpl implements DTOFieldFiller {
    @Override
    @SneakyThrows
    public Object fillCreateFields(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("createDate")) {
                field.setAccessible(true);
                field.set(obj, LocalDateTime.now());
            } else if (field.getName().equals("createUser")) {
                // TODO: 2/8/22  set user from session
            }
        }
        return obj;
    }

    @Override
    @SneakyThrows
    public Object fillUpdateFields(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("updateDate")) {
                field.setAccessible(true);
                field.set(obj, LocalDateTime.now());
            } else if (field.getName().equals("updateUser")) {
                // TODO: 2/8/22  set user from session
            }
        }
        return obj;
    }
}

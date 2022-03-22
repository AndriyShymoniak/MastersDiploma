package com.nulp.shymoniak.mastersproject.utility.impl;

import com.nulp.shymoniak.mastersproject.utility.DTOFieldFiller;
import com.nulp.shymoniak.mastersproject.utility.SecuritySessionUtility;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DTOFieldFillerImpl implements DTOFieldFiller {
    private final SecuritySessionUtility sessionUtility;

    @Override
    @SneakyThrows
    public Object fillCreateFields(Object obj) {
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals("createDate")) {
                field.setAccessible(true);
                field.set(obj, LocalDateTime.now());
            } else if (field.getName().equals("createUser")) {
                field.setAccessible(true);
                field.set(obj, sessionUtility.getCurrentUserFromSession());
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
                field.setAccessible(true);
                field.set(obj, sessionUtility.getCurrentUserFromSession());
            }
        }
        return obj;
    }
}

package com.example.simple_crud.common.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public abstract class BaseDto<E> implements Serializable {

    public BaseDto() {
    }

    public void copyFromDto(E t) {
        BeanUtils.copyProperties(this, t, this.getNullPropertyNames(this));
    }

    public String[] getNullPropertyNames(Object source) {
        List<String> nullValuePropertyNames = new ArrayList<>();
        Field[] var3 = source.getClass().getDeclaredFields();
        for (Field f : var3) {
            try {
                f.setAccessible(true);
                if (f.get(source) == null) {
                    nullValuePropertyNames.add(f.getName());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return nullValuePropertyNames.toArray(new String[0]);
    }

    public E fromDto() {
        try {
            Class<?> clazz = Class.forName(((Class<?>)((ParameterizedType)this.getClass().getGenericSuperclass()).getActualTypeArguments()[0]).getTypeName());
            Constructor<?> cons = clazz.getConstructor();
            E t = (E) cons.newInstance();
            BeanUtils.copyProperties(this, t);
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}

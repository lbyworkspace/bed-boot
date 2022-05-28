package com.bed.utils;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class BeanUtils {

    public <T> T CopyOne(Object source, Class<T> targetClass,String... ignoreField){
        try {
            T Target = targetClass.getDeclaredConstructor().newInstance();
            org.springframework.beans.BeanUtils.copyProperties(source,Target,ignoreField);
            return Target;
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



    public <T> T CopyMore(ArrayList<T> sources, Class<T> targetClass){
        try {
            T Target = targetClass.getDeclaredConstructor().newInstance();
            for (T source : sources) {
                org.springframework.beans.BeanUtils.copyProperties(source,Target);
            }
            return Target;
        } catch (Exception e){
            e.printStackTrace();
            throw  new RuntimeException(e);
        }
    }

    public <T> List<T> CopyList(List<?> sources, Class<T> targetClass){
        try {
            List<T> list = new ArrayList<>();
            for (Object source : sources) {
                T Target = targetClass.getDeclaredConstructor().newInstance();
                org.springframework.beans.BeanUtils.copyProperties(source,Target);
                list.add(Target);
            }
            return list;
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}

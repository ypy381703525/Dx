package com.example.mvplibrary.utils;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by dell on 2017/3/24.
 */

public class TypeUtil {
    //获取泛型
    public static <T> T getObject(Object o, int index){
        try {
            //获取指定类上面的制定下标的实例
//            Type type = ((ParameterizedType) o.getClass().getGenericSuperclass()).getActualTypeArguments()[index];
//            T t = (T) type.getClass().newInstance();

            /**
             *  Object      getClass 获取指定对象
             *              getGenericSuperclass()获取class上的泛型
             *              (ParameterizedType)强转成泛型参数
             *              getActualTypeArguments()获取class上的所有泛型（数组）
             *              (Class<T>)强转成所传泛型
             *              newInstance()获取所传泛型实例
             */
            T t = ((Class<T>) ((ParameterizedType) o.getClass().getGenericSuperclass()).getActualTypeArguments()[index]).newInstance();

            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}

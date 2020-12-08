package com.david.module.util.vendors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class ObjectUtil {


    /**
     * 获取对象里属性值为null的属性名称数组
     *
     * @param source
     * @return
     */
    public static String[] getNullPropertiesKeys(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> nullKeys = new HashSet<String>();
        for (PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                nullKeys.add(pd.getName());
            }
        }
        String[] result = new String[nullKeys.size()];
        return nullKeys.toArray(result);
    }

    /**
     * 除去source中null属性，copy source 到target
     *
     * @param source
     * @param target
     */
    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertiesKeys(source));
    }

    /**
     * 获取属性数组
     */
    public static String[] getFieldName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];
        for (int i = 0; i < fields.length; i++) {
//            System.out.println(fields[i].getType());
            fieldNames[i] = fields[i].getName();
        }
        return fieldNames;
    }

    /**
     * 根据属性名获取属性值
     *
     * @param fieldName
     * @param o
     * @return
     */
    public static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {

            return null;
        }
    }

    public static void setFieldValueByName(Object o, String fieldName, Object value) {
        if (o == null || fieldName == null) {
            return;
        }

        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String setter = "set" + firstLetter + fieldName.substring(1);

            Field field = o.getClass().getDeclaredField(fieldName);
            Class classF = field.getType();
            Method method = o.getClass().getMethod(setter, classF);
            method.invoke(o, value);

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }

    public static String convertToString(Object in) {

        StringBuilder stringBuilder = new StringBuilder();
        String[] fieldNames = ObjectUtil.getFieldName(in);
        for (String each : fieldNames) {
            Object value = null;
            try {
                value = ObjectUtil.getFieldValueByName(each, in);
            } catch (Exception ex) {
            }

            String eachString = new String("{" + each);
            if (value != null) {
                eachString += ":" + value.toString() + "}";
            } else {
                eachString += "}";
            }

            stringBuilder.append(eachString);
        }
        return stringBuilder.toString();
    }

    /**
     * 将对象属性中空白字符串都转为null
     *
     * @param o
     */
    public static void initBlankStringToNull(Object o) {
        if (o == null || o.getClass().isPrimitive() || o.getClass().isInterface()) {
            return;
        }

        String[] fields = ObjectUtil.getFieldName(o);
        for (String eachField : fields) {

            Field field = null;
            try {
                field = o.getClass().getDeclaredField(eachField);
            } catch (NoSuchFieldException ex) {
            }

            if (field != null && field.getType().isAssignableFrom(String.class)) {
                String value = (String) ObjectUtil.getFieldValueByName(eachField, o);

                boolean isAllWhitespace = value.matches("^\\s*$");
                if (value.isEmpty() || isAllWhitespace) {
                    ObjectUtil.setFieldValueByName(o, eachField, null);
                }
            }
        }
    }
}

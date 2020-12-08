package com.david.module.util.vendors;


import java.util.*;

public class ColectionUtil {

    public static <E> List<Object> getFields(Collection<E> collection, String fieldName) {

        List<Object> list = new ArrayList<Object>(collection.size());
        for (Iterator<E> iterator = collection.iterator(); iterator.hasNext(); ) {
            E element = iterator.next();
            Object object = ObjectUtil.getFieldValueByName(fieldName, element);
            String name = object.getClass().getTypeName();

            try {
                Class clazz = Class.forName(name);
                list.add(object);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * 按照fieldName 组成一个新的集合
     *
     * @param collection 集合
     * @param fieldName  collection中element某属性的名称
     * @param <E>        element类型
     * @return
     */
    public static <E> List<Object> pickByField(Collection<E> collection, String fieldName) throws BizException {

        if (fieldName == null) {
            throw new BizException("fieldName = null");
        }

        if (collection == null) {
            throw new BizException("collection = null");
        }


        List list = new ArrayList();
        for (Iterator<E> iterator = collection.iterator(); iterator.hasNext(); ) {
            E element = iterator.next();
            Object subElement = ObjectUtil.getFieldValueByName(fieldName, element);
            list.add(subElement);
        }
        return list;
    }

    /**
     * 将集合按照fieldName 变为一个map
     *
     * @param collection
     * @param fieldName  collection中element某属性的名称
     * @param <E>        collection中element类型
     * @return
     */
    public static <E> HashMap<String, E> transationToMapbyField(Collection<E> collection, String fieldName) {

        HashMap<String, E> map = new HashMap<>();
        for (Iterator<E> iterator = collection.iterator(); iterator.hasNext(); ) {
            E element = iterator.next();
            Object key = ObjectUtil.getFieldValueByName(fieldName, element);
            map.put(key.toString(), element);
        }
        return map;
    }

    /**
     * 将collection1和collection2以共有的field组织成为一个新的复合性集合
     *
     * @param collection1
     * @param collection2
     * @param fieldName
     * @param <E2>
     * @param <E1>
     * @return
     */
    public static <E2, E1> List<Composation<E1, E2>> getComposationColBy(Collection<E1> collection1, Collection<E2> collection2, String fieldName) {

        HashMap<String, E1> v1 = transationToMapbyField(collection1, fieldName);
        HashMap<String, E2> v2 = transationToMapbyField(collection2, fieldName);

        Integer max = Math.max(collection1.size(), collection2.size());

        // 组成新的list
        List<Composation<E1, E2>> list = new ArrayList<>(max);

//        for (Iterator iterator = v1.entrySet().iterator();iterator.hasNext();){
//        }


        Iterator entries = v1.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry entry = (Map.Entry) entries.next();
            String key = (String) entry.getKey();

            E1 valueInCollection1 = (E1) entry.getValue();
            E2 valueInCollection2 = v2.get(key);

            Composation<E1, E2> composation = new Composation<>();
            composation.setElement1(valueInCollection1);
            composation.setElememt2(valueInCollection2);

            list.add(composation);
        }

        return list;
    }

    public static <E, V> Map<E, V> mapComposerId(Collection<V> collection) {
        Map<E, V> map = new HashMap<E, V>(collection.size());
//        for (Iterator<V> iterator = collection.iterator(); iterator.hasNext();) {
//            V v = iterator.next();
//            map.put(c.getComposerId(v), v);
//        }
        return map;
    }


//    public static <E, V> Map<E, V> mapComposerId(Collection<V> collection, Composer<E, V> c) {
//        Map<E, V> map = new HashMap<E, V>(collection.size());
//        for (Iterator<V> iterator = collection.iterator(); iterator.hasNext();) {
//            V v = iterator.next();
//            map.put(c.getComposerId(v), v);
//        }
//        return map;
//    }
//
//    public static <E, V> List<E> getComposerIds(Collection<V> collection, Composer<E, V> c) {
//        List<E> list = new LinkedList<E>();
//        for (Iterator<V> iterator = collection.iterator(); iterator.hasNext();) {
//            V v = iterator.next();
//            list.add(c.getComposerId(v));
//        }
//
//        return list;
//    }

}

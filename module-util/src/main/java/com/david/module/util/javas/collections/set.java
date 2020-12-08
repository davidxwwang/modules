package com.david.module.util.javas.collections;

import java.util.*;

// todo  A collection that contains no duplicate elements
public class set {

    static void testTreeSet() {

        TreeSet<PersonDTO> treeSet = new TreeSet<>(new Comparator<PersonDTO>() {
            @Override
            public int compare(PersonDTO o1, PersonDTO o2) {

                if (o1.getGander() < o2.getGander()) {
                    return -1; //  -1 o1往前排
                }
                return 0;
            }
        });

        PersonDTO e = new PersonDTO();
        e.setGander(1);

        treeSet.add(e);

        boolean isOK = treeSet.add(e); // isOk = false 无法加入 set中不能加入重复的数据

    }


    static void testHashSet() {

        HashSet<PersonDTO> hashSet = new HashSet<>();
        PersonDTO e = new PersonDTO();
        e.setGander(1);

        hashSet.add(e);


    }


    public static void main(String[] args) {

        testTreeSet();

        testHashSet();


    }
}
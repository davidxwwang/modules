package com.david.module.util.lambda;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionStreams {


    static void testFilter(List<PersonDTO> personDTOS) {

        Stream<PersonDTO> personDTOStream = personDTOS.stream();
        Stream<PersonDTO> personDTOparallelStream = personDTOS.parallelStream();


        Predicate<PersonDTO> predicate = personDTO -> {
            return personDTO.getName().equals("山大王");
        };
        //personDTOStream.filter(predicate);
        // 只保留那些叫 "山大王"的person
        Stream<PersonDTO> filterPersonDTOStream = personDTOStream.filter(personDTO -> {
            return personDTO.getName().equals("山大王");
        });
        List<PersonDTO> filterPersonDTOS = filterPersonDTOStream.collect(Collectors.toList());

        System.out.print("");
    }

    static void testMap(List<PersonDTO> personDTOS) {

        Function<PersonDTO, HashMap<String, Object>> function = (a) -> {
            HashMap<String, Object> x = new HashMap<>();
            x.put("key", "valiue");
            return x;
        };

        Stream<HashMap<String, Object>> xx = personDTOS.stream().map(e -> {
            HashMap<String, Object> each = new HashMap<>();
            each.put("key1", e.getName());
            each.put("key2", e.getGander().toString());
            each.put("key3", e.getHometown());
            return each;
        });


        List<HashMap<String, Object>> yy = xx.collect(Collectors.toList());

        System.out.print("");
    }

    /**
     * 聚合操作
     *
     * @param personDTOS
     */
    static void testReduction(List<PersonDTO> personDTOS) {
//        String result = personDTOS
//                .stream()
//                .reduce("", (partialString, element) -> element.getGander().toString() + partialString);
//        System.out.print("");


        List<String> letters = Arrays.asList("a", "b", "c", "d", "e");
        String result = letters
                .stream()
                .reduce("", (partialString, element) -> partialString + element);

        // assertThat(result).isEqualTo("abcde");
    }

    /**
     * 排序
     *
     * @param personDTOS
     */
    static void testSort(List<PersonDTO> personDTOS) {


        List<PersonDTO> sortedCategoryLowestGood =
                personDTOS.stream().sorted(
                        Comparator.comparing(PersonDTO::getGander, (x, y) -> {
                            // 把 gander中是偶数的排在前面
                            return x % 2 == 0 ? -1 : 1;
                        })).collect(Collectors.toList());

        System.out.print("");
    }

    public static void main(String[] args) {

        List<PersonDTO> personDTOS = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            PersonDTO personDTO = new PersonDTO();
            personDTO.setGander(i);
            personDTO.setHometown("china");
            personDTO.setName(i % 2 == 0 ? "山大王" : "水中鱼");

            personDTOS.add(personDTO);
        }

        testSort(personDTOS);


    }


}

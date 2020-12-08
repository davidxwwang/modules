package com.david.module.util.lambda;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.*;

/**
 * 就是匿名方法
 */
public class Lambdas {

    /**
     * 过滤，判断
     */
    static void testPredicate() {
        Predicate<String> predicate = e -> (e != null);
        boolean test = predicate.test(null);

        Predicate<Integer> lessThen = i -> (i < 18);
        Predicate<Integer> bigThen = i -> (i > 10);

        // 组成一个大于10而且小于18的lambda表达式
        Predicate<Integer> lessBigPredicate = lessThen.and(bigThen);
        boolean test2 = lessBigPredicate.test(15);

        System.out.print("");

    }

    /**
     * 计算，会返回数据
     */
    static void testFunction() {


        Function<PersonDTO, HashMap<String, Object>> function0 = (a) -> {
            HashMap<String, Object> x = new HashMap<>();
            x.put("key", a.getGander());
            x.put("key2", a.getGander());
            x.put("key3", a.getName());
            return x;
        };

        PersonDTO personDTO = new PersonDTO();
        personDTO.setName("david");
        personDTO.setHometown("lanzhou");
        personDTO.setGander(1);
        HashMap<String, Object> hashMap = function0.apply(personDTO);


        BiFunction<Integer, Integer, Integer> function = (a, b) -> {
            return 10 * a + b;
        };

        // 结果是  46;
        Integer result = function.apply(4, 6);

        // 乘以100的
        Function<Integer, Integer> mul100 = a -> {
            return 100 * a;
        };
        BiFunction<Integer, Integer, Integer> function2 = function.andThen(mul100);

        // 结果是  4600
        Integer result2 = function2.apply(4, 6);


        System.out.print("");
    }

    /**
     * 计算，处理，不会返回数据
     */
    static void testConsumer() {
        BiConsumer<Integer, PersonDTO> consumer = (a, b) -> {
            System.out.print("\n性别是：" + b.getGander());
        };

        PersonDTO personDTO = new PersonDTO();
        personDTO.setGander(1);
        consumer.accept(1, personDTO);

        // 对相同的数据进行两次的操作
        BiConsumer<Integer, PersonDTO> consumerAndThen = consumer.andThen(
                (a, b) -> {
                    System.out.print("\n不要输入这样的性别标示，完全不清晰！！！" + b.getGander());
                }
        );
        PersonDTO personDTO2 = new PersonDTO();
        personDTO2.setGander(0);
        consumerAndThen.accept(0, personDTO2);


        System.out.print("");
    }

    static void testSupplier() {

        PersonDTO personDTO = new PersonDTO();
        personDTO.setGander(1);


        Supplier<List<PersonDTO>> supplier = () -> {
            return Arrays.asList(personDTO);
        };
        List<PersonDTO> personDTOS = supplier.get();

        System.out.print("");
    }

    /**
     * 聚合 stream
     */
    static void testReduce() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);

        // 结果是 21（numbers的累加和）+ reduce中的 identity值
        int result = numbers
                .stream()
                .reduce(0, (subtotal, element) -> subtotal - element);

        System.out.print("");

        //  assertThat(result).isEqualTo(21);
    }

    public static void main(String[] args) {

        testPredicate();

        testFunction();

        testConsumer();

        testSupplier();

        testReduce();

    }

}



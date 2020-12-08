package com.david.module.util.javas;

import java.util.Date;
import java.util.Optional;

public class OptionalDemo {


    public static void main(String[] args) {

        // Optional 是个容器 他的目的就是为了防止nullPointer（空指针）存在的

        Optional<String> fullName = Optional.ofNullable(null);


        // 判断是不是null
        boolean isNull = fullName.isPresent();

        // 如果fullName = null，给他一个默认值
        String defaultFullname = fullName.orElseGet(() -> "david——王");


        // 如果fullName = null，给他一个默认值
        String mappedFullname = fullName.map(s -> "Hey " + s + "!").orElse("Hey Stranger!");

        // 如果fullName存在，就执行下lambda表达式
        fullName.ifPresent(e -> {
            System.out.print("fullname = " + fullName.get());
        });


        Optional<OptinalItemDTO> itemDTO = getDP();
        itemDTO.ifPresent(e -> {

            Date date = e.getCreatedDate().orElseGet(() -> new Date());
            Integer status = e.getStatus().orElse(-1);
            // todo do something else
//            RuntimeException runtimeException = e.getStoreID().orElseThrow(e->{return new RuntimeException();});
        });

        OptinalItemDTO finalItem = itemDTO.orElseGet(() -> {
            OptinalItemDTO itemDTO1 = new OptinalItemDTO();
            return itemDTO1;
        });


    }

    static Optional<OptinalItemDTO> getDP() {

        OptinalItemDTO optinalItemDTO = new OptinalItemDTO();
        optinalItemDTO.setStatus(Optional.of(1));

        Optional optional = Optional.of(optinalItemDTO);
        return optional;
    }


}

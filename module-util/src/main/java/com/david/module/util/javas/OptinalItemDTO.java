package com.david.module.util.javas;

import lombok.Data;

import java.util.Date;
import java.util.Optional;

@Data
public class OptinalItemDTO {

    private Optional<String> storeID;

    private Optional<Integer> status;

    private Optional<Date> createdDate;
}

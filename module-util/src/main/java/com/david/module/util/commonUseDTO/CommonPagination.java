package com.david.module.util.commonUseDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@ToString
public class CommonPagination<T> {

    private static final Integer MAX_PAGE_LIMIT = 20;

    /**
     * 每页多少数据
     */
    @Setter
    @Getter
    private Integer limit;

    /**
     * 第几页
     */
    @Setter
    @Getter
    private Integer page;

    /**
     * 一共有多少数据
     */
    @Setter
    @Getter
    private Integer totalAmount;

    /**
     * 一共多少页
     */
    @Setter
    @Getter
    private Integer totalPages;

    /**
     * 数据
     */
    private List<T> dataList = new ArrayList<>();


    public CommonPagination(Integer limit, Integer page) {

        this.limit = (limit <= 0 || limit > MAX_PAGE_LIMIT) ? MAX_PAGE_LIMIT : limit;
        this.page = page <= 0 ? 0 : page;

    }


}

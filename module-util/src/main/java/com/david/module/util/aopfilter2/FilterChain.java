package com.david.module.util.aopfilter2;

import com.david.module.util.vendors.YamlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class FilterChain {

    public static final int MAXINCREAMENT = 8;

    private Integer currentExcuteFilterIndex = 0;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private List<FilterInterFace> filters = new ArrayList<>();

    /**
     * 使用yml文件初始化一个FilterChain
     *
     * @param path    yml文件路径
     * @param section 加载yml文件的section
     * @return
     */
    public static FilterChain configWithYML(String path, String section) throws FilterChainException, IllegalAccessException, InstantiationException {

        FilterChain chain = new FilterChain();


        Map<String, Object> aopFilterMap = null;
        try {
            aopFilterMap = YamlUtil.yamlToMap(path);
        } catch (Exception e) {
            e.printStackTrace();
            throw new FilterChainException();
        }

        if (aopFilterMap == null) {
            throw new FilterChainException();
        }

        List<String> classNameList = (List<String>) aopFilterMap.get(section);
        for (String className : classNameList) {
            Class clazz = null;
            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException ex) {
                throw new FilterChainException();
            }

            // 判断某类是否执行某协议
            //boolean isImplement = clazz.getInterfaces();

            FilterInterFace instance = (FilterInterFace) clazz.newInstance();
            if (instance instanceof FilterInterFace) {
                chain.filters.add(instance);
            } else {
                throw new FilterChainException();
            }
        }

        return chain;
    }

    /**
     * 执行filter
     *
     * @param context
     * @throws FilterChainException
     */
    public void execute(FilterChainContext context) throws FilterChainException {

        if (currentExcuteFilterIndex == (this.filters.size() - 1)) {
            return;
        }

        FilterInterFace filterInterFace = filters.get(currentExcuteFilterIndex);
        filterInterFace.dofilter(context, this);

        currentExcuteFilterIndex++;
    }


    private void addFilter(FilterInterFace filter) {

        for (FilterInterFace each : this.filters) {
            if (filter == each) {
                return;
            }
        }

        Integer filterArraySize = this.filters.size();
        if (currentExcuteFilterIndex == filterArraySize) {
            List<FilterInterFace> newFilters = new ArrayList<>(filterArraySize + MAXINCREAMENT);
            for (FilterInterFace eachFilter : this.filters) {
                newFilters.add(eachFilter);
            }

            newFilters.add(filter);
            this.filters = newFilters;
        }
    }

    private void release() {
        this.filters.clear();
        currentExcuteFilterIndex = 0;
    }
}

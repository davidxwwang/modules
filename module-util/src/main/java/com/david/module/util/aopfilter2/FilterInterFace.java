package com.david.module.util.aopfilter2;

public interface FilterInterFace {

    public void dofilter(FilterChainContext context, FilterChain chain) throws FilterChainException;
}

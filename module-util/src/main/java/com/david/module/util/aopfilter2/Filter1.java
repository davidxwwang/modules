package com.david.module.util.aopfilter2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Filter1 implements FilterInterFace {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void dofilter(FilterChainContext context, FilterChain chain) throws FilterChainException {

        logger.info("filter1 execute");

        chain.execute(context);

    }
}

package com.david.module.controller;

import io.netty.channel.socket.nio.NioChannelOption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.channels.FileChannel;
import java.nio.channels.SelectableChannel;
import java.util.List;

@RestController
public class HelloController {

    /**
     * 客户端
     */
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/index")
    public String index(){
        List<String> services = discoveryClient.getServices();
        return services.toString();
    }
}

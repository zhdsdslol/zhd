package com.example.my_users.models;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestTemplateConfig {

    @Bean
    @LoadBalanced//负载均衡 服务器名对应地址
    public RestTemplate restTemplate() {

        return new RestTemplate();
    }
}

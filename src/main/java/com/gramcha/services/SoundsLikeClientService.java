package com.gramcha.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * Created by gramachandran on 25/10/18.
 */
@Service
public class SoundsLikeClientService {
    @Autowired
    private LoadBalancerClient loadBalancer;


    private WebClient getClient() {
        WebClient client = null;

        ServiceInstance serviceInstance = loadBalancer.choose("sounds-like-query-service");

        System.out.println(serviceInstance.getUri());

        String baseUrl = serviceInstance.getUri().toString();
        baseUrl = baseUrl + "/";
        //baseUrl=baseUrl+"/soundslike/";
        System.out.println("Client URL = " + baseUrl);
        client = WebClient.create(baseUrl);

        return client;
    }

    public Mono<String> getSoundsLikeResult(String word) {
        System.out.println("start = "+ new Date());
        Mono<String> result = getClient().get()
                .uri("soundslike/"+word)
                .retrieve()
                .bodyToMono(String.class);
        System.out.println("end = "+ new Date());
        System.out.println("result = "+ result.toString());
        return result;
    }
}

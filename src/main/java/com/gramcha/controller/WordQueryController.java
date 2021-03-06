package com.gramcha.controller;

import com.gramcha.services.AntonymsClientService;
import com.gramcha.services.SoundsLikeClientService;
import com.gramcha.services.SynonymsClientService;
import com.netflix.discovery.converters.Auto;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by gramachandran on 25/10/18.
 */
@RestController
public class WordQueryController {
    @Autowired
    Environment environment;
    @Autowired
    SoundsLikeClientService soundsLikeClientService;

    @Autowired
    AntonymsClientService antonymsClientService;
    @Autowired
    SynonymsClientService synonymsClientService;
    String hostname;
    @PostConstruct
    void init() throws UnknownHostException {

        String port = environment.getProperty("local.server.port");
        hostname = InetAddress.getLocalHost().getHostAddress()+":"+port;
        System.out.println("hostname = "+hostname);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(path = "/synonyms/{word}")
    public Mono<String> synonyms(@PathVariable String word){
        Mono<String> result = synonymsClientService.getSynonymsResult(word);
        return result;
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(path = "/antonyms/{word}")
    public Mono<String> antonyms(@PathVariable String word){
        Mono<String> result = antonymsClientService.getAntonymsResult(word);
        return result;
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping(path = "/soundslike/{word}")
    public Mono<String> soundslike(@PathVariable String word) throws UnknownHostException {

        Mono<String> result = soundsLikeClientService.getSoundsLikeResult(word);
        return result;
//        init();
//        Mono<String> result = Mono.just(hostname+" "+"Hello, world");
//        return result;
    }

    public Mono<String> fallback(String word, Throwable hystrixCommand){
        System.out.println(hystrixCommand.toString());
        Mono<String> result = Mono.just(word);
        return result;
    }
}

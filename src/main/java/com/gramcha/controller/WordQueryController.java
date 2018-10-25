package com.gramcha.controller;

import com.gramcha.services.SoundsLikeClientService;
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

    String hostname;
    @PostConstruct
    void init() throws UnknownHostException {

        String port = environment.getProperty("local.server.port");
        hostname = InetAddress.getLocalHost().getHostAddress()+":"+port;
        System.out.println("hostname = "+hostname);
    }

    @RequestMapping(path = "/synonyms")
    public Mono<String> synonyms() throws UnknownHostException {
        init();
        Mono<String> result = Mono.just(hostname+" "+"Hello, world");
        return result;
    }

    @RequestMapping(path = "/soundslike/{word}")
    public Mono<String> soundslike(@PathVariable String word) throws UnknownHostException {
//        init();
        Mono<String> result = soundsLikeClientService.getSoundsLikeResult(word);
        return result;

//        Mono<String> result = Mono.just(hostname+" "+"Hello, world");
//        return result;
    }
}

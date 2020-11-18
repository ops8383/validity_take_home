package com.validity.monolithstarter.rest;

import com.validity.monolithstarter.service.FindDuplicateService;
import com.validity.monolithstarter.service.HelloService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.inject.Inject;

@RestController
@RequestMapping("/api")
public class HelloController {

    @Inject
    private HelloService helloService;
    @Inject
    private FindDuplicateService findDuplicateService;

    @CrossOrigin(origins = "*")
    @GetMapping("/hello")
    public String getHelloMessage() {
        return helloService.getHelloMessage();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/duplicates")
    public String getDuplicates() {
        return findDuplicateService.getDuplicates().toString();
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/deduplicates")
    public String getDeduplicates() {
        return findDuplicateService.getDeduplicates().toString();
    }

}

package com.example.taxrobot.initializer;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/admin")
public class InitializerController {
    private final InitializerService initializerService;

    public InitializerController(InitializerService initializerService) {
        this.initializerService = initializerService;
    }

    @GetMapping(path = "/init")
    public void init(){
        initializerService.init();
    }
}

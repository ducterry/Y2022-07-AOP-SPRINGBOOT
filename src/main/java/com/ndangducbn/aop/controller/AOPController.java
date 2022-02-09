package com.ndangducbn.aop.controller;

import com.ndangducbn.aop.annotation.Log;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aop/")
public class AOPController {
    @Log
    @GetMapping("/one")
    public void methodOne(String name) {

    }

    @Log
    @GetMapping("/two")
    public void methodTwo() {
    }

    @Log
    @GetMapping("/three")
    public void methodThree(String name, String age) {

    }
}

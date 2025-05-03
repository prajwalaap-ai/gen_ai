package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CalculateController {


    @GetMapping("/add")
    public int add(@RequestParam int a, @RequestParam int b) {
        Calculator calculator = new Calculator();
        return calculator.add(a, b);
    }


    @GetMapping("/subtract")
    public int subtract(@RequestParam int a, @RequestParam int b) {
        Calculator calculator = new Calculator();
        return calculator.subtract(a, b);
    }


    @GetMapping("/multiply")
    public int multiply(@RequestParam int a, @RequestParam int b) {
        Calculator calculator = new Calculator();
        return calculator.multiply(a, b);
    }


    @GetMapping("/divide")
    public int divide(@RequestParam int a, @RequestParam int b) {
        Calculator calculator = new Calculator();
        return calculator.divide(a, b);
    }




}

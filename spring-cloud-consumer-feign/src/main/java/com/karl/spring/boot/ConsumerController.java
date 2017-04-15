package com.karl.spring.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by karl on 4/11/17.
 */
@RestController
public class ConsumerController {
    @Autowired
    ComputeClient computeClient;

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public Integer add() {
        return computeClient.add(10, 20);
    }

    @RequestMapping(value = "/mul", method = RequestMethod.GET)
    public Integer mul(@RequestParam(value="a", defaultValue = "5") Integer a, @RequestParam(value="b", defaultValue = "6") Integer b) {
        return computeClient.mul(a, b);
    }

    @RequestMapping(value = "/slowCalculate", method = RequestMethod.GET)
    public Integer slowCalculate(@RequestParam(value="a", defaultValue = "5") Integer a, @RequestParam(value="b", defaultValue = "6") Integer b) {
        return computeClient.slowCalculate(a, b);
    }
}
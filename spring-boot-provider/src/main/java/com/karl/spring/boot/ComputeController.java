package com.karl.spring.boot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

/**
 * Created by karl on 4/11/17.
 */
@RestController
public class ComputeController {
    private final Logger logger = Logger.getLogger("ComputeController");
    @Autowired
    private DiscoveryClient client;

    @Autowired
    private SlowService slowService;

    @RequestMapping(value = "/add" ,method = RequestMethod.GET)
    public Integer add(@RequestParam Integer a, @RequestParam Integer b) {
        ServiceInstance instance = client.getLocalServiceInstance();
        Integer r = a + b;
        logger.info("/add, host:" + instance.getHost() + ", service_id:" + instance.getServiceId() + ", result:" + r);
        return r;
    }

    @RequestMapping(value = "/mul" ,method = RequestMethod.GET)
    public Integer mul(@RequestParam Integer a, @RequestParam Integer b) {
        ServiceInstance instance = client.getLocalServiceInstance();
        Integer r = a * b;
        logger.info("/mul, host:" + instance.getHost() + ", service_id:" + instance.getServiceId() + ", result:" + r);
        return r;
    }

    @RequestMapping(value = "/slowCalculate" ,method = RequestMethod.GET)
    public Integer slowCalculate(@RequestParam Integer a, @RequestParam Integer b) throws ExecutionException, InterruptedException {
        ServiceInstance instance = client.getLocalServiceInstance();
        Integer r = slowService.slowCalculate(a, b);
        logger.info("/slowCalculate, host:" + instance.getHost() + ", service_id:" + instance.getServiceId() + ", result:" + r);
        return r;
    }
}
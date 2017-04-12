package com.karl.spring.boot;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.concurrent.*;

/**
 * Created by karl on 4/13/17.
 */
@Service
public class SlowService implements ApplicationListener {
    private ExecutorService es;
    private ConcurrentHashMap<String, FutureTask<Integer>> request = new ConcurrentHashMap<String, FutureTask<Integer>>();

    public Integer slowCalculate(Integer a, Integer b) throws ExecutionException, InterruptedException {
        String requestParamBulk = a + "" + b;
        FutureTask<Integer> futureTask = request.get(requestParamBulk);
        if(futureTask != null){
            return futureTask.get();
        }
        CallableDemo calTask=new CallableDemo(a, b);
        FutureTask<Integer> task=new FutureTask<Integer>(calTask);
        es.submit(task);
        request.put(requestParamBulk, task);
        Integer ret = task.get();
        request.remove(requestParamBulk);
        System.out.println("remove request:"+requestParamBulk.length());
        return ret;

    }

    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        es = Executors.newSingleThreadExecutor();
    }

    private class CallableDemo implements Callable<Integer> {
        private Integer a;
        private Integer b;
        public CallableDemo(Integer a, Integer b) {
            this.a = a;
            this.b = b;
        }

        public Integer call() throws Exception {
            try {
                Thread.sleep(40000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("slow calculate...!!!!!");
            return a * b -b;
        }
    }

}

package com._4paradigm.cofing;

import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Scope("prototype")
public class AsyncTaskTestService {
 
    @Async
    public void asyncTaskTest() {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + "aaaaaaaaaaaa");
        }
    }
}


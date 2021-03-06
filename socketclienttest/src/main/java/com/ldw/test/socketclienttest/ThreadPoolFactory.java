package com.ldw.test.socketclienttest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolFactory {

    private static ThreadPoolFactory ourInstance = new ThreadPoolFactory();

    public static ThreadPoolFactory getInstance() {
        return ourInstance;
    }

    private ExecutorService threadPool;

    private ThreadPoolFactory() {
        threadPool = Executors.newCachedThreadPool();
    }

    public ExecutorService getThreadPool() {
        return threadPool;
    }

    public void execute(Runnable run) {
        threadPool.submit(run);
    }
}

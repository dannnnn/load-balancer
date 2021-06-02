package com.codeflow.load.balance.balancers;

import com.codeflow.load.balance.server.repository.ServerRepositoryImpl;
import org.junit.Test;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RoundRobinLoadBalanceTest {

    @Test
    public void getServer_concurrently_loadBalanceWorks() throws BrokenBarrierException, InterruptedException {
        ServerRepositoryImpl serverRepository = new ServerRepositoryImpl();
        serverRepository.addServer("a", 1);
        serverRepository.addServer("b", 2);
        RoundRobinLoadBalance roundRobinLoadBalance = new RoundRobinLoadBalance(serverRepository);

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
            System.out.println("All at the barrier");
        });
        Thread thread1 = new Thread(() -> {
            try {
                cyclicBarrier.await();
                System.out.println(String.format("Running %s", Thread.currentThread().getName()));
                System.out.println(roundRobinLoadBalance.getServer("any"));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }, "t1");
        Thread thread2 = new Thread(() -> {
            try {
                cyclicBarrier.await();
                System.out.println(String.format("Running %s", Thread.currentThread().getName()));
                System.out.println(roundRobinLoadBalance.getServer("any"));
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }, "t2");
        thread1.start();
        thread2.start();
        System.out.println("Start the threads");
        cyclicBarrier.await();
    }

    @Test
    public void getServers_collectResult_oneThreadWaitsTheSecondOne() throws ExecutionException, InterruptedException, BrokenBarrierException {
        ServerRepositoryImpl serverRepository = new ServerRepositoryImpl();
        serverRepository.addServer("a", 1);
        serverRepository.addServer("b", 2);
        RoundRobinLoadBalance roundRobinLoadBalance = new RoundRobinLoadBalance(serverRepository);

        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> System.out.println("All at the barrier"));
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<String> future1 = executorService.submit(() -> {
            cyclicBarrier.await();
            System.out.println(String.format("Running %s", Thread.currentThread().getName()));
            String ip = roundRobinLoadBalance.getServer("any");
            System.out.println(String.format("%s Resolved %s", Thread.currentThread().getName(), ip));
            return ip;
        });
        Future<String> future2 = executorService.submit(() -> {
            cyclicBarrier.await();
            System.out.println(String.format("Running %s", Thread.currentThread().getName()));
            String ip = roundRobinLoadBalance.getServer("any");
            System.out.println(String.format("%s Resolved %s", Thread.currentThread().getName(), ip));
            return ip;
        });

        System.out.println("Start the threads");
        cyclicBarrier.await();

//        Thread.sleep(10000);

        System.out.println(future2.get());
        System.out.println(future1.get());


    }
}

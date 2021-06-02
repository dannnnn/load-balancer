package com.codeflow.main;

import com.codeflow.load.balance.BalanceType;
import com.codeflow.load.balance.LoadBalanceService;
import com.codeflow.load.balance.LoadBalanceServiceImpl;
import com.codeflow.load.balance.ServerRepository;
import com.codeflow.load.balance.balancers.HashLoadBalance;
import com.codeflow.load.balance.balancers.RandomLoadBalance;
import com.codeflow.load.balance.balancers.RoundRobinLoadBalance;
import com.codeflow.load.balance.balancers.RoundRobinWeightLoadBalance;
import com.codeflow.load.balance.balancers.WeightRandomLoadBalance;
import com.codeflow.load.balance.server.repository.ServerRepositoryImpl;

import static com.codeflow.load.balance.BalanceType.HASH;
import static com.codeflow.load.balance.BalanceType.RANDOM;
import static com.codeflow.load.balance.BalanceType.ROUND_ROBIN;
import static com.codeflow.load.balance.BalanceType.ROUND_ROBIN_WEIGHT;
import static com.codeflow.load.balance.BalanceType.WEIGHT_RANDOM;


public class Main {

    public static void main(String[] args) throws InterruptedException {
        LoadBalanceService loadBalanceService = config();

        for (BalanceType balanceType : BalanceType.values()) {
            System.out.println(loadBalanceService.loadBalance(balanceType, "127.0.0.1"));
        }
    }

    private static LoadBalanceService config() {
        ServerRepository serverRepository = new ServerRepositoryImpl();
        serverRepository.addServer("192.168.1.100", 1);
        serverRepository.addServer("192.168.1.101", 1);
        serverRepository.addServer("192.168.1.102", 4);
        serverRepository.addServer("192.168.1.103", 1);
        serverRepository.addServer("192.168.1.104", 1);
        serverRepository.addServer("192.168.1.105", 3);
        serverRepository.addServer("192.168.1.106", 1);
        serverRepository.addServer("192.168.1.107", 2);
        serverRepository.addServer("192.168.1.108", 1);
        serverRepository.addServer("192.168.1.109", 1);
        serverRepository.addServer("192.168.1.110", 1);

        LoadBalanceService loadBalanceService = new LoadBalanceServiceImpl(serverRepository);
        loadBalanceService.registerLoadBalance(ROUND_ROBIN, new RoundRobinLoadBalance(serverRepository));
        loadBalanceService.registerLoadBalance(RANDOM, new RandomLoadBalance(serverRepository));
        loadBalanceService.registerLoadBalance(ROUND_ROBIN_WEIGHT, new RoundRobinWeightLoadBalance(serverRepository));
        loadBalanceService.registerLoadBalance(WEIGHT_RANDOM, new WeightRandomLoadBalance(serverRepository));
        loadBalanceService.registerLoadBalance(HASH, new HashLoadBalance(serverRepository));
        return loadBalanceService;
    }
}

package com.codeflow.load.balance;

import com.codeflow.load.balance.balancers.RandomLoadBalance;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LoadBalanceServiceImpl implements LoadBalanceService {

    private final Map<BalanceType, LoadBalance> loadBalanceMap = new ConcurrentHashMap<>();
    private final ServerRepository serverRepository;

    public LoadBalanceServiceImpl(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public void registerLoadBalance(BalanceType balanceType, LoadBalance loadBalance) {
        loadBalanceMap.put(balanceType, loadBalance);
    }

    @Override
    public String loadBalance(BalanceType balanceType, String clientIp) throws InterruptedException {
        System.out.println(String.format("Running %s", balanceType));
        return loadBalanceMap.getOrDefault(balanceType, new RandomLoadBalance(serverRepository)).getServer(clientIp);
    }
}

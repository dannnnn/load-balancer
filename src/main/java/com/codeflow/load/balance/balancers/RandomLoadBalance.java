package com.codeflow.load.balance.balancers;

import com.codeflow.load.balance.LoadBalance;
import com.codeflow.load.balance.ServerRepository;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomLoadBalance implements LoadBalance {
    private final ServerRepository serverRepository;

    public RandomLoadBalance(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public String getServer(String clientIp) {
        Map<String, Integer> ipMap = Map.copyOf(serverRepository.findAllServers());
        List<String> ips = List.copyOf(ipMap.keySet());
        return ips.get(new Random().nextInt(ips.size()));
    }
}

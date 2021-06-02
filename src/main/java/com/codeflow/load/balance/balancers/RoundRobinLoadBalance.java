package com.codeflow.load.balance.balancers;

import com.codeflow.load.balance.LoadBalance;
import com.codeflow.load.balance.ServerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RoundRobinLoadBalance implements LoadBalance {

    private static Integer position = 0;
    private final ServerRepository serverRepository;

    public RoundRobinLoadBalance(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public String getServer(String clientIp) throws InterruptedException {
        Map<String, Integer> ipMap = Map.copyOf(serverRepository.findAllServers());
        List<String> ips = new ArrayList<>(ipMap.keySet());
        System.out.println(String.format("%s waiting", Thread.currentThread().getName()));
        synchronized (position) {
            System.out.println(String.format("%s locked", Thread.currentThread().getName()));
            Thread.sleep(3000);
            if (position > ips.size())
                position = 0;
            return ips.get(position++);
        }
    }
}

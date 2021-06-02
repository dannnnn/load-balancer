package com.codeflow.load.balance.balancers;

import com.codeflow.load.balance.LoadBalance;
import com.codeflow.load.balance.ServerRepository;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * The advantage of source address hashing is that it ensures that the same client IP address will be hashed to the same
 * back-end server until the list of back-end servers changes. According to this feature, stateful session sessions can
 * be established between service consumers and service providers.
 */
public class RoundRobinWeightLoadBalance extends AbstractWeightLoadBalance implements LoadBalance {
    private static Integer position = 0;
    private final ServerRepository serverRepository;

    public RoundRobinWeightLoadBalance(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public String getServer(String clientIp) {
        Map<String, Integer> ipMap = Map.copyOf(serverRepository.findAllServers());
        Set<String> serverIps = ipMap.keySet();

        ArrayList<String> amplifiedServerIps = getAmplifiedIpList(ipMap, serverIps);
        synchronized (position) {
            if (position > amplifiedServerIps.size())
                position = 0;

            return amplifiedServerIps.get(position);
        }
    }
}

package com.codeflow.load.balance.balancers;

import com.codeflow.load.balance.LoadBalance;
import com.codeflow.load.balance.ServerRepository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The advantage of source address hashing is that it ensures that the same client IP address will be hashed to the same
 * back-end server until the list of back-end servers changes. According to this feature, stateful session sessions can
 * be established between service consumers and service providers.
 */
public class HashLoadBalance implements LoadBalance {

    private final ServerRepository serverRepository;

    public HashLoadBalance(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public String getServer(String ip) {
        String clientIp = Objects.requireNonNull(ip);

        Map<String, Integer> ipMap = Map.copyOf(serverRepository.findAllServers());
        List<String> ips = List.copyOf(ipMap.keySet());

        return ips.get(clientIp.hashCode() % ips.size());
    }
}

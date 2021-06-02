package com.codeflow.load.balance.server.repository;

import com.codeflow.load.balance.ServerRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServerRepositoryImpl implements ServerRepository {

    private final Map<String, Integer> ipWeightMap = new ConcurrentHashMap<>();

    @Override
    public Map<String, Integer> findAllServers() {
        return ipWeightMap;
    }

    @Override
    public void addServer(String ip, Integer weight) {
        ipWeightMap.put(ip, weight);
    }
}

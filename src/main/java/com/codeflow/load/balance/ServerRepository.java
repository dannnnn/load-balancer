package com.codeflow.load.balance;

import java.util.Map;

public interface ServerRepository {

    Map<String, Integer> findAllServers();

    void addServer(String ip, Integer weight);
}

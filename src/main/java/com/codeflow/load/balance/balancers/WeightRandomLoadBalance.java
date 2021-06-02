package com.codeflow.load.balance.balancers;

import com.codeflow.load.balance.LoadBalance;
import com.codeflow.load.balance.ServerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Similar to polling method, only a weight calculation code is added before the server address is obtained. According
 * to the weight, the address is repeatedly added to the server address list. The larger the weight, the more requests
 * the server obtains per round.
 */
public class WeightRandomLoadBalance extends AbstractWeightLoadBalance implements LoadBalance {
    private final ServerRepository serverRepository;

    public WeightRandomLoadBalance(ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    @Override
    public String getServer(String clientIp) {
        Map<String, Integer> ipMap = Map.copyOf(serverRepository.findAllServers());
        List<String> serverIps = List.copyOf(ipMap.keySet());
        ArrayList<String> amplifiedIpList = getAmplifiedIpList(ipMap, serverIps);

        return amplifiedIpList.get(new Random().nextInt(amplifiedIpList.size()));
    }

}

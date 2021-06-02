package com.codeflow.load.balance.balancers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

abstract class AbstractWeightLoadBalance {
    protected ArrayList<String> getAmplifiedIpList(Map<String, Integer> ipMap, Collection<String> serverIps) {
        Iterator<String> ipIterator = serverIps.iterator();
        ArrayList<String> serverList = new ArrayList<>();
        while (ipIterator.hasNext()) {
            String ip = ipIterator.next();
            Integer weight = ipMap.get(ip);
            for (var i = 0; i < weight; i++) {
                serverList.add(ip);
            }
        }
        return serverList;
    }
}

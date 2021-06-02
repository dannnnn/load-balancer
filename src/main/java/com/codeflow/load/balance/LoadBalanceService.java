package com.codeflow.load.balance;

public interface LoadBalanceService {

    void registerLoadBalance(BalanceType balanceType, LoadBalance loadBalance);

    String loadBalance(BalanceType balanceType, String clientIp) throws InterruptedException;
}

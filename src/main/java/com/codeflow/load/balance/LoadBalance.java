package com.codeflow.load.balance;

public interface LoadBalance {

    String getServer(String clientIp) throws InterruptedException;

}

package com.codeflow.load.balance.balancers;

import com.codeflow.load.balance.server.repository.ServerRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class HashLoadBalanceTest {

    @Test
    public void getServer_multipleTries_returnsSameServerIp() {
        ServerRepositoryImpl serverRepository = new ServerRepositoryImpl();
        serverRepository.addServer("ip1", 1);
        serverRepository.addServer("ip2", 1);
        HashLoadBalance hashLoadBalance = new HashLoadBalance(serverRepository);
        Assertions.assertThat(hashLoadBalance.getServer("a")).isEqualTo("ip1");
        Assertions.assertThat(hashLoadBalance.getServer("a")).isEqualTo("ip1");
        Assertions.assertThat(hashLoadBalance.getServer("b")).isEqualTo("ip2");
        Assertions.assertThat(hashLoadBalance.getServer("b")).isEqualTo("ip2");

    }
}

package com.gaurav.gateway.springcloudgateway.cloudconfig;


import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.BaseLoadBalancer;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.RibbonClientName;
import org.springframework.cloud.netflix.ribbon.RibbonLoadBalancerClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Random;

/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */
@Configuration
public class CustomLoadBalancerConfiguration extends AbstractLoadBalancerRule{
    private Random random;

    public CustomLoadBalancerConfiguration() {
        random = new Random();
    }

    @Override
    public Server choose(Object key) {
        ILoadBalancer loadBalancer = getLoadBalancer();
        if (loadBalancer == null) {
            return null;
        }

        List<Server> servers = loadBalancer.getReachableServers();
        if (!CollectionUtils.isEmpty(servers)) {
            int randomIndex = random.nextInt(servers.size());
            return servers.get(randomIndex);
        }

        return null;
    }


    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {

    }
}

package com.gaurav.gateway.springcloudgateway.cloudconfig;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */
@Configuration
public class RibbonConfig {
    @Bean
    public IRule ribbonRule() {
        return new CustomLoadBalancerConfiguration();
    }
}

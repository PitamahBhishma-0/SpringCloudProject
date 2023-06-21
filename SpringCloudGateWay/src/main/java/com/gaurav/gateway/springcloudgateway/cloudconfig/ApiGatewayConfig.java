package com.gaurav.gateway.springcloudgateway.cloudconfig;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.config.GatewayAutoConfiguration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author bhishma<gaurav.basyal @ fonepay.com>
 */
@Configuration
@EnableZuulProxy
@EnableDiscoveryClient
@RibbonClients(defaultConfiguration = RibbonConfig.class)
public class ApiGatewayConfig  {


    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("LOGIN-BLOG-SERVICE", r -> r.path("/auth/**")
                        .uri("lb://LOGIN-BLOG-SERVICE"))
                .route("blog-service", r -> r.path("/blog/**")
                        .uri("lb://LOGIN-BLOG-SERVICE"))
                .build();
    }


//    @Bean
//    public GatewayProperties gatewayProperties() {
//        return new GatewayProperties();
//    }
}

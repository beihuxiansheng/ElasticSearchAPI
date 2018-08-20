package com.xinhuanet.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class MyConfig {
    @Bean
    public TransportClient client() throws UnknownHostException {
        Settings settings = Settings.builder()
                .put("cluster.name", "N")
                .put("client.transport.sniff", true)    //嗅探整个集群,自动帮你添加，并且自动发现新加入集群的机器
                .build();
        TransportClient client = new PreBuiltTransportClient(settings)
                .addTransportAddress(new TransportAddress(InetAddress.getByName("10.125.9.126"), 9300));
                /*.addTransportAddress(new TransportAddress(InetAddress.getByName("10.125.9.127"), 9300))
                .addTransportAddress(new TransportAddress(InetAddress.getByName("10.125.9.128"), 9300));*/

        return client;
    }
}
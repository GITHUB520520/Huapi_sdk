package com.huapi.huapi_client_sdk;

import com.huapi.huapi_client_sdk.client.HuApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: Huapi客户端配置
 */
@Configuration
@ConfigurationProperties("huapi.client")
@Data
@ComponentScan
public class HuApiClientConfig {
    private String accessKey;
    private String secretKey;
    @Bean
    public HuApiClient huApiClient(){
        return new HuApiClient(accessKey,secretKey);
    }
}

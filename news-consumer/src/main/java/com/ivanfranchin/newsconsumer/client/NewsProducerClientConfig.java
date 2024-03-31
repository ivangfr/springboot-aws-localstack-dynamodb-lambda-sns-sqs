package com.ivanfranchin.newsconsumer.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class NewsProducerClientConfig {

    @Value("${news-producer.url}")
    private String newsProducerUrl;

    @Bean
    public NewsProducerClient newsProducerClient() {
        RestClient restClient = RestClient.builder().baseUrl(newsProducerUrl).build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        return factory.createClient(NewsProducerClient.class);
    }
}

package com.openpay.marvel.api.config;

import com.openpay.marvel.api.MarvelApi;
import com.openpay.marvel.api.impl.MarvelApiImpl;
import com.openpay.marvel.api.utils.MarvelApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Configuration
public class ApiConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiConfiguration.class);

    @Bean
    public MarvelApi instance(@Qualifier("marvel-client") RestClient restClient) {
        return new MarvelApiImpl(restClient);
    }

    @Bean("marvel-client")
    public RestClient marvelRestClient(
            @Value("${marvel.url}") String url,
            @Value("${marvel.public-key}") String publicKey,
            @Value("${marvel.private-key}") String privateKey) {

        return RestClient.builder()
                .baseUrl(url)
                .requestInterceptor((request, body, execution) -> {
                    long timestamp = System.currentTimeMillis();

                    var hash = MarvelApiUtils.computeHash(publicKey, privateKey, timestamp);

                    URI uri = UriComponentsBuilder.fromUri(request.getURI())
                            .queryParam("apikey", publicKey)
                            .queryParam("hash", hash)
                            .queryParam("ts", timestamp)
                            .build().toUri();
                    var newRequest = new HttpRequestWrapper(request) {
                        @Override
                        @NonNull
                        public URI getURI() {
                            return uri;
                        }
                    };
                    return execution.execute(newRequest, body);
                })
                .build();
    }
}

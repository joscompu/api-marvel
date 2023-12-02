package com.openpay.marvelservice.service.api.impl;

import com.openpay.marvelservice.service.api.MarvelService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

//Author: Jose Calderon
@Service
public class MarvelServiceImpl implements MarvelService {
    private final String url;
    private final String urlCharacterId;
    private RestTemplate restTemplate;

    public MarvelServiceImpl(@Value("${url}") String url, @Value("${character.id}") String urlCharacterId) {
        this.url = url;
        this.urlCharacterId = urlCharacterId;
    }

    @Override
    public String getCharacters() {
        restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, String.class);
    }

    @Override
    public String getCharacterId(Long characterId) {
        restTemplate = new RestTemplate();
        String uri = UriComponentsBuilder
                .fromUriString(urlCharacterId)
                .buildAndExpand(characterId).toUriString();
        return restTemplate.getForObject(uri, String.class);
    }
}

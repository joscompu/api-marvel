package com.openpay.marvel.api.impl;

import com.openpay.marvel.api.ApiResponse;
import com.openpay.marvel.api.MarvelApi;
import com.openpay.marvel.api.errors.CharacterNotFound;
import com.openpay.marvel.api.model.Character;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import static org.springframework.http.HttpStatus.*;

public final class MarvelApiImpl implements MarvelApi {

    private final RestClient restClient;

    public MarvelApiImpl(RestClient restClient) {
        this.restClient = restClient;
    }

    @Override
    public Character getCharacter(String id) {
        var result = restClient.get()
                .uri("/characters/{id}", Map.of("id", id))
                .retrieve()
                .onStatus(status -> status.isSameCodeAs(NOT_FOUND), (request, response) -> {
                    throw CharacterNotFound.create(id);
                })
                .body(new ParameterizedTypeReference<ApiResponse<Character>>() {
                });


        if (Objects.requireNonNull(result.data()).results().isEmpty()) {
            throw CharacterNotFound.create(id);
        }
        return result.data().results().get(0);
    }

    @Override
    public List<Character> getCharacters(List<Function<UriBuilder, UriBuilder>> filters) {
        Objects.requireNonNull(filters);
        var result = restClient.get()

                .uri("/characters", new Function<UriBuilder, URI>() {
                    @Override
                    public URI apply(UriBuilder uriBuilder) {
                        filters.forEach(filter -> filter.apply(uriBuilder));
                        return uriBuilder.build();
                    }
                })

                .retrieve()
                .body(new ParameterizedTypeReference<ApiResponse<Character>>() {
                });


        return Objects.requireNonNull(result.data()).results();
    }
}

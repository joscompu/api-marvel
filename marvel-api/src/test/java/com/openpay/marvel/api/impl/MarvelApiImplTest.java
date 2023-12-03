package com.openpay.marvel.api.impl;

import com.openpay.marvel.api.EnableMockServer;
import com.openpay.marvel.api.MarvelApi;
import com.openpay.marvel.api.errors.CharacterNotFound;
import com.openpay.marvel.api.model.Character;
import com.openpay.marvel.api.model.Comics;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockserver.client.server.MockServerClient;
import org.mockserver.model.HttpRequest;
import org.mockserver.model.HttpResponse;
import org.mockserver.model.JsonBody;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.*;

public class MarvelApiImplTest extends EnableMockServer {

    private MarvelApi api;

    @BeforeAll
    public void setupClient() {
        var restClient = RestClient.builder()
                .baseUrl(String.format("http://localhost:%s", mockServer.getPort()))
                .build();

        api = new MarvelApiImpl(restClient);
    }

    @Test
    void givenCharacterId_whenFind_thenReturnFound() {
        var id = "1010354";
        createCharacterFoundExpectation(id);
        var character = api.getCharacter(id);

        assertThat(character)
                .hasFieldOrPropertyWithValue("name", "Adam Warlock")
                .extracting(Character::comics)
                .hasFieldOrPropertyWithValue("available", 201);

    }

    @Test
    void givenCharacterId_whenFind_thenNotFound() {
        var id = "123";
        createCharacterNotFoundExpectation(id);
        assertThatThrownBy(() -> api.getCharacter(id))
                .isInstanceOf(CharacterNotFound.class);

    }

    private void createCharacterFoundExpectation(String id) {
        createExpectation()
                .when(request()
                        .withPath("/characters/" + id))
                .respond(response()
                        .withStatusCode(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(new JsonBody(getResourceContent("responses/character-by-id.json"))));
    }

    private void createCharacterNotFoundExpectation(String id) {
        createExpectation()
                .when(request()
                        .withPath("/characters/" + id))
                .respond(response()
                        .withStatusCode(404)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(new JsonBody(getResourceContent("responses/character-not-found.json"))));
    }

    @Test
    void givenListOfCharacters_thenReturnThem() {
        createCharacterListExpectation();
        var characters = api.getCharacters(List.of(
                new PaginationFilter()
        ));

        assertThat(characters)
                .hasSize(20)
                .extracting(Character::name)
                .contains("3-D Man", "Aaron Stack");
    }

    @Test
    void givenListOfCharacters_whenUseFilter_thenReturnThem() {
        createCharacterFilteredListExpectation();
        var characters = api.getCharacters(List.of(
                new PaginationFilter(2, null),
                new ComicsFilter(List.of("21366"))
        ));

        assertThat(characters)
                .hasSize(2);
    }

    private void createCharacterListExpectation() {
        createExpectation()
                .when(request()
                        .withPath("/characters")
                        .withQueryStringParameter("limit", "20"))
                .respond(response()
                        .withStatusCode(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(new JsonBody(getResourceContent("responses/characters.json"))));
    }

    private void createCharacterFilteredListExpectation() {
        createExpectation()
                .when(request()
                        .withPath("/characters")
                        .withQueryStringParameter("limit", "2")
                        .withQueryStringParameter("offset", "0")
                        .withQueryStringParameter("comics", "21366"))
                .respond(response()
                        .withStatusCode(200)
                        .withHeader("Content-Type", "application/json; charset=utf-8")
                        .withBody(new JsonBody(getResourceContent("responses/characters-filter-by-limit_and-comic.json"))));
    }
}

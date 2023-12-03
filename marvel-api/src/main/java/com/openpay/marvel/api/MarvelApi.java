package com.openpay.marvel.api;

import com.openpay.marvel.api.model.Character;
import org.springframework.web.util.UriBuilder;

import java.util.List;
import java.util.function.Function;

public interface MarvelApi {

    Character getCharacter(String id);

    List<Character> getCharacters(List<Function<UriBuilder, UriBuilder>> filters);
}

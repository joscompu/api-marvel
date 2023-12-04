package com.openpay.marvelservice.controller;

import com.openpay.marvel.api.MarvelApi;
import com.openpay.marvel.api.impl.ComicsFilter;
import com.openpay.marvel.api.impl.PaginationFilter;
import com.openpay.marvel.api.model.Character;
import com.openpay.marvelservice.audit.ExecutionLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

//Author: Jose Calderon
@RestController
@RequestMapping("/characters")
public class CharacterController {
    private final MarvelApi api;

    public CharacterController(@Autowired MarvelApi api) {
        this.api = api;
    }

    @ExecutionLog
    @GetMapping(produces = "application/json")
    public List<Character> getCharacters(@RequestParam(value = "limit", required = false) Integer limit,
                                         @RequestParam(value = "offset", required = false) Integer offset,
                                         @RequestParam(value = "comics", required = false, defaultValue = "") String comics) {

        return api.getCharacters(List.of(
                new PaginationFilter(limit, offset),
                new ComicsFilter(Arrays.asList(comics.split(",")))));
    }

    @ExecutionLog
    @GetMapping(value = "/{characterId}", produces = "application/json")
    public Character getCharactersById(@PathVariable String characterId) {
        return api.getCharacter(characterId);
    }
}

package com.openpay.marvelapi.controller;

import com.openpay.marvelapi.service.api.MarvelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Author: Jose Calderon
@RestController()
@RequestMapping("/marvel")
public class MarvelController {
    private final MarvelService marvelService;

    public MarvelController(@Autowired MarvelService marvelService) {
        this.marvelService = marvelService;
    }

    @GetMapping(value = "/characters", produces = "application/json")
    public String getCharacters() {
        return marvelService.getCharacters();
    }

    @GetMapping(value = "/characters/{characterId}", produces = "application/json")
    public String getCharactersById(@PathVariable Long characterId) {
        return marvelService.getCharacterId(characterId);
    }
}

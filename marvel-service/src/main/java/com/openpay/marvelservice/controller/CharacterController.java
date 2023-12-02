package com.openpay.marvelservice.controller;

import com.openpay.marvelservice.service.api.MarvelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Author: Jose Calderon
@RestController
@RequestMapping("/marvel")
public class CharacterController {
    private final MarvelService marvelService;

    public CharacterController(@Autowired MarvelService marvelService) {
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

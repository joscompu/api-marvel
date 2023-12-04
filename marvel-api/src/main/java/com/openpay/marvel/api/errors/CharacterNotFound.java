package com.openpay.marvel.api.errors;

public class CharacterNotFound extends RuntimeException {

    private CharacterNotFound(String id) {
        super(String.format("Character not found with id = %s", id));
    }

    public static CharacterNotFound create(String id) {
        return new CharacterNotFound(id);
    }
}

package com.openpay.marvel.api.model;

import java.net.URI;
import java.time.ZonedDateTime;

public record Character(
        String id,
        String name,
        String description,
        URI resourceURI,
        ZonedDateTime modified,

        Comics comics,
        Series series,
        Stories stories) {

    public Character(String id,
                     String name) {
        this(id, name, null, null, null, null, null, null);
    }

}


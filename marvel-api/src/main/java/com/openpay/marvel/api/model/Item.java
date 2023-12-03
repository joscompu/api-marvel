package com.openpay.marvel.api.model;

import java.net.URI;

public record Item(
        String name,
        URI resourceURI,
        String type) {
}

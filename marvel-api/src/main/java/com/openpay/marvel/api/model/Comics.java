package com.openpay.marvel.api.model;

import java.net.URI;
import java.util.List;

public record Comics(
        URI collectionURI,
        int available,
        List<Item> items) {

}

package com.openpay.marvel.api.model;

import java.net.URI;
import java.util.List;

public record Series(URI collectionURI,
                     List<Item> items) {
}

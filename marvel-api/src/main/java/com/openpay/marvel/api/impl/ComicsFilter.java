package com.openpay.marvel.api.impl;

import org.springframework.web.util.UriBuilder;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

public record ComicsFilter(List<String> comicIds) implements Function<UriBuilder, UriBuilder> {

    public ComicsFilter {
        Objects.requireNonNull(comicIds);
    }

    @Override
    public UriBuilder apply(UriBuilder uriBuilder) {
        if (comicIds.isEmpty()) return uriBuilder;

        return uriBuilder
                .queryParam("comics", comicIds.toString()
                        .replace("[", "")
                        .replace("]", ""));
    }
}

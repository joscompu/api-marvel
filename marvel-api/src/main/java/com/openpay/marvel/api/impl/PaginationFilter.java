package com.openpay.marvel.api.impl;

import org.springframework.web.util.UriBuilder;

import java.util.Objects;
import java.util.function.Function;

public record PaginationFilter(Integer limit,
                               Integer offset) implements Function<UriBuilder, UriBuilder> {

    public PaginationFilter(final Integer limit, final Integer offset) {
        this.limit = Objects.requireNonNullElse(limit, 20);
        if (this.limit > 20 || this.limit < 1) {
            throw new IllegalArgumentException(String.format("Limit %s value can not be greater than 20", limit));
        }
        this.offset = Objects.requireNonNullElse(offset, 0);
        if (this.offset < 0) {
            throw new IllegalArgumentException(String.format("Offset %s value can not be lower than 0", offset));
        }
    }

    public PaginationFilter() {
        this(20, 0);
    }

    @Override
    public UriBuilder apply(UriBuilder uriBuilder) {
        return uriBuilder
                .queryParam("limit", limit)
                .queryParam("offset", offset);
    }
}

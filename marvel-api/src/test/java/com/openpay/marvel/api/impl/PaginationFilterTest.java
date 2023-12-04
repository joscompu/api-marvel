package com.openpay.marvel.api.impl;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PaginationFilterTest {

    @Test()
    public void givenInvalidValues_thenThrownException() {

        assertThatThrownBy(() -> new PaginationFilter(10, -1))
                .isInstanceOf(RuntimeException.class);

        assertThatThrownBy(() -> new PaginationFilter(0, 10))
                .isInstanceOf(RuntimeException.class);

        assertThatThrownBy(() -> new PaginationFilter(10, -1));
    }
}

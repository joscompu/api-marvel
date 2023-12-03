package com.openpay.marvel.api.utils;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MarvelApiUtilsTest {

    @Test
    void testComputedHash() {
        var publicKey = "1fec86b2e8744db4da3cfb57ffe07969";
        var privateKey = "693ebc8fa7fdcfff175337bd567b45ae19f31c7a";
        var timestamp = 1L;

        var expectedHash = "612428678a6b20e756b48c5cd0124c3a";

        var hash = MarvelApiUtils.computeHash(publicKey, privateKey, timestamp);

        assertThat(hash).isEqualTo(expectedHash);
    }
}

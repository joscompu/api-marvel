package com.openpay.marvel.api.utils;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

public final class MarvelApiUtils {

    private MarvelApiUtils() {
    }

    public static String computeHash(
            String publicKey,
            String privateKey,
            long timestamp) {
        return DigestUtils.md5DigestAsHex(String.format("%d%s%s", timestamp, privateKey, publicKey).getBytes(StandardCharsets.UTF_8));
    }
}

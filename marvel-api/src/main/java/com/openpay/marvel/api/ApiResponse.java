package com.openpay.marvel.api;

public record ApiResponse<T>(String code,
                             String status,
                             String copyright,
                             String attributionText,
                             String attributionHTML,
                             String etag,
                             ApiResult<T> data) {
}

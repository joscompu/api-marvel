package com.openpay.marvel.api;

import java.util.List;

public record ApiResult<T>(int offset,
                           int limit,
                           int total,
                           int count,
                           List<T> results) {
}

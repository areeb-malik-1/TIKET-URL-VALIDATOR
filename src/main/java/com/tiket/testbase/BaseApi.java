package com.tiket.testbase;

import com.fasterxml.jackson.databind.JsonNode;
import com.tiket.model.ApiResult;

import java.net.http.HttpResponse;

public interface BaseApi {
    ApiResult hitApi() throws Exception;

    default void isSuccess(HttpResponse<?> response, JsonNode data) {
        int statusCode = response.statusCode();
        assert (statusCode >= 200 && statusCode < 300);
        assert data != null;
    }
}

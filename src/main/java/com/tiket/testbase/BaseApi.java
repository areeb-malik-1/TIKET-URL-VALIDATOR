package com.tiket.testbase;

import com.tiket.model.ApiResult;

public interface BaseApi {
    ApiResult hitApi() throws Exception;

    default void isSuccess(int statusCode) {
        assert (statusCode >= 200 && statusCode < 300);
    }
}

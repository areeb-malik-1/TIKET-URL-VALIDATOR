package com.tiket.testbase;

import com.tiket.model.ApiResult;

import static org.hamcrest.core.Is.is;

public interface BaseApi {
    ApiResult hitApi() throws Exception;

    default void isSuccess(int statusCode) {
        Assertion.assertThat("Api statusCode", statusCode >= 200 && statusCode < 300, is(true));
    }
}

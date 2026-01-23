package com.tiket.testbase;

import com.fasterxml.jackson.databind.JsonNode;
import com.tiket.model.ApiResult;
import org.apache.logging.log4j.Logger;

import java.net.http.HttpResponse;

import static com.tiket.testbase.BaseTest.NON_WORKING_APIS;

public interface BaseApi {

    Logger logger = org.apache.logging.log4j.LogManager.getLogger(BaseApi.class);

    ApiResult hitApi() throws Exception;

    default void isSuccess(HttpResponse<?> response, JsonNode data) {
        int statusCode = response.statusCode();
        boolean ok = statusCode >= 200 && statusCode < 300;
        if(data == null) {
            logger.debug("Base Api Failed, Response Status Code: {}, for API: {}, requestUrl: {}", statusCode, this.getClass().getSimpleName(), response.request().uri());
            NON_WORKING_APIS.add(this.getClass().getSimpleName());
        }
        assert ok && data != null;
    }
}

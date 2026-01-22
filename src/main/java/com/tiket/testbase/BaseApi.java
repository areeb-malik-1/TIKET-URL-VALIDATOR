package com.tiket.testbase;

import com.fasterxml.jackson.databind.JsonNode;
import com.tiket.model.ApiResult;
import org.apache.logging.log4j.Logger;

import java.net.http.HttpResponse;

public interface BaseApi {

    Logger logger = org.apache.logging.log4j.LogManager.getLogger(BaseApi.class);

    ApiResult hitApi() throws Exception;

    default void isSuccess(HttpResponse<?> response, JsonNode data) {
        int statusCode = response.statusCode();
        boolean ok = statusCode >= 200 && statusCode < 300;
        if(!ok || data == null) {
            logger.debug("Base Api Failed, Response Status Code: {}, for API: {}, requestUrl: {}", statusCode, this.getClass().getSimpleName(), response.request().uri());
        }
        assert ok && data != null;
    }
}

package com.tiket.testbase;

import com.fasterxml.jackson.databind.JsonNode;
import com.tiket.core.CurlBuilder;
import com.tiket.model.ApiResult;
import com.tiket.model.Platform;
import org.apache.logging.log4j.Logger;

import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static com.tiket.testbase.BaseTest.NON_WORKING_APIS;

public interface BaseApi {

    Logger logger = org.apache.logging.log4j.LogManager.getLogger(BaseApi.class);
    Platform platform = Platform.parse(System.getProperty("platform"));

    ApiResult hitApi() throws Exception;

    default void isSuccess(HttpRequest request, HttpResponse<?> response, JsonNode data) {
        isSuccess(request, response, data, null);
    }

    default void isSuccess(HttpRequest request, HttpResponse<?> response, JsonNode data, String requestBody) {
        int statusCode = response.statusCode();
        boolean ok = statusCode >= 200 && statusCode < 300;
        if(!ok || data == null) {
            logger.debug("Base Api Failed, Response Status Code: {}, for API: {}, requestUrl: {}, is data null?: {}, curl: {}", statusCode, this.getClass().getSimpleName(), response.request().uri(), data == null, CurlBuilder.toCurl(request, response, requestBody));
            NON_WORKING_APIS.add(this.getClass().getSimpleName());
        }
        assert ok && data != null;
    }
}

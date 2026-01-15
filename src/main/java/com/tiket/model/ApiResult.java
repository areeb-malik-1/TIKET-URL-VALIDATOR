package com.tiket.model;

import com.fasterxml.jackson.databind.JsonNode;

public record ApiResult(JsonNode data, int status) {}

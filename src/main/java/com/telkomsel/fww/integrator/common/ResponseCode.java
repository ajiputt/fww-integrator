package com.telkomsel.fww.integrator.common;

public enum ResponseCode {
    SUCCESS("00", "SUCCESS"),
    SUCCESS_CREATE("01", "CREATION WAS SUCCESS"),
    ERROR_CONNECTION_FEIGN("02", "CONNECTION ERROR"),
    ERROR_VALIDATION("03", "VALIDATION ERROR"),
    ERROR_PARAMETER("04", "MISSING PARAMETER"),
    ERROR_METHOD("05", "METHOD NOT ALLOWED"),
    ERROR_GENERAL("05", "INTERNAL SERVER ERROR"),
    ERROR_PASSENGER("06", "PASSENGER DOES NOT MEET REQUIREMENT");

    private final String code;
    private final String description;


    ResponseCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}

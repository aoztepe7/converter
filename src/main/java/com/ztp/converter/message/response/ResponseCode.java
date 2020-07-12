package com.ztp.converter.message.response;

import lombok.Getter;

@Getter
public enum ResponseCode {

    SUCCESS(200, "SUCCESS"),
    HOME_PAGE_URL_ERROR(202, "WEB LINK NOT CONTAIN HOME PAGE URL"),
    UNDEFINED_LINK_ERROR(203, "UNDEFINED LINK ERROR"),

    LINK_NOT_FOUND(301, "LINK NOT FOUND"),

    UNDEFINED_ERROR(999, "UNDEFINED ERROR");

    private Integer code;
    private String message;

    ResponseCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}

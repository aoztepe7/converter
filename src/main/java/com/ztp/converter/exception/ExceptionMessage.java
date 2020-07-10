package com.ztp.converter.exception;

import com.ztp.converter.message.response.ResponseCode;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.TYPE)
@Retention(RUNTIME)
public @interface ExceptionMessage {
    ResponseCode responseCode() default ResponseCode.UNDEFINED_ERROR;
}

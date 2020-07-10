package com.ztp.converter.handler;

@FunctionalInterface
public interface Handler<T, R> {
    R execute(T request);
}

package com.ztp.converter.exception;


import com.ztp.converter.message.response.ResponseCode;

@ExceptionMessage(responseCode = ResponseCode.LINK_NOT_FOUND)
public class LinkNotFoundException extends DomainException {
}

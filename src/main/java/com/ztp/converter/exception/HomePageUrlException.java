package com.ztp.converter.exception;


import com.ztp.converter.message.response.ResponseCode;

@ExceptionMessage(responseCode = ResponseCode.HOME_PAGE_URL_ERROR)
public class HomePageUrlException extends DomainException {
}

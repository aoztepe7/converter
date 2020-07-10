package com.ztp.converter.message.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommonResponse extends BaseResponse {

    private String deepLink;

    @Builder
    public CommonResponse(ResponseCode responseCode, String deepLink) {
        super(responseCode);
        this.deepLink = deepLink;
    }
}

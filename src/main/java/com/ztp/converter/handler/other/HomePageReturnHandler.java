package com.ztp.converter.handler.other;

import com.ztp.converter.domain.Type;
import com.ztp.converter.domain.link.Link;
import com.ztp.converter.domain.link.LinkService;
import com.ztp.converter.exception.LinkNotFoundException;
import com.ztp.converter.handler.Handler;
import com.ztp.converter.message.response.CommonResponse;
import com.ztp.converter.message.response.ResponseCode;
import com.ztp.converter.message.request.LinkCreateCommand;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class HomePageReturnHandler implements Handler<LinkCreateCommand, CommonResponse> {

    private final LinkService linkService;
    private static final String BASE_DEEP_LINK = "ty://?Page=Home";

    @Override
    public CommonResponse execute(LinkCreateCommand request) {
        try {
            Link link = linkService.getByWebLink(request.getWebLink());

            return CommonResponse.builder()
                    .deepLink(link.getDeepLink())
                    .responseCode(ResponseCode.SUCCESS)
                    .build();

        } catch (LinkNotFoundException exception) {
            Link link = homePageReturnerFiller(request.getWebLink());
            linkService.create(link);

            return CommonResponse.builder()
                    .deepLink(link.getDeepLink())
                    .responseCode(ResponseCode.SUCCESS)
                    .build();
        }
    }

    private Link homePageReturnerFiller(String webLink) {
        Link link = new Link();
        link.setWebLink(webLink);
        link.setDeepLink(BASE_DEEP_LINK);
        link.setType(Type.HOME_PAGE);
        return link;
    }
}

package com.ztp.converter.handler.search;

import com.ztp.converter.domain.Type;
import com.ztp.converter.domain.link.Link;
import com.ztp.converter.domain.link.LinkService;
import com.ztp.converter.exception.LinkNotFoundException;
import com.ztp.converter.handler.Handler;
import com.ztp.converter.message.response.CommonResponse;
import com.ztp.converter.message.response.ResponseCode;
import com.ztp.converter.message.request.LinkCreateCommand;
import com.ztp.converter.utils.Helper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SearchLinkCreateHandler implements Handler<LinkCreateCommand, CommonResponse> {

    private final LinkService linkService;
    private final Helper helper;

    @Override
    public CommonResponse execute(LinkCreateCommand request) {
        try {
            linkService.getByWebLink(request.getWebLink().toLowerCase());

            return CommonResponse.builder()
                    .deepLink(helper.searchDeepLinkCreator(request.getWebLink()))
                    .responseCode(ResponseCode.SUCCESS)
                    .build();

        } catch (LinkNotFoundException exception) {
            Link link = searchFiller(request.getWebLink());
            linkService.create(link);

            return CommonResponse.builder()
                    .deepLink(helper.searchDeepLinkCreator(request.getWebLink()))
                    .responseCode(ResponseCode.SUCCESS)
                    .build();
        }
    }

    private Link searchFiller(String webLink) {
        Link link = new Link();
        link.setWebLink(webLink.toLowerCase());
        link.setDeepLink(helper.searchDeepLinkCreator(webLink));
        link.setType(Type.SEARCH);
        return link;
    }
}

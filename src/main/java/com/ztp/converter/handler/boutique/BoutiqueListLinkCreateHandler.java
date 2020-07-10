package com.ztp.converter.handler.boutique;

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
public class BoutiqueListLinkCreateHandler implements Handler<LinkCreateCommand, CommonResponse> {

    private final LinkService linkService;
    private final Helper helper;

    @Override
    public CommonResponse execute(LinkCreateCommand request) {
        try {
            Link link = linkService.getByWebLink(request.getWebLink());

            return CommonResponse.builder()
                    .deepLink(link.getDeepLink())
                    .responseCode(ResponseCode.SUCCESS)
                    .build();

        } catch (LinkNotFoundException exception) {
            Link link = boutiqueListFiller(request.getWebLink());
            linkService.create(link);

            return CommonResponse.builder()
                    .deepLink(link.getDeepLink())
                    .responseCode(ResponseCode.SUCCESS)
                    .build();
        }
    }

    private Link boutiqueListFiller(String webLink) {
        Link link = new Link();
        link.setWebLink(webLink);
        Long lastSectionId = linkService.getLastSectionId();
        if (lastSectionId != null) {
            lastSectionId++;
        } else {
            lastSectionId = 1L;
        }
        link.setSectionId(lastSectionId);
        link.setDeepLink(helper.boutiqueDeepLinkCreator(lastSectionId));
        link.setType(Type.BOUTIQUE);
        return link;
    }
}

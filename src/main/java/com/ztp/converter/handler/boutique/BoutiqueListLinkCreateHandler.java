package com.ztp.converter.handler.boutique;

import com.ztp.converter.config.UrlConfig;
import com.ztp.converter.domain.Type;
import com.ztp.converter.domain.link.Link;
import com.ztp.converter.domain.link.LinkService;
import com.ztp.converter.exception.LinkNotFoundException;
import com.ztp.converter.handler.Handler;
import com.ztp.converter.message.response.CommonResponse;
import com.ztp.converter.message.response.ResponseCode;
import com.ztp.converter.message.request.LinkCreateCommand;
import com.ztp.converter.utils.Helper;
import com.ztp.converter.utils.SectionMapService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@AllArgsConstructor
@Component
public class BoutiqueListLinkCreateHandler implements Handler<LinkCreateCommand, CommonResponse> {

    private final SectionMapService sectionMapService;
    private final LinkService linkService;
    private final Helper helper;
    private final UrlConfig urlConfig;

    @Override
    public CommonResponse execute(LinkCreateCommand request) {
        String sectionName = sectionNameSplitter(request.getWebLink());
        String sectionId = sectionMapService.getValue(sectionName);
        if(!StringUtils.isEmpty(sectionId)) {
            try {
                Link link = linkService.getBySectionName(sectionName);

                return CommonResponse.builder()
                        .deepLink(link.getDeepLink())
                        .responseCode(ResponseCode.SUCCESS)
                        .build();

            } catch (LinkNotFoundException exception) {
                Link link = boutiqueListObjectFiller(request.getWebLink(),sectionName,sectionId);
                linkService.create(link);

                return CommonResponse.builder()
                        .deepLink(link.getDeepLink())
                        .responseCode(ResponseCode.SUCCESS)
                        .build();
            }
        } else {
            return CommonResponse.builder()
                    .deepLink(urlConfig.getDeepHomePage())
                    .responseCode(ResponseCode.SUCCESS)
                    .build();
        }

    }

    private Link boutiqueListObjectFiller(String webLink,String sectionName,String sectionId) {
        Link link = new Link();
        link.setWebLink(webLink);
        link.setSectionName(sectionName);
        link.setDeepLink(helper.boutiqueDeepLinkCreator(sectionId));
        link.setType(Type.BOUTIQUE);
        link.setIsValid(true);
        return link;
    }

    private String sectionNameSplitter(String webLink){
        return webLink.replace(urlConfig.getBaseWebBoutiqueLink(),"");
    }
}

package com.ztp.converter.handler.detail;

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

import java.util.HashMap;

@AllArgsConstructor
@Component
public class ProductDetailLinkCreateHandler implements Handler<LinkCreateCommand, CommonResponse> {

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
            Link link = productDetailFiller(request.getWebLink());
            linkService.create(link);

            return CommonResponse.builder()
                    .deepLink(link.getDeepLink())
                    .responseCode(ResponseCode.SUCCESS)
                    .build();
        }
    }

    private Link productDetailFiller(String webLink) {
        Link link = new Link();
        link.setWebLink(webLink);
        HashMap<String, String> parsedMap = helper.productDetailLinkParser(webLink);
        link.setBrandOrCategoryName(parsedMap.get("brandOrCategoryName"));
        link.setProductName(parsedMap.get("productName"));
        link.setContentId(Long.parseLong(parsedMap.get("contentId")));
        if (!parsedMap.get("boutiqueId").isBlank()) {
            link.setBoutiqueId(Long.parseLong(parsedMap.get("boutiqueId")));
        } else {
            link.setBoutiqueId(null);
        }
        if (!parsedMap.get("merchantId").isBlank()) {
            link.setMerchantId(Long.parseLong(parsedMap.get("merchantId")));
        } else {
            link.setMerchantId(null);
        }
        link.setDeepLink(helper.productDetailDeepLinkCreator(link));
        link.setType(Type.PRODUCT_DETAIL);
        link.setIsValid(true);
        return link;
    }
}

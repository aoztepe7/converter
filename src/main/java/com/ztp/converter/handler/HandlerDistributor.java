package com.ztp.converter.handler;

import com.ztp.converter.exception.HomePageUrlException;
import com.ztp.converter.handler.boutique.BoutiqueListLinkCreateHandler;
import com.ztp.converter.handler.detail.ProductDetailLinkCreateHandler;
import com.ztp.converter.handler.other.HomePageReturnHandler;
import com.ztp.converter.handler.search.SearchLinkCreateHandler;
import com.ztp.converter.message.response.CommonResponse;
import com.ztp.converter.message.request.LinkCreateCommand;
import com.ztp.converter.utils.DistributorRuler;
import com.ztp.converter.utils.Helper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class HandlerDistributor {

    private final BoutiqueListLinkCreateHandler boutiqueListLinkCreateHandler;
    private final ProductDetailLinkCreateHandler productDetailLinkCreateHandler;
    private final SearchLinkCreateHandler searchLinkCreateHandler;
    private final HomePageReturnHandler homePageReturnHandler;
    private final Helper helper;
    private final DistributorRuler distributorRuler;

    public CommonResponse execute(LinkCreateCommand createCommand) {
        if (!helper.homePageChecker(createCommand.getWebLink())) {
            throw new HomePageUrlException();
        }

        if (distributorRuler.isSearchLink(createCommand.getWebLink())) {
            return searchLinkCreateHandler.execute(createCommand);
        }

        createCommand.setWebLink(helper.usLocaleConverter(createCommand.getWebLink()));

        if (distributorRuler.isBoutiqueLink(createCommand.getWebLink())) {
            return boutiqueListLinkCreateHandler.execute(createCommand);
        }

        if (distributorRuler.isProductDetailLink(createCommand.getWebLink())) {
            return productDetailLinkCreateHandler.execute(createCommand);
        } else {
            return homePageReturnHandler.execute(createCommand);
        }
    }

}

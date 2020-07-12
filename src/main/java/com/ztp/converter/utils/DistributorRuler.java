package com.ztp.converter.utils;

import com.ztp.converter.config.PrefixConfig;
import com.ztp.converter.config.UrlConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class DistributorRuler {

    private final UrlConfig urlConfig;
    private final PrefixConfig prefixConfig;

    public boolean isBoutiqueLink(String webLink) {
        return homePageEraser(webLink).contains(prefixConfig.getBoutiquePrefix());
    }

    public boolean isProductDetailLink(String webLink) {
        return homePageEraser(webLink).contains(prefixConfig.getProductDetailPrefix());
    }

    public boolean isSearchLink(String webLink) {
        return homePageEraser(webLink).contains(prefixConfig.getSearchPrefix());
    }

    public String homePageEraser(String webLink) {
        return webLink.replace(urlConfig.getWebHomePage(), "");
    }


}

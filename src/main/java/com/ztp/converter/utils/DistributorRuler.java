package com.ztp.converter.utils;

import org.springframework.stereotype.Component;

@Component
public class DistributorRuler {

    private static final String HOME_PAGE = "https://www.trendyol.com";

    public boolean isBoutiqueLink(String webLink) { // TODO isBoutiqueLİnk olabilir mi
        return homePageEraser(webLink).contains("butik/liste");
    }

    public boolean isProductDetailLink(String webLink) {
        return homePageEraser(webLink).contains("-p-");
    }

    public boolean isSearchLink(String webLink) {
        return homePageEraser(webLink).contains("tum--urunler");
    }

    public String homePageEraser(String webLink) {
        return webLink.replace(HOME_PAGE, "");
    }


}

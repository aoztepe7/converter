package com.ztp.converter.utils;

import com.ztp.converter.domain.link.Link;
import org.springframework.stereotype.Component;

import java.text.Normalizer;
import java.util.HashMap;
import java.util.Locale;

@Component
public class Helper {

    private static final String BASE_URL = "https://www.trendyol.com";
    private static final String BASE_DEEP_LINK_WITH_SECTION = "ty://?Page=Home&SectionId=";
    private static final String BASE_SEARCH_DEEP_LINK = "ty://?Page=Search&Query=";
    private static final String BASE_SEARCH_WEB_LINK = "https://www.trendyol.com/tum--urunler?q=";
    private static final String PRODUCT_DETAIL_BASE_DEEP_LINK = "ty://?Page=Product&ContentId=";

    public boolean homePageChecker(String webLink) {
        return webLink.startsWith(BASE_URL);
    }

    public String usLocaleConverter(String name) {
        String normalizer = Normalizer.normalize(name, Normalizer.Form.NFD);
        normalizer = normalizer.replaceAll("[^\\p{ASCII}]", "");
        normalizer = normalizer.replaceAll("\\p{M}", "");
        normalizer = normalizer.toLowerCase(Locale.ENGLISH);
        return normalizer;
    }

    public String productDetailDeepLinkCreator(Link link) {
        String contentFilledLink = String.format("%s%s", PRODUCT_DETAIL_BASE_DEEP_LINK, link.getContentId().toString());
        if (link.getBoutiqueId() != null) {
            contentFilledLink = String.format("%s&%s%s", contentFilledLink, "CampaignId=", link.getBoutiqueId().toString());
        }
        if (link.getMerchantId() != null) {
            contentFilledLink = String.format("%s&%s%s", contentFilledLink, "MerchantId=", link.getMerchantId());
        }
        return contentFilledLink;
    }

    public String boutiqueDeepLinkCreator(Long id) {
        return String.format("%s%s", BASE_DEEP_LINK_WITH_SECTION, id);
    }

    public String searchDeepLinkCreator(String webLink) {
        String baseEraser = webLink.replace(BASE_SEARCH_WEB_LINK, "");
        return String.format("%s%s", BASE_SEARCH_DEEP_LINK, usLocaleConverterWithOutLowerCase(baseEraser));
    }

    public String usLocaleConverterWithOutLowerCase(String name) {
        String normalizer = Normalizer.normalize(name, Normalizer.Form.NFD);
        normalizer = normalizer.replaceAll("[^\\p{ASCII}]", "");
        normalizer = normalizer.replaceAll("\\p{M}", "");
        return normalizer;
    }

    public HashMap<String, String> productDetailLinkParser(String fullLink) {
        HashMap<String, String> parsedMap = new HashMap<>();
        String eraseBaseLink = fullLink.replace(BASE_URL + "/", "");
        String brandOrCategoryName = eraseBaseLink.split("/", 2)[0];
        String productName = eraseBaseLink.split("/", 3)[1].split("-p-")[0];
        String afterPChar = eraseBaseLink.split("-p-")[1];
        String contentId = "";
        String boutiqueId = "";
        String merchantId = "";
        if (afterPChar.contains("?")) {
            contentId = afterPChar.split("\\?(?!\\?)")[0];
            if (afterPChar.contains("&")) {
                String[] boutiqueOrMerchantId = afterPChar.replace(contentId + "?", "").split("&");
                if (boutiqueOrMerchantId[0].contains("boutiqueid")) {
                    boutiqueId = boutiqueOrMerchantId[0].replace("boutiqueid=", "");
                }
                if (boutiqueOrMerchantId[1].contains("merchantid")) {
                    merchantId = boutiqueOrMerchantId[1].replace("merchantid=", "");
                }
            } else {
                String boutiqueOrMerchantId = afterPChar.replace(contentId + "?", "");
                if (boutiqueOrMerchantId.contains("merchantid")) {
                    merchantId = boutiqueOrMerchantId.replace("merchantid=", "");
                }
                if (boutiqueOrMerchantId.contains("boutiqueid")) {
                    boutiqueId = boutiqueOrMerchantId.replace("boutiqueid=", "");
                }
            }
        } else {
            contentId = afterPChar.split("\\?(?!\\?)")[0];
        }
        parsedMap.put("brandOrCategoryName", brandOrCategoryName);
        parsedMap.put("productName", productName);
        parsedMap.put("contentId", contentId);
        parsedMap.put("boutiqueId", boutiqueId);
        parsedMap.put("merchantId", merchantId);
        return parsedMap;
    }
}

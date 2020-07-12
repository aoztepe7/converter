package com.ztp.converter.utils;

import com.ztp.converter.config.UrlConfig;
import com.ztp.converter.domain.link.Link;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

import java.util.HashMap;

@RunWith(MockitoJUnitRunner.class)
public class HelperTest {

    @InjectMocks
    private Helper helper;

    @Mock
    private UrlConfig urlConfig;

    @Test
    public void homePageChecker_ShouldReturnTrue_WhenLinkStartsWithHomePageURL() {
        //arrange
        String webLink = "https://www.trendyol.com/butik/liste/erkek";

        when(urlConfig.getWebHomePage()).thenReturn("https://www.trendyol.com");

        // act
        boolean result = helper.homePageChecker(webLink);

        // assertion
        Assert.assertTrue(result);
    }

    @Test
    public void homePageChecker_ShouldReturnFalse_WhenLinkNotStartsWithHomePageURL() {
        //arrange
        String webLink = "https://www.google.com/butik/liste/erkek";

        when(urlConfig.getWebHomePage()).thenReturn("https://www.trendyol.com");

        // act
        boolean result = helper.homePageChecker(webLink);

        // assertion
        Assert.assertFalse(result);
    }

    @Test
    public void usLocaleConverter_ShouldSucceed_1() {
        //arrange
        String women = "KADİN";

        // act
        String result = helper.usLocaleConverter(women);

        // assertion
        Assert.assertEquals("kadin", result);
    }

    @Test
    public void usLocaleConverter_ShouldSucceed_2() {
        //arrange
        String child = "ÇOCUK";

        // act
        String result = helper.usLocaleConverter(child);

        // assertion
        Assert.assertEquals("cocuk", result);
    }

    @Test
    public void usLocaleConverterWithOutLowerCase_ShouldSucceed_1() {
        //arrange
        String child = "ÇOCUK";

        // act
        String result = helper.usLocaleConverterWithOutLowerCase(child);

        // assertion
        Assert.assertEquals("COCUK", result);
    }

    @Test
    public void usLocaleConverterWithOutLowerCase_ShouldSucceed_2() {
        //arrange
        String women = "KADİN";

        // act
        String result = helper.usLocaleConverterWithOutLowerCase(women);

        // assertion
        Assert.assertEquals("KADIN", result);
    }

    @Test
    public void productDetailDeepLinkCreator_ShouldSucceed_WhenFullInformationIsGiven() {
        //arrange
        Link link = getFullInformationDummyLink();

        when(urlConfig.getBaseProductDetailDeepLink()).thenReturn("ty://?Page=Product&ContentId=");

        //act
        String result = helper.productDetailDeepLinkCreator(link);

        //assertion
        Assert.assertEquals("ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064", result);
    }

    @Test
    public void productDetailDeepLinkCreator_ShouldSucceed_WhenOnlyContentIdIsGiven() {
        //arrange
        Link link = getOnlyContentIdDummyLink();

        when(urlConfig.getBaseProductDetailDeepLink()).thenReturn("ty://?Page=Product&ContentId=");

        //act
        String result = helper.productDetailDeepLinkCreator(link);

        //assertion
        Assert.assertEquals("ty://?Page=Product&ContentId=1925865", result);
    }

    @Test
    public void productDetailDeepLinkCreator_ShouldSucceed_WhenBoutiqueIdIsGiven() {
        //arrange
        Link link = getBoutiqueIdFilledDummyLink();

        when(urlConfig.getBaseProductDetailDeepLink()).thenReturn("ty://?Page=Product&ContentId=");

        //act
        String result = helper.productDetailDeepLinkCreator(link);

        //assertion
        Assert.assertEquals("ty://?Page=Product&ContentId=1925865&CampaignId=439892", result);
    }

    @Test
    public void productDetailDeepLinkCreator_ShouldSucceed_WhenMerchantIdIsGiven() {
        //arrange
        Link link = getMerchantIdFilledDummyLink();

        when(urlConfig.getBaseProductDetailDeepLink()).thenReturn("ty://?Page=Product&ContentId=");

        //act
        String result = helper.productDetailDeepLinkCreator(link);

        //assertion
        Assert.assertEquals("ty://?Page=Product&ContentId=1925865&MerchantId=105064", result);
    }

    @Test
    public void boutiqueDeepLinkCreator_ShouldSucceed_WhenLongIdIsGiven() {
        //arrange
        String givenId = "2";

        when(urlConfig.getBaseBoutiqueDeepLink()).thenReturn( "ty://?Page=Home&SectionId=");

        //act
        String result = helper.boutiqueDeepLinkCreator(givenId);

        //assertion
        Assert.assertEquals("ty://?Page=Home&SectionId=2", result);
    }

    @Test
    public void searchDeepLinkCreator_ShouldSucceed_whenQueryContainsUpperAndLowerCase() {
        //arrange
        String webLink = "https://www.trendyol.com/tum--urunler?q=%C3%BCt%C3%BC";

        when(urlConfig.getBaseWebSearchLink()).thenReturn("https://www.trendyol.com/tum--urunler?q=");

        when(urlConfig.getBaseSearchDeepLink()).thenReturn("ty://?Page=Search&Query=");

        //act
        String result = helper.searchDeepLinkCreator(webLink);

        //assertion
        Assert.assertEquals("ty://?Page=Search&Query=%C3%BCt%C3%BC", result);
    }

    @Test
    public void searchDeepLinkCreator_ShouldSucceed_whenQueryIsLowerCase() {
        //arrange
        String webLink = "https://www.trendyol.com/tum--urunler?q=elbise";

        when(urlConfig.getBaseWebSearchLink()).thenReturn("https://www.trendyol.com/tum--urunler?q=");

        when(urlConfig.getBaseSearchDeepLink()).thenReturn("ty://?Page=Search&Query=");

        //act
        String result = helper.searchDeepLinkCreator(webLink);

        //assertion
        Assert.assertEquals("ty://?Page=Search&Query=elbise", result);
    }

    @Test
    public void searchDeepLinkCreator_ShouldSucceed_whenQueryIsUpperCase() {
        //arrange
        String webLink = "https://www.trendyol.com/tum--urunler?q=ELBISE";

        when(urlConfig.getBaseWebSearchLink()).thenReturn("https://www.trendyol.com/tum--urunler?q=");

        when(urlConfig.getBaseSearchDeepLink()).thenReturn("ty://?Page=Search&Query=");

        //act
        String result = helper.searchDeepLinkCreator(webLink);

        //assertion
        Assert.assertEquals("ty://?Page=Search&Query=ELBISE", result);
    }

    @Test
    public void searchDeepLinkCreator_ShouldSucceed_whenQueryIsUpperCaseAndContainsNonASCIIChar() {
        //arrange
        String webLink = "https://www.trendyol.com/tum--urunler?q=ELBİSE";

        when(urlConfig.getBaseWebSearchLink()).thenReturn("https://www.trendyol.com/tum--urunler?q=");

        when(urlConfig.getBaseSearchDeepLink()).thenReturn("ty://?Page=Search&Query=");

        //act
        String result = helper.searchDeepLinkCreator(webLink);

        //assertion
        Assert.assertEquals("ty://?Page=Search&Query=ELBISE", result);
    }

    @Test
    public void productDetailLinkParser_ShouldSucceed_WhenFullLinkGiven() {
        //arrange
        String webLink = helper.usLocaleConverter("https://www.trendyol.com/casio/erkek-kol-saati-p-1925865?boutiqueId=439892&merchantId=105064");

        when(urlConfig.getWebHomePage()).thenReturn("https://www.trendyol.com");

        //act
        HashMap<String, String> parsedMap = helper.productDetailLinkParser(webLink);

        //assertion
        Assert.assertEquals(parsedMap.get("brandOrCategoryName"), "casio");

        Assert.assertEquals(parsedMap.get("productName"), "erkek-kol-saati")
        ;
        Assert.assertEquals(parsedMap.get("contentId"), "1925865");

        Assert.assertEquals(parsedMap.get("boutiqueId"), "439892");

        Assert.assertEquals(parsedMap.get("merchantId"), "105064");
    }

    @Test
    public void detailLinkParser_ShouldSucceed_WhenOnlyContentIdGiven() {
        //arrange
        String webLink = "https://www.trendyol.com/casio/erkek-kol-saati-p-1925865";

        when(urlConfig.getWebHomePage()).thenReturn("https://www.trendyol.com");

        //act
        HashMap<String, String> parsedMap = helper.productDetailLinkParser(webLink);

        //assertion
        Assert.assertEquals(parsedMap.get("brandOrCategoryName"), "casio");

        Assert.assertEquals(parsedMap.get("productName"), "erkek-kol-saati");

        Assert.assertEquals(parsedMap.get("contentId"), "1925865");

        Assert.assertEquals(parsedMap.get("boutiqueId"), "");

        Assert.assertEquals(parsedMap.get("merchantId"), "");
    }

    @Test
    public void productDetailLinkParser_ShouldSucceed_WhenOnlyBoutiqueIdGiven() {
        //arrange
        String webLink = helper.usLocaleConverter("https://www.trendyol.com/casio/erkek-kol-saati-p-1925865?boutiqueId=439892");

        when(urlConfig.getWebHomePage()).thenReturn("https://www.trendyol.com");

        //act
        HashMap<String, String> parsedMap = helper.productDetailLinkParser(webLink);

        //assertion
        Assert.assertEquals(parsedMap.get("brandOrCategoryName"), "casio");

        Assert.assertEquals(parsedMap.get("productName"), "erkek-kol-saati");

        Assert.assertEquals(parsedMap.get("contentId"), "1925865");

        Assert.assertEquals(parsedMap.get("boutiqueId"), "439892");

        Assert.assertEquals(parsedMap.get("merchantId"), "");
    }

    @Test
    public void productDetailLinkParser_ShouldSucceed_WhenOnlyMerchantIdGiven() {
        //arrange
        String webLink = helper.usLocaleConverter("https://www.trendyol.com/casio/erkek-kol-saati-p-1925865?merchantId=439892");

        when(urlConfig.getWebHomePage()).thenReturn("https://www.trendyol.com");

        //act
        HashMap<String, String> parsedMap = helper.productDetailLinkParser(webLink);

        //assertion
        Assert.assertEquals(parsedMap.get("brandOrCategoryName"), "casio");

        Assert.assertEquals(parsedMap.get("productName"), "erkek-kol-saati");

        Assert.assertEquals(parsedMap.get("contentId"), "1925865");

        Assert.assertEquals(parsedMap.get("boutiqueId"), "");

        Assert.assertEquals(parsedMap.get("merchantId"), "439892");
    }


    private Link getFullInformationDummyLink() {
        Link link = new Link();
        link.setId(1L);
        link.setWebLink(helper.usLocaleConverter("https://www.trendyol.com/casio/erkek-kol-saati-p-1925865?boutiqueId=439892&merchantId=105064"));
        link.setDeepLink("ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064");
        link.setBoutiqueId(439892L);
        link.setMerchantId(105064L);
        link.setBrandOrCategoryName("casio");
        link.setProductName("erkek-kol-saati");
        link.setContentId(1925865L);
        link.setDeleted(false);
        return link;
    }

    private Link getOnlyContentIdDummyLink() {
        Link link = new Link();
        link.setId(1L);
        link.setWebLink(helper.usLocaleConverter("https://www.trendyol.com/casio/erkek-kol-saati-p-1925865"));
        link.setDeepLink("ty://?Page=Product&ContentId=1925865");
        link.setBoutiqueId(null);
        link.setMerchantId(null);
        link.setBrandOrCategoryName("casio");
        link.setProductName("erkek-kol-saati");
        link.setContentId(1925865L);
        link.setDeleted(false);
        return link;
    }

    private Link getBoutiqueIdFilledDummyLink() {
        Link link = new Link();
        link.setId(1L);
        link.setWebLink(helper.usLocaleConverter("https://www.trendyol.com/casio/erkek-kol-saati-p-1925865?boutiqueId=439892"));
        link.setDeepLink("ty://?Page=Product&ContentId=1925865&CampaignId=439892");
        link.setBoutiqueId(439892L);
        link.setMerchantId(null);
        link.setBrandOrCategoryName("casio");
        link.setProductName("erkek-kol-saati");
        link.setContentId(1925865L);
        link.setDeleted(false);
        return link;
    }

    private Link getMerchantIdFilledDummyLink() {
        Link link = new Link();
        link.setId(1L);
        link.setWebLink(helper.usLocaleConverter("https://www.trendyol.com/casio/erkek-kol-saati-p-1925865?merchantId=105064"));
        link.setDeepLink("ty://?Page=Product&ContentId=1925865&MerchantId=105064");
        link.setBoutiqueId(null);
        link.setMerchantId(105064L);
        link.setBrandOrCategoryName("casio");
        link.setProductName("erkek-kol-saati");
        link.setContentId(1925865L);
        link.setDeleted(false);
        return link;
    }
}

package com.ztp.converter.utils;

import com.ztp.converter.config.PrefixConfig;
import com.ztp.converter.config.UrlConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DistributorRulerTest {

    @InjectMocks
    private DistributorRuler distributorRuler;

    @Mock
    private UrlConfig urlConfig;

    @Mock
    private PrefixConfig prefixConfig;

    @Test
    public void boutiqueParser_ShouldReturnTrue_WhenLinkContainsBoutiqueWords() {
        //arrange
        String link = "https://www.trendyol.com/butik/liste/erkek";

        when(urlConfig.getWebHomePage()).thenReturn("https://www.trendyol.com");

        when(prefixConfig.getBoutiquePrefix()).thenReturn("butik/liste");

        //act
        boolean result = distributorRuler.isBoutiqueLink(link);

        //assertion
        Assert.assertTrue(result);
    }

    @Test
    public void boutiqueParser_ShouldReturnFalse_WhenLinkNotContainsBoutiqueWords() {
        //arrange
        String link = "https://www.trendyol.com/test/liste";

        when(urlConfig.getWebHomePage()).thenReturn("https://www.trendyol.com");

        when(prefixConfig.getBoutiquePrefix()).thenReturn("butik/liste");

        //act
        boolean result = distributorRuler.isBoutiqueLink(link);

        //assertion
        Assert.assertFalse(result);
    }

    @Test
    public void productDetailParser_ShouldReturnTrue_WhenLinkContainsPCharWithLines() {
        //arrange
        String link = "https://www.trendyol.com/casio/erkek-kol-saati-p-1921685";

        when(urlConfig.getWebHomePage()).thenReturn("https://www.trendyol.com");

        when(prefixConfig.getProductDetailPrefix()).thenReturn("-p-");

        //act
        boolean result = distributorRuler.isProductDetailLink(link);

        //assertion
        Assert.assertTrue(result);
    }

    @Test
    public void productDetailParser_ShouldReturnFalse_WhenLinkNotContainsPCharWithLines() {
        //arrange
        String link = "https://www.trendyol.com/casio/erkek-kol-saati-1921685";

        when(urlConfig.getWebHomePage()).thenReturn("https://www.trendyol.com");

        when(prefixConfig.getProductDetailPrefix()).thenReturn("-p-");

        //act
        boolean result = distributorRuler.isProductDetailLink(link);

        //assertion
        Assert.assertFalse(result);
    }

    @Test
    public void searchPageParser_ShouldReturnTrue_WhenLinkContainsQueryWord() {
        //arrange
        String link = "https://www.trendyol.com/tum--urunler?q=%C3";

        when(urlConfig.getWebHomePage()).thenReturn("https://www.trendyol.com");

        when(prefixConfig.getSearchPrefix()).thenReturn("tum--urunler");

        //act
        boolean result = distributorRuler.isSearchLink(link);

        //assertion
        Assert.assertTrue(result);
    }

    @Test
    public void searchPageParser_ShouldReturnFalse_WhenLinkNotContainsExactQueryWord() {
        //arrange
        String link = "https://www.trendyol.com/tum-urunler?q=%C";

        when(urlConfig.getWebHomePage()).thenReturn("https://www.trendyol.com");

        when(prefixConfig.getSearchPrefix()).thenReturn("tum--urunler");

        //act
        boolean result = distributorRuler.isSearchLink(link);

        //assertion
        Assert.assertFalse(result);
    }

    @Test
    public void homePageEraser_ShouldReturnHomePageLinkErasedLink() {
        //arrange
        String link = "https://www.trendyol.com/tum-urunler?q=%C";

        when(urlConfig.getWebHomePage()).thenReturn("https://www.trendyol.com");

        //act
        String result = distributorRuler.homePageEraser(link);

        //assertion
        Assert.assertEquals("/tum-urunler?q=%C", result);
    }
}

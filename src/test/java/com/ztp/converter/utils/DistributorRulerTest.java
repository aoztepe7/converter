package com.ztp.converter.utils;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DistributorRulerTest {

    @InjectMocks
    private DistributorRuler distributorRuler;

    @Test
    public void boutiqueParser_ShouldReturnTrue_WhenLinkContainsBoutiqueWords() {
        //arrange
        String link = "https://www.trendyol.com/butik/liste/erkek";

        //act
        boolean result = distributorRuler.isBoutiqueLink(link);

        //assertion
        Assert.assertTrue(result);
    }

    @Test
    public void boutiqueParser_ShouldReturnFalse_WhenLinkNotContainsBoutiqueWords() {
        //arrange
        String link = "https://www.trendyol.com/test/liste";

        //act
        boolean result = distributorRuler.isBoutiqueLink(link);

        //assertion
        Assert.assertFalse(result);
    }

    @Test
    public void productDetailParser_ShouldReturnTrue_WhenLinkContainsPCharWithLines() {
        //arrange
        String link = "https://www.trendyol.com/casio/erkek-kol-saati-p-1921685";

        //act
        boolean result = distributorRuler.isProductDetailLink(link);

        //assertion
        Assert.assertTrue(result);
    }

    @Test
    public void productDetailParser_ShouldReturnFalse_WhenLinkNotContainsPCharWithLines() {
        //arrange
        String link = "https://www.trendyol.com/casio/erkek-kol-saati-1921685";

        //act
        boolean result = distributorRuler.isProductDetailLink(link);

        //assertion
        Assert.assertFalse(result);
    }

    @Test
    public void searchPageParser_ShouldReturnTrue_WhenLinkContainsQueryWord() {
        //arrange
        String link = "https://www.trendyol.com/tum--urunler?q=%C3";

        //act
        boolean result = distributorRuler.isSearchLink(link);

        //assertion
        Assert.assertTrue(result);
    }

    @Test
    public void searchPageParser_ShouldReturnFalse_WhenLinkNotContainsExactQueryWord() {
        //arrange
        String link = "https://www.trendyol.com/tum-urunler?q=%C";

        //act
        boolean result = distributorRuler.isSearchLink(link);

        //assertion
        Assert.assertFalse(result);
    }

    @Test
    public void homePageEraser_ShouldReturnHomePageLinkErasedLink() {
        //arrange
        String link = "https://www.trendyol.com/tum-urunler?q=%C";

        //act
        String result = distributorRuler.homePageEraser(link);

        //assertion
        Assert.assertEquals("/tum-urunler?q=%C", result);
    }
}

package com.ztp.converter.handler;

import com.ztp.converter.config.PrefixConfig;
import com.ztp.converter.config.UrlConfig;
import com.ztp.converter.exception.HomePageUrlException;
import com.ztp.converter.handler.boutique.BoutiqueListLinkCreateHandler;
import com.ztp.converter.handler.detail.ProductDetailLinkCreateHandler;
import com.ztp.converter.handler.other.HomePageReturnHandler;
import com.ztp.converter.handler.search.SearchLinkCreateHandler;
import com.ztp.converter.message.request.LinkCreateCommand;
import com.ztp.converter.message.response.CommonResponse;
import com.ztp.converter.message.response.ResponseCode;
import com.ztp.converter.utils.DistributorRuler;
import com.ztp.converter.utils.Helper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class HandlerDistributorTest {

    @InjectMocks
    private Helper helper;

    @InjectMocks
    private DistributorRuler distributorRuler;

    private HandlerDistributor handlerDistributor;

    @Mock
    private BoutiqueListLinkCreateHandler boutiqueListLinkCreateHandler;

    @Mock
    private ProductDetailLinkCreateHandler productDetailLinkCreateHandler;

    @Mock
    private SearchLinkCreateHandler searchLinkCreateHandler;

    @Mock
    private HomePageReturnHandler homePageReturnHandler;

    @Mock
    private UrlConfig urlConfig;

    @Mock
    private PrefixConfig prefixConfig;

    @Before
    public void setUp() {
        handlerDistributor = new HandlerDistributor(boutiqueListLinkCreateHandler,
                productDetailLinkCreateHandler, searchLinkCreateHandler, homePageReturnHandler, helper, distributorRuler);
    }


    @Test(expected = HomePageUrlException.class)
    public void handlerDistributor_ShouldThrowException_WhenLinkIsNotStartsWithHomePageURL() {
        //arrange
        LinkCreateCommand createCommand = new LinkCreateCommand();

        when(urlConfig.getWebHomePage()).thenReturn("https://www.trendyol.com");

        createCommand.setWebLink("https://www.google.com");

        //act
        handlerDistributor.execute(createCommand);
    }

    @Test()
    public void handlerDistributor_ShouldTriggerBoutiqueListLinkCreateHandler_WhenLinkContainsBoutiqueWords() {
        //arrange
        LinkCreateCommand createCommand = new LinkCreateCommand();

        createCommand.setWebLink("https://www.trendyol.com/butik/liste/erkek");

        when(prefixConfig.getBoutiquePrefix()).thenReturn("butik/liste");

        when(urlConfig.getWebHomePage()).thenReturn("https://www.trendyol.com");

        when(prefixConfig.getSearchPrefix()).thenReturn("tum--urunler");

        when(boutiqueListLinkCreateHandler.execute(createCommand)).thenReturn(getDummyResponse());

        //act
        handlerDistributor.execute(createCommand);

        //assertion
        verify(boutiqueListLinkCreateHandler, times(1)).execute(createCommand);

        verify(productDetailLinkCreateHandler, times(0)).execute(createCommand);

        verify(searchLinkCreateHandler, times(0)).execute(createCommand);

        verify(homePageReturnHandler, times(0)).execute(createCommand);
    }

    @Test()
    public void handlerDistributor_ShouldTriggerProductDetailLinkCreateHandler_WhenLinkContainsPCharWithLines() {
        //arrange
        LinkCreateCommand createCommand = new LinkCreateCommand();

        createCommand.setWebLink("https://www.trendyol.com/casio/erkek-kol-saati-p-1921685");

        when(productDetailLinkCreateHandler.execute(createCommand)).thenReturn(getDummyResponse());

        when(urlConfig.getWebHomePage()).thenReturn("https://www.trendyol.com");

        when(prefixConfig.getSearchPrefix()).thenReturn("tum--urunler");

        when(prefixConfig.getBoutiquePrefix()).thenReturn("butik/liste");

        when(prefixConfig.getProductDetailPrefix()).thenReturn("-p-");

        //act
        handlerDistributor.execute(createCommand);

        //assertion
        verify(productDetailLinkCreateHandler, times(1)).execute(createCommand);

        verify(searchLinkCreateHandler, times(0)).execute(createCommand);

        verify(boutiqueListLinkCreateHandler, times(0)).execute(createCommand);

        verify(homePageReturnHandler, times(0)).execute(createCommand);
    }

    @Test()
    public void handlerDistributor_ShouldTriggerSearchLinkCreateHandler_WhenLinkContainsQueryWord() {
        //arrange
        LinkCreateCommand createCommand = new LinkCreateCommand();

        createCommand.setWebLink("https://www.trendyol.com/tum--urunler?q=%C3");

        when(searchLinkCreateHandler.execute(createCommand)).thenReturn(getDummyResponse());

        when(urlConfig.getWebHomePage()).thenReturn("https://www.trendyol.com");

        when(prefixConfig.getSearchPrefix()).thenReturn("tum--urunler");


        //act
        handlerDistributor.execute(createCommand);

        //assertion
        verify(searchLinkCreateHandler, times(1)).execute(createCommand);

        verify(boutiqueListLinkCreateHandler, times(0)).execute(createCommand);

        verify(homePageReturnHandler, times(0)).execute(createCommand);

        verify(productDetailLinkCreateHandler, times(0)).execute(createCommand);
    }

    @Test()
    public void handlerDistributor_ShouldTriggerHomePageReturnHandler_WhenLinkNotContainsAnySpecificWords() {
        //arrange
        LinkCreateCommand createCommand = new LinkCreateCommand();

        createCommand.setWebLink("https://www.trendyol.com/Hesaplar/");

        when(urlConfig.getWebHomePage()).thenReturn("https://www.trendyol.com");

        when(prefixConfig.getBoutiquePrefix()).thenReturn("butik/liste");

        when(prefixConfig.getSearchPrefix()).thenReturn("tum--urunler");

        when(prefixConfig.getProductDetailPrefix()).thenReturn("-p-");

        when(homePageReturnHandler.execute(createCommand)).thenReturn(getDummyResponse());

        //act
        handlerDistributor.execute(createCommand);

        //assertion
        verify(homePageReturnHandler, times(1)).execute(createCommand);

        verify(searchLinkCreateHandler, times(0)).execute(createCommand);

        verify(boutiqueListLinkCreateHandler, times(0)).execute(createCommand);

        verify(productDetailLinkCreateHandler, times(0)).execute(createCommand);
    }

    private CommonResponse getDummyResponse(){
        return CommonResponse.builder()
                .responseCode(ResponseCode.SUCCESS)
                .deepLink("dummyDeepLink")
                .build();
    }
}

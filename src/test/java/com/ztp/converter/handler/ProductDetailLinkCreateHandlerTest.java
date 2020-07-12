package com.ztp.converter.handler;

import com.ztp.converter.config.UrlConfig;
import com.ztp.converter.domain.link.Link;
import com.ztp.converter.domain.link.LinkService;
import com.ztp.converter.exception.LinkNotFoundException;
import com.ztp.converter.handler.detail.ProductDetailLinkCreateHandler;
import com.ztp.converter.message.request.LinkCreateCommand;
import com.ztp.converter.message.response.CommonResponse;
import com.ztp.converter.utils.Helper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class ProductDetailLinkCreateHandlerTest {

    @Mock
    private LinkService linkService;

    @InjectMocks
    private Helper helper;

    private ProductDetailLinkCreateHandler productDetailLinkCreateHandler;

    @Mock
    private UrlConfig urlConfig;

    @Before
    public void setUp() {
        productDetailLinkCreateHandler = new ProductDetailLinkCreateHandler(linkService, helper);
    }

    @Test
    public void productDetailLinkCreateHandlerShouldReturnExistingLink_WhenSameWebLinkFound() {
        //arrange
        Link existLink = getDummyLink();

        LinkCreateCommand createCommand = new LinkCreateCommand();

        createCommand.setWebLink("https://www.trendyol.com/casio/erkek-kol-saati-p-1925865?boutiqueId=439892&merchantId=105064");

        when(linkService.getByWebLink(createCommand.getWebLink())).thenReturn(existLink);

        //act
        CommonResponse response = productDetailLinkCreateHandler.execute(createCommand);

        //assertion
        Assert.assertEquals(existLink.getDeepLink(), response.getDeepLink());

        verify(linkService, times(1)).getByWebLink(createCommand.getWebLink());

        verify(linkService, times(0)).create(any(Link.class));
    }

    @Test
    public void productDetailCreateHandlerShouldCreateNewEntry_WhenWebLinkNotFound() {
        //arrange
        LinkCreateCommand createCommand = new LinkCreateCommand();

        createCommand.setWebLink(helper
                .usLocaleConverter("https://www.trendyol.com/casio/erkek-kol-saati-p-1925865?boutiqueId=439892&merchantId=105064"));

        when(urlConfig.getWebHomePage()).thenReturn("https://www.trendyol.com");

        when(urlConfig.getBaseProductDetailDeepLink()).thenReturn("ty://?Page=Product&ContentId=");

        when(linkService.getByWebLink(createCommand.getWebLink())).thenThrow(LinkNotFoundException.class);

        //act
        CommonResponse response = productDetailLinkCreateHandler.execute(createCommand);

        //assertion
        Assert.assertEquals("ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064", response.getDeepLink());

        verify(linkService, times(1)).getByWebLink(createCommand.getWebLink());

        verify(linkService, times(1)).create(any(Link.class));
    }

    private Link getDummyLink() {
        Link link = new Link();
        link.setContentId(1925865L);
        link.setProductName("erkek-kol-saati");
        link.setBrandOrCategoryName("casio");
        link.setMerchantId(105064L);
        link.setBoutiqueId(439892L);
        link.setDeepLink("ty://?Page=Product&ContentId=1925865&CampaignId=439892&MerchantId=105064");
        link.setWebLink("https://www.trendyol.com/casio/erkek-kol-saati-p-1925865?boutiqueId=439892&merchantId=105064");
        link.setDeleted(false);
        return link;
    }
}

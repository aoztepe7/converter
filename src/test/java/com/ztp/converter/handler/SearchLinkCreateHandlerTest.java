package com.ztp.converter.handler;

import com.ztp.converter.config.UrlConfig;
import com.ztp.converter.domain.link.Link;
import com.ztp.converter.domain.link.LinkService;
import com.ztp.converter.exception.LinkNotFoundException;
import com.ztp.converter.handler.search.SearchLinkCreateHandler;
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
public class SearchLinkCreateHandlerTest {

    @Mock
    private LinkService linkService;

    @Mock
    private UrlConfig urlConfig;

    @InjectMocks
    private Helper helper;

    private SearchLinkCreateHandler searchLinkCreateHandler;

    @Before
    public void setUp() {
        searchLinkCreateHandler = new SearchLinkCreateHandler(linkService, helper);
    }

    @Test
    public void searchLinkCreateHandlerShouldReturnExistingLink_WhenSameWebLinkFound_WithOriginalCaseSensitivityLowerCase() {
        //arrange
        Link existLink = getDummyLink();

        LinkCreateCommand createCommand = new LinkCreateCommand();

        createCommand.setWebLink("https://www.trendyol.com/tum--urunler?q=elbise");

        when(urlConfig.getBaseWebSearchLink()).thenReturn("https://www.trendyol.com/tum--urunler?q=");

        when(urlConfig.getBaseSearchDeepLink()).thenReturn("ty://?Page=Search&Query=");

        when(linkService.getByWebLink(createCommand.getWebLink())).thenReturn(existLink);

        //act
        CommonResponse response = searchLinkCreateHandler.execute(createCommand);

        //assertion
        Assert.assertEquals("ty://?Page=Search&Query=elbise", response.getDeepLink());

        verify(linkService, times(1)).getByWebLink(createCommand.getWebLink());
    }

    @Test
    public void searchLinkCreateHandlerShouldReturnExistingLink_WhenSameWebLinkFound_WithOriginalCaseSensitivityUpperCase() {
        //arrange
        Link existLink = getDummyLink();

        LinkCreateCommand createCommand = new LinkCreateCommand();

        createCommand.setWebLink("https://www.trendyol.com/tum--urunler?q=ELBISE");

        when(urlConfig.getBaseWebSearchLink()).thenReturn("https://www.trendyol.com/tum--urunler?q=");

        when(urlConfig.getBaseSearchDeepLink()).thenReturn("ty://?Page=Search&Query=");

        when(linkService.getByWebLink(createCommand.getWebLink().toLowerCase())).thenReturn(existLink);

        //act
        CommonResponse response = searchLinkCreateHandler.execute(createCommand);

        //assertion
        Assert.assertEquals("ty://?Page=Search&Query=ELBISE", response.getDeepLink());

        verify(linkService, times(1)).getByWebLink(createCommand.getWebLink().toLowerCase());
    }

    @Test
    public void searchPageCreateHandlerShouldCreateNewEntry_WhenWebLinkNotFound() {
        //arrange
        LinkCreateCommand createCommand = new LinkCreateCommand();

        createCommand.setWebLink("https://www.trendyol.com/tum--urunler?q=elbise");

        when(urlConfig.getBaseWebSearchLink()).thenReturn("https://www.trendyol.com/tum--urunler?q=");

        when(urlConfig.getBaseSearchDeepLink()).thenReturn("ty://?Page=Search&Query=");

        when(linkService.getByWebLink(createCommand.getWebLink())).thenThrow(LinkNotFoundException.class);

        //act
        CommonResponse response = searchLinkCreateHandler.execute(createCommand);

        //assertion
        Assert.assertEquals("ty://?Page=Search&Query=elbise", response.getDeepLink());

        verify(linkService, times(1)).getByWebLink(createCommand.getWebLink());

        verify(linkService, times(1)).create(any(Link.class));
    }

    private Link getDummyLink() {
        Link link = new Link();
        link.setDeepLink("ty://?Page=Search&Query=ELBISE");
        link.setWebLink("https://www.trendyol.com/tum--urunler?q=elbise");
        link.setDeleted(false);
        return link;
    }
}

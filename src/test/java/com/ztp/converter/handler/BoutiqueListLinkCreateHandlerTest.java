package com.ztp.converter.handler;

import com.ztp.converter.config.UrlConfig;
import com.ztp.converter.domain.link.Link;
import com.ztp.converter.domain.link.LinkService;
import com.ztp.converter.exception.LinkNotFoundException;
import com.ztp.converter.handler.boutique.BoutiqueListLinkCreateHandler;
import com.ztp.converter.message.request.LinkCreateCommand;
import com.ztp.converter.message.response.CommonResponse;
import com.ztp.converter.utils.Helper;
import com.ztp.converter.utils.SectionMapService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class BoutiqueListLinkCreateHandlerTest {

    @Mock
    private LinkService linkService;

    @Mock
    private SectionMapService sectionMapService;

    @InjectMocks
    private Helper helper;

    @Mock
    private UrlConfig urlConfig;

    private BoutiqueListLinkCreateHandler boutiqueListLinkCreateHandler;

    @Before
    public void setUp() throws Exception {
        boutiqueListLinkCreateHandler = new BoutiqueListLinkCreateHandler(sectionMapService, linkService, helper, urlConfig);
    }

    @Test
    public void boutiqueCreateHandlerShouldReturnExistingLink_WhenSameWebLinkFound() {
        //arrange
        Link existLink = getDummyLink();

        LinkCreateCommand createCommand = new LinkCreateCommand();

        createCommand.setWebLink("https://www.trendyol.com/butik/liste/1");

        when(urlConfig.getBaseWebBoutiqueLink()).thenReturn("https://www.trendyol.com/butik/liste/");

        when(sectionMapService.getValue(any(String.class))).thenReturn("1");

        when(linkService.getBySectionName(any(String.class))).thenReturn(existLink);

        //act
        CommonResponse response = boutiqueListLinkCreateHandler.execute(createCommand);

        //assertion
        Assert.assertEquals(existLink.getDeepLink(), response.getDeepLink());

        verify(linkService, times(1)).getBySectionName(any(String.class));

        verify(sectionMapService, times(1)).getValue(any(String.class));
    }

    @Test
    public void boutiqueCreateHandlerShouldCreateNewEntry_WhenWebLinkNotFound() {
        //arrange
        LinkCreateCommand createCommand = new LinkCreateCommand();

        createCommand.setWebLink("https://www.trendyol.com/butik/liste/1");

        when(urlConfig.getBaseWebBoutiqueLink()).thenReturn("https://www.trendyol.com/butik/liste/");

        when(urlConfig.getBaseBoutiqueDeepLink()).thenReturn("ty://?Page=Home&SectionId=");

        when(sectionMapService.getValue(any(String.class))).thenReturn("1");

        when(linkService.getBySectionName(any(String.class))).thenThrow(LinkNotFoundException.class);

        //act
        CommonResponse response = boutiqueListLinkCreateHandler.execute(createCommand);

        //assertion
        Assert.assertEquals("ty://?Page=Home&SectionId=1", response.getDeepLink());

        verify(linkService, times(1)).getBySectionName(any(String.class));

        verify(linkService, times(1)).create(any(Link.class));

        verify(sectionMapService, times(1)).getValue(any(String.class));
    }

    @Test
    public void boutiqueCreateHandlerShouldReturnHomePage_WhenSectionNotFoundInRedis() {
        //arrange
        LinkCreateCommand createCommand = new LinkCreateCommand();

        createCommand.setWebLink("https://www.trendyol.com/butik/liste/5");

        when(urlConfig.getBaseWebBoutiqueLink()).thenReturn("https://www.trendyol.com/butik/liste/");

        when(urlConfig.getDeepHomePage()).thenReturn("ty://?Page=Home");

        when(sectionMapService.getValue(any(String.class))).thenReturn(null);

        //act
        CommonResponse response = boutiqueListLinkCreateHandler.execute(createCommand);

        //assertion
        Assert.assertEquals("ty://?Page=Home", response.getDeepLink());

        verify(linkService, times(0)).getBySectionName(any(String.class));

        verify(linkService, times(0)).create(any(Link.class));

        verify(sectionMapService, times(1)).getValue(any(String.class));
    }

    private Link getDummyLink() {
        Link link = new Link();
        link.setIsValid(true);
        link.setDeepLink("ty://?Page=Home&SectionId=1");
        link.setWebLink("https://www.trendyol.com/butik/liste/1");
        link.setDeleted(false);
        return link;
    }
}

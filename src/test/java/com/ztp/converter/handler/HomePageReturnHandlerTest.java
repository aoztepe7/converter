package com.ztp.converter.handler;

import com.ztp.converter.domain.link.Link;
import com.ztp.converter.domain.link.LinkService;
import com.ztp.converter.exception.LinkNotFoundException;
import com.ztp.converter.handler.other.HomePageReturnHandler;
import com.ztp.converter.message.request.LinkCreateCommand;
import com.ztp.converter.message.response.CommonResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class HomePageReturnHandlerTest {

    @Mock
    private LinkService linkService;

    private HomePageReturnHandler homePageReturnHandler;

    @Before
    public void setUp() {
        homePageReturnHandler = new HomePageReturnHandler(linkService);
    }

    @Test
    public void homePageReturnHandlerShouldReturnExistingLink_WhenSameWebLinkFound() {
        //arrange
        Link existLink = getDummyLink();

        LinkCreateCommand createCommand = new LinkCreateCommand();

        createCommand.setWebLink("https://www.trendyol.com/wrong-link");

        when(linkService.getByWebLink(createCommand.getWebLink())).thenReturn(existLink);

        //act
        CommonResponse response = homePageReturnHandler.execute(createCommand);

        //assertion
        Assert.assertEquals("ty://?Page=Home", response.getDeepLink());

        verify(linkService, times(1)).getByWebLink(createCommand.getWebLink());
    }

    @Test
    public void homePageReturnHandlerShouldCreateNewEntry_WhenWebLinkNotFound() {
        //arrange
        LinkCreateCommand createCommand = new LinkCreateCommand();

        createCommand.setWebLink("https://www.trendyol.com/wrong-link");

        when(linkService.getByWebLink(createCommand.getWebLink())).thenThrow(LinkNotFoundException.class);

        //act
        CommonResponse response = homePageReturnHandler.execute(createCommand);

        //assertion
        Assert.assertEquals("ty://?Page=Home", response.getDeepLink());

        verify(linkService, times(1)).getByWebLink(createCommand.getWebLink());

        verify(linkService, times(1)).create(any(Link.class));
    }

    private Link getDummyLink() {
        Link link = new Link();
        link.setDeepLink("ty://?Page=Home");
        link.setWebLink("https://www.trendyol.com/wrong-link");
        link.setDeleted(false);
        return link;
    }
}

package com.ztp.converter.handler;

import com.ztp.converter.domain.link.Link;
import com.ztp.converter.domain.link.LinkService;
import com.ztp.converter.exception.LinkNotFoundException;
import com.ztp.converter.handler.boutique.BoutiqueListLinkCreateHandler;
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
public class BoutiqueListLinkCreateHandlerTest {

    @Mock
    private LinkService linkService;

    @InjectMocks
    private Helper helper;

    private BoutiqueListLinkCreateHandler boutiqueListLinkCreateHandler;

    @Before
    public void setUp() throws Exception {
        boutiqueListLinkCreateHandler = new BoutiqueListLinkCreateHandler(linkService, helper);
    }

    @Test
    public void boutiqueCreateHandlerShouldReturnExistingLink_WhenSameWebLinkFound() {
        //arrange
        Link existLink = getDummyLink();

        LinkCreateCommand createCommand = new LinkCreateCommand();

        createCommand.setWebLink("https://www.trendyol.com/butik/liste/1");

        when(linkService.getByWebLink(createCommand.getWebLink())).thenReturn(existLink);

        //act
        CommonResponse response = boutiqueListLinkCreateHandler.execute(createCommand);

        //assertion
        Assert.assertEquals(existLink.getDeepLink(), response.getDeepLink());

        verify(linkService, times(1)).getByWebLink(createCommand.getWebLink());
    }

    @Test
    public void boutiqueCreateHandlerShouldCreateNewEntry_WhenWebLinkNotFound() {
        //arrange
        LinkCreateCommand createCommand = new LinkCreateCommand();

        createCommand.setWebLink("https://www.trendyol.com/butik/liste/1");

        when(linkService.getByWebLink(createCommand.getWebLink())).thenThrow(LinkNotFoundException.class);

        when(linkService.getLastSectionId()).thenReturn(2L);

        //act
        CommonResponse response = boutiqueListLinkCreateHandler.execute(createCommand);

        //assertion
        Assert.assertEquals("ty://?Page=Home&SectionId=3", response.getDeepLink());

        verify(linkService, times(1)).getByWebLink(createCommand.getWebLink());

        verify(linkService, times(1)).create(any(Link.class));

        verify(linkService, times(1)).getLastSectionId();
    }

    private Link getDummyLink() {
        Link link = new Link();
        link.setSectionId(1L);
        link.setDeepLink("ty://?Page=Home&SectionId=1");
        link.setWebLink("https://www.trendyol.com/butik/liste/1");
        link.setDeleted(false);
        return link;
    }
}

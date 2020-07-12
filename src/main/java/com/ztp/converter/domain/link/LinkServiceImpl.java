package com.ztp.converter.domain.link;

import com.ztp.converter.exception.LinkNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@AllArgsConstructor
@Component
public class LinkServiceImpl implements LinkService {

    private final LinkRepository linkRepository;

    @Override
    public Link getBySectionName(String sectionName) {
        return Optional.ofNullable(linkRepository.findBySectionNameAndDeletedFalseAndIsValidTrue(sectionName))
                .orElseThrow(LinkNotFoundException::new);
    }

    @Override
    public Link create(Link link) {
        return linkRepository.save(link);
    }

    @Override
    public Link getByWebLink(String webLink) {
        return Optional.ofNullable(linkRepository.findByWebLinkAndDeletedFalseAndIsValidTrue(webLink))
                .orElseThrow(LinkNotFoundException::new);
    }
}

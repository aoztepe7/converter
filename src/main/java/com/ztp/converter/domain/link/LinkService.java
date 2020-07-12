package com.ztp.converter.domain.link;

public interface LinkService {
    Link getBySectionName(String sectionName);

    Link create(Link link);

    Link getByWebLink(String webLink);
}

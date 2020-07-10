package com.ztp.converter.domain.link;

public interface LinkService {
    Link getByWebLink(String webLink);

    Link create(Link link);

    Long getLastSectionId();
}

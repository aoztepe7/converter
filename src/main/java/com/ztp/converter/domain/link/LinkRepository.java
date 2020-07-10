package com.ztp.converter.domain.link;

import org.springframework.data.repository.CrudRepository;

public interface LinkRepository extends CrudRepository<Link, Long> {
    Link findByWebLinkAndDeletedFalse(String webLink);

    Long countBySectionIdNotNullAndDeletedFalse();
}

package com.ztp.converter.domain.link;

import org.springframework.data.repository.CrudRepository;

public interface LinkRepository extends CrudRepository<Link, Long> {
    Link findBySectionNameAndDeletedFalseAndIsValidTrue(String sectionName);

    Link findByWebLinkAndDeletedFalseAndIsValidTrue(String webLink);
}

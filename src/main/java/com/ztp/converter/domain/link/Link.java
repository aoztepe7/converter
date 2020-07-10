package com.ztp.converter.domain.link;

import com.ztp.converter.domain.Type;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "links")
public class Link {
    @Id
    @Column(nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long id;

    @Column(name = "web_link", nullable = false)
    private String webLink;

    @Column(name = "deep_link", nullable = false)
    private String deepLink;

    @Column(name = "section_id")
    private Long sectionId;

    @Column(name = "brand_or_category_name")
    private String brandOrCategoryName;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "content_id")
    private Long contentId;

    @Column(name = "boutique_id")
    private Long boutiqueId;

    @Column(name = "merchant_id")
    private Long merchantId;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false)
    private Boolean deleted = false;
}

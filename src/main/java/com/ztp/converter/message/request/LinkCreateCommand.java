package com.ztp.converter.message.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class LinkCreateCommand {

    @NotEmpty(message = "Web Link Can Not Be Empty!")
    private String webLink;
}

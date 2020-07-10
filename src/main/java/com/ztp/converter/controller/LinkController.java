package com.ztp.converter.controller;

import com.ztp.converter.handler.HandlerDistributor;
import com.ztp.converter.message.response.CommonResponse;
import com.ztp.converter.message.request.LinkCreateCommand;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
public class LinkController {

    private final HandlerDistributor handlerDistributor;

    @PostMapping("/create")
    public CommonResponse create(@RequestBody @Valid LinkCreateCommand createCommand) {
        return handlerDistributor.execute(createCommand);
    }
}

package com.ztp.converter.event;

import com.ztp.converter.utils.SectionMapService;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class SectionMapCreateEvent implements ApplicationListener<ApplicationReadyEvent> {

    private final SectionMapService sectionMapService;

    @SneakyThrows
    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
        sectionMapService.setRedisData();
    }
}

package ru.test.assignment.service;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ru.test.assignment.model.entity.Proxy;
import ru.test.assignment.model.rest.ProxyRs;

@Component
public class ProxyRsConverter implements Converter<Proxy, ProxyRs> {

    @Override
    public ProxyRs convert(Proxy entity) {
        return ProxyRs.builder()
                .id(entity.getId())
                .name(entity.getName())
                .hostname(entity.getHostname())
                .type(entity.getType())
                .username(entity.getUsername())
                .password(entity.getPassword())
                .active(entity.getActive())
                .port(entity.getPort())
                .build();
    }
}

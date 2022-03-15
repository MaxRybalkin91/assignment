package ru.test.assignment.service;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;
import ru.test.assignment.model.entity.Proxy;
import ru.test.assignment.model.rest.ProxyRq;

@Service
public class ProxyRqConverter implements Converter<ProxyRq, Proxy> {

    @Override
    public Proxy convert(ProxyRq request) {
        return Proxy.builder()
                .id(request.getId())
                .name(request.getName())
                .hostname(request.getHostname())
                .type(request.getType())
                .port(request.getPort())
                .username(request.getUsername())
                .password(request.getPassword())
                .active(request.getActive())
                .build();
    }
}

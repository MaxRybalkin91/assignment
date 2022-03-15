package ru.test.assignment.model.rest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.test.assignment.model.ProxyType;

@Setter
@Getter
@Builder
public class ProxyRs {
    private Long id;
    private String name;
    private ProxyType type;
    private String hostname;
    private Integer port;
    private String username;
    private String password;
    private Boolean active;
}

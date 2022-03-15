package ru.test.assignment.model.rest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.test.assignment.model.ProxyType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Setter
@Getter
@Builder
public class ProxyRq {

    private Long id;

    @NotNull
    @Size(max = 120)
    private String name;

    @NotNull
    private ProxyType type;

    @NotNull
    @Size(max = 120)
    private String hostname;

    @NotNull
    private Integer port;

    @NotNull
    @Size(min = 4, max = 20)
    private String username;

    @NotNull
    @Size(min = 4, max = 20)
    private String password;

    @NotNull
    private Boolean active;

}

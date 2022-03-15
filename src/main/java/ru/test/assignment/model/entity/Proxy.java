package ru.test.assignment.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.test.assignment.model.ProxyType;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "proxy")
public class Proxy {

    @Id
    @GeneratedValue(generator = "proxyIdGenerator")
    @SequenceGenerator(name = "proxyIdGenerator", sequenceName = "PROXY_ID_SEQ", allocationSize = 1)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private ProxyType type;

    private String hostname;

    private Integer port;

    private String username;

    private String password;

    private Boolean active;

}

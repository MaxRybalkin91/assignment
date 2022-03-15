package ru.test.assignment.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.test.assignment.AssignmentApplication;
import ru.test.assignment.model.ProxyType;
import ru.test.assignment.model.entity.Proxy;
import ru.test.assignment.model.rest.ProxyRq;
import ru.test.assignment.repository.ProxyRepository;
import ru.test.assignment.service.ProxyRqConverter;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AssignmentApplication.class)
@WebAppConfiguration
public abstract class AbstractControllerTest {
    protected final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private ProxyRepository proxyRepository;

    @Autowired
    private ProxyRqConverter requestConverter;

    protected MockMvc mockMvc;

    @PostConstruct
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void clearDb() {
        proxyRepository.deleteAll();
    }

    protected ResultActions doSave(String requestJson) throws Exception {
        return mockMvc.perform(post("/save")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson));
    }

    protected ResultActions doEdit(String requestJson) throws Exception {
        return mockMvc.perform(put("/edit")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestJson));
    }

    protected ResultActions doDelete(Long id) throws Exception {
        return mockMvc.perform(delete(String.format("/%d", id))
                .contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    protected ProxyRq getTestRequest() {
        return ProxyRq.builder()
                .name(UUID.randomUUID().toString())
                .type(ProxyType.HTTP)
                .hostname(UUID.randomUUID().toString())
                .port(1234)
                .username("test")
                .password("test")
                .active(true)
                .build();
    }

    protected Proxy saveProxyToDb() {
        final Proxy proxy = requestConverter.convert(getTestRequest());
        return proxyRepository.save(proxy);
    }

    protected List<Proxy> getSavedTestProxies() {
        return List.of(
                proxyRepository.save(getTestProxy("name1")),
                proxyRepository.save(getTestProxy("name2")),
                proxyRepository.save(getTestProxy("name3"))
        );
    }

    private Proxy getTestProxy(String name) {
        return Proxy.builder()
                .name(name)
                .type(ProxyType.HTTP)
                .hostname(name)
                .port(1234)
                .username("test")
                .password("test")
                .active(true)
                .build();
    }
}

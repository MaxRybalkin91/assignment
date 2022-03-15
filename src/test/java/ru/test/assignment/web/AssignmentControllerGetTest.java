package ru.test.assignment.web;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import ru.test.assignment.model.entity.Proxy;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class AssignmentControllerGetTest extends AbstractControllerTest {
    private static final String GET_BY_ID_PATH = "/%d";

    private List<Proxy> savedProxies;

    @Before
    public void saveProxies() {
        savedProxies = getSavedTestProxies();
    }

    @Test
    public void testGetAllProxies() throws Exception {
        final Proxy proxy1 = savedProxies.get(0);
        final Proxy proxy2 = savedProxies.get(1);
        final Proxy proxy3 = savedProxies.get(2);
        mockMvc.perform(get("/all"))
                .andExpect(jsonPath("$.content[0].name", Matchers.equalTo(proxy1.getName())))
                .andExpect(jsonPath("$.content[1].type", Matchers.equalTo(proxy2.getType().toString())))
                .andExpect(jsonPath("$.content[2].port", Matchers.equalTo(proxy3.getPort())));
    }

    @Test
    public void testGetProxyById() throws Exception {
        final Proxy proxy1 = savedProxies.get(0);
        final Proxy proxy2 = savedProxies.get(1);
        final long notExistId = savedProxies.get(1).getId() + 1000;
        mockMvc.perform(get(getPathById(proxy1.getId())))
                .andExpect(jsonPath("$.name", Matchers.equalTo(proxy1.getName())))
                .andExpect(jsonPath("$.username", Matchers.equalTo(proxy1.getUsername())));
        mockMvc.perform(get(getPathById(proxy2.getId())))
                .andExpect(jsonPath("$.hostname", Matchers.equalTo(proxy2.getHostname())))
                .andExpect(jsonPath("$.type", Matchers.equalTo(proxy2.getType().toString())));
        mockMvc.perform(get(getPathById(notExistId)))
                .andExpect(content().string(""));
    }

    @Test
    public void testGetProxyByNameAndType() throws Exception {
        final Proxy proxy1 = savedProxies.get(0);
        mockMvc.perform(get("/")
                        .param("name", proxy1.getName())
                        .param("type", proxy1.getType().toString()))
                .andExpect(jsonPath("$.[0].id", Matchers.equalTo(proxy1.getId().intValue())))
                .andExpect(jsonPath("$.[0].name", Matchers.equalTo(proxy1.getName())))
                .andExpect(jsonPath("$.[0].type", Matchers.equalTo(proxy1.getType().toString())));
    }

    private String getPathById(Long id) {
        return String.format(GET_BY_ID_PATH, id);
    }
}

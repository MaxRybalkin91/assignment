package ru.test.assignment.web;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AssignmentControllerDeleteTest extends AbstractControllerTest {

    @Test
    public void testDeleteProxy() throws Exception {
        final Long savedEntityId = saveProxyToDb().getId();
        doDelete(savedEntityId)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testDeleteNotFoundProxy() throws Exception {
        doDelete(1L)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("message",
                        Matchers.matchesPattern(("(.*)wasn't found(.*)"))));
    }
}

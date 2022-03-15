package ru.test.assignment.web;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import ru.test.assignment.model.rest.ProxyRq;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.test.assignment.service.ProxyService.NO_ID_MESSAGE;

public class AssignmentControllerSaveTest extends AbstractControllerTest {

    @Test
    public void testSaveProxy() throws Exception {
        final ProxyRq request = getTestRequest();
        doSave(objectMapper.writeValueAsString(request))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testSaveUniqueError() throws Exception {
        final ProxyRq request = getTestRequest();
        final String requestJson = objectMapper.writeValueAsString(request);
        doSave(requestJson)
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
        doSave(requestJson)
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("message",
                        Matchers.matchesPattern("Error saving to DB:(.*)")));
    }

    @Test
    public void testSaveWithIdError() throws Exception {
        final ProxyRq request = getTestRequest();
        request.setId(1L);
        final String requestJson = objectMapper.writeValueAsString(request);
        doSave(requestJson)
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("message",
                        Matchers.equalTo(NO_ID_MESSAGE)));
    }

    @Test
    public void testSaveInvalidRequest() throws Exception {
        final ProxyRq request = getTestRequest();
        request.setPassword("");
        doSave(objectMapper.writeValueAsString(request))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message",
                        Matchers.matchesPattern("Incorrect fields values(.*)")));
    }
}

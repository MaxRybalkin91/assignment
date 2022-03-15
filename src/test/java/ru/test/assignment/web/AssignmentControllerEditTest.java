package ru.test.assignment.web;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.springframework.http.MediaType;
import ru.test.assignment.model.rest.ProxyRq;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.test.assignment.service.ProxyService.REQUIRED_ID_MESSAGE;

public class AssignmentControllerEditTest extends AbstractControllerTest {

    @Test
    public void testEditProxy() throws Exception {
        final ProxyRq request = getTestRequest();
        request.setId(1L);
        doEdit(objectMapper.writeValueAsString(request))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testEditUniqueError() throws Exception {
        final ProxyRq request = getTestRequest();
        doSave(objectMapper.writeValueAsString(request));
        request.setId(2L);
        doEdit(objectMapper.writeValueAsString(request))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("message",
                        Matchers.matchesPattern("Error saving to DB:(.*)")));
    }

    @Test
    public void testEditWithIdError() throws Exception {
        doEdit(objectMapper.writeValueAsString(getTestRequest()))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("message",
                        Matchers.equalTo(REQUIRED_ID_MESSAGE)));
    }

    @Test
    public void testEditInvalidRequest() throws Exception {
        final ProxyRq request = getTestRequest();
        request.setUsername("");
        doEdit((objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("message",
                        Matchers.matchesPattern("Incorrect fields values(.*)")));
    }
}

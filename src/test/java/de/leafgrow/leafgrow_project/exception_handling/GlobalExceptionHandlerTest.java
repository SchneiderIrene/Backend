package de.leafgrow.leafgrow_project.exception_handling;

import de.leafgrow.leafgrow_project.config.SecurityConfig;
import de.leafgrow.leafgrow_project.security.sec_filter.TokenFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SecurityConfig.class)
public
class GlobalExceptionHandlerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private TokenFilter tokenFilter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoginEndpoint() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders.post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAccessEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/auth/access")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/register")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterConfirmEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/register/confirm")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testRegisterResentEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/register/resent")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testSwaggerUiEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/swagger-ui/")
                        .contentType(MediaType
                                .APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testApiDocsEndpoint() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/v3/api-docs/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}




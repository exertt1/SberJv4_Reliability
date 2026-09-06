package com.app.sberjv4_reliabilty2;

import com.app.sberjv4_reliabilty2.app.Main;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        classes = Main.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class TimeoutIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldTimeoutException() throws Exception {
        mockMvc.perform(get("/game-store/recommendations-slow"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(504))
                .andExpect(jsonPath("$.message").value("Сервер-поставщик нагружен, попробуйте ещё раз немного позже..."));
    }
}

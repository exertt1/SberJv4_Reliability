package com.app.sberjv4_reliabilty2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest(
        classes = com.app.sberjv4_reliabilty2.app.Main.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
public class ExceptionHandlerTest {
    @Autowired
    MockMvc mockMvc;

    @Test
    void should500StatusCode() throws Exception {
        mockMvc.perform(get("/game-store/error-test"))
                .andExpect(status().is5xxServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.message").value("Ошибка"))
                .andExpect(jsonPath("$.uri").value("/game-store/error-test"));
    }
}

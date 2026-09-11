package com.app.sberjv4_reliabilty2;

import com.app.sberjv4_reliabilty2.app.Main;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Profile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        classes = Main.class,
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@AutoConfigureMockMvc
@Profile("dev")
public class CacheIntegrationTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    @Qualifier("getGameServer")
    private WireMockServer gameServer;

    @Test
    void checkCache() throws Exception {
        mockMvc.perform(get("/game-store/games/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Elden Ring"));

        mockMvc.perform(get("/game-store/games/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Elden Ring"));

        gameServer.verify(1, WireMock.getRequestedFor(
                WireMock.urlPathMatching("/games/.*")
        ));
    }
}

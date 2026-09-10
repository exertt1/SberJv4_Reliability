//package com.app.sberjv4_reliabilty2;
//
//import com.app.sberjv4_reliabilty2.app.Main;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
//import org.springframework.context.annotation.Profile;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest(
//        classes = Main.class,
//        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
//)
//@AutoConfigureMockMvc
//@Profile("dev")
//public class CircuitBreakerIntegrationTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    void ShouldReturn503() throws Exception {
//
//        for (int i = 0; i < 10; i++) {
//            mockMvc.perform(get("/game-store/fail-recommendation"))
//                    .andExpect(status().isInternalServerError());
//        }
//
//        mockMvc.perform(get("/game-store/fail-recommendation"))
//                .andExpect(status().isServiceUnavailable());
//    }
//}

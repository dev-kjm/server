package com.motoo.server;

import com.motoo.server.config.TestProfile;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@ActiveProfiles(TestProfile.TEST)
class ServerApplicationTests {

    @Test
    void contextLoads() {
    }

}

package com.tbp.backend;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class TbpBackendApplicationTests {

    @Test
    void mainRunsWithoutExceptions() {
        // This test fails if during the start any exception will appear
        TbpBackendApplication.main(new String[]{});
        assertTrue("Dummy assertion", true);
    }
}
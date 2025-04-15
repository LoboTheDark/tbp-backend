package com.tbp.backend;

import org.junit.jupiter.api.Test;

import static org.springframework.test.util.AssertionErrors.assertTrue;

class TbpBackendApplicationTests {

    @Test
    void mainRunsWithoutExceptions() {
        // This test fails if during the start any exception will appear
        TbpBackendApplication.main(new String[]{});
        assertTrue("Dummy assertion", true);
    }
}
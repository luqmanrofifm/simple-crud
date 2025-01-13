package com.example.simple_crud.constant;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CurrentUserTest {

    private CurrentUser currentUserUnderTest;

    @BeforeEach
    void setUp() {
        currentUserUnderTest = new CurrentUser();
    }

    @Test
    void testSet() {
        // Setup
        // Run the test
        currentUserUnderTest.set("email", "userId");

        // Verify the results
    }

    @Test
    void testGetEmail() {
        currentUserUnderTest.set("email", "userId");
        assertThat(currentUserUnderTest.getEmail()).isEqualTo("email");
    }

    @Test
    void testGetUserId() {
        currentUserUnderTest.set("email", "userId");
        assertThat(currentUserUnderTest.getUserId()).isEqualTo("userId");
    }
}

package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import view.LoginForm;

import javax.swing.*;

class LoginFormTest {

    @Test
    void testLoginFormInitialization() {
        LoginForm loginForm = new LoginForm();
        assertNotNull(loginForm, "❌ LoginForm should be initialized.");
        assertTrue(loginForm.isVisible(), "❌ LoginForm should be visible.");
    }

    @Test
    void testLoginButtonExists() {
        LoginForm loginForm = new LoginForm();
        JButton loginButton = (JButton) loginForm.getContentPane().getComponent(5); // Assuming index 5 is login button
        assertEquals("LOG IN", loginButton.getText(), "❌ Login button label incorrect.");
    }
}
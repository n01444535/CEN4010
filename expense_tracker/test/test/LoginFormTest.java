package test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Component;

import org.junit.jupiter.api.Test;

import view.LoginForm;

import javax.swing.*;

class LoginFormTest {

	@Test
	void testLoginButtonExists() {
	    LoginForm loginForm = new LoginForm();

	    JButton loginButton = null;
	    for (Component comp : loginForm.getContentPane().getComponents()) {
	        if (comp instanceof JButton btn && btn.getText().equals("LOG IN")) {
	            loginButton = btn;
	            break;
	        }
	    }

	    assertNotNull(loginButton, "❌ Login button not found.");
	    assertEquals("LOG IN", loginButton.getText(), "❌ Login button label incorrect.");
	}
}
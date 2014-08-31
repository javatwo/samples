package org.vandenbrandt.rafsrestfuluser.model;

import static org.junit.Assert.*; 

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.junit.Before;
import org.junit.Test;
import org.vandenbrandt.rafsrestfuluser.model.User;

public class UserTests {
	private User user1;
	
	private static String USER1_NAME = "user1";
	private static String USER1_EMAIL = "user1@company.com";
	private static String USER1_PASSWORD = "mypwd";
	
	@Before
	public void init() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		user1 = new User(USER1_NAME, USER1_EMAIL, USER1_PASSWORD);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testCreateUserWithInvalidEmailThrowsException() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		new User(USER1_NAME, "invalidemail", USER1_PASSWORD);
	}
	
	@Test
	public void testCorrectPasswordValidatesToTrue() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		assertTrue(user1.validatePassword(USER1_PASSWORD));
	}
	
	@Test
	public void testInvalidPasswordValidatesToFalse() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		assertFalse(user1.validatePassword(USER1_PASSWORD + "wrong"));
	}
	
	@Test
	public void testEmptyPasswordValidatesToFalse() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		assertFalse(user1.validatePassword(""));
	}

	@Test
	public void testNullPasswordValidatesToFalse() throws UnsupportedEncodingException, NoSuchAlgorithmException {
		assertFalse(user1.validatePassword(null));
	}
	
	
}

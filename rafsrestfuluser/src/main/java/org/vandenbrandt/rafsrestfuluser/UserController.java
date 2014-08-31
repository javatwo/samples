package org.vandenbrandt.rafsrestfuluser;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.vandenbrandt.rafsrestfuluser.model.User;
import org.vandenbrandt.rafsrestfuluser.service.UserService;

/**
 * Handles web requests for the RESTful User resource.
 */
@Controller
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	private UserService userService;
	
	@Inject
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	/**
	 * Method to verify that the servlet is up and running
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home!");
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
	
	/**
	 * creates a new user.
	 * @throws BindException 
	 */
	@RequestMapping(value = "/users", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public @ResponseBody User createUser(@RequestBody User user) throws BindException {
		logger.info("Let's create new user: " +user);
		userService.save(user);
		return user;
	}
	
	/**
	 * returns a specific user.
	 */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public @ResponseBody User getUser(@PathVariable long id) {
		logger.info("Let's get the specific user with id: " + id);
		
		User user = userService.findById(id);
		logger.info("User: " + user);
		return user;
	}
	
	@RequestMapping(value="/users/{id}", method=RequestMethod.PUT)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void putUser(@PathVariable("id") long id,
			@RequestBody User user) {
		logger.info("Let's update user[" + id + "] using: " +user);
	  userService.update(id, user);
	}
	
	/**
	 * delete a specific user.
	 */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteUser(@PathVariable long id) {
		logger.info("Let's delete the specific user with id: " + id);
		
		userService.delete(id);
	}
	
	/**
	 * returns the list of all users.
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public @ResponseBody List<User> findUsers(HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		logger.info("Let's find some users");
		String name = request.getParameter("name");
		logger.info("with name: " + name);
		String email = request.getParameter("email");
		logger.info("and email: " + email);
		List<User> users = userService.findAll(name, email);
		return users;
	}
	
	/**
	 * returns the list of all users.
	 * @throws NoSuchAlgorithmException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/users/pwdcheck", method = RequestMethod.GET)
	public @ResponseBody boolean validatePassword(HttpServletRequest request) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		logger.info("Let's validate the user's password");
		String email = request.getParameter("email");
		logger.info("with email: " + email);
		String password = request.getParameter("pwd");
		logger.info("and password: " + password);
		boolean isPasswordValid = userService.validatePassword(email, password);
		logger.info("Is password valid = " + isPasswordValid);
		return isPasswordValid;
	}
	
}

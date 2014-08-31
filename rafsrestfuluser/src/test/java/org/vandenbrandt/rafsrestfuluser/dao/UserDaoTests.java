package org.vandenbrandt.rafsrestfuluser.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.vandenbrandt.rafsrestfuluser.dao.UserDao;
import org.vandenbrandt.rafsrestfuluser.model.User;

public class UserDaoTests extends BaseDaoTests {

	@Resource
	private UserDao userDao;
	private User user1;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		user1 = new User("user1", "user1@company.com", "mypwd");
	}
	
	@Test
	public void testInitiallyFindAllReturnsEmptyList() {
		assertTrue(userDao.findAll().isEmpty());
	}

	@Test
	public void testSaveAddsUserToRepository() {
		userDao.save(user1);
		assertTrue(userDao.findAll().size()==1);
	}
	
	@Test
	public void testFindSomeWithNameSpecifiedReturnsCorrectUser() {
		List<User> users = userDao.findSome(user1.getName(), null);
		assertTrue(users.size() == 1);
		assertTrue(users.get(0).getName().equals(user1.getName()));
	}
	
	@Test
	public void testFindSomeWithEmailSpecifiedReturnsCorrectUser() {
		List<User> users = userDao.findSome(null, user1.getEmail());
		assertTrue(users.size() == 1);
		assertTrue(users.get(0).getEmail().equals(user1.getEmail()));
	}
	
	@Test
	public void testFindSomeWithNameAndEmailSpecifiedReturnsCorrectUser() {
		List<User> users = userDao.findSome(user1.getName(), user1.getEmail());
		assertTrue(users.size() == 1);
		assertTrue(users.get(0).getName().equals(user1.getName()));
		assertTrue(users.get(0).getEmail().equals(user1.getEmail()));
	}
	
	@Test
	public void testFindSomeWithWrongNameSpecifiedReturnsEmptyList() {
		List<User> users = userDao.findSome(user1.getName() + "wrong", user1.getEmail());
		assertTrue(users.size() == 0);
	}
	
}

package org.vandenbrandt.rafsrestfuluser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.vandenbrandt.rafsrestfuluser.dao.UserDao;
import org.vandenbrandt.rafsrestfuluser.model.User;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Override
	public User findById(long uniqueIdentifier) {
		return userDao.findById(uniqueIdentifier);
	}

	@Override
	public List<User> findAll(String name, String email) {
		if (name==null && email==null) {
			return userDao.findAll();
		} else {
			return userDao.findSome(name, email);
		}
	}

	@Override
	public void save(User user) {
		userDao.save(user);
	}

	@Override
	public void update(long id, User user) {
		userDao.update(id, user);
	}

	@Override
	public void delete(long id) {
		User user = userDao.findById(id);
		userDao.delete(user);
	}

	@Override
	public boolean validatePassword(String email, String password) {
		List<User> users = userDao.findSome(null, email);
		if (users.size() == 1) {
				return users.get(0).validatePassword(password);
		} else {
			return false;
		}
		
	}

}

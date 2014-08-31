package org.vandenbrandt.rafsrestfuluser.service;

import java.util.List;

import org.vandenbrandt.rafsrestfuluser.model.User;

public interface UserService {
    public User findById( long id );
    public List<User> findAll(String name, String email);
    public void save( User user );
    public void update(long id, User user );
    public void delete( long id );
	public boolean validatePassword(String email, String password);
}

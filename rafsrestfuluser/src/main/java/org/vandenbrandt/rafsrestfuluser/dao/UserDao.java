package org.vandenbrandt.rafsrestfuluser.dao;

import java.util.List;

import org.vandenbrandt.rafsrestfuluser.model.User;

public interface UserDao {
    public User findById( long id );
    public List<User> findAll();
    public void save( User user );
    public void update(long id, User user );
    public void delete( User user );
	public List<User> findSome( String name, String email);
}

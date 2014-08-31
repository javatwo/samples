package org.vandenbrandt.rafsrestfuluser.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.vandenbrandt.rafsrestfuluser.model.User;

@Repository( "userDao")
public class UserDaoImpl extends HibernateDaoSupport implements UserDao {
	
	private static String FROM_CLAUSE="from org.vandenbrandt.rafsrestfuluser.model.User";

	public User findById(long id) {
		return getHibernateTemplate().get( User.class, id );
	}

	@SuppressWarnings("unchecked")
	public List<User> findAll() {
		return (List<User>)getHibernateTemplate().find(FROM_CLAUSE);
	}

	public void save(User user) {
		getHibernateTemplate().save(user);
	}

	public void update(long id, User user) {
		User savedUser = findById(id);
		savedUser.update(user);
		getHibernateTemplate().update(savedUser);
	}

	public void delete(User user) {
		getHibernateTemplate().delete(user);
	}
	
	@Autowired
    public void init( SessionFactory sessionFactory )
    {
        setSessionFactory( sessionFactory );
    }

	/**
	 * Method to search for Users with a certain name or email address.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findSome(String name, String email) {
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		if (name != null) {
			criteria.add(Restrictions.eq("name", name));
		}
		if (email != null) {
			criteria.add(Restrictions.eq("email", email));
		}
		return getHibernateTemplate().findByCriteria(criteria);
	}
}
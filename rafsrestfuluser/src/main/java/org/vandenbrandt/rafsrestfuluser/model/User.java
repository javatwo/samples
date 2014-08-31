package org.vandenbrandt.rafsrestfuluser.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

/**
 * Class representing a User with name, email and password.
 * The password is saved in the object as a hash and can thus not be returned.
 * You can however verify that a supplied password is correct.
 * 
 * @author rafvandenbrandt
 *
 */
@Entity
@Table(name="USER")
public class User implements Serializable {

	private static final long serialVersionUID = 7745069951469864528L;

	@Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column( name = "ID" )
	private long id;
	
    @Column( name = "NAME", nullable = false)
    private String name;

    @Column( name = "EMAIL", nullable = false, unique = true )
    private String email;

    @Column( name = "PASSWORDHASH", nullable = false)
    private byte[] passwordHash;
    
    public User() {
    }
    
    public User(String name, String email, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
		this.name = name;
		checkValidEmailAddress(email);
		this.email = email;
		passwordHash = calculateHashOfAPassword(password);
	}
    
	public String getEmail()
    {
        return email;
    }

    public void setEmail( String email )
    {
		checkValidEmailAddress(email);
        this.email = email;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public long getId()
    {
        return id;
    }

    public void setPassword( String password ) throws UnsupportedEncodingException, NoSuchAlgorithmException
    {
        this.passwordHash = calculateHashOfAPassword(password);
    } 
    
    /**
     * Method to verify the password of a user.
     * 
     * @param password
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    public boolean validatePassword( String password ) {
    	if (password == null) {
    		return false;
    	}
    	byte[] passwordHash;
		try {
			passwordHash = calculateHashOfAPassword(password);
		} catch (UnsupportedEncodingException e) {
			// If this happens we cannot validate the password
			return false;
		} catch (NoSuchAlgorithmException e) {
			// If this happens we cannot validate the password
			return false;
		}
    	int length = passwordHash.length;
    	if (length != this.passwordHash.length) {
    		return false;
    	}
    	for (int i=0; i<length; i++) {
			if (passwordHash[i]!=this.passwordHash[i]) {
				return false;
			}
		}
    	
    	return true;
    }
   
    /**
     * Method to validate an email address syntactically
     * @param email
     */
    public static void checkValidEmailAddress(String email) {
    	// TODO: improve the email validation check
    	if (email == null) {
    		throw new IllegalArgumentException("email can not be null");
    	}
    	if (email.indexOf('@') == -1) {
    		throw new IllegalArgumentException("email must contain an '@' character");
    	}
    }
    
     
    /**
     * Function to calculate the 
     * @param password String representation of the password to be hashed
     * @return byte array hashed password
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     */
    private byte[] calculateHashOfAPassword(String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
    	byte[] bytesOfMessage = password.getBytes("UTF-8");

    	MessageDigest md = MessageDigest.getInstance("MD5");
    	return md.digest(bytesOfMessage);
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email
				+ ", passwordHash=" + Arrays.toString(passwordHash) + "]";
	}

	public void update(User user) {
		name = user.name;
		email = user.email;
		passwordHash = user.passwordHash;
	}

    
}
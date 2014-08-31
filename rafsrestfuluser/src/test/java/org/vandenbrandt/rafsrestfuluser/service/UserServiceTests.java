package org.vandenbrandt.rafsrestfuluser.service;

import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.vandenbrandt.rafsrestfuluser.dao.UserDaoImpl;
import org.vandenbrandt.rafsrestfuluser.service.UserServiceImpl;

public class UserServiceTests {
	
    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserDaoImpl userDao;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }


	@Test
	public void testFindByIdMustCallFindByIdOnDao() {
		userService.findById(1);
		verify(userDao).findById(1);
		}
}

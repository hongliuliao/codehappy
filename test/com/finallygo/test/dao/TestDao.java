package com.finallygo.test.dao;

import junit.framework.TestCase;

import com.finallygo.dbutil.DaoFactory;
import com.finallygo.pojo.User;
import com.wanczy.fin.dao.UserDao;

public class TestDao extends TestCase {

	UserDao dao=(UserDao) DaoFactory.getDao(UserDao.class);
	protected void setUp() throws Exception {
	}

	public void testFindById() {
		assertTrue(dao.findById(1)!=null) ;
	}

	public void testRemoveUser() {
		fail("Not yet implemented");
	}

	public void testSaveUser() {
		fail("Not yet implemented");
	}

	public void testUpdateUser() {
		fail("Not yet implemented");
	}

	public void testCountUser() {
		dao.countUser(new User());
	}

}

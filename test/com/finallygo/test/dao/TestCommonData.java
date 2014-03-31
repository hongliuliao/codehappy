package com.finallygo.test.dao;

import com.finallygo.common.CommonData;

import junit.framework.TestCase;

public class TestCommonData extends TestCase {

	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testGetSmartName() {
		System.out.println(CommonData.getSmartName("user_password"));
		System.out.println();
	}

}

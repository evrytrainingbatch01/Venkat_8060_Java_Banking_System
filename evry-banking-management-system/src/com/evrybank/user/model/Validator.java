package com.evrybank.user.model;

import com.evrybank.admin.dao.EvryBankAdminDao;
import com.evrybank.admin.dao.impl.EvryBankAdminDaoImpl;

public class Validator {
	public EvryBankAdminDao evryBankAdminDao = new EvryBankAdminDaoImpl();

	public Customer validateUser(final String userName, final String password) {
		return evryBankAdminDao.checkUser(userName, password);
	}
}

package com.mini.service;

import java.util.List;

import com.mini.entity.UserAccounts;

public interface IUserService {

	public String saveOrUpdateUserAcc(UserAccounts userAccounts);
	public List<UserAccounts> getAllUserAccounts();
	public UserAccounts getUserAccount(Integer userId);
	public boolean deleteUserAcc(Integer userId);
	public boolean updateUserAccountStatus(Integer userId,String status);
}

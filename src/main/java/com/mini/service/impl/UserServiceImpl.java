package com.mini.service.impl;

import java.util.List;
import java.util.Optional;

import org.apache.catalina.authenticator.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.mini.constants.UserAccountsConstants;
import com.mini.entity.UserAccounts;
import com.mini.repo.UserAccountRepo;
import com.mini.service.IUserService;

@Service
@CacheConfig(cacheNames = "userAccountCache")
public class UserServiceImpl implements IUserService{
	
	Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
	
	public final static String CREATED=UserAccountsConstants.ACCOUNT_REGISTER_SUCCESS;
	public final static String UPDATED=UserAccountsConstants.ACCOUNT_UPDATED_SUCCESS;
	
	@Autowired
	private UserAccountRepo userAccRepo;
	@CacheEvict(cacheNames = "userAccounts", allEntries = true)
	public String saveOrUpdateUserAcc(UserAccounts userAccounts) {
		// TODO Auto-generated method stub
		Integer userId = userAccounts.getUserId();
		logger.info("user Id is :"+userId);
		//For the first time when the inserting the record Active status set to "Y" .
		if(userId==null) {
			userAccounts.setActiveSW("Y");
			logger.info("initially if user is null then put activeSW is :"+userAccounts.getActiveSW());
		}

		userAccRepo.save(userAccounts);
		if(userId==null) {
			logger.info("The status is : {}"+CREATED);
			return CREATED;
		}
		else {
			logger.info("The status is : {}",UPDATED);
			return UPDATED;
		}
	}
	@Cacheable(cacheNames = "userAccounts")
	public List<UserAccounts> getAllUserAccounts() {
		// TODO Auto-generated method stub
		List<UserAccounts> findAll = userAccRepo.findAll();
		System.out.println("fetched from database");
		return findAll;
	}
	@Cacheable(cacheNames = "userAccounts", key = "#userId", unless = "#result == null")
	public UserAccounts getUserAccount(Integer userId) {
		// TODO Auto-generated method stub
		Optional<UserAccounts> userRecord = userAccRepo.findById(userId);
		if(userRecord.isPresent()) {
			return userRecord.get();
		}
		return null;
	}
	@Caching(evict = { @CacheEvict(cacheNames = "userAccounts", key = "#userId"),
			@CacheEvict(cacheNames = "userAccounts", allEntries = true) })
	public boolean deleteUserAcc(Integer userId) {
		// TODO Auto-generated method stub
		boolean existsById = userAccRepo.existsById(userId);
		if(existsById) {
			userAccRepo.deleteById(userId);
			return true;
		}
		return false;		
	}

	@Override
	//@CacheEvict(cacheNames = "customers", allEntries = true)
	public boolean updateUserAccountStatus(Integer userId, String status) {
		// TODO Auto-generated method stub
		userAccRepo.updateUserAccStatus(userId, status);
		return false;
	}


}

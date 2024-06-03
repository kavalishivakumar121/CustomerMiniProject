package com.mini.service.impl;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.mini.entity.UserAccounts;
import com.mini.repo.UserAccountRepo;
import com.mini.service.IUserService;

@Service
public class UserServiceImpl implements IUserService{
	
	Logger logger=LoggerFactory.getLogger(UserServiceImpl.class);
	@Autowired
	private UserAccountRepo userAccRepo;

	@Override
	
	//@CachePut(value="UserAccounts",key="#userAccounts.getUserId()")
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
			logger.info("User Record saved");
			return "User Record Saved";
		}
		else {
			logger.info("User Record updated");
			return "User Record Updated";
		}
	}

	@Override
	@Cacheable(value="UserAccounts")
	public List<UserAccounts> getAllUserAccounts() {
		// TODO Auto-generated method stub
		List<UserAccounts> findAll = userAccRepo.findAll();
		System.out.println("fetched from database");
		return findAll;
	}

	@Override
	//@Cacheable(value="UserAccounts",key = "#userId")
	public UserAccounts getUserAccount(Integer userId) {
		// TODO Auto-generated method stub
		Optional<UserAccounts> userRecord = userAccRepo.findById(userId);
		if(userRecord.isPresent()) {
			return userRecord.get();
		}
		return null;
	}

	@Override
	//@CacheEvict(value="existsById",key="#userId")
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
	public boolean updateUserAccountStatus(Integer userId, String status) {
		// TODO Auto-generated method stub
		userAccRepo.updateUserAccStatus(userId, status);
		return false;
	}


}

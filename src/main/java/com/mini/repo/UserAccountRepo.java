package com.mini.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mini.entity.UserAccounts;

import jakarta.transaction.Transactional;

@Repository
public interface UserAccountRepo extends JpaRepository<UserAccounts, Integer> {

	@Modifying
	@Transactional
	@Query("update UserAccounts set activeSW=:status where userId=:userId")
	public void updateUserAccStatus(Integer userId,String status);
}

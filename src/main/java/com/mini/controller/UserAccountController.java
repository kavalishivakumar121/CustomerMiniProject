package com.mini.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mini.constants.UserAccountsConstants;
import com.mini.entity.UserAccounts;
import com.mini.service.IUserService;

@Controller
public class UserAccountController {
	
	Logger logger=LoggerFactory.getLogger(UserAccountController.class);
	 
	@Autowired
	private IUserService userService;
	
	public final static String SUCCESS=UserAccountsConstants.ACCOUNT_REGISTER_SUCCESS;
	public final static String UPDATED=UserAccountsConstants.ACCOUNT_UPDATED_SUCCESS;
	
	
	@GetMapping("/")
	public String index(Model model) {
		
		model.addAttribute("user",new UserAccounts());
		logger.info("displaying index page");
		return "index";
	}
	
	@PostMapping("/save-user")
	public String handleSubmitButton(@ModelAttribute ("user") UserAccounts user , Model model) {
		
		String  msg= userService.saveOrUpdateUserAcc(user);
		logger.info(msg);
		Integer userId=user.getUserId();
		logger.info("user Id is :"+userId);
		String message=msg+" with "+userId;
		model.addAttribute("msg",message);
		logger.info("message added to the model :"+msg);
		model.addAttribute("user",new UserAccounts());//For not showing the form data after submition of the record.form data will be cleared.
		if(msg.equals(SUCCESS)) {
			return "index";
		}
		if(msg.equals(UPDATED)) {
			return "redirect:/view-users";
		}
		return "index";
		
	}
	
	@GetMapping("/view-users")
	public String getUsers(Model model) {
		
		List<UserAccounts> allUsers = userService.getAllUserAccounts();
		model.addAttribute("allUsers",allUsers);
		return "view-users";
	}
	
	@GetMapping("/edit")
	public String editUser(@RequestParam("id") Integer userId,  Model model) {
		UserAccounts userAccount = userService.getUserAccount(userId);
		model.addAttribute("user",userAccount);
		return "updateAccount";
	}
	
	@GetMapping("/delete")
	public String deleteUserAcc(@RequestParam("id") Integer userId,Model model) {
		boolean deleteUserAcc = userService.deleteUserAcc(userId);
		if(deleteUserAcc) {
			model.addAttribute("msg",userId+"--"+"User Account Deleted.");
		}
		return "forward:/view-users";
	}
	
	@GetMapping("/update")
	public String updateUserAccStatus(@RequestParam("id") Integer userId,@RequestParam("status") String status,Model model) {
		
		userService.updateUserAccountStatus(userId, status);
		
		if("Y".equals(status)) {
			model.addAttribute("msg","User Account  activated");
		}
		else {
			model.addAttribute("msg","User Account De-activated");
				
		}
		
		return "forward:/view-users";
	}

}

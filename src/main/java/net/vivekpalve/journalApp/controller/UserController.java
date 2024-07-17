package net.vivekpalve.journalApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.vivekpalve.journalApp.Service.UserService;
import net.vivekpalve.journalApp.entities.User;

@RestController
@RequestMapping("/users")
public class UserController {

	
	
	@Autowired
	private UserService userService;
	
	
	@GetMapping
	public List<User> getAllUser() {
		
		List<User> users = userService.getAllUsers();
		return users;
	}
	
//	@PostMapping
//	public void createUser(@RequestBody User user) {
//		userService.createUser(user);
//	}
	
	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody User user){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User userInDb = userService.findByUserName(userName);
		userInDb.setUserName(user.getUserName());
		userInDb.setPassword(user.getPassword());
		userService.createUser(userInDb);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}
}

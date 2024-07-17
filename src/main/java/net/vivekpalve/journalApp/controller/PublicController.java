package net.vivekpalve.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.vivekpalve.journalApp.Service.UserService;
import net.vivekpalve.journalApp.entities.User;

@RestController
@RequestMapping("/public")
public class PublicController {

	
	@Autowired
	private UserService userService;
	
	@PostMapping("/create-user")
	public void createUser(@RequestBody User user) {
		userService.createUser(user);
	}
}

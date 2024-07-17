package net.vivekpalve.journalApp.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import net.vivekpalve.journalApp.entities.User;
import net.vivekpalve.journalApp.respository.UserRepository;

@Component
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();
	
	
	public void createUser(User user) {
		user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER"));
		userRepository.save(user);
	}
	public void saveUser(User user) {
		userRepository.save(user);
	}
	
	public List<User> getAllUsers(){
		List<User> users = userRepository.findAll();
		
		return users;
	}
	
	public Optional<User> getById(ObjectId Id) {
		return userRepository.findById(Id);
	}
	
	public void deleteById(ObjectId Id) {
		
		userRepository.deleteById(Id);
	}
	
	public User findByUserName(String userName) {
		User user = userRepository.findByUserName(userName);
		
		return user;
	}
	public void saveAdmin(User user) {
		user.setPassword(PASSWORD_ENCODER.encode(user.getPassword()));
		user.setRoles(Arrays.asList("USER","ADMIN"));
		userRepository.save(user);
	}
}

package net.vivekpalve.journalApp.respository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import net.vivekpalve.journalApp.entities.User;

public interface UserRepository extends MongoRepository<User, ObjectId> {

	
	User findByUserName(String userName);
}

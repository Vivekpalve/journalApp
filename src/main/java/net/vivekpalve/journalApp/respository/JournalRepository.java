package net.vivekpalve.journalApp.respository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import net.vivekpalve.journalApp.entities.Journal;

public interface JournalRepository extends MongoRepository<Journal, ObjectId>{

}

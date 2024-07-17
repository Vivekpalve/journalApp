package net.vivekpalve.journalApp.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.vivekpalve.journalApp.entities.Journal;
import net.vivekpalve.journalApp.entities.User;
import net.vivekpalve.journalApp.respository.JournalRepository;

@Component
public class JournalService {

	@Autowired
	private JournalRepository journalRepository;
	
	@Autowired
	private UserService userService;
	
	@Transactional
	public void createJournal(Journal journal,String userName) {
		
		try {
			User user = userService.findByUserName(userName);
			journal.setDate(LocalDateTime.now());
			Journal saved = journalRepository.save(journal);
			user.getJournalEntries().add(saved);
			userService.saveUser(user);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			throw new RuntimeException("An error occured when saving the entry",e);
		}
		
		
	}
	
	public void createJournal(Journal journal) {
		
		journalRepository.save(journal);
		
	}
	
	public List<Journal> getAllJournals(){
		List<Journal> Journals = journalRepository.findAll();
		
		return Journals;
	}
	
	public Optional<Journal> getById(ObjectId Id) {
		return journalRepository.findById(Id);
	}
	
	@Transactional
	public Boolean deleteById(ObjectId Id, String userName) {
		Boolean removed = false;
		try {
			User user = userService.findByUserName(userName);
			removed = user.getJournalEntries().removeIf(x -> x.getId().equals(Id));
			if(removed){
				userService.saveUser(user);
				journalRepository.deleteById(Id);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
			throw new RuntimeException("An error occured when Deleting the entry",e);
		}
		return removed;
	}
}

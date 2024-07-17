package net.vivekpalve.journalApp.controller;

import java.time.LocalDateTime;
import java.util.List;


import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.vivekpalve.journalApp.Service.JournalService;
import net.vivekpalve.journalApp.Service.UserService;
import net.vivekpalve.journalApp.entities.Journal;
import net.vivekpalve.journalApp.entities.User;

import java.util.Optional;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/journal")
public class JournalControllerv2 {
	
	//private Map<Long, Journal> journalList = new HashMap<>();
	
	@Autowired
	private JournalService journalService;
	
	
	@Autowired
	private UserService userService;
	
	
	
	@GetMapping()
	public ResponseEntity<?> getAllJournalsOfUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		 User user = userService.findByUserName(userName);
//	     List<Journal> allJournals = journalService.getAllJournals();
		 List<Journal> allJournals = user.getJournalEntries();
	     if(allJournals != null) {
	    	 return new ResponseEntity<List<Journal>>(allJournals ,HttpStatus.OK);
	     }
	     return new ResponseEntity<List<Journal>>(HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("id/{id}")
	public ResponseEntity<Journal> getJournalById(@PathVariable ObjectId id) {
		 Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		 String userName = authentication.getName();
		 User user = userService.findByUserName(userName);
		 List<Journal> journalList = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
		 if(!journalList.isEmpty()) {
			Optional<Journal>  journal= journalService.getById(id);
		    if(journal.isPresent()) {
		    	return new ResponseEntity<>(journal.get(), HttpStatus.OK);
		    }
		 }
	     return new ResponseEntity<Journal>(HttpStatus.NOT_FOUND);
	     
	}
	
	@DeleteMapping("id/{id}")
	public ResponseEntity<?> deleteJournalById(@PathVariable ObjectId id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		Boolean deleteById = journalService.deleteById(id,userName);
		
		if(deleteById) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}else {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping()
	public ResponseEntity<Journal> createJournal(@RequestBody Journal journal) {
		try {
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String userName = authentication.getName();
			journalService.createJournal(journal,userName);
			return new ResponseEntity<Journal>(journal,HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO: handle exception
			return new ResponseEntity<Journal>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PutMapping("id/{id}")
	public ResponseEntity<?> UpdateJournalById(@PathVariable ObjectId id ,@RequestBody Journal journal) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User user = userService.findByUserName(userName);
		 List<Journal> journalList = user.getJournalEntries().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
		 if(!journalList.isEmpty()) {
			Optional<Journal>  journalEntry= journalService.getById(id);
		    if(journalEntry.isPresent()) {
		    	Journal journalById = journalEntry.get();
				journalById.setTitle(journal.getTitle() !=null && !journal.getTitle().equals("") ? journal.getTitle() : journalById.getTitle());
				journalById.setContent(journal.getContent() !=null && !journal.getContent().equals("") ? journal.getContent() :journalById.getContent());
				journalService.createJournal(journalById);
				return new ResponseEntity<>(journalById,HttpStatus.OK);
		    }
		 }
//		Journal journalById = journalService.getById(id).orElseThrow(null);
//		if(journalById != null) {
//		}
		 return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}

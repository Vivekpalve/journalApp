package net.vivekpalve.journalApp.entities;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Document(collection = "Users")
@Data
@NoArgsConstructor
public class User {

	@Id
	private ObjectId id;
	
	@Indexed(unique = true)
	@NonNull
	private String userName;
	@NonNull
	private String password;
	
	@DBRef
	private List<Journal> journalEntries = new ArrayList<>();
	
	private List<String> roles;

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Journal> getJournalEntries() {
		return journalEntries;
	}

	public void setJournalEntries(List<Journal> journalEntries) {
		this.journalEntries = journalEntries;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	
	
	
	
	
}

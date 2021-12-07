package com.rafabene.demo.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Message")
public class Message implements Serializable {

    @Id
	@GeneratedValue 
	private Long id;

	@NotNull
	@Size(min = 3, max = 20)
	private String username;

	@NotNull
	@Size(min = 5)
	private String message;

	private Date timestamp = new Date();

    public Message() {

	}

	public Message(String username, String message) {
		this.username = username;
		this.message = message;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getId() {
		return id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

}

package com.oneq.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "questions")
public class Question {
	@Id
	@GeneratedValue
	public Long id;
	public String content;
	private String path;
	private String pass;
	private Date deadline;
	@Column(name = "create_user")
	private String createUser;
	@Column(name = "create_date")
	private Date createDate;
	
	@OneToMany
	private List<User> users;
	
	@OneToMany
	private List<Choice> choices;
}

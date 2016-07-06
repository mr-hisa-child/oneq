package com.oneq.entity;

import java.util.Date;
import java.util.List;

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
@Table(name = "contents")
public class Content {
	@Id
	@GeneratedValue
	private Long id;
	private String title;
	private String path;
	private String pass;
	private Date deadline;
	private Date created;
	@OneToMany
	private List<User> users;
}

package com.oneq.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue
	private Long id;
	private String name;
	private Date created;
	@ManyToOne
	private Content content;
	
}

package com.oneq.entity;

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
@Table(name = "choices")
public class Choice {
	@Id
	@GeneratedValue
	private Long id;
	private String content;
	@ManyToOne
	private Question question;
}

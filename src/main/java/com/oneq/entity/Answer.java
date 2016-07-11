package com.oneq.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.*;


import lombok.Data;
import lombok.ToString;

@Data
@ToString
@Entity
@Table(name = "answers")
public class Answer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private Date answerDate;
	@ManyToOne
	@JoinColumn(name = "questions_id")
	private Question question;
	@JoinTable(name = "answers_choices", joinColumns = {
	        @JoinColumn(name = "answers_id", referencedColumnName = "id")}, inverseJoinColumns = {
	        @JoinColumn(name = "choices_id", referencedColumnName = "id")})
	@ManyToMany
	private List<Choice> choices;
	
}

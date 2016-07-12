package com.oneq.entity;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String content;
	@ManyToOne
	@JoinColumn(name = "questions_id")
	private Question question;
	@JoinTable(name = "answers_choices", joinColumns = {
	        @JoinColumn(name = "choices_id", referencedColumnName = "id")}, inverseJoinColumns = {
	        @JoinColumn(name = "answers_id", referencedColumnName = "id")})
	@ManyToMany
	private List<Answer> answers;
	
	public Choice(){
		
	}
	public Choice(Long id){
		this.id = id;
	}
}

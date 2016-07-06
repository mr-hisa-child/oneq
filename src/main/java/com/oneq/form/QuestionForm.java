package com.oneq.form;

import java.util.List;

import lombok.Data;

@Data
public class QuestionForm {
	private String content;
	private String createUser;
	private String pass;
	private String deadline;
	private String isMulti;
	private String userRequired;
	private List<String> choices;
}

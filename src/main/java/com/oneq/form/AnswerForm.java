package com.oneq.form;

import java.util.List;

import lombok.Data;

@Data
public class AnswerForm {
	private String name;
	private List<Long> choices;
}

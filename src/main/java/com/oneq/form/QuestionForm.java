package com.oneq.form;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.oneq.validation.Choice;

import lombok.Data;

@Data
public class QuestionForm {
	@NotEmpty(message = "質問を入力してください")
	private String content;
	@Choice(message = "選択肢は２つ以上設定してください")
	private List<String> choices = new ArrayList<>();
}

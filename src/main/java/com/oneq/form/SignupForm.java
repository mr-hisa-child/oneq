package com.oneq.form;

import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class SignupForm {
	@NotEmpty(message = "パスコードを入力してください")
	private String path;
	@NotEmpty(message = "管理パスワードを入力してください")
	private String adminPass;
}

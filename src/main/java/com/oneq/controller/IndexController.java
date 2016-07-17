package com.oneq.controller;

import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oneq.entity.Question;
import com.oneq.form.SignupForm;
import com.oneq.service.QuestionService;

@Controller
public class IndexController {

	private static final String FORWARD_INDEX = "index";
	private static final String REDIRECT_FORM = "redirect:/question/new";
	private static final String REDIRECT_HOME = "redirect:/question/home";
	private static final String REDIRECT_ANSWER = "redirect:/answer/form";

	@Autowired
	private QuestionService questionService;

	@Autowired
	private HttpSession session;

	/**
	 * トップ画面表示
	 * 
	 * @param signupForm
	 * @param model
	 * @return トップ画面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String index(@ModelAttribute SignupForm signupForm, Model model) {
		return FORWARD_INDEX;
	}

	/**
	 * アンケート作成時の認証登録
	 * 
	 * @param signupForm
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(params = "create", method = RequestMethod.POST)
	public String create(@ModelAttribute @Valid SignupForm signupForm, final BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return FORWARD_INDEX;
		}

		Optional<Question> question = Optional.ofNullable(this.questionService.findByPath(signupForm.getPath()));
		if (question.isPresent()) {
			bindingResult.rejectValue("path", null, "別のコードを入力してください");
			return FORWARD_INDEX;
		}

		final Question entity = new Question();
		entity.setPath(signupForm.getPath());
		entity.setAdminPass(signupForm.getAdminPass());
		this.questionService.create(entity);

		session.setAttribute("path", signupForm.getPath());

		return REDIRECT_FORM;
	}

	/**
	 * 集計結果閲覧時の認証
	 * 
	 * @param signupForm
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(params = "summary", method = RequestMethod.POST)
	public String summary(@ModelAttribute @Valid SignupForm signupForm, final BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return FORWARD_INDEX;
		}

		Optional<Question> question = Optional.ofNullable(this.questionService.findByPath(signupForm.getPath()));
		if (!question.isPresent()) {
			bindingResult.rejectValue("path", null, "OneQコードが違います");
			return FORWARD_INDEX;
		}

		if (!question.get().getAdminPass().equals(signupForm.getAdminPass())) {
			bindingResult.rejectValue("adminPass", null, "パスワードが違います");
			return FORWARD_INDEX;
		}

		session.setAttribute("path", question.get().getPath());

		return REDIRECT_HOME;
	}

	/**
	 * 回答時の認証
	 * 
	 * @param signupForm
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(params = "answer", method = RequestMethod.POST)
	public String answer(@ModelAttribute SignupForm signupForm, final BindingResult bindingResult) {

		Optional<Question> question = Optional.ofNullable(this.questionService.findByPath(signupForm.getPath()));
		if (!question.isPresent()) {
			bindingResult.rejectValue("path", null, "入力されたOneQコードは存在しません");
			return FORWARD_INDEX;
		}

		session.setAttribute("path", signupForm.getPath());

		return REDIRECT_ANSWER;
	}
}

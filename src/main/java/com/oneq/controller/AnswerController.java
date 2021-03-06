package com.oneq.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oneq.entity.Answer;
import com.oneq.entity.Choice;
import com.oneq.entity.Question;
import com.oneq.form.AnswerForm;
import com.oneq.service.AnswerService;
import com.oneq.service.QuestionService;
import com.oneq.util.Constant;
import com.oneq.util.DateUtil;

@Controller
@RequestMapping(Constant.ControllerPath.ANSWER_ROOT)
public class AnswerController {

	private static final String FORWARD_FORM = "answer/form";
	private static final String FORWARD_COMPLETE = "answer/complete";
	private static final String REDIRECT_COMPLETE = "redirect:/answer/complete";

	@Autowired
	private AnswerService answerService;

	@Autowired
	private QuestionService questionService;

	@Autowired
	private HttpSession session;

	/**
	 * アンケート登録画面表示
	 * 
	 * @return アンケート登録画面
	 */
	@RequestMapping(value = Constant.ControllerPath.ANSWER_FORM, method = RequestMethod.GET)
	public String form(@ModelAttribute AnswerForm answerForm, final Model model) {

		String path = (String) session.getAttribute("path");

		Optional<Question> question = Optional.ofNullable(this.questionService.findByPath(path));

		if (!question.isPresent()) {
			return Constant.PAGE_404;
		}

		model.addAttribute("question", question.get().content);

		Map<Long, String> choices = new LinkedHashMap<>();
		question.get().getChoices().stream().forEach(c -> choices.put(c.getId(), c.getContent()));
		model.addAttribute("choices", choices);

		return FORWARD_FORM;
	}

	/**
	 * アンケート登録
	 * 
	 * @return リダイレクト先
	 */
	@RequestMapping(value = Constant.ControllerPath.ANSWER_SEND, method = RequestMethod.POST)
	public String update(@Validated @ModelAttribute final AnswerForm form, final BindingResult bindingResult) {

		String path = (String) session.getAttribute("path");

		Optional<Question> question = Optional.ofNullable(this.questionService.findByPath(path));

		if (!question.isPresent()) {
			return Constant.PAGE_404;
		}

		if (bindingResult.hasErrors()) {
			return FORWARD_FORM;
		}

		final List<Choice> choices = new ArrayList<>();
		form.getChoices().stream().forEach(c -> choices.add(new Choice(c)));

		Answer answer = new Answer();
		answer.setQuestion(question.get());
		answer.setAnswerDate(DateUtil.now());
		answer.setChoices(choices);

		this.answerService.create(answer);

		return REDIRECT_COMPLETE;
	}

	/**
	 * 完了画面表示
	 * 
	 * @return 完了画面
	 */
	@RequestMapping(value = Constant.ControllerPath.ANSWER_COMPLETE, method = RequestMethod.GET)
	public String complete() {
		session.invalidate();
		return FORWARD_COMPLETE;
	}

}

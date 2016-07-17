package com.oneq.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.oneq.form.QuestionForm;
import com.oneq.service.ChoiceService;
import com.oneq.service.QuestionService;
import com.oneq.util.Constant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Controller
@RequestMapping(Constant.ControllerPath.QUESTION_ROOT)
public class QuestionController {

	private static final String FORWARD_FORM = "question/form";
	private static final String FORWARD_HOME = "question/home";
	private static final String REDIRECT_HOME = "redirect:/question/home";

	@Autowired
	private QuestionService questionService;

	@Autowired
	private ChoiceService choiceService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private HttpSession session;

	/**
	 * アンケート登録画面表示
	 * 
	 * @return アンケート登録画面
	 */
	@RequestMapping(value = Constant.ControllerPath.QUESTION_FORM, method = RequestMethod.GET)
	public String form(@ModelAttribute QuestionForm questionForm, final Model model) {
		return FORWARD_FORM;
	}

	/**
	 * アンケート登録画面表示
	 * 
	 * @return アンケート登録画面
	 */
	@RequestMapping(value = Constant.ControllerPath.QUESTION_EDIT, method = RequestMethod.GET)
	public String edit(@ModelAttribute QuestionForm questionForm) {
		return FORWARD_FORM;
	}

	/**
	 * アンケート登録
	 * 
	 * @return リダイレクト先
	 */
	@RequestMapping(value = Constant.ControllerPath.QUESTION_UPDATE, method = RequestMethod.POST)
	public String update(@Validated @ModelAttribute final QuestionForm form, final BindingResult bindingResult) {
		return REDIRECT_HOME;
	}

	/**
	 * ホーム画面表示
	 * 
	 * @return ホーム画面
	 */
	@RequestMapping(value = Constant.ControllerPath.QUESTION_HOME, method = RequestMethod.GET)
	public String home(final Model model) {
		return FORWARD_HOME;
	}

	@Data
	@AllArgsConstructor
	public class Summary {
		private String choice;
		private int count;

	}

}

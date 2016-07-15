package com.oneq.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.oneq.entity.Answer;
import com.oneq.entity.Choice;
import com.oneq.entity.Question;
import com.oneq.form.AnswerForm;
import com.oneq.form.QuestionForm;
import com.oneq.form.SigninForm;
import com.oneq.form.SignupForm;
import com.oneq.repository.QuestionRepository;
import com.oneq.service.AnswerService;
import com.oneq.service.QuestionService;
import com.oneq.util.Constant;
import com.oneq.util.DateUtil;

@Controller
@RequestMapping(Constant.ControllerPath.ANSWER_ROOT)
public class AnswerController {
	
	private static final String FORWARD_INDEX = "answer/index";
	private static final String FORWARD_FORM = "answer/form";
	private static final String FORWARD_COMPLETE = "answer/complete";
	private static final String REDIRECT_FORM = "redirect:/answer/form";
	private static final String REDIRECT_COMPLETE = "redirect:/answer/complete";
	
	@Autowired
    private AnswerService answerService;
	
	@Autowired
	private QuestionService questionService;
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(@ModelAttribute SigninForm signinForm){
		return FORWARD_INDEX;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String create(@Validated @ModelAttribute final SigninForm form,final BindingResult bindingResult){	
		return REDIRECT_FORM;
	}

	/**
	 * アンケート登録画面表示
	 * @return アンケート登録画面
	 */
	@RequestMapping(value=Constant.ControllerPath.ANSWER_FORM,method = RequestMethod.GET)
	public String form(@ModelAttribute AnswerForm answerForm,final Model model){
		return FORWARD_FORM;
	}
	
	/**
	 * アンケート登録
	 * @return リダイレクト先
	 */
	@RequestMapping(value = Constant.ControllerPath.ANSWER_SEND,method = RequestMethod.POST)
	public String update(@Validated @ModelAttribute final AnswerForm form,final BindingResult bindingResult){
		return REDIRECT_COMPLETE;
	}
	
	/**
	 * 完了画面表示
	 * @return 完了画面
	 */
	@RequestMapping(value = Constant.ControllerPath.ANSWER_COMPLETE,method = RequestMethod.GET)
	public String complete(){
		return FORWARD_COMPLETE;
	}
	
}

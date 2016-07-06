package com.oneq.controller;

import java.text.MessageFormat;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oneq.entity.Question;
import com.oneq.form.QuestionForm;
import com.oneq.repository.QuestionRepository;
import com.oneq.service.QuestionService;
import com.oneq.util.Constant;
import com.oneq.util.DateUtil;

@Controller
@RequestMapping(Constant.ControllerPath.QUESTION_ROOT)
public class QuestionController {
	
	@Autowired
    private QuestionService questionService;

	/**
	 * アンケート登録画面表示
	 * @return アンケート登録画面
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String form(){
		return "question/form";
	}
	
	/**
	 * アンケート登録
	 * @return リダイレクト先
	 */
	@RequestMapping(value = Constant.ControllerPath.QUESTION_CREATE,method = RequestMethod.POST)
	public String create(QuestionForm form){
		Question entry = new Question();
		entry.setContent(form.getContent());
		entry.setCreateUser(form.getCreateUser());
		entry.setPass(form.getPass());
		entry.setDeadline(DateUtil.toDate(form.getDeadline(), DateUtil.FORMAT_YYYYMMDD));
		final Question question = this.questionService.create(entry);
		final StringBuilder sb = new StringBuilder();
		sb.append(Constant.REDIRECT);
		sb.append(Constant.ControllerPath.QUESTION_ROOT);
		sb.append(Constant.SEP_SLASH);
		sb.append(question.getPath());
		sb.append(Constant.ControllerPath.QUESTION_HOME);
		return sb.toString();
	}
	
	/**
	 * ホーム画面表示
	 * @return ホーム画面
	 */
	@RequestMapping(value = Constant.ControllerPath.QUESTION_HOME,method = RequestMethod.GET)
	public String home(){
		return "question/home";
	}
	
}

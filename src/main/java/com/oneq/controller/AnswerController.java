package com.oneq.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
public class AnswerController {
	
	@Autowired
    private AnswerService answerService;
	
	@Autowired
	private QuestionService questionService;
	
	@RequestMapping(value = Constant.ControllerPath.ANSWER_ROOT,method = RequestMethod.GET)
	public String index(@PathVariable("path") final String path,@ModelAttribute SigninForm signinForm){
		Optional<Question> question = Optional.ofNullable(this.questionService.findByPath(path));
		if(!question.isPresent()){
			return "error/404";
		}
		return "answer/index";
	}
	
	@RequestMapping(value = Constant.ControllerPath.ANSWER_ROOT,method = RequestMethod.POST)
	public String create(@PathVariable("path") final String path,@Validated @ModelAttribute final SigninForm form,final BindingResult bindingResult,final RedirectAttributes attributes){
		
		Optional<Question> question = Optional.ofNullable(this.questionService.findByPath(path));
		if(!question.isPresent()){
			return "error/404";
		}
		
		if(!question.get().getPass().equals(form.getPass())){
			bindingResult.reject("error.invalid.pass","default");
			return "answer/index";
		}
		
		return new StringBuilder()
				.append(Constant.REDIRECT)
				.append(Constant.SEP_SLASH)
				.append(path)
				.append("/answer/form")
				.toString();
	}

	/**
	 * アンケート登録画面表示
	 * @return アンケート登録画面
	 */
	@RequestMapping(value=Constant.ControllerPath.ANSWER_FORM,method = RequestMethod.GET)
	public String form(@PathVariable("path") final String path,@ModelAttribute AnswerForm answerForm,final Model model){
		Optional<Question> question = Optional.ofNullable(this.questionService.findByPath(path));
		
		if(!question.isPresent()){
			// error
		}
		model.addAttribute("question",question.get().content);
		Map<Long,String> choices = new LinkedHashMap<>();
		question.get().getChoices().stream().forEach(c -> choices.put(c.getId(), c.getContent()));
		model.addAttribute("choices",choices);
		return "answer/form";
	}
	
	/**
	 * アンケート登録
	 * @return リダイレクト先
	 */
	@RequestMapping(value = Constant.ControllerPath.ANSWER_SEND,method = RequestMethod.POST)
	public String update(@PathVariable("path") final String path,@Validated @ModelAttribute final AnswerForm form,final BindingResult bindingResult){
		
		Optional<Question> question = Optional.ofNullable(this.questionService.findByPath(path));
		
		if(!question.isPresent()){
			// error
		}
		
		if (bindingResult.hasErrors()) {
            return "answer/index";
        }
		
		Answer answer = new Answer();
		answer.setQuestion(question.get());
		answer.setAnswerDate(DateUtil.now());
		answer.setName(form.getName());
		final List<Choice> choices = new ArrayList<>();
		form.getChoices().stream().forEach(c -> choices.add(new Choice(c)));
		answer.setChoices(choices);
		
		this.answerService.create(answer);
		
		return new StringBuilder()
				.append(Constant.REDIRECT)
				.append(Constant.SEP_SLASH)
				.append(path)
				.append("/answer/complete")
				.toString();
	}
	
	/**
	 * 完了画面表示
	 * @return 完了画面
	 */
	@RequestMapping(value = Constant.ControllerPath.ANSWER_COMPLETE,method = RequestMethod.GET)
	public String complete(@PathVariable("path") final String path){
		return "answer/complete";
	}
	
}

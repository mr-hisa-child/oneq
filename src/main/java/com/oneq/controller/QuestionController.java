package com.oneq.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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

import com.oneq.entity.Choice;
import com.oneq.entity.Question;
import com.oneq.form.QuestionForm;
import com.oneq.form.SignupForm;
import com.oneq.repository.QuestionRepository;
import com.oneq.service.ChoiceService;
import com.oneq.service.QuestionService;
import com.oneq.util.Constant;
import com.oneq.util.DateUtil;

@Controller
public class QuestionController {
	
	@Autowired
    private QuestionService questionService;
	
	@Autowired
	private ChoiceService choiceService;
	
	@Autowired
	private HttpServletRequest request;
	
	@RequestMapping(value = Constant.ControllerPath.QUESTION_ROOT,method = RequestMethod.GET)
	public String index(@ModelAttribute SignupForm signupForm,Model model){
		
		signupForm.setPath(questionService.getGeneratePath());
		System.out.println(request.getRequestURL());
		model.addAttribute("url",request.getRequestURL());
		
		return "index";
	}
	
	@RequestMapping(value = Constant.ControllerPath.QUESTION_CREATE,method = RequestMethod.POST)
	public String create(@Validated @ModelAttribute final SignupForm form,final BindingResult bindingResult,final RedirectAttributes attributes){
		
		if (bindingResult.hasErrors()) {
            return "index";
        }
		
		final Question question = new Question();
		question.setPath(form.getPath());
		question.setAdminPass(form.getAdminPass());
		this.questionService.create(question);
		
		return new StringBuilder()
				.append(Constant.REDIRECT)
				.append(Constant.SEP_SLASH)
				.append(form.getPath())
				.append("/question/new")
				.toString();
	}

	/**
	 * アンケート登録画面表示
	 * @return アンケート登録画面
	 */
	@RequestMapping(value=Constant.ControllerPath.QUESTION_FORM,method = RequestMethod.GET)
	public String form(@PathVariable("path") final String path,@ModelAttribute QuestionForm questionForm,final Model model){
		
		Optional<Question> question = Optional.ofNullable(this.questionService.findByPath(path));
		
		if(!question.isPresent()){
			// NotFound
		}
		
		
		return "question/form";
	}
	
	/**
	 * アンケート登録
	 * @return リダイレクト先
	 */
	@RequestMapping(value = Constant.ControllerPath.QUESTION_UPDATE,method = RequestMethod.POST)
	public String update(@PathVariable("path") final String path,@Validated @ModelAttribute final QuestionForm form){
		
		Optional<Question> question = Optional.ofNullable(this.questionService.findByPath(path));
		
		if(!question.isPresent()){
			
		}
		Question entry = question.get();
		entry.setContent(form.getContent());
		entry.setCreateUser(form.getCreateUser());
		entry.setPass(form.getPass());
		entry.setDeadline(DateUtil.toDate(form.getDeadline(), DateUtil.FORMAT_YYYYMMDD));
		this.questionService.update(entry);
		
		List<Choice> choices = new ArrayList<>();
		form.getChoices().stream().forEach(c -> {
			Choice choice = new Choice();
			choice.setContent(c);
			choice.setQuestion(entry);
			choices.add(choice);
		});
		this.choiceService.craete(choices);
		
		return new StringBuilder()
				.append(Constant.REDIRECT)
				.append(Constant.SEP_SLASH)
				.append(path)
				.append("/question")
				.toString();
	}
	
	/**
	 * ホーム画面表示
	 * @return ホーム画面
	 */
	@RequestMapping(value = Constant.ControllerPath.QUESTION_HOME,method = RequestMethod.GET)
	public String home(@PathVariable("path") final String path,final Model model){
		Optional<Question> question = Optional.ofNullable(this.questionService.findByPath(path));
		
		if(!question.isPresent()){
			// Not Found
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(request.getScheme())
		.append("://")
		.append(request.getServerName());
		if(request.getServerPort() != 80){
			sb.append(":").append(request.getServerPort());
		}
		sb.append(request.getContextPath()).append("/")
		.append(path)
		.append("/answer");
		
		model.addAttribute("url",sb.toString());
		
		return "question/home";
	}
	
}

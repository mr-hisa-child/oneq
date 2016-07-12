package com.oneq.controller;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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

import lombok.AllArgsConstructor;
import lombok.Data;

@Controller
@RequestMapping(Constant.ControllerPath.QUESTION_ROOT)
public class QuestionController {
	
	private static final String FORWARD_INDEX = "index";
	private static final String FORWARD_FORM = "question/form";
	private static final String FORWARD_HOME = "question/home";
	private static final String FORWARD_SIGNIN = "question/index";
	private static final String REDIRECT_FORM = "redirect:/question/new";
	private static final String REDIRECT_HOME = "redirect:/question/home";
	
	@Autowired
    private QuestionService questionService;
	
	@Autowired
	private ChoiceService choiceService;
	
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private HttpSession session;
	
	@RequestMapping(method = RequestMethod.GET)
	public String index(@ModelAttribute SignupForm signupForm,Model model){
		
		signupForm.setPath(questionService.getGeneratePath());
		System.out.println(request.getRequestURL());
		model.addAttribute("url",request.getRequestURL());
		
		return FORWARD_INDEX;
	}
	
	@RequestMapping(value = Constant.ControllerPath.QUESTION_SIGNIN,method = RequestMethod.GET)
	public String signin(@ModelAttribute SignupForm signupForm){
		return FORWARD_SIGNIN;
	}
	
	@RequestMapping(value = Constant.ControllerPath.QUESTION_SIGNIN,method = RequestMethod.POST)
	public String signin(@ModelAttribute SignupForm signupForm,final BindingResult bindingResult){
		
		if (bindingResult.hasErrors()) {
            return FORWARD_SIGNIN;
        }
		
		Optional<Question> question = Optional.ofNullable(this.questionService.findByPath(signupForm.getPath()));
		if(!question.isPresent()){
			bindingResult.rejectValue("path",null,"OneQコードが違います");
			return FORWARD_SIGNIN;
		}
		
		if(!question.get().getAdminPass().equals(signupForm.getAdminPass())){
			bindingResult.rejectValue("adminPass",null,"パスワードが違います");
			return FORWARD_SIGNIN;
		}
		
		session.setAttribute("path", question.get().getPath());
		
		return REDIRECT_HOME;
	}
	
	@RequestMapping(value = Constant.ControllerPath.QUESTION_CREATE,method = RequestMethod.POST)
	public String create(@Validated @ModelAttribute final SignupForm form,final BindingResult bindingResult){
		
		if (bindingResult.hasErrors()) {
            return FORWARD_INDEX;
        }
		
		Optional<Question> question = Optional.ofNullable(this.questionService.findByPath(form.getPath()));
		if(question.isPresent()){
			bindingResult.rejectValue("path",null,"別のコードを入力してください");
			return FORWARD_INDEX;
		}
		
		final Question entity = new Question();
		entity.setPath(form.getPath());
		entity.setAdminPass(form.getAdminPass());
		this.questionService.create(entity);
		
		session.setAttribute("path", form.getPath());
		
		return REDIRECT_FORM;
	}

	/**
	 * アンケート登録画面表示
	 * @return アンケート登録画面
	 */
	@RequestMapping(value=Constant.ControllerPath.QUESTION_FORM,method = RequestMethod.GET)
	public String form(@ModelAttribute QuestionForm questionForm,final Model model){
		
		final String path = (String)session.getAttribute("path");
		Optional<Question> question = Optional.ofNullable(this.questionService.findByPath(path));
		
		if(!question.isPresent()){
			return Constant.PAGE_404;
		}
		
		return FORWARD_FORM;
	}
	
	/**
	 * アンケート登録画面表示
	 * @return アンケート登録画面
	 */
	@RequestMapping(value=Constant.ControllerPath.QUESTION_EDIT,method = RequestMethod.GET)
	public String edit(@ModelAttribute QuestionForm questionForm){
		
		final String path = (String)session.getAttribute("path");
		Optional<Question> question = Optional.ofNullable(this.questionService.findByPath(path));
		
		if(!question.isPresent()){
			return Constant.PAGE_404;
		}
		
		questionForm.setContent(question.get().getContent());
		question.get().getChoices().stream().forEach(c -> questionForm.getChoices().add(c.getContent()));
		
		return FORWARD_FORM;
	}
	
	/**
	 * アンケート登録
	 * @return リダイレクト先
	 */
	@RequestMapping(value = Constant.ControllerPath.QUESTION_UPDATE,method = RequestMethod.POST)
	public String update(@Validated @ModelAttribute final QuestionForm form,final BindingResult bindingResult){
		
		final String path = (String)session.getAttribute("path");
		Optional<Question> question = Optional.ofNullable(this.questionService.findByPath(path));
		
		if(!question.isPresent()){
			return Constant.PAGE_404;
		}
		
		if(bindingResult.hasErrors()){
			return FORWARD_FORM;
		}
		
		Question entry = question.get();
		entry.setContent(form.getContent());
		this.questionService.update(entry);
		
		this.choiceService.deleteAll(entry.getId());
		
		List<Choice> choices = new ArrayList<>();
		form.getChoices().stream().forEach(c -> {
			Choice choice = new Choice();
			choice.setContent(c);
			choice.setQuestion(entry);
			choices.add(choice);
		});
		this.choiceService.craete(choices);
		
		return REDIRECT_HOME;
	}
	
	/**
	 * ホーム画面表示
	 * @return ホーム画面
	 */
	@RequestMapping(value = Constant.ControllerPath.QUESTION_HOME,method = RequestMethod.GET)
	public String home(final Model model){
		
		final String path = (String)session.getAttribute("path");
		Optional<Question> question = Optional.ofNullable(this.questionService.findByPath(path));
		
		if(!question.isPresent()){
			return Constant.PAGE_404;
		}
		
		List<Choice> choices = this.choiceService.findAll(question.get().getId());
		final List<Summary> summaryList = new ArrayList<>();
		choices.stream().forEach(c -> summaryList.add(new Summary(c.getContent(),c.getAnswers().size())));
		
		model.addAttribute("summaryList", summaryList);
		
		StringBuilder sb = new StringBuilder();
		sb.append(request.getScheme())
		.append("://")
		.append(request.getServerName());
		if(request.getServerPort() != 80){
			sb.append(":").append(request.getServerPort());
		}
		sb.append(request.getContextPath())
		.append(Constant.SEP_SLASH)
		.append(Constant.ControllerPath.ANSWER_ROOT);
		
		model.addAttribute("url",sb.toString());
		
		return FORWARD_HOME;
	}
	
	@Data
	@AllArgsConstructor
	public class Summary{
		private String choice;
		private int count;
		
	}
	
}

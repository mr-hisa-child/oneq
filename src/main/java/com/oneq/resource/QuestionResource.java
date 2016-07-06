package com.oneq.resource;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.oneq.entity.Question;
import com.oneq.repository.QuestionRepository;
import com.oneq.service.QuestionService;

@RestController
public class QuestionResource {
	
	@Autowired
    private QuestionService questionService;
	
//	@RequestMapping("/")
//	public List<Question> find(){
//		return questionService.findAll();
//	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Question find(@PathVariable Long id){
		return questionService.find(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Question create(@RequestBody @Valid final Question question){
//		questionService.save(question);
		
		return null;
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public Question update(@RequestBody @Valid final Question question){
		return null;
	}
}

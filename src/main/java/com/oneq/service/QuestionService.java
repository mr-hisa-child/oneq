package com.oneq.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.oneq.entity.Question;
import com.oneq.form.QuestionForm;
import com.oneq.repository.QuestionRepository;
import com.oneq.util.DateUtil;

@Service
public class QuestionService {
	@Qualifier("questionRepository")
    @Autowired
	private QuestionRepository questionRepository;

	public Question find(Long id) {
		return this.questionRepository.findOne(id);
	}
	
	public Question findByPath(final String path){
		return this.questionRepository.findByPath(path);
	}
	
	public Question create(Question entity){
//		String path = RandomStringUtils.randomAlphanumeric(8);
//		Question question = this.questionRepository.findByPath(path);
//		if(question != null){
//			return create(entity);
//		}
//		entity.setPath(path);
		entity.setCreateDate(DateUtil.now());
		
		return this.questionRepository.save(entity);
	}
	
	public Question update(Question entity) {
		
		return this.questionRepository.save(entity);
	}
	
	public String getGeneratePath(){
		String path = RandomStringUtils.randomAlphanumeric(8);
		Optional<Question> question = Optional.ofNullable(this.questionRepository.findByPath(path));
		if(question.isPresent()){
			return getGeneratePath();
		}
		return path;
	}
}

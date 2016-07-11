package com.oneq.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.oneq.entity.Answer;
import com.oneq.entity.Question;
import com.oneq.repository.AnswerRepository;
import com.oneq.repository.QuestionRepository;
import com.oneq.util.DateUtil;

@Service
public class AnswerService {
	
    @Autowired
	private AnswerRepository asnwerRepository;

	public Answer create(final Answer answer){
		return this.asnwerRepository.save(answer);
	}
}

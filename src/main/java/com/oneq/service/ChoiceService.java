package com.oneq.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oneq.entity.Choice;
import com.oneq.repository.ChoiceRepository;

@Service
public class ChoiceService {
	@Autowired
	private ChoiceRepository choiceRepository;
	
	public void create(Choice entity){
		this.choiceRepository.save(entity);
	}
	
	public void craete(List<Choice> entitys){
		this.choiceRepository.save(entitys);
	}
}

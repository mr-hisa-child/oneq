package com.oneq.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oneq.entity.Question;
import com.oneq.entity.Answer;
import com.oneq.entity.Choice;

@Repository
public interface ChoiceRepository extends JpaRepository<Choice,Long>{
	public List<Choice> findByQuestionId(Long questionId);
}

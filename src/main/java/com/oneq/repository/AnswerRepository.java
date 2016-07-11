package com.oneq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oneq.entity.Question;
import com.oneq.entity.Answer;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long>{
	
}

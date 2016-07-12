package com.oneq.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.oneq.entity.Question;

@Repository
public interface QuestionRepository extends JpaRepository<Question,Long>{
	public Question findByPath(final String path);
}

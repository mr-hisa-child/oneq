package com.oneq.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ChoiceValidator implements ConstraintValidator<Choice, List<String>> {

	@Override
	public void initialize(Choice arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isValid(List<String> obj, ConstraintValidatorContext arg1) {
		
		if(obj == null || obj.size() < 2){
			return false;
		}
		return true;
	}

}

package com.oneq.util;

public class Constant {
	
	public static final String REDIRECT = "redirect:";
	public static final String SEP_SLASH = "/";
	
	public static class ControllerPath {
		public static final String QUESTION_ROOT = "/question";
		public static final String QUESTION_FORM = "/";
		public static final String QUESTION_CREATE = "/create";
		public static final String QUESTION_HOME = "/{path}/home";
		
		public static final String ANSWER_ROOT = "/answer";
		public static final String ANSWER_FORM = "/{path}";
		public static final String ANSWER_SEND = "/{path}/send";
		public static final String ANSWER_COMPLETE = "/{path}/complete";
	}
	
	public static class ResourcePath {
		public static final String QUESTION = "/questions";
		public static final String QUESTION_FIND = "/{id}";
		
	}
	
}

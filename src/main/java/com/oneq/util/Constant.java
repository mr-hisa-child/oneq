package com.oneq.util;

public class Constant {
	
	public static final String REDIRECT = "redirect:";
	public static final String SEP_SLASH = "/";
	public static final String PAGE_404 = "error/404";
	
	public static class ControllerPath {
		public static final String QUESTION_ROOT = "question";
		public static final String QUESTION_CREATE = "/create";
		public static final String QUESTION_FORM = "/new";
		public static final String QUESTION_EDIT = "/edit";
		public static final String QUESTION_UPDATE = "/update";
		public static final String QUESTION_HOME = "/home";
		public static final String QUESTION_SIGNIN = "/signin";
		
		public static final String ANSWER_ROOT = "answer";
		public static final String ANSWER_FORM = "/form";
		public static final String ANSWER_SEND = "/send";
		public static final String ANSWER_COMPLETE = "/complete";
	}
	
	public static class ResourcePath {
		public static final String QUESTION = "/questions";
		public static final String QUESTION_FIND = "/{id}";
		
	}
	
}

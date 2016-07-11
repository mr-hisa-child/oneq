package com.oneq.util;

public class Constant {
	
	public static final String REDIRECT = "redirect:";
	public static final String SEP_SLASH = "/";
	
	public static class ControllerPath {
		public static final String QUESTION_ROOT = "/";
		public static final String QUESTION_CREATE = "/create";
		public static final String QUESTION_FORM = "/{path}/question/new";
		public static final String QUESTION_UPDATE = "/{path}/question/create";
		public static final String QUESTION_HOME = "/{path}/question";
		
		public static final String ANSWER_ROOT = "/{path}/answer";
		public static final String ANSWER_FORM = "/{path}/answer/form";
		public static final String ANSWER_SEND = "/{path}/answer/send";
		public static final String ANSWER_COMPLETE = "/{path}/answer/complete";
	}
	
	public static class ResourcePath {
		public static final String QUESTION = "/questions";
		public static final String QUESTION_FIND = "/{id}";
		
	}
	
}

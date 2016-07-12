package com.oneq.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class SessionCheckFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		
		final HttpServletRequest req = (HttpServletRequest)request;
		if(exclude(req)){
			filterChain.doFilter(request, response);
			return;
		}
		
		
		final HttpServletResponse res = (HttpServletResponse)response;
		final HttpSession session = req.getSession();
		
		
		final String path = (String)session.getAttribute("path");
		
		if(StringUtils.isEmpty(path)){
			if(req.getRequestURI().contains("answer")){
				res.sendRedirect(req.getContextPath() + "/answer");
			}else{
				res.sendRedirect(req.getContextPath() + "/question");
			}
			return;
		}
		
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}
	
	private boolean exclude(final HttpServletRequest req){
		return req.getRequestURI().equals(req.getContextPath() + "/answer") 
				|| req.getRequestURI().equals(req.getContextPath() + "/question")
				|| req.getRequestURI().equals(req.getContextPath() + "/question/create")
				|| req.getRequestURI().equals(req.getContextPath() + "/question/signin");
	}

}

package com.pinelet.utp.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class NoCacheFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException
	 {
	  ((HttpServletResponse) response).setHeader("Cache-Control","no-cache");
	  ((HttpServletResponse) response).setHeader("Pragma","no-cache");
	  ((HttpServletResponse) response).setDateHeader ("Expires", -1);
	  filterChain.doFilter(request, response);
	 }

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub

	}

}

/*
 * $Id: ActionImpl.java 471756 2006-11-06 15:01:43Z husted $
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.pinelet.utp.manage.action;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * Base Action class for the Tutorial package.
 */
@SuppressWarnings("unchecked")
public abstract class ActionImpl extends ActionSupport implements ModelDriven,  ServletRequestAware, ServletResponseAware{
	
	private static final long serialVersionUID = -582944613840272021L;
	
	protected HttpServletRequest request;
	
	protected HttpServletResponse response;
	
	protected Map jsonmap;
	
	protected List jsonlist;
	
	public List getJsonlist() {
		return jsonlist;
	}
	
	public Map getJsonmap() {
		return jsonmap;
	}
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
}

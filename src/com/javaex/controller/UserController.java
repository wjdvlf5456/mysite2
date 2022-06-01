package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.util.WebUitl;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//포스트 방식일 때 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		System.out.println(action);
		
		if ("joinForm".equals(action)) {
			
			//포워드
			WebUitl.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
			
		} else if("join".equals(action)){
			
			
			
			WebUitl.forward(request, response, "WEB-INF/views/user/JoinOk.jsp");
			
		} else if("loginForm".equals(action)){	//로그인 폼일 때
			//포워드
			WebUitl.forward(request, response, "WEB-INF/views/user/loginForm.jsp");
		} else if("login".equals(action)){
			
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");
			
			
			
		} else if("modifyForm".equals(action)){
			
			WebUitl.forward(request, response, "WEB-INF/views/user/modifyForm.jsp");
			
		} else if("modify".equals(action)){
			
			WebUitl.forward(request, response, "WEB-INF/views/user/modifyForm.jsp");
			
		} else {
			System.out.println("action 파라미터 없음");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

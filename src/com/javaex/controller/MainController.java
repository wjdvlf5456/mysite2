package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.util.WebUitl;

@WebServlet("/main")
public class MainController extends HttpServlet {
	
	//필드
	private static final long serialVersionUID = 1L;
      
	//메소드 일반
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//코드 작성
		System.out.println("MainController");
		
		//포워드
		WebUitl.forward(request, response, "/WEB-INF/views/main/index.jsp");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

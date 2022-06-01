package com.javaex.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.util.WebUitl;

@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//포스트 방식일 때 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
		
		//action 파라미터 생성
		String action = request.getParameter("action");
		System.out.println(action);
		
		
		//add관리
		if ("addList".equals(action)) {
			
			
			WebUitl.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
			
		} else if("add".equals(action)){
			
			WebUitl.forward(request, response, "/gbc?action=addList");
			
		//delete관리	
		} else if("deleteForm".equals(action)){
			
			WebUitl.forward(request, response, "/WEB-INF/views/guestbook/deletForm.jsp");
			
		} else if("delete".equals(action)){
			
			WebUitl.forward(request, response, "/gbc?action=deleteForm");

		} else {
			System.out.println("action 파라미터 없음");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}

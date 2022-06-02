package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUitl;
import com.javaex.vo.UserVo;

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
			
			//파라미터 만들기
			String id = request.getParameter("id");
			String password  = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			//파라미터 이용하여 Vo에 새 주소 만들기
			UserVo userVo  = new UserVo(id, password, name, gender);
			//데이터베이스에 정보 추가
			UserDao userDao = new UserDao();
			userDao.userInsert(userVo);
			
			//포워드
			WebUitl.forward(request, response, "WEB-INF/views/user/joinOk.jsp");
			
		} else if("loginForm".equals(action)){	//로그인 폼일 때
			//포워드
			WebUitl.forward(request, response, "WEB-INF/views/user/loginForm.jsp");
		} else if("login".equals(action)){
			
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			
			UserVo userVo = new UserVo(id,password);
			
			UserDao userDao = new UserDao();
			
			//userDao.
			
			
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

package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 포스트 방식일 때 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
		UserDao userDao = new UserDao();

		String action = request.getParameter("action");
		System.out.println(action);

		if ("joinForm".equals(action)) {
			System.out.println("UserController>joinForm");

			// 회원가입 폼 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");

		} else if ("join".equals(action)) {

			// 파라미터 만들기
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");

			// 파라미터 이용하여 Vo에 새 주소 만들기
			UserVo userVo = new UserVo(id, password, name, gender);
			System.out.println(userVo);
			// 데이터베이스에 정보 추가
			userDao.userInsert(userVo);

			// 포워드
			WebUtil.forward(request, response, "WEB-INF/views/user/joinOk.jsp");

		} else if ("loginForm".equals(action)) { // 로그인 폼일 때
			System.out.println("UserController>loginForm");

			// 포워드
			WebUtil.forward(request, response, "WEB-INF/views/user/loginForm.jsp");
		} else if ("login".equals(action)) {

			String id = request.getParameter("id");
			String password = request.getParameter("password");

			UserVo userVo = new UserVo();
			userVo.setId(id);
			userVo.setPassword(password);

			UserVo authUser = userDao.getUser(userVo);

			if (authUser == null) {
				System.out.println("로그인 실패");
				WebUtil.forward(request, response, "WEB-INF/views/user/loginForm.jsp");
			} else {
				System.out.println("로그인 성공");

				HttpSession session = request.getSession();
				session.setAttribute("authUser", authUser);

				WebUtil.forward(request, response, "/main");

			}

		} else if ("logout".equals(action)) {
			System.out.println("UserController>logoust");

			HttpSession session = request.getSession();
			session.removeAttribute(action);
			session.invalidate();

			WebUtil.forward(request, response, "/main");

			// 수정 =================================================
		} else if ("modifyForm".equals(action)) {

			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			int no = authUser.getNo();
			// no로 사용자 정보 가져오기
			UserVo userVo = userDao.getUser(no);

			// 포워드
			request.setAttribute("userVo", userVo);
			WebUtil.forward(request, response, "WEB-INF/views/user/modifyForm.jsp");

		} else if ("modify".equals(action)) { // 수정

			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			
			// 파라미터 꺼내기
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			int no = authUser.getNo();

			// 바인딩
			UserVo userVo = new UserVo();
			userVo.setId(id);
			userVo.setPassword(password);
			userVo.setName(name);
			userVo.setGender(gender);
			userVo.setNo(no);

			// dao를 사용한다.
			userDao.userUpdate(userVo);

			authUser = userDao.getUser(userVo);
			request.setAttribute("authUser", authUser);
			WebUtil.redirect(request, response, "./main");

			// 수정 끝 =================================================
		} else {
			System.out.println("action 파라미터 없음");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
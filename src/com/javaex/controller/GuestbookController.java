package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestBookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestBookVo;

@WebServlet("/gbc")
public class GuestbookController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 포스트 방식일 때 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");
		GuestBookDao guestBookDao = new GuestBookDao();

		// action 파라미터 생성
		String action = request.getParameter("action");
		System.out.println(action);

		// add관리
		if ("addList".equals(action)) {
			List<GuestBookVo> guestList = guestBookDao.guestSelect();
			
			request.setAttribute("guestList", guestList);
			// 포워딩
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");

		} else if ("add".equals(action)) {
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String content = request.getParameter("content");

			GuestBookVo guestBookVo = new GuestBookVo();
			guestBookVo.setName(name);
			guestBookVo.setPassword(password);
			guestBookVo.setContent(content);

			guestBookDao.guestAdd(guestBookVo);

			WebUtil.redirect(request, response, "./gbc?action=addList");

			// delete관리
		} else if ("deleteForm".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");

		} else if ("delete".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			String password = request.getParameter("password");

			guestBookDao.guestDelete(no, password);
			
			WebUtil.redirect(request, response, "./gbc?action=addList");
			
			/*
			if (count == 1) {	//삭제 되었을 시
			} else {	// 비밀번호 다를 경우
				WebUtil.redirect(request, response, "/gbc?action=deleteForm");
			}
			*/
		} else {
			System.out.println("action 파라미터 없음");
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
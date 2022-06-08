package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 포스트 방식일 때 한글깨짐 방지
		request.setCharacterEncoding("UTF-8");

		// 코드 작성
		System.out.println("BoardController");
		BoardDao boardDao = new BoardDao();

		// action 파라미터 생성
		String action = request.getParameter("action");
		System.out.println(action);

		// ==================================================================================================
		if ("list".equals(action)) {
			List<Object> boardList = boardDao.boardSelect();
			System.out.println(boardList.toString());

			request.setAttribute("boardList", boardList);

			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
		} else if ("modifyForm".equals(action)) {

			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
		} else if ("modify".equals(action)) {

		} else if ("read".equals(action)) {
			int no = Integer.parseInt(request.getParameter("no"));
			boardDao.getHit(no);

			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");

			// ==================================================================================================

		} else if ("writeForm".equals(action)) { // 글쓰기
			System.out.println("writeForm");

			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
		} else if ("write".equals(action)) {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int userNo = Integer.parseInt(request.getParameter("no"));

			BoardVo boardVo = new BoardVo(title, content, userNo);
			boardDao.boardInsert(boardVo);

			WebUtil.redirect(request, response, "./board?action=list");

			// ==================================================================================================
		} else if ("delete".equals(action)) { // 삭제
			int no = Integer.parseInt(request.getParameter("no"));

			boardDao.boardDelete(no);

			WebUtil.forward(request, response, "./board?action=list");

		} else {
			System.out.println(action + " 액션파라미터 없음");

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}

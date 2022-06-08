<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="com.javaex.vo.UserVo"%>
<%@ page import="com.javaex.vo.BoardVo"%>
<%@ page import="java.util.List"%>

<%
List<BoardVo> boardList = (List<BoardVo>) request.getAttribute("boardList");
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link href="/mysite2/assets/css/mysite.css" rel="stylesheet" type="text/css">
<link href="/mysite2/assets/css/board.css" rel="stylesheet" type="text/css">

</head>
<body>
	<div id="wrap">

		<!-- header -->
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<!-- //header -->

		<div id="nav">
			<ul class="clearfix">
				<li><a href="">입사지원서</a></li>
				<li><a href="./board?action=list">게시판</a></li>
				<li><a href="">갤러리</a></li>
				<li><a href="./gbc?action=addList">방명록</a></li>
			</ul>
		</div>
		<!-- //nav -->

		<div id="container" class="clearfix">
			<div id="aside">
				<h2>게시판</h2>
				<ul>
					<li><a href="">일반게시판</a></li>
					<li><a href="">댓글게시판</a></li>
				</ul>
			</div>
			<!-- //aside -->

			<div id="content">

				<div id="content-head">
					<h3>게시판</h3>
					<div id="location">
						<ul>
							<li>홈</li>
							<li>게시판</li>
							<li class="last">일반게시판</li>
						</ul>
					</div>
					<div class="clear"></div>
				</div>
				<!-- //content-head -->

				<div id="board">
					<div id="list">
						<form action="./board?" method="post">
							<div class="form-group text-right">
								<input type="text">
								<button type="submit" id=btn_search>검색</button>
							</div>
						</form>
						<table>
							<thead>
								<tr>
									<th>번호</th>
									<th>제목</th>
									<th>작성자</th>
									<th>조회수</th>
									<th>작성일</th>
									<th>관리</th>
								</tr>
							</thead>
							<c:forEach items="${boardList}" var="bList" varStatus="status">
								<tbody>
									<tr>
										<td>${bList.no}</td>
										<td class="text-left"><a href="./board?action=read&no=${bList.no }">${bList.title }</a></td>
										<th>${bList.name}</th>
										<td>${bList.hit}</td>
										<td>${bList.regDate}</td>
										<td><a href="./board?action=delete&no=${bList.no }">[삭제]</a></td>
									</tr>
								</tbody>
							</c:forEach>
						</table>

						<div id="paging">
							<ul>
								<li><a href="">◀</a></li>
								<li><a href="">1</a></li>
								<li><a href="">2</a></li>
								<li><a href="">3</a></li>
								<li><a href="">4</a></li>
								<li class="active"><a href="">5</a></li>
								<li><a href="">6</a></li>
								<li><a href="">7</a></li>
								<li><a href="">8</a></li>
								<li><a href="">9</a></li>
								<li><a href="">10</a></li>
								<li><a href="">▶</a></li>
							</ul>


							<div class="clear"></div>
						</div>
						<c:if test="${authUser.name!=null }">
							<a id="btn_write" href="/mysite2/board?action=writeForm">글쓰기</a>
						</c:if>
						<%-- <c:choose>
							<c:when test="${empty authUser}">
							</c:when>
							<!-- 로그인 한 상태 -->
							<c:otherwise>
								<a id="btn_write" href="/mysite2/board?action=writeForm">글쓰기</a>
							</c:otherwise>
						</c:choose>

	 --%>
					</div>
					<!-- //list -->
				</div>
				<!-- //board -->
			</div>
			<!-- //content  -->

		</div>
		<!-- //container  -->


		<!-- footer -->
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		<!-- //footer -->
	</div>
	<!-- //wrap -->

</body>

</html>


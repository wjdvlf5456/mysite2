package com.javaex.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

public class BoardDao {

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@webdb_high?TNS_ADMIN=/Users/choijungphil/jungphil/Wallet_webdb";
	private String id = "admin";
	private String pw = "^^65Rhcemdtla";

	// DB 구축 메소드
	public void getConnection() {
		try {
			// 오라클 드라이버 로딩
			Class.forName(driver);

			// 데이터베이스 연결
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	// 자원정리 메소드
	public void close() {
		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}

	public List<BoardVo> boardSelect() {

		List<BoardVo> boardList = new ArrayList<BoardVo>();
		getConnection();

		try {
			// sql문 준비
			String query = "";
			query += " select b.no, ";
			query += " 		  b.title,";
			query += " 		  b.hit,";
			query += " 		  to_char(b.reg_date,'yy-mm-dd hh:mi'), ";
			query += " 		  s.name";
			query += " from users s";
			query += " left outer join board b on b.user_no = s.no";
			query += " order by b.no desc ";
			
			// 바인딩
			pstmt = conn.prepareStatement(query);

			// 실행
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				int no = rs.getInt(1);
				String title = rs.getString(2);
				int hit = rs.getInt(3);
				String regDate = rs.getString(4);
				String name = rs.getString(5);

				BoardVo boardVo = new BoardVo(no, title, hit, regDate, name);
				
				boardList.add(boardVo);
			}



			for (int i = 0; i < boardList.size(); i++) {
				boardList.get(i).toString();
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);

		}

		close();

		return boardList;
	}

	public int boardInsert(BoardVo boardVo) {
		int count = -1;
		getConnection();

		try {
			// sql문 준비
			String query = "";
			query += " insert into board(no,title,content,reg_date,user_no) ";
			query += " values(seq_board_no.nextval,?,?,sysdate,?) ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getUserNo());

			// 실행
			count = pstmt.executeUpdate();

			// 결과처리
			System.out.println(count + "건이 등록되었습니다.");

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		close();

		return count;

	}

	public int boardDelete(int no) {
		int count = 1;
		getConnection();

		try {
			// 쿼리문 준비
			String query = "";
			query += " delete from board";
			query += " where no = ?";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			// 실행
			count = pstmt.executeUpdate();

			// 결과처리
			System.out.println(count + "건이 삭제되었습니다.");

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		close();

		return count;
	}

	public int boardUpdate(BoardVo boardVo) {

		int count = -1;
		getConnection();

		try {
			// 쿼리문 작성
			String query = "";
			query += " update board ";
			query += " set 	 title = ?, ";
			query += " 	   	 content = ?, ";
			query += " where user_no = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, boardVo.getTitle());
			pstmt.setString(2, boardVo.getContent());
			pstmt.setInt(3, boardVo.getUserNo());

			count = pstmt.executeUpdate();

			System.out.println(count + "건이 수정되었습니다.");

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		close();

		return count;
	}

	public void getHit(int no) {
		int count = 0;
		getConnection();
		try {

			String query = "";
			query += " select hit ";
			query += " from board";
			query += " where no = ? ";
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				count = rs.getInt(1);
				count++;
			}

			String squery = "";
			squery += " update board ";
			squery += " set	  hit =? ";
			squery += " where no = ? ";

			pstmt = conn.prepareStatement(squery);
			pstmt.setInt(1, count);
			pstmt.setInt(2, no);
			pstmt.executeUpdate();

			conn.close();

		} catch (SQLException e) {
			System.out.println("error: " + e);
		} catch (Exception e) {
			e.printStackTrace();
		}

		close();

	}

	public BoardVo getBoard(int no) {
		BoardVo boardVo = null;
		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " select s.name, ";
			query += " 		  b.hit,";
			query += " 		  to_char(b.reg_date, 'yy-mm-dd hh:mi'), ";
			query += " 		  b.title,";
			query += " 		  b.content ";
			query += " from users s";
			query += " left outer join board b on b.user_no = s.no";
			query += " where b.no = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, no);

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			if (rs.next()) {
				String name = rs.getString(1);
				int hit = rs.getInt(2);
				String regDate = rs.getString(3);
				String title = rs.getString(4);
				String content = rs.getString(5);

				boardVo = new BoardVo(no, title, content, hit, regDate, name);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();

		return boardVo;
	}

}

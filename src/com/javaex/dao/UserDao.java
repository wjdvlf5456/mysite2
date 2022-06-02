package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.UserVo;

public class UserDao {

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@webdb_high?TNS_ADMIN=/Users/choijungphil/jungphil/Wallet_webdb";
	private String id = "admin";
	private String pw = "^^65Rhcemdtla";

	// DB구축 메소드
	public void getConnection() {
		try {
			// 1. 오라클 드라이버 로딩
			Class.forName(driver);
			// 2. 데이터베이스 접속
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
			System.out.println("erroe: " + e);
		}
	}

	// 메소드 - 일반
	public List<UserVo> userSelect() {

		List<UserVo> userList = new ArrayList<UserVo>();
		getConnection();

		try {
			// sql문 준비
			String query = "";
			query += " select no, ";
			query += " 		  id,";
			query += " 		  password,";
			query += " 		  name,";
			query += " 		  gender";
			query += " from users";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			// 실행
			rs = pstmt.executeQuery();

			while (rs.next()) {

				int no = rs.getInt("no");
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String gender = rs.getString("gender");

				UserVo userVo = new UserVo(no, id, password, name, gender);

				userList.add(userVo);
			}

			for (int i = 0; i < userList.size(); i++) {
				userList.get(i).toString();
			}

		} catch (SQLException e) {
			System.out.println("error: " + e);

		}

		close();

		return userList;
	}

	// 회원가입
	public int userInsert(UserVo userVo) {
		int count = 1;
		getConnection();

		try {
			// sql문 준비
			String query = "";
			query += " insert into users ";
			query += " values(seq_users_id.nextval, ?, ?, ? ,?) ";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());

			// 실행
			// 성공횟수
			count = pstmt.executeUpdate();

			// 결과처리
			System.out.println(count + "건이 등록되었습니다.");

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		close();

		return count;
	}

	public int userDelete(UserVo userVo) {
		int count = 1;
		getConnection();

		try {
			// 쿼리문 준비
			String query = "";
			query += " delete from users";
			query += " where no = ?";

			// 바인딩
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, userVo.getNo());

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

	public int userUpdate(UserVo userVo) {

		int count = 1;
		getConnection();

		try {
			String query = "";
			query += " update users ";
			query += " set id = ?, ";
			query += " 	   password = ?, ";
			query += " 	   name = ?, ";
			query += " 	   gender = ? ";
			query += "where no = ? ";

			pstmt = conn.prepareStatement(query);

			count = pstmt.executeUpdate();
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());

			System.out.println(count + "건이 수정되었습니다.");

		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		close();

		return count;
	}

	// 사용자 정보 가져오기(로그인시 사용)
	public UserVo getUser(UserVo userVo) {

		UserVo authUser = null;

		this.getConnection();

		try {

			// 3. SQL문 준비 / 바인딩 / 실행
			// SQL문 준비
			String query = "";
			query += " select no, ";
			query += "        name ";
			query += " from users ";
			query += " where id = ? ";
			query += " and password = ? ";

			// 바인딩
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());

			// 실행
			rs = pstmt.executeQuery();

			// 4.결과처리
			while (rs.next()) {
				int no = rs.getInt("no");
				String name = rs.getString("name");

				authUser = new UserVo();
				authUser.setNo(no);
				authUser.setName(name);
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		this.close();
		return authUser;
	}

}

package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	

}

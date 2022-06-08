package com.javaex.dao;

import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardTest {
	
	public static void main(String[] args) {
		
		BoardDao boardDao = new BoardDao();
		List<BoardVo> bList = boardDao.boardSelect();
		
		System.out.println(bList.get(0).toString());
		
		
		
		
		
		
		
	}

}

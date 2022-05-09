package days04.board.test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.junit.jupiter.api.Test;

import com.util.DBconn;

import days04.board.BoardDAO;
import days04.board.BoardDAOImpl;
import days04.board.BoardDTO;
import days04.board.BoardService;

class BoardServiceTest {
	/*
	@Test
	void insertServicetest() {
		Connection conn = DBconn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);
		
		BoardService service = new BoardService(dao);

		BoardDTO dto = new BoardDTO();

		dto.setWriter("홍길동");
		dto.setPwd("1234");
		dto.setEmail("hong@naver.com");
		dto.setTitle("두 번째 게시글");
		dto.setTag(0);
		dto.setContent("두 번째 게시글 내용...");

		int rowCount = service.insertService(dto);

		if (rowCount == 1) {
			System.out.println("> 새 게시글 작성 완료!");
		} else {
			System.out.println("> 게시글 작성 실패!");
		} // if
		
		DBconn.close();
		
	} // insertServicetest
	*/
	
	@Test
	void selectServicetest() {
		Connection conn = DBconn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);
		
		BoardService service = new BoardService(dao);
		
		ArrayList<BoardDTO> list = service.selectService();
		
		if (list == null) {
			System.out.println("> 게시글이 존재하지 않습니다.");
		} else {
			Iterator<BoardDTO> ir = list.iterator();
			while (ir.hasNext()) {
				BoardDTO dto  = ir.next();
				System.out.println(dto);
			} // while
		
		} // if
		
	} // selectServicetest
	
} // class

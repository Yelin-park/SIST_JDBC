package days04.board;

import java.sql.SQLException;
import java.util.ArrayList;

public interface BoardDAO {
	// 인터페이스에서 생성하는 메서드는 추상메서드로 앞에 public abstract 생략되어져 있음
	
	// 1. 모든 게시글 목록 반환하는 메서드
	public abstract ArrayList<BoardDTO> select() throws SQLException;
	
	// 2. 새로운 게시글 추가하는 메서드
	 // 처리된 것을 rowCount로 받아서 리턴
	 // DTO 처리가 안되면 SQL예외가 발생하니 throws
	int insert(BoardDTO dto) throws SQLException;
	
	// 3-1. 조회수 증가 메서드(int seq가 매개변수	void 리턴타입)
	void increaseReaded(int seq) throws SQLException; // 예외는 service로 떠넘기기~
	
	// 3-2. 게시글 정보 반환 메서드(int seq가 매개변수	BoardDTO dto 리턴타입)
	BoardDTO view(int seq) throws SQLException;
	
	// 4. 게시글 삭제하는 메서드
	int delete(int seq) throws SQLException;

	// 5. 게시글 수정하는 메서드
	int update(BoardDTO dto) throws SQLException;
	
	// 6. 게시글 검색하는 메서드
	ArrayList<BoardDTO> search(int searchCondition, String searchWord) throws SQLException;
	
} // interface

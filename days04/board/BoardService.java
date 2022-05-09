package days04.board;

import java.sql.SQLException;
import java.util.ArrayList;

//BoardController -> BoardService -> BoardDAOImpl -> 오라클 서버
public class BoardService {
	// 필드
	private BoardDAO dao = null;
	
	// 1. 생성자를 통한 의존성 주입(DI)
	public BoardService(BoardDAO dao) {
		this.dao = dao;
	}
	// 2. setter를 통한 의존성 주입(DI)
	public void setDao(BoardDAO dao) {
		this.dao = dao;
	}

	// 1. 게시글 목록 서비스 메서드
	public ArrayList<BoardDTO> selectService(){
		ArrayList<BoardDTO> list = null;
		
		try {
			// 1. 로그 기록 서비스
			System.out.println("> 게시글 목록 조회 - 로그 기록");
			// 2. 게시글 목록
			list = this.dao.select(); // DB를 연동하는 작업
		} catch (SQLException e) {
			e.printStackTrace();
		} // try
		
		return list;
	} // selectService
	
	// 2. 게시글 추가 서비스 메서드
	public int insertService(BoardDTO dto){
		int rowCount = 0;
		
		try {
		// 트랜잭션 : 아래 작업이 모두 되어야함. 하나라도 되지 않으면 원상태로 복구
		// 트랜잭션 처리 시작
			// 1. 로그 기록 서비스
			System.out.println("> 게시글 추가 - 로그 기록");
			// 2. 게시글 추가
			rowCount = this.dao.insert(dto);
			// 3. 작성자 포인트 1증가
			// this.dao.updatePoint(작성자);
		// 트랜잭션 처리 종료
		} catch (SQLException e) {
			e.printStackTrace();
		} // try
		
		return rowCount;
		
	} // insertService
	
	// 3. 해당 게시글 상세보기 메서드
	//  - 해당 게시글 조회수 증가
	//  - 해당 게시글 dto 객체 가져오기
	public BoardDTO viewService(int seq) {
		BoardDTO dto = null;
		
		try {
		// 트랜잭션 시작
			// 1. 조회수 증가 작업
			this.dao.increaseReaded(seq);
			// 2. 게시글 가져오는 작업
			dto = this.dao.view(seq); 
			// 3. 로그 기록
			System.out.println("> 게시글 상세보기 - 로그 기록");
		// 트랜잭션 종료
		} catch (SQLException e) {
			e.printStackTrace();
		} // try
		
		return dto;
		
	} // viewService
	
	// 4. 해당 게시글 삭제하는 메서드
	public int deleteService(int seq) {
		int rowCount = 0;
		
		try {
			// 1. 로그 기록 서비스
			System.out.println("> 게시글 삭제 - 로그 기록");
			// 2. 게시글 삭제
			rowCount = this.dao.delete(seq);
		} catch (SQLException e) {
			e.printStackTrace();
		} // try
		
		return rowCount;		

	} // deleteService
	
	// 5. 해당 게시글 수정하는 메서드
	public int updateService(BoardDTO dto) {
		int rowCount = 0;
		
		try {
			// 1. 로그 기록 서비스
			System.out.println("> 게시글 수정 - 로그 기록");
			// 2. 게시글 삭제
			rowCount = this.dao.update(dto);
		} catch (SQLException e) {
			e.printStackTrace();
		} // try
		
		return rowCount;
	} // updateService
	
	// 6. 게시글 검색하는 메서드
	public ArrayList<BoardDTO> searchService(int searchCondition, String searchWord) {
		ArrayList<BoardDTO> list = null;
		
		try {
			// 1. 로그 기록 서비스
			System.out.println("> 게시글 검색 목록 조회 - 로그 기록");
			// 2. 게시글 목록
			list = this.dao.search(searchCondition, searchWord);
		} catch (SQLException e) {
			e.printStackTrace();
		} // try
		
		return list;
	} // searchService
	
} // class

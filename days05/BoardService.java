package days04.board;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.util.DBconn;

// BoardController -> BoardService -> BoardDAOImpl -> 오라클 서버
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
	
	// 1-1. 게시글 목록 서비스 메서드 + 페이징 처리 추가
	public ArrayList<BoardDTO> selectService(int currentPage, int numberPerPage){
		ArrayList<BoardDTO> list = null;
		
		try {
			// 1. 로그 기록 서비스
			System.out.println("> 게시글 목록 조회 - 로그 기록");
			// 2. 게시글 목록
			list = this.dao.select(currentPage, numberPerPage);
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
		// 트랜잭션 시작		오라클 PL/SQL 프로시저에서도 할 수 있음
			((BoardDAOImpl)this.dao).getConn().setAutoCommit(false);
			
			// 1. 조회수 증가 작업
			this.dao.increaseReaded(seq);
			// 2. 게시글 가져오는 작업
			dto = this.dao.view(seq); 
			// 3. 로그 기록
			System.out.println("> 게시글 상세보기 - 로그 기록");
		// 트랜잭션 종료
			((BoardDAOImpl)this.dao).getConn().commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				((BoardDAOImpl)this.dao).getConn().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				// Connection 객체가 아직 닫히지 않았으니 다시 기본값으로 바꿔놓기
				((BoardDAOImpl)this.dao).getConn().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally
		
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
	
	public ArrayList<BoardDTO> searchService(int currentPage, int numberPerPage, int searchCondition, String searchWord) {
		ArrayList<BoardDTO> list = null;

		try {
			// 1. 로그 기록 서비스
			System.out.println("> 게시글 검색 목록 조회 - 로그 기록");
			// 2. 게시글 목록
			list = this.dao.search(currentPage, numberPerPage, searchCondition, searchWord);
		} catch (SQLException e) {
			e.printStackTrace();
		} // try

		return list;
	} // searchService
	
	public String pageService(int currentPage, int numberPerPage, int numberOfPageBlock) {
		String pagingBlock = "\t\t\t";
		
		try {
			// int totalRecords = this.dao.getTotalRecords();
			int totalPages = this.dao.getTotalPages(numberPerPage);
			int startOfPageBlock = 1;
			int endOfPageBlock;

			startOfPageBlock = (currentPage-1) / numberOfPageBlock * numberOfPageBlock + 1;
			endOfPageBlock = startOfPageBlock + numberOfPageBlock - 1;
			if(endOfPageBlock > totalPages) endOfPageBlock = totalPages;

			if (startOfPageBlock != 1) pagingBlock += " < ";
			for (int j = startOfPageBlock; j <= endOfPageBlock; j++) {
				pagingBlock += String.format(j==currentPage ? "[%d] " : "%d ", j);
			} // for j

			if (endOfPageBlock != totalPages) pagingBlock += " > ";

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pagingBlock;
	} // pageService
	
	public String pageService(int currentPage, int numberPerPage, int numberOfPageBlock
								, int searchCondition, String searchWord) {
		String pagingBlock = "\t\t\t";
		
		try {
			// int totalRecords = this.dao.getTotalRecords();
			int totalPages = this.dao.getTotalPages(numberPerPage, searchCondition, searchWord); // 검색된 결과의 총페이지 수 반환
			int startOfPageBlock = 1;
			int endOfPageBlock;

			startOfPageBlock = (currentPage-1) / numberOfPageBlock * numberOfPageBlock + 1;
			endOfPageBlock = startOfPageBlock + numberOfPageBlock - 1;
			if(endOfPageBlock > totalPages) endOfPageBlock = totalPages;

			if (startOfPageBlock != 1) pagingBlock += " < ";
			for (int j = startOfPageBlock; j <= endOfPageBlock; j++) {
				pagingBlock += String.format(j==currentPage ? "[%d] " : "%d ", j);
			} // for j

			if (endOfPageBlock != totalPages) pagingBlock += " > ";

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return pagingBlock;
	} // pageService
	
} // class

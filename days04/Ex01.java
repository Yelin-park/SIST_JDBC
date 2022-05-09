package days04;

import java.sql.Connection;

import com.util.DBconn;

import days04.board.BoardController;
import days04.board.BoardDAO;
import days04.board.BoardDAOImpl;
import days04.board.BoardService;

/**
 * @author yelin
 * @date 2022-05-09 09:44:20
 * @subject
 * @content
 * 		
	 	모델2방식의 [MVC 패턴]으로 개발
	 	
		1. tbl_cstvsboard 게시판 테이블 생성
		
		2. days04.board.BoardDTO 생성
		
		3. days04.board.BoardDAO 인터페이스 선언
			- DAO(Data Access Object) -> CRUD 객체
				Access -> CRUD 작업
				데이터를 처리할 수 있는 편리성, 보안성 때문에 DAO 사용
				
		4. days04.board.BoardDAOImpl (위에 있는 인터페이스를 구현한 클래스)
		
		5. BoardDAOImpl.select() / insert() 메서드 구현
			-> DAO 객체의 select() / insert() 메서드 [단위 테스트]
				- 단위 테스트 : 잘 작동하는지 테스트하는 것
				- days04.board.test 패키지 추가
				- BoardDAOImplTest 생성 - JUnit Test Case로 생성함
		
		6. days04.board.BoardService 클래스 추가
			- DAO 객체를 호출하는 BoardService 클래스 구현
			
			* BoardService 클래스는 왜 구현할까?
			   목록페이지에서 한 개의 게시글을 보기 위해서 제목을 클릭하면
			   클릭한 [게시글의 상세보기]
			   상세보기를하면 아래 두 개의 작업이 하나로 묶여서 실행되어야함
			   하나의 작업이 실행되지 않았으면 다시 원상태로 복구해야함
			   (트랜잭션 처리)
			   1) 클릭한 게시글의 조회수 증가하는 작업
			   2) 클릭한 게시글의 정보 SELECT해와서 출력하는 작업
		
		7. days04.board.test.BoardServiceTest 추가 - JUnit Test Case로 생성함
		
		8. BoardController 생성	모든 요청 -> 처리 -> 응답
						   		메뉴선택 -> 처리 -> boardService -> boardDAO -> 콘솔 출력
						   		위와 같은 작업을 중앙에서 제어를 해주는 클래스
						   		
			[게시글 목록]
			      글쓰기 버튼 클릭 -> 새 글 입력 창
									  [저장]
			
			[게시글 상세 보기]
			[게시글 목록]에서 보고자 하는 게시글 제목 클릭하면 -> 글번호를 가져와서 -> 해당 글번호의 상세 게시글 정보 출력
												ㄴ 여기에서는 클릭을 못하니 입력받아서 처리!
			게시글 상세 보기를 하게 되면.. 아래와 같은 작업도 필요
			글번호의 조회수(readed) 컬럼을 1증가 
			글번호의 게시글 정보를 BoardDTO에 담아서 반환하는 메서드 필요
			[게시글 수정]		// [게시글 삭제]
		
		 9. 삭제
		 	1) 삭제할 게시글 번호를 입력? 2
		 	
		 10. 수정

 */
public class Ex01 {

	public static void main(String[] args) {
		
		Connection conn = DBconn.getConnection();
		BoardDAO dao = new BoardDAOImpl(conn);
		BoardService service = new BoardService(dao);
		BoardController controller = new BoardController(service);
		
		controller.start();
		
	} // main

} // class

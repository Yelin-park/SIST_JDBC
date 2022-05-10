package days04.board;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBconn;

public class BoardController {
	// 필드
	private int selectedNumber;
	private Scanner scanner = null;
	private BoardService service = null;
	
	// [ days05 ]
	// 페이징 처리를 위해서 필요한 필드
	private int numberPerPage = 15;
	private int currentPage = 1;
	private int numberOfPageBlock = 10;
	
	public BoardController() {
		this.scanner = new Scanner(System.in); // 디폴트 생성자에서 스캐너 객체 생성
	}
	
	public BoardController(BoardService service) {
		this(); // 자동으로 디폴트 생성자 호출
		this.service = service;
	}
	
	// 메서드
	public void start() {
		while (true) {
			메뉴출력();
			메뉴선택();
			메뉴처리();
		} // whule
		
	} // start

	private void 메뉴출력() {
		String[] menus = {"새글", "목록", "보기", "수정", "삭제", "검색", "종료"};
		System.out.println("[ 메뉴 ]");
		for (int i = 0; i < menus.length; i++) {
			System.out.printf("%d. %s\t", i+1, menus[i]);
		} // for i
		System.out.println();
	} // 메뉴출력
	
	private void 메뉴선택() {
		System.out.print("> 메뉴를 선택하세요? ");
		this.selectedNumber = this.scanner.nextInt();
		
	} // 메뉴선택
	
	private void 메뉴처리() {
		switch (selectedNumber) {
		case 1: // 새글
			새글쓰기();
			break;
		case 2: // 목록
			목록보기();
			break;
		case 3: // 보기
			상세보기();
			break;
		case 4: // 수정
			수정하기();
			break;
		case 5: // 삭제
			삭제하기();
			break;
		case 6: // 검색
			검색하기();
			break;
		case 7: // 종료
			exit();
			break;
		} // switch
		
	} // 메뉴처리
	
	private void 검색하기() {
// ㄱ. 검색에 페이징 처리
		System.out.print("> 검색된 결과 중 보고자하는 현재 페이지(currentPage) 번호를 입력하세요? ");
		this.currentPage = this.scanner.nextInt();
		
		int searchCondition; // 검새조건
		System.out.print("> 검색 조건 : 제목(1), 내용(2), 작성자(3), 제목+내용(4) 선택하세요? ");
		searchCondition = this.scanner.nextInt();
		System.out.print("> 검색어를 입력하세요? ");
		String searchWord = this.scanner.next();
		
		// 목록보기() 모든 코딩 복사 붙여넣기
		// ArrayList<BoardDTO> list = this.service.searchService(searchCondition, searchWord);
// ㄴ. 검색에 페이징 처리
		ArrayList<BoardDTO> list = this.service.searchService(currentPage, numberPerPage, searchCondition, searchWord);
		
		// 뷰(View) - 출력 담당이지만 현재는 여기서 출력하는 코딩
		System.out.println("\t\t 게시판");
		System.out.println("--------------------------------------------------------------------------");
		System.out.printf("%s\t%-40s\t%s\t%-10s\t%s\n", "글번호", "글제목", "글쓴이", "작성일", "조회수");
		System.out.println("--------------------------------------------------------------------------");
		if (list == null) {
			System.out.println("\t > [알림] 게시글이 존재하지 않습니다. ");
		} else {
			Iterator<BoardDTO> ir = list.iterator();
			while (ir.hasNext()) {
				BoardDTO dto = ir.next();
				System.out.printf("%d\t%-30s %s\t%-10s\t%d\n"
									, dto.getSeq(), dto.getTitle(), dto.getWriter(), dto.getWritedate(), dto.getReaded());
			} // while
		} // if
		
		System.out.println("--------------------------------------------------------------------------");
		String pagingBlock = this.service.pageService(this.currentPage, this.numberPerPage
						, this.numberOfPageBlock, searchCondition, searchWord);
		System.out.println(pagingBlock);
		System.out.println("--------------------------------------------------------------------------");
		
		일시정지();		
		
	} // 검색하기

	private void 수정하기() {
		System.out.print("> 수정할 게시글의 seq, email, title, content 입력하세요? ");
		int seq = this.scanner.nextInt();
		
		// 1. 수정하고자하는 게시글의 정보를 출력
		BoardDTO dto = this.service.viewService(seq);
		if (dto == null) { 
			System.out.println("> 해당 게시글은 존재하지 않습니다.");
			return;
		} // if
		
		// 해당 게시글 정보 출력(View 담당)
		System.out.println("ㄱ. 글번호 : " + seq);
		System.out.println("ㄴ. 작성자 : " + dto.getWriter());
		System.out.println("ㄷ. 조회수 : " + dto.getReaded());
		System.out.println("ㄹ. 글제목 : " + dto.getTitle());
		System.out.println("ㅁ. 글내용 : " + dto.getContent());
		System.out.println("ㅂ. 작성일 : " + dto.getWritedate());
		
		//
		String email = this.scanner.next();
		String title = this.scanner.next();
		String content = this.scanner.next();
		
		dto = new BoardDTO();
		dto.setSeq(seq);
		dto.setEmail(email);
		dto.setTitle(title);
		dto.setContent(content);
		
		int rowCount = this.service.updateService(dto);
		if (rowCount == 1) {
			System.out.printf("> %d번 게시글 수정 완료!\n", seq);
		} // if
		
		일시정지();
		
	} // 수정하기

	private void 삭제하기() {
		System.out.print("> 게시글 글번호(seq)를 입력하세요? ");
		int seq = this.scanner.nextInt();
		
		int rowCount = this.service.deleteService(seq);
		
		if (rowCount == 1) {
			System.out.printf("> %d번 게시글 삭제 완료!\n", seq);
		} // if
		
		일시정지();
	} // 삭제

	// 게시글 상세보기하는 메서드
	private void 상세보기() {
		System.out.print("> 게시글 글번호(seq)를 입력하세요? ");
		int seq = this.scanner.nextInt();
		
		BoardDTO dto = this.service.viewService(seq);
		if (dto == null) { 
			System.out.println("> 해당 게시글은 존재하지 않습니다.");
			return;
		} // if
		
		// 해당 게시글 정보 출력(View 담당)
		System.out.println("ㄱ. 글번호 : " + seq);
		System.out.println("ㄴ. 작성자 : " + dto.getWriter());
		System.out.println("ㄷ. 조회수 : " + dto.getReaded());
		System.out.println("ㄹ. 글제목 : " + dto.getTitle());
		System.out.println("ㅁ. 글내용 : " + dto.getContent());
		System.out.println("ㅂ. 작성일 : " + dto.getWritedate());
		
		System.out.println("\n [수정] [삭제] [목록(Home)]");
		
		일시정지();
		
	} // 상세보기

	// 목록 페이지에서 '글쓰기' 버튼을 클릭하면
	// 새글 정보 입력을 받아서 dto -> DB INSERT
	// 다시 목록 페이지 출력
	private void 새글쓰기() {
		System.out.print("> writer, pwd, email, title, tag, content를 입력하세요? ");
		String[] datas = this.scanner.next().split(",");
		String writer = datas[0];
		String pwd = datas[1];
		String email = datas[2];
		String title = datas[3];
		int tag = Integer.parseInt(datas[4]);
		String content = datas[5];
		
		BoardDTO dto = new BoardDTO();
		dto.setWriter(writer);
		dto.setPwd(pwd);
		dto.setEmail(email);
		dto.setTitle(title);
		dto.setTag(tag);
		dto.setContent(content);
		
		int rowCount = this.service.insertService(dto);
		if (rowCount == 1) {
			System.out.println("> 새글 쓰기 완료!");
		}
		
		일시정지();
		
	} // 새글쓰기

	// 게시글 목록 조회하는 메서드
	private void 목록보기() {
		System.out.print("> 보고자하는 현재 페이지(currentPage) 번호를 입력하세요? ");
		this.currentPage = this.scanner.nextInt();
		
		ArrayList<BoardDTO> list = this.service.selectService(this.currentPage, this.numberPerPage);
		
		// 뷰(View) - 출력 담당이지만 현재는 여기서 출력하는 코딩
		System.out.println("\t\t 게시판");
		System.out.println("--------------------------------------------------------------------------");
		System.out.printf("%s\t%-25s\t%s\t%-10s\t%s\n", "글번호", "글제목", "글쓴이", "작성일", "조회수");
		System.out.println("--------------------------------------------------------------------------");
		if (list == null) {
			System.out.println("\t > [알림] 게시글이 존재하지 않습니다. ");
		} else {
			Iterator<BoardDTO> ir = list.iterator();
			while (ir.hasNext()) {
				BoardDTO dto = ir.next();
				System.out.printf("%d\t%-30s %s\t%-10s\t%d\n"
									, dto.getSeq(), dto.getTitle(), dto.getWriter(), dto.getWritedate(), dto.getReaded());
			} // while
		} // if
		System.out.println("--------------------------------------------------------------------------");
		// System.out.println("\t\t\t [1] 2 3 4 5 6 7 8 9 10 >"); // 페이징 블럭
		String pagingBlock = this.service.pageService(this.currentPage, this.numberPerPage, this.numberOfPageBlock);
		System.out.println(pagingBlock);
		System.out.println("--------------------------------------------------------------------------");
		
		일시정지();
	} // 목록보기
	
	private void 일시정지() {
		System.out.println("\t 계속하려면 엔터치세요.");
		try {
			System.in.read();
			System.in.skip(System.in.available());
		} catch (IOException e) {
			e.printStackTrace();
		} // try
		
	} // 일시정지

	// 종료하는 메서드
	private void exit() {
		DBconn.close();
		System.out.println("\t\t [알림] 프로그램 종료");
		System.exit(-1);
	} // exit

} // class

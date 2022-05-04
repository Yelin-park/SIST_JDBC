package days03;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBconn;

import days02.DeptDTO;

/**
 * @author yelin
 * @date 2022-05-04 11:10:13
 * @subject PreparedStatement 설명
 * @content
 * 			하나의 PreparedStatement 객체로 쿼리를 여러 번 처리할 수 있다. (재사용 가능)
 * 			ex) Statement는 망치질 할 때 마다 철물점에서 망치를 계속 사오는 것
 * 				PreparedStatement는 망치를 하나로 계속 사용하는 것
 */
public class Ex02 {
	private static Connection conn = null;

	private static String[] menus = {
			"부서 조회",
			"부서 추가",
			"부서 수정",
			"부서 삭제",
			"부서 검색",
			"종료"
	}; // 메뉴를 담은 string 배열

	private static int selectedNumber; // 메뉴를 선택해서 저장하는 변수

	private static Scanner sc = new Scanner(System.in);

	private static char _continue = 'Y';

	public static void main(String[] args) {

		conn = DBconn.getConnection(); // DB OPEN - 예외가 발생하지 않으면 연결 성공

		while (true) { // 무한루프
			메뉴출력();
			메뉴선택();
			메뉴처리();
		} // while

	} // main

	private static void 메뉴출력() {
		System.out.println(">메뉴 출력<");
		for (int i = 0; i < menus.length; i++) {
			System.out.printf("%d. %s\n", (i+1), menus[i]);
		} // for
	} // 메뉴 출력

	private static void 메뉴선택() {
		System.out.print("> 메뉴를 선택하세요? ");
		selectedNumber = sc.nextInt();
	} // 메뉴 선택

	private static void 메뉴처리() {
		switch (selectedNumber) {
		case 1: // 조회
			selectAllDept();
			break;
		case 2: // 추가
			insertDept();
			break;
		case 3: // 수정
			updateDept();
			break;
		case 4: // 삭제
			deleteDept();
			break;
		case 5: // 검색
			searchDept();
			break;
		case 6: // 종료
			exit();
			break;
		default:
			break;
		} // switch

	} // 메뉴 처리

	// 부서 검색하는 함수
	private static void searchDept() {
		System.out.println("[검색 조건]");
		System.out.println("1. 부서명");
		System.out.println("2. 지역명");
		System.out.println("3. 부서명 + 지역명");

		System.out.print("> 검색 조건을 선택하세요? ");
		int searchCondition = sc.nextInt();

		System.out.print("> 검색어를 입력하세요? ");
		String searchWord = sc.next();

		// selectAllDept 코딩 복사 붙여넣기
		String sql = "SELECT * "
				+ "FROM dept ";

		if (searchCondition == 1) { // 부서명 검색
			//sql += String.format("WHERE dname LIKE '%%%s%%'", searchWord);
			sql += "WHERE dname LIKE ?";
			
		} else if(searchCondition == 2) { // 지역명 검색
			//sql += String.format("WHERE loc LIKE '%%%s%%'", searchWord);
			sql += "WHERE loc LIKE ?";
		} else if(searchCondition == 3) { // 부서명 + 지역명 검색
			//sql += String.format("WHERE dname LIKE '%%%1$s%%' OR loc LIKE '%%%1$s%%'", searchWord);
			sql += "WHERE REGEXP_LIKE(dname, ?) OR REGEXP_LIKE(loc, ?)";
		}

		sql += " ORDER BY deptno ASC";

		// Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<DeptDTO> deptList = null;

		try {
			pstmt = conn.prepareStatement(sql); // static으로 선언된 Connection이 있음
			
			if (searchCondition == 3) {
				pstmt.setString(1, searchWord);
				pstmt.setString(2, searchWord);
			} else {
				pstmt.setString(1, "%" + searchWord + "%");
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				deptList = new ArrayList<DeptDTO>(); // 부서가 존재하면 ArrayList 생성
				do {
					int deptno = rs.getInt(1);
					String dname = rs.getString(2);
					String loc = rs.getString(3);

					DeptDTO dto = new DeptDTO(deptno, dname, loc);
					deptList.add(dto);

				} while (rs.next());
			} // if
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close(); // ***** 닫는 작업 필수 *****
				pstmt.close(); // ***** 닫는 작업 필수 *****
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		} // try

		// ArrayList(deptList) 출력
		printDept(deptList);

		일시정지();

		System.out.println("=END=");

	} // searchDept

	// 부서 삭제하는 함수
	private static void deleteDept() {
		// 삭제할 부서번호를 입력받아서 삭제하는 코딩 작성
		System.out.print("> 삭제할 부서번호(deptno) 입력하세요? ");
		int pdeptno = sc.nextInt();

		/*
		String sql = String.format(
				"DELETE dept"
						+ " WHERE deptno = %d"
						, pdeptno);
		*/
		String sql = "DELETE dept" + " WHERE deptno = ?";
		
		// Statement stmt = null;
		PreparedStatement pstmt = null;

		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pdeptno);
			int rowCount = pstmt.executeUpdate();
			
			if (rowCount == 1) {
				System.out.printf("%d번 부서 삭제 완료!!!\n", pdeptno);
			} //if

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		일시정지();

	} // deleteDept

	// 부서 수정하는 함수 
	private static void updateDept() {
		System.out.print("> 수정할 부서번호(deptno) 입력하세요? ");
		int pdeptno = sc.nextInt();
		System.out.print("> 수정할 부서명, 지역명을 입력하세요? ");
		String pdname = sc.next();
		String ploc = sc.next();
		
		/*
		String sql = String.format(
				"UPDATE dept"
						+ " SET dname = '%s', loc = '%s' "
						+ "WHERE deptno = %d"
						, pdname, ploc, pdeptno);
		*/
		String sql = "UPDATE dept SET dname = ?, loc = ? WHERE deptno = ?";
		
		// Statement stmt = null;
		PreparedStatement pstmt = null;

		try {
			// stmt = conn.createStatement();
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, pdname);
			pstmt.setString(2, ploc);
			pstmt.setInt(3, pdeptno);
			
			int rowCount = pstmt.executeUpdate();
			// int rowCount = stmt.executeUpdate(sql); // DML문 - 작업한 갯수를 돌려줌
			// Auto Commit 된다 ***(기억)
			if (rowCount == 1) {
				System.out.printf("> %d번 부서 수정 완료!!!\n", pdeptno);
			} // if
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				// stmt.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // try
		} // try

		일시정지();

	} // updateDept

	// 부서 추가하는 함수
	private static void insertDept() {
		do {
			// deptno, dname, loc 입력받기
			System.out.println("[부서 정보 입력]");
			System.out.print("1. 부서명 입력하세요? ");
			String pdname = sc.next();
			System.out.print("2. 지역명 입력하세요? ");
			String ploc = sc.next();

			//String sql = String.format("INSERT INTO dept (deptno, dname, loc) VALUES ( seq_dept.NEXTVAL, '%s', '%s')"
			//		, pdname, ploc);
			
			// PreparedStatement는 ? 바인딩 변수를 사용한다.
			// ? 날짜, 문자 홑따옴표 붙이지 않는다.
			String sql = "INSERT INTO dept (deptno, dname, loc) VALUES ( seq_dept.NEXTVAL, ?, ?)";
			
			// System.out.println(sql); // 쿼리 잘 작성했는지 한 번 확인해보기

			// Statement stmt = null;
			PreparedStatement pstmt = null;

			try {
				// stmt = conn.createStatement();
				pstmt = conn.prepareStatement(sql);
				
				// int rowCount = stmt.executeUpdate(sql); // DML문 - 작업한 갯수를 돌려줌
				// Auto Commit 된다 ***(기억)
				// ?, ? 파라미터(매개변수) 값을 설정하고 나서 executeUpdate() 해야함
				pstmt.setString(1, pdname);
				pstmt.setString(2, ploc);
				int rowCount = pstmt.executeUpdate();

				if (rowCount == 1) {
					System.out.println("> 1개 부서 추가 완료!!!");
				} // if
			} catch (SQLException e) {
				e.printStackTrace();
				// seq_dept 새로 동적으로 50번 시작할 수 있도록 생성 - 동적쿼리
			} finally {
				try {
					// stmt.close();
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				} // try
			} // try

			계속여부확인();
		} while (Character.toUpperCase(_continue) == 'Y');

		일시정지();

	} // insertDept

	private static void 계속여부확인() {
		System.out.println("> 계속 하시겠습니까?(Y/N)");
		try {
			_continue = (char)System.in.read();
			System.in.skip(System.in.available());
		} catch (IOException e) {
			e.printStackTrace();
		} // try
	} // 계속여부확인

	// 모든 부서 정보 조회하는 함수
	private static void selectAllDept() {
		String sql = "SELECT * "
				+ "FROM dept "
				+ "ORDER BY deptno ASC";

		// Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<DeptDTO> deptList = null;

		try {
			// stmt = conn.createStatement(); // static으로 선언된 Connection이 있음
			// 재사용하기 때문에 쿼리(sql)를 담고있음 *** 
			pstmt = conn.prepareStatement(sql); 
			// rs = stmt.executeQuery(sql);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				deptList = new ArrayList<DeptDTO>(); // 부서가 존재하면 ArrayList 생성
				do {
					int deptno = rs.getInt(1);
					String dname = rs.getString(2);
					String loc = rs.getString(3);

					DeptDTO dto = new DeptDTO(deptno, dname, loc);
					deptList.add(dto);

				} while (rs.next());
			} // if
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close(); // ***** 닫는 작업 필수 *****
				// stmt.close(); // ***** 닫는 작업 필수 *****
				pstmt.close(); // ***** 닫는 작업 필수 *****
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		} // try

		// 커넥션을 여기서 닫아버리면 반복할 수 없음
		// DBconn.close(); // ***** 닫는 작업 필수 *****

		// ArrayList(deptList) 출력
		printDept(deptList);

		일시정지();

		System.out.println("=END=");
	} // selectAllDept

	private static void 일시정지() {
		System.out.println("\t\t엔터치면 계속합니다.");
		try {
			System.in.read();
			System.in.skip(System.in.available());
		} catch (IOException e) {
			e.printStackTrace();
		} // try
	} // 일시정지

	private static void printDept(ArrayList<DeptDTO> deptList) {
		System.out.println("----------------------------------------------");
		System.out.printf("%s\t%s\t%s\n", "DEPTNO", "DNAME", "LOC");
		System.out.println("----------------------------------------------");

		if(deptList == null) {
			System.out.println("\t부서가 존재하지 않습니다.");
			return;
		} // if

		Iterator<DeptDTO> ir = deptList.iterator();

		while (ir.hasNext()) {
			DeptDTO dto = ir.next();
			System.out.println(dto);
		} // while
		System.out.println("----------------------------------------------");

	} // printDept

	private static void exit() {
		System.out.println("\t\t[알림] 프로그램이 종료 됩니다.");
		DBconn.close(); // DB 닫기 ***
		System.exit(-1);
	} // exit

} // class
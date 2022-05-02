package days01;

import java.sql.DriverManager;

/**
 * @author yelin
 * @date 2022-05-02 14:28:46
 * @subject JDBC 수업 1일차
 * @content jdbcPro 자바프로젝트 생성 및 환경 설정
 */
public class Ex01 {

	public static void main(String[] args) {
		System.out.println("야리니");
		
		/*
		 * 1. JDBC
		 * 	1) [J]ava [D]ata [B]ase [C]onnectivity
		 *  2) 자바 표준 인터페이스 - DBMS 연동 + 작업(CRUD) -> CRUD? create read update delete..
		 *  3) Data Base 연동 기술
		 *  
		 * 2. Java 응용 프로그램 + Oracle DBMS 연동
		 * 					  JDBC(표준 인터페이스)
		 * 	1) 오라클사에서 JDBC를 구현한 드라이버(driver)가 Oracle Driver이며, 다운을 받아야 한다. (Oracle 다운받을 때 자동으로 받아져서 따로 다운할 필요 X)
		 * 	2) ojdbc6.jar 파일이 Oracle Driver이다. (C드라이브에 있음)
		 * 
		 * 3. JDBC Driver? DBMS를 연결할 수 있는 드라이버
		 * 	  MY SQL -> MY SQL사의 드라이버를 다운 받아야 함.
		 * 	  MS SQL -> MS사의 드라이버를 다운 받아야 함.
		 * 	  Oracle -> Oracle 사의 드라이버를 다운 받아야 함.
		 * 
		 * 4. JDBC Driver의 종류
		 * 	1) Type1 - ODBC Driver (제어판\시스템 및 보안\관리 도구에 있음)
		 * 		ㄴ 브릿지 사용 연동 / 편리하지만 성능이 떨어짐(중간에 전달받아서 하는 방식)
		 * 	2) Type2 - Native API Driver
		 * 		ㄴ C, C++등으로 만들어진 Library로 DB 연동
		 * 	3) Type3 - 네트워크 프로토콜 Driver
		 * 		ㄴ 중간에 있는 미들웨어 서버에게 전달하면 DB 작업 처리
		 * 	4) Type4 - Thin Driver (DBMS 프로토콜 Driver) *****
		 * 		ㄴ 순수 자바로 만들어졌으며, DBMS를 직접 호출(연결), 가장 많이 사용된다.
		 * 
		 * 5. DBMS 연결(Connection)하는 순서 ***[암기]
		 * 	1) Class.forName() 메서드로 드라이버(JDBC Driver) 로딩
		 *  2) DriverManager 클래스의 getConnection() 메서드를 사용해서 Connection 객체를 얻어온다.
		 *  	어떤 DB서버에 연결할지
		 *  	어떤 계정에 연결할지
		 *  	계정 비밀번호
		 *  	포트
		 *  	등등
		 *   Connection conn = DriverManager.getConnection(서버,ID,PW);
		 *  3) 필요한(질의응답) 작업 - CRUD
		 *  4) 연결 종료 : Connection 객체 close() 
		 * 						
		 *  
		 */

	} // main

} // class

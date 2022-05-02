package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import oracle.jdbc.driver.OracleDriver;

/**
 * @author yelin
 * @date 2022-05-02 15:55:42
 * @subject DB 연동 + 필요한 작업(CRUD)
 * @content dept 테이블의 부서 정보를 조회
 * 		
 * 		[쿼리들을 실행하는 객체 3가지] -> Connection conn을 사용해서 생성한다.
 * 			1) Statement - 기본 객체
 *			2) PreparedStatement - 바인딩 변수를 사용해서 쿼리 실행 객체
 *			3) CallableStatement - 저장 프로시저를 호출해서 쿼리 실행 객체
 *
 *-----------------------------------------------
 *			stmt.executeQuery(sql); -> SELECT(DQL 문) 사용할 때
 *			stmt.executeUpdate(sql); -> INSERT, UPDATE, DELETE(DML 문) 사용할 때
 * 
 * 
 */
public class Ex03 {

	public static void main(String[] args) {
		// DB 연동
		String className = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "SCOTT";
		String password = "tiger";
		Connection conn = null;
		
		String sql = "SELECT * FROM dept"; // 쿼리에 있던 ;은 삭제! ***
		
		Statement stmt = null;
		
		try {
			// 1. Class.forName() JDBC Driver 로딩
			Class.forName(className);
			// 2. DriverManager.getConnection() 커넥션 객체를 얻어온다 + 연결문자열(url, id, pw)
			conn = DriverManager.getConnection(url, user, password);
			// 3. 필요한 작업 - CRUD
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql); // SELECT(DQL 문) 사용할 때
			// stmt.executeUpdate(sql); // INSERT, UPDATE, DELETE(DML 문) 사용할 때
			
			// rs.next() - Resultset 안에 읽어오고자 하는 다음 레코드가 있는지 물어보는 함수로 true(있음)/false(없음) 돌려줌
			if (rs.next()) {
				// 다음(next) 레코드를 읽어와서 출력하는 작업
				int deptno = rs.getInt(1);
				String dname = rs.getString("dname"); // 2를 줘도 되고, 컬럼명을 줘도 됨
				String loc = rs.getString("loc"); // 3을 줘도 되고, 컬럼명을 줘도 됨
				
				System.out.printf("%d\t%s\t%s\n", deptno, dname, loc);
			} else {
				System.out.println("읽을 레코드는 없습니다.");
			}
			
			if (rs.next()) {
				// 다음(next) 레코드를 읽어와서 출력하는 작업
				int deptno = rs.getInt(1);
				String dname = rs.getString("dname"); // 2를 줘도 되고, 컬럼명을 줘도 됨
				String loc = rs.getString("loc"); // 3을 줘도 되고, 컬럼명을 줘도 됨
				
				System.out.printf("%d\t%s\t%s\n", deptno, dname, loc);
			} else {
				System.out.println("읽을 레코드는 없습니다.");
			}
			
			if (rs.next()) {
				// 다음(next) 레코드를 읽어와서 출력하는 작업
				int deptno = rs.getInt(1);
				String dname = rs.getString("dname"); // 2를 줘도 되고, 컬럼명을 줘도 됨
				String loc = rs.getString("loc"); // 3을 줘도 되고, 컬럼명을 줘도 됨
				
				System.out.printf("%d\t%s\t%s\n", deptno, dname, loc);
			} else {
				System.out.println("읽을 레코드는 없습니다.");
			}
			
			if (rs.next()) {
				// 다음(next) 레코드를 읽어와서 출력하는 작업
				int deptno = rs.getInt(1);
				String dname = rs.getString("dname"); // 2를 줘도 되고, 컬럼명을 줘도 됨
				String loc = rs.getString("loc"); // 3을 줘도 되고, 컬럼명을 줘도 됨
				
				System.out.printf("%d\t%s\t%s\n", deptno, dname, loc);
			} else {
				System.out.println("읽을 레코드는 없습니다.");
			}
			
			if (rs.next()) {
				// 다음(next) 레코드를 읽어와서 출력하는 작업
				int deptno = rs.getInt(1);
				String dname = rs.getString("dname"); // 2를 줘도 되고, 컬럼명을 줘도 됨
				String loc = rs.getString("loc"); // 3을 줘도 되고, 컬럼명을 줘도 됨
				
				System.out.printf("%d\t%s\t%s\n", deptno, dname, loc);
			} else {
				System.out.println("읽을 레코드는 없습니다.");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				stmt.close();
				//4. close 작업
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("=END=");

	} // main

} // class

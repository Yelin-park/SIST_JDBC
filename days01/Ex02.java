package days01;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author yelin
 * @date 2022-05-02 15:13:20
 * @subject Connection 객체를 사용해서 DBMS(Oracle) 연결/닫기
 * @content
 * 			Class.forName("oracle.jdbc.driver.OracleDriver");
 * 			oracle.jdbc.driver : 패키지명
 *			OracleDriver : 클래스명
 * 
 */
public class Ex02 {

	public static void main(String[] args) {
		// DB를 연결할 때 사용하는 연결문자열(url, user, password)
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "SCOTT";
		String password = "tiger"; // 비밀번호 대소문자 구분한다.
		
		try {
			// 1. Class.forName() JDBC Driver 로딩
			Class.forName("oracle.jdbc.driver.OracleDriver");

			// 2. DriverManager.getConnection()로 객체를 얻어온다.
			Connection conn = DriverManager.getConnection(url, user, password);
			
			// 3. 필요한 작업(CRUD) : 지금은 안하고 DB 연동만 확인할 것임
			
			System.out.println(conn);
			System.out.println(conn.isClosed()); // 리턴자료형 : boolean -> 열렸니? false(열림), true(닫힘)
			
			// 4. 연결종료(Connection 객체 닫기)
			conn.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("END");

	} // main

} // class

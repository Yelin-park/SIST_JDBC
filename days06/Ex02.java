package days06;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.util.DBconn;

/**
 * @author yelin
 * @date 2022-05-11 10:57:34
 * @subject CallableStatement와 출력용 파라미터 사용하는 예제2
 * @content ID 중복 체크
 * 				jQuery + AJAX(에이작스, 아작스)로 아래와 같은 것을 처리...나중에 다시 배울것!
 */
public class Ex02 {

	public static void main(String[] args) {
		// up_idCheck
		System.out.print("> 중복 체크할 ID(empno)를 입력하세요? ");
		Scanner sc = new Scanner(System.in);
		int pempno = sc.nextInt();
		
		Connection conn = null;
		CallableStatement cstmt = null;
		
		String sql = "{ call up_idCheck(?, ?)}";
		
		conn = DBconn.getConnection();
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, pempno); // IN
			cstmt.registerOutParameter(2, oracle.jdbc.OracleTypes.INTEGER); // OUT
			cstmt.executeQuery();
			int idCheck = (int) cstmt.getObject(2);
			
			if (idCheck == 0) {
				System.out.println("사용 가능한 ID(empno) 입니다.");
			} else {
				System.out.println("이미 사용중인 ID(empno) 입니다.");
			} // if
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				cstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // try
		} // finally
		
		DBconn.close();
		System.out.println("END");

	} // main

} // class

/*
7369
7499
7521
7566
7654
7698
7782
7839
7844
7900
7902
7934
*/
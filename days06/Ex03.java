package days06;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

import com.util.DBconn;

/**
 * @author yelin
 * @date 2022-05-11 11:25:17
 * @subject CallableStatement와 출력용 파라미터 사용하는 예제3
 * @content 로그인 체크
 */
public class Ex03 {

	public static void main(String[] args) {
		// up_logon
		/*
		아이디(empno)    [			]
		비밀번호(ename)  [			]
			 [로그인] [회원가입]
			 
		ORM(MyBatis) -> 반복적인 작업을 안하고 쿼리만 작성하면 가능
		
		*/
		System.out.print("> 로그인 체크할 ID(empno)와 PW(ename)을 입력하세요? ");
		Scanner sc = new Scanner(System.in);
		int pempno = sc.nextInt();
		String pename = sc.next();
		Connection conn = null;
		CallableStatement cstmt = null;
		
		String sql = "{ call up_logon(?, ?, ?)}";
		
		conn = DBconn.getConnection();
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, pempno); // IN
			cstmt.setString(2, pename); // IN
			cstmt.registerOutParameter(3, oracle.jdbc.OracleTypes.INTEGER); // OUT
			cstmt.executeQuery();
			int logonCheck = (int) cstmt.getObject(3);
			
			if (logonCheck == 0) {
				System.out.println("로그인 성공!");
			} else if(logonCheck == -1){
				System.out.println("로그인 실패 - PW가 일치하지 않습니다.");
			} else if(logonCheck == 1) {
				System.out.println("로그인 실패 - ID가 존재하지 않습니다.");
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

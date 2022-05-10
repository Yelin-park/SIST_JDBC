package days05;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.util.DBconn;

/**
 * @author yelin
 * @date 2022-05-10 15:20:25
 * @subject 트랜잭션 처리 - 자바 JDBC에서 처리
 * @content > 트랜잭션 처리한 상태의 예제
 * 
 * 		 [트랜잭션 처리 - 자바 JDBC에서 처리]
 *		 하나의 논리적인 작업 단위 == 트랜잭션
 *		 하나의 논리적인 작업 단위 모두가 완료(성공) - 커밋
 *		 하나의 논리적인 작업 단위 모두가 완료 X - 롤백
 *		 예) 계좌 이체 = 1) + 2) 모두 성공하면 커밋, 하나라도 실패하면 롤백
 *		 		1) A -> 돈을 인출
 *				2) B -> A의 돈을 입금
 *		
 *		 1) dept 부서테이블에 90/QC/SEOUL 추가 - 성공
 *		 2) dept 부서테이블에 90/XX/YY 추가 - 실패
 *		 		-> 추가 취소하기
 * 
 */
public class Ex02_02 {

	public static void main(String[] args) {
		Connection conn = null;
		conn = DBconn.getConnection();
		
		PreparedStatement pstmt = null;
		int rowCount = 0;
		
		String sql = "INSERT INTO dept VALUES(?, ?, ?)";
		
		try {
			// ㄱ. 자동으로 커밋하지 않도록 설정
			conn.setAutoCommit(false); 
			
			// 1) dept 부서테이블에 90/QC/SEOUL 추가
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 90);
			pstmt.setString(2, "QC");
			pstmt.setString(3, "SEOUL");
			rowCount = pstmt.executeUpdate();
			
			if (rowCount == 1) {
				System.out.println("1) dept 부서테이블에 90/QC/SEOUL 추가 - 성공!");
			} // if
			
			// 2) dept 부서테이블에 90/XX/YY 추가
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, 90);
			pstmt.setString(2, "XX");
			pstmt.setString(3, "YY");
			rowCount = pstmt.executeUpdate();
			
			if (rowCount == 1) {
				System.out.println("2) dept 부서테이블에 90/XX/YY 추가 - 성공!");
			} // if
			
			// ㄴ. 두개의 작업이 다 성공하면 커밋을 하겠다.
			conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try { // ㄷ. 하나라도 실패시 롤백을 하겠다.
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // try
		} // finally 
		
		DBconn.close();
		System.out.println("END");
		
	} // main

} // class

package days05;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.util.DBconn;

/**
 * @author yelin
 * @date 2022-05-10 16:42:35
 * @subject
 * @content
 */
public class Ex05 {

	public static void main(String[] args) {
		String sql = "{ call up_deleteDept(?) }";
		// 1. up_deleteDept : 부서를 삭제하는 저장프로시저 생성
		// Ex04.java 복사 붙이기 수정
		Connection conn = null;
		CallableStatement cstmt = null;
		
		int rowCount = 0;
		
		conn = DBconn.getConnection();
		
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, 90);
			
			rowCount = cstmt.executeUpdate();
			
			if (rowCount == 1) {
				System.out.println("> 부서 삭제 완료!!");
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

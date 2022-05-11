package days06;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.util.DBconn;

public class 복습문제3 {

	public static void main(String[] args) {
		String sql = "{ call up_deleteDept(?) }";
		
		Connection conn = null;
		CallableStatement cstmt = null;
		
		int rowCount = 0;
		conn = DBconn.getConnection();
		
		try {
			conn.setAutoCommit(false);
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, 90);
			rowCount = cstmt.executeUpdate();
			
			if (rowCount == 1) {
				System.out.println("> 부서 삭제 완료!!");
			} else if (rowCount == 0) {
				throw new SQLException("부서 번호가 존재하지 않습니다.");
			} // if
			
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} // try 
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

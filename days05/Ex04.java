package days05;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;

import com.util.DBconn;

/**
 * @author yelin
 * @date 2022-05-10 16:26:27
 * @subject 
 * @content up_updateDept 저장 프로시저 생성 : 부서 정보를 수정
 * 			 pdeptno
 * 			 pdname
 * 			 ploc
 * 
 */
public class Ex04 {

	public static void main(String[] args) {
		// String sql = "{ call up_updateDept(pdeptno => ?, ploc => ?) }"; // 원래 부서명은 그대로 유지
		String sql = "{ call up_updateDept(pdeptno => ?, pdname => ?) }"; // 원래 지역명은 그대로 유지
		// String sql = "{ call up_updateDept(pdeptno => ?, pdname => ?, ploc => ?) }";
	
		Connection conn = null;
		CallableStatement cstmt = null;
		
		int rowCount = 0;
		
		conn = DBconn.getConnection();
		
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, 90);
			cstmt.setString(2, "SALES");
			
			rowCount = cstmt.executeUpdate();
			
			if (rowCount == 1) {
				System.out.println("> 부서 수정 완료!!");
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

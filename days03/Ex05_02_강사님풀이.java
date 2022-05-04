package days03;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

import com.util.DBconn;

/**
 * @author yelin
 * @date 2022-05-04 14:54:06
 * @subject
 * @content
 */
public class Ex05_02_강사님풀이 {

	public static void main(String[] args) {
		
		String sql = "SELECT grade ,losal, hisal, COUNT(*) cnt "
					+ "FROM salgrade s JOIN emp e ON sal BETWEEN losal AND hisal "
					+ "GROUP BY grade, losal, hisal "
					+ "ORDER BY grade ASC";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		ArrayList<SalgradeDTO> list = null;
		SalgradeDTO dto = null;
		
		conn = DBconn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			// ? 파라미터 설정할 거 있는지 꼭 확인
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				list = new ArrayList<SalgradeDTO>();
				do {
					int grade =  rs.getInt("grade");
					int losal = rs.getInt("losal");
					int hisal = rs.getInt("hisal");
					int cnt = rs.getInt("cnt");

					dto = new SalgradeDTO(grade, losal, hisal, cnt);
					list.add(dto);
					
				} while (rs.next());
			} // if
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		DBconn.close();
		
		printSalgrade(list);
		
	}

	private static void printSalgrade(ArrayList<SalgradeDTO> list) {
		if (list == null) {
			System.out.println("가져올 데이터 X");
			return;
		}
		
		Iterator<SalgradeDTO> ir = list.iterator();
		while (ir.hasNext()) {
			SalgradeDTO dto = ir.next();
			System.out.println(dto);
		}
	} // main

} // class
/*
[실행결과]
deptno dname empno ename sal
1등급   (     700~1200 ) - 2명
    20   RESEARCH   7369   SMITH   800
    30   SALES         7900   JAMES   950
2등급   (   1201~1400 ) - 2명
 30   SALES   7654   MARTIN   2650
 30   SALES   7521   WARD      1750   
3등급   (   1401~2000 ) - 2명
 30   SALES   7499   ALLEN      1900
 30   SALES   7844   TURNER   1500
4등급   (   2001~3000 ) - 4명
  10   ACCOUNTING   7782   CLARK   2450
 20   RESEARCH   7902   FORD   3000
 20   RESEARCH   7566   JONES   2975
 30   SALES   7698   BLAKE   2850
5등급   (   3001~9999 ) - 1명   
 10   ACCOUNTING   7839   KING   5000
 */

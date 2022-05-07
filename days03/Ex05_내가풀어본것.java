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
public class Ex05_내가풀어본것 {
	// -
	private static String[] sGrade = {"( 700~1200 )", "( 1201~1400 )", "( 1401~2000 )", "( 2001~3000 )", "( 3001~9999 )"};

	public static void main(String[] args) {

		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<EDSDTO> list = null;

		conn = DBconn.getConnection();

		String sql;

		try {
			for (int i = 1; i <= 5; i++) {

				sql = "SELECT grade || '등급' AS \"등급\", '(' || losal || '~' || hisal || ')' AS \"범위\" "
						+ "FROM salgrade "
						+ "WHERE grade = ?";

				pstmt = conn.prepareStatement(sql);

				pstmt.setInt(1, i);

				rs = pstmt.executeQuery();

				if (rs.next()) {
					do {
						String 등급 = rs.getString("등급");
						String 범위 = rs.getString("범위");

						System.out.printf("%s\t%s", 등급, 범위);

						sql = "SELECT t.deptno, t.dname, t.empno, t.ename, t.hiredate, t.sal "
								+ "FROM(\r\n"
								+ "     SELECT d.deptno, dname, empno, ename, hiredate, sal, grade "
								+ "     FROM dept d, emp e, salgrade s "
								+ "     WHERE d.deptno = e.deptno AND e.sal BETWEEN s.losal AND s.hisal "
								+ "     ORDER BY grade "
								+ ") t\r\n"
								+ "WHERE t.grade = ?";

						pstmt = conn.prepareStatement(sql);

						pstmt.setInt(1, i);

						rs = pstmt.executeQuery();

						if (rs.next()) {
							list = new ArrayList<EDSDTO>();
							do {
								int deptno = rs.getInt("deptno");
								String dname = rs.getString("dname");
								int empno = rs.getInt("empno");
								String ename = rs.getString("ename");
								double sal = rs.getDouble("sal");
								// int grade =  rs.getInt("grade");

								EDSDTO dto = new EDSDTO(deptno, dname, empno, ename, sal);
								list.add(dto);
							} while (rs.next());

							int s = list.size();

							System.out.println(" - " + s + "명");
							printInfo(list);
						} // if

					} while (rs.next());
					
				} // if

			} // for i

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

	}

	private static void printInfo(ArrayList<EDSDTO> list) {
		if (list == null) {
			System.out.println("가져올 데이터가 없습니다.");
			return;
		}
		Iterator<EDSDTO> ir = list.iterator();
		while (ir.hasNext()) {
			EDSDTO dto = ir.next();
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

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
 * @date 2022-05-04 14:15:18
 * @subject
 * @content
 */
public class Ex04 {

	public static void main(String[] args) {
		// deptno, dname, empno, ename, hiredate, job, sal, comm, pay(sal + comm), salgrade
		// ArrayList<> list
		// printEmp(list)
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ArrayList<EmpDeptSalDTO> list = null;
		EmpDeptSalDTO dto = null;
		
		conn = DBconn.getConnection();
		
		String sql = "SELECT d.deptno, dname, empno, ename, hiredate, job, sal, comm, sal + NVL(comm, 0) pay, grade "
					+ "FROM dept d, emp e, salgrade s "
					+ "WHERE d.deptno = e.deptno AND e.sal BETWEEN s.losal AND s.hisal "
					+ "ORDER BY grade";
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				list = new ArrayList<EmpDeptSalDTO>();
				do {
					int deptno = rs.getInt("deptno");
					String dname = rs.getString("dname");
					int empno = rs.getInt("empno");
					String ename = rs.getString("ename");
					Date hiredate = rs.getDate("hiredate");
					String job = rs.getString("job");; 
					double sal = rs.getDouble("sal");
					double comm = rs.getDouble("comm");
					double pay = rs.getDouble("pay");
					int grade =  rs.getInt("grade");;

					dto  =new EmpDeptSalDTO(deptno, dname, empno, ename, hiredate, job, sal, comm, pay, grade);
					list.add(dto);
				} while (rs.next());
			}
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
		
		printEmp(list);
		
	}

	private static void printEmp(ArrayList<EmpDeptSalDTO> list) {
		if (list == null) {
			System.out.println("가져올 데이터 X");
			return;
		}
		
		Iterator<EmpDeptSalDTO> ir = list.iterator();
		while (ir.hasNext()) {
			EmpDeptSalDTO dto = ir.next();
			System.out.println(dto);
		}
	} // main

} // class

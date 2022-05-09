package days04;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import com.util.DBconn;

public class Ex01_복습문제 {

	public static void main(String[] args) {
		Connection conn = null;
		PreparedStatement pstmt = null, empPstmt = null;
		ResultSet rs = null, empRs = null;
		RankDTO dto = null;
		EmpDTO empdto = null;
		ArrayList<EmpDTO> list = null;
		LinkedHashMap<RankDTO, ArrayList<EmpDTO>> map = null;
		
		String sql = "SELECT d.deptno, dname, COUNT(empno) "
					+ "FROM emp e RIGHT JOIN dept d ON d.deptno = e.deptno "
					+ "GROUP BY d.deptno, dname "
					+ "ORDER BY d.deptno";
		
		String empSql = "SELECT RANK() OVER(PARTITION BY d.deptno ORDER BY sal) \"등수\" "
						+ "        ,empno, ename, sal "
						+ "FROM emp e JOIN dept d ON d.deptno = e.deptno "
						+ "WHERE d.deptno = ?";
		
		conn = DBconn.getConnection();
		try {
			map = new LinkedHashMap<RankDTO, ArrayList<EmpDTO>>();
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				do {
					int deptno = rs.getInt(1);
					String dname = rs.getString(2);
					int cnt = rs.getInt(3);
					
					dto = new RankDTO(deptno, dname, cnt);
					
					empPstmt = conn.prepareStatement(empSql);
					empPstmt.setInt(1, deptno);
					empRs = empPstmt.executeQuery();

					if (empRs.next()) {
						list = new ArrayList<EmpDTO>();

						do {
							int 등수 = empRs.getInt(1);
							int empno = empRs.getInt(2);
							String ename = empRs.getString(3);
							double sal = empRs.getDouble(4);

							empdto = new EmpDTO(등수, empno, ename, sal);

							list.add(empdto);
							
						} while (empRs.next());
					} else {
						list = new ArrayList<EmpDTO>();
					}
					
					empPstmt.close();
					empRs.close();
					
					map.put(dto, list);
					
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
		} // try
		
		DBconn.close();
		
		printMap(map);
		
	} // main

	private static void printMap(LinkedHashMap<RankDTO, ArrayList<EmpDTO>> map) {
		Set<Entry<RankDTO, ArrayList<EmpDTO>>> es = map.entrySet();
		Iterator<Entry<RankDTO, ArrayList<EmpDTO>>> ir = es.iterator();
		
		while (ir.hasNext()) {
			Entry<RankDTO, ArrayList<EmpDTO>> entry = ir.next();
			System.out.println(entry.getKey());
			ArrayList<EmpDTO> list = entry.getValue();
			Iterator<EmpDTO> vir = list.iterator();
			if (vir.hasNext()) {
				System.out.println("\t등수 empno ename sal");
				while (vir.hasNext()) {
					EmpDTO dto = vir.next();
					System.out.println(dto);
				} // while
			} else {
				System.out.println("\t사원이 존재하지 않습니다.");
			}

		} // while
	} // printMap

} // class

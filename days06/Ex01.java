package days06;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBconn;

import days02.EmpDTO;

/**
 * @author yelin
 * @date 2022-05-11 10:10:15
 * @subject CallableStatement와 출력용 파라미터(커서) 사용하는 예제
 * @content deptno에 해당하는 사원정보 출력
 * 			emp테이블에서 deptno를 파라미터로 입력받아서 그 부서에 속해있는 사원 정보 반환하는 저장프로시저 생성(up_selectEmp)
 * 			Java에서 CallableStatement를 사용해서 출력 처리
 */
public class Ex01 {

	public static void main(String[] args) {
		System.out.print("> 부서번호(deptno)를 입력하세요? ");
		Scanner sc = new Scanner(System.in);
		int pdeptno = sc.nextInt();
		// 부서번호 있는지 체크를 자바 또는 프로시저 내에서 예외처리 해도 된다. 프로시저에서 처리하는 것이 편리
		
		String sql = "{ call up_selectemp(?, ?)}"; // 매개변수 : 부서번호, 커서
		Connection conn = null;
		CallableStatement cstmt = null;
		ResultSet rs = null;
		ArrayList<EmpDTO> list = null;
		EmpDTO dto = null;
		
		conn = DBconn.getConnection();
		
		int empno;
		String ename;
		String job;
		int mgr;
		Date hiredate;
		double sal;
		double comm;
		int deptno;
		
		try {
			cstmt = conn.prepareCall(sql);
			cstmt.setInt(1, pdeptno);
			
			// registerOutParameter -> 출력용 매개변수 타입 : oracle.jdbc.OracleTypes.CURSOR
			cstmt.registerOutParameter(2, oracle.jdbc.OracleTypes.CURSOR ); // SQLType -> 커서 타입
			
			// 반환값을 cursor가 가지고 있어서 rs로 반환하지 않아서 executeQuery만 실행하고 getObject로 값을 가져옴
			cstmt.executeQuery();
			rs = (ResultSet) cstmt.getObject(2); // 결과물이 커서에 있기 때문에 ResultSet 형변환 후 rs에 담음
			
			if (rs.next()) {
				list = new ArrayList<EmpDTO>();
				do {
					dto = new EmpDTO();
					
					dto.setEmpno(rs.getInt("empno"));
					dto.setEname(rs.getString("ename"));
					dto.setJob(rs.getString("job"));
					dto.setMgr(rs.getInt("mgr"));
					dto.setHiredate(rs.getDate("hiredate"));
					dto.setSal(rs.getDouble("sal"));
					dto.setComm(rs.getDouble("comm"));
					dto.setDeptno(rs.getInt("deptno"));
					
					list.add(dto);

				} while (rs.next());
			} // if
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				cstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} 
		} // finally
		
		DBconn.close();
		
		printEmp(list);
		
		System.out.println("END");
		
	} // main

	private static void printEmp(ArrayList<EmpDTO> list) {
		if (list == null) {
			System.out.println("해당 부서번호에는 사원이 존재하지 않습니다.");
		} // if
		Iterator<EmpDTO> ir = list.iterator();
		while (ir.hasNext()) {
			EmpDTO dto = ir.next();
			System.out.println(dto);
		} // while
	} // printEmp

} // class

package days06;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

import com.util.DBconn;

/**
 * @author yelin
 * @date 2022-05-11 11:47:32
 * @subject 리플렉션(reflection) 
 * @content - 반사, 반영
 * 			 - ResultSet 결과물에 대한 정보를 추출해서 사용할 수 있는 기술
 * 				(레코드의 결과말고 다른 결과를 추출할 수 있음)
 * 			ResultSet으로부터 어떤 컬럼이 어떤 자료형을 가지고 있는지 등등 정보를 얻어와서 사용하는 기술을 [리플렉션] 이라고 한다.
 */
public class Ex04 {

	public static void main(String[] args) {
		String sql = "SELECT table_name "
					+ "FROM tabs "
					+ "ORDER BY table_name";
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String tableName = null; // 테이블명을 저장할 변수
		
		conn = DBconn.getConnection();
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			int no = 1;
			while (rs.next()) {
				tableName = rs.getString(1);
				System.out.printf("%d. %s\n", no++, tableName);
			} // while
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // try
		} // finally
		
		System.out.print("> 테이블명을 입력하세요? ");
		Scanner sc = new Scanner(System.in);
		tableName = sc.next();
		// java.sql.SQLSyntaxErrorException: ORA-00903: invalid table name
		// 테이블명, 컬럼명은 ? (바인딩 변수) 사용할 수 없다. *****(암기)
		/*
		sql = "SELECT * "
				+ "FROM ? ";
		*/
		
		// ***
		sql = "SELECT * "
				+ "FROM " + tableName;
		
		try {
			pstmt = conn.prepareStatement(sql);
			// pstmt.setString(1, tableName);
			rs = pstmt.executeQuery();
			
			/*
			while (rs.next()) {
				int deptno = rs.getInt("deptno");
				String dname = rs.getString("dname");
				String loc = rs.getString("loc");
				System.out.printf("%d\t%s\t%s\n", deptno, dname, loc);

			} // while
			 */
			
			// ResultSet으로부터 어떤 컬럼이 어떤 자료형을 가지고 있는지 등등 정보를 얻어와서 사용하는 기술을 [리플렉션] 이라고 한다.
			ResultSetMetaData rsmd = rs.getMetaData();	
			// 1. 컬럼 갯수
			int columnCount = rsmd.getColumnCount();
			// System.out.println("> 컬럼갯수 : " + columnCount);
			
			// 2. 컬럼 정보
			for (int i = 1; i <= columnCount; i++) {
				String columnName = rsmd.getColumnName(i); // 컬럼명
				String columnTypeName = rsmd.getColumnTypeName(i); // 컬럼의 자료형
				int columnType = rsmd.getColumnType(i); // 컬럼의 자료형을 숫자로 나타낸 것
				// NUMBER(p, s)
				int scale = rsmd.getScale(i); // 실수라면 scale이 0보다 큼
				int precision = rsmd.getPrecision(i); // 자료형의 정밀도(크기)를 가져옴
				System.out.printf("%s - %s(%d) - %d,%d\n", columnName, columnTypeName, columnType, precision, scale);
			} // for i
			
			System.out.println("-------------------------------------------------------------");
			for (int i = 1; i <= columnCount; i++) {
				System.out.printf("%s\t", rsmd.getColumnName(i));
			} // for i 
			
			System.out.println("\n-------------------------------------------------------------");
			while (rs.next()) {
				for (int i = 1; i <= columnCount; i++) {
					
					// 2 NUMBER, 93 DATE, 12 VARCHAR2
					// scale > 0 -> 실수(double)
					int scale = rsmd.getScale(i);
					int columnType = rsmd.getColumnType(i);
					
					if (columnType == 2 && scale == 0) {
						System.out.printf("%d\t", rs.getInt(i));
					} else if (columnType == 2 && scale != 0) {
						System.out.printf("%.2f\t", rs.getDouble(i));
					} else if (columnType == 93) {
						System.out.printf("%tF\t", rs.getDate(i));
					} else if (columnType == 12) {
						System.out.printf("%s\t", rs.getString(i));
					} // if
				} // for
				System.out.println();
			} // while
			System.out.println("\n-------------------------------------------------------------");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			} // try
		} // finally

		DBconn.close();
		System.out.println("END");
	} // main

} // class

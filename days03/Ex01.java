package days03;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

import com.util.DBconn;

/**
 * @author yelin
 * @date 2022-05-04 10:02:13
 * @subject 복습문제 1번 풀이
 * @content
 */
public class Ex01 {

	public static void main(String[] args) {
		// System.getProperty("dir.user");
		String fileName =".\\src\\com\\util\\ConnectionString.properties";
		Properties p = new Properties();
		Reader reader;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<DepartmentsDTO> list = null;
		
		try {
			reader = new FileReader(fileName);
			p.load(reader);
			
			String url = p.getProperty("url");
			String user = p.getProperty("user");
			String password = p.getProperty("password");
			
			conn = DBconn.getConnection(url, user, password);
			stmt = conn.createStatement();
			
			String sql = "SELECT *"
						+ " FROM departments "
						+ "WHERE manager_id IS NULL";
			
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				list = new ArrayList<DepartmentsDTO>();
				do {
					int department_id = rs.getInt(1);
					String department_name = rs.getString(2);
					int manager_id = rs.getInt(3);
					int location_id = rs.getInt(4);
					
					DepartmentsDTO dto = new DepartmentsDTO(department_id, department_name, manager_id, location_id);
					list.add(dto);
					
					// System.out.println(dto);
				} while (rs.next());
			} // else {
				// System.out.println("가져올 정보가 없습니다.");
			// }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		printDepartment(list);
		
		DBconn.close();
		
	} // main

	private static void printDepartment(ArrayList<DepartmentsDTO> list) {
		if (list == null) {
			System.out.println("가져올 정보가 없습니다.");
			return;
		}
		
		Iterator<DepartmentsDTO> ir = list.iterator();
		while (ir.hasNext()) {
			DepartmentsDTO dto = ir.next();
			System.out.println(dto);
		}
	} // printDepartment

} // class

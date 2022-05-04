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
 * @date 2022-05-04 10:23:26
 * @subject 복습문제 2번 풀이
 * @content
 */
public class Ex01_02 {

	public static void main(String[] args) {
		String fileName =".\\src\\com\\util\\ConnectionString.properties";
		Properties p = new Properties();
		Reader reader;
		
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		ArrayList<JobsDTO> list = null;
		
		try {
			reader = new FileReader(fileName);
			p.load(reader);
			
			String url = p.getProperty("url");
			String user = p.getProperty("user");
			String password = p.getProperty("password");
			
			conn = DBconn.getConnection(url, user, password);
			stmt = conn.createStatement();
			
			String sql = "SELECT REPLACE(job_id, 'RE', '[RE]') job_id "
						+ ", REGEXP_REPLACE(job_title, '(RE|Re|rE|re)', '[\\1]') job_title "
						+ "FROM jobs "
						+ "WHERE REGEXP_LIKE(job_id, 're', 'i') "
						+ "OR REGEXP_LIKE(job_title, 're', 'i')";
			
			rs = stmt.executeQuery(sql);
			if (rs.next()) {
				list = new ArrayList();
				
				System.out.printf("%s\t%s\n", "JOB_ID", "JOB_TITLE");
				System.out.println("-------------------------------------------------------------");
				do {
					String job_id = rs.getString(1);
					String job_title = rs.getString(2);
					
					JobsDTO dto = new JobsDTO(job_id, job_title);
					list.add(dto);
					
				} while (rs.next());
			} 
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
		
		printJobs(list);
		
		DBconn.close();
		
	} // main
	
	private static void printJobs(ArrayList<JobsDTO> list) {
		if (list == null) {
			System.out.println("가져올 정보가 없습니다.");
			return;
		}
		
		Iterator<JobsDTO> ir = list.iterator();
		while (ir.hasNext()) {
			JobsDTO dto = ir.next();
			System.out.println(dto);
		}
	} // printDepartment

} // class

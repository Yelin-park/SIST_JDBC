package days06;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import com.util.DBconn;

import days04.board.BoardDTO;

public class 복습문제 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int currentPage, numPerPage, totalRecords, PageBlock;
		
		System.out.print("currentPage, numPerPage, totalRecords, PageBlock를 입력하세요? ");
		String[] datas = sc.next().split(",");
		currentPage = Integer.parseInt(datas[0]);
		numPerPage = Integer.parseInt(datas[1]);
		totalRecords = Integer.parseInt(datas[2]);
		PageBlock = Integer.parseInt(datas[3]);
		
		int totalPages = (int) Math.ceil((double) totalRecords / numPerPage);

		int start = (currentPage-1) / numPerPage * numPerPage + 1;
		int end = start + numPerPage -1;
		if(end > totalPages) end = totalPages;
		
		if (start != 1) System.out.print(" < "); 
		
		for (int i = start; i <= end; i++) {
			System.out.printf(i == currentPage ? "[%d] " : "%d ", i);
		} // for i
		
		if (end != totalPages) System.out.print(" > "); 
		
		System.out.println("=========================================================");
		
		System.out.print("> 검색 조건 : 제목(1), 내용(2), 작성자(3), 제목+내용(4) 선택하세요? ");
		int condition = sc.nextInt();
		System.out.print("> 검색어를 입력하세요? ");
		String searchWord = sc.next();
		
		search(currentPage, numPerPage, condition, searchWord);
		
	} // main
	
	public static ArrayList<BoardDTO> search(int currentPage, int numPerPage, int condition, String searchWord) {
		ArrayList<BoardDTO> list = null;
		BoardDTO dto = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		conn = DBconn.getConnection();

		int seq;
		String writer;
		String email;
		String title;
		int readed;
		Date writedate;
		
		String sql = "SELECT * "
				+ "FROM( "
				+ "    SELECT ROWNUM no, t.* "
				+ "    FROM("
				+ "SELECT seq, writer, email, title, readed, writedate "
				+ " FROM tbl_cstvsboard ";

		switch (condition) {
		case 1: // 제목
			sql += " WHERE REGEXP_LIKE(title,?,'i') ";
			break;
		case 2: // 내용
			sql += " WHERE REGEXP_LIKE(content,?,'i') ";
			break;
		case 3: // 작성자
			sql += " WHERE REGEXP_LIKE(writer,?,'i') ";
			break;
		case 4: // 제목 + 내용
			sql += " WHERE REGEXP_LIKE(title,?,'i') OR REGEXP_LIKE(content,?,'i') ";
			break;
		}

		sql += " ORDER BY seq DESC "
				+ "  ) t "
				+ ") a "
				+ "WHERE a.no BETWEEN ? AND ? ";
		
		int begin = (currentPage - 1) / numPerPage * numPerPage + 1;
		int end = begin + numPerPage - 1;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, searchWord);
			if (condition == 4) {
				pstmt.setString(2, searchWord);
				pstmt.setInt(3, begin);
				pstmt.setInt(4, end);
			} else {
				pstmt.setInt(2, begin);
				pstmt.setInt(3, end);
			}
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				list = new ArrayList<BoardDTO>();
				do {
					seq = rs.getInt("seq");
					writer = rs.getString("writer");
					email = rs.getString("email");
					title = rs.getString("title");
					readed = rs.getInt("readed");
					writedate = rs.getDate("writedate");
					
					dto = new BoardDTO(seq, writer, email, title, writedate, readed);
					list.add(dto);
				} while (rs.next());
			} else {
				list = new ArrayList<BoardDTO>();
			} // if
			
			System.out.println("\t\t 게시판");
			System.out.println("--------------------------------------------------------------------------");
			
			printList(list);
			
			System.out.println("--------------------------------------------------------------------------");
			// String pagingBlock = paging(currentPage, numPerPage, numberOfPageBlock, condition, searchWord);
			// System.out.println(pagingBlock);
			System.out.println("--------------------------------------------------------------------------");
		
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} // finally

		DBconn.close();

		return list;
	}

	public static void printList(ArrayList<BoardDTO> list) {
		if (list == null) {
			System.out.println("검색 조건에 해당하는 게시글이 없습니다.");
		} else {
			Iterator<BoardDTO> ir = list.iterator();
			while (ir.hasNext()) {
				BoardDTO dto = ir.next();
				System.out.println(dto);
			}
		}
	}
	
	public static String paging(int currentPage, int numberPerPage, int numberOfPageBlock
			, int searchCondition, String searchWord) {
		String pagingBlock = "\t\t\t";
		

		// int totalRecords = this.dao.getTotalRecords();
		int totalPages = 0; // 검색된 결과의 총페이지 수 반환
		int startOfPageBlock = 1;
		int endOfPageBlock;

		startOfPageBlock = (currentPage-1) / numberOfPageBlock * numberOfPageBlock + 1;
		endOfPageBlock = startOfPageBlock + numberOfPageBlock - 1;
		if(endOfPageBlock > totalPages) endOfPageBlock = totalPages;

		if (startOfPageBlock != 1) pagingBlock += " < ";
		for (int j = startOfPageBlock; j <= endOfPageBlock; j++) {
			pagingBlock += String.format(j==currentPage ? "[%d] " : "%d ", j);
		} // for j

		if (endOfPageBlock != totalPages) pagingBlock += " > ";
		
		return pagingBlock;
	} // pageService
	
} // class

package days04.board;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.util.DBconn;

public class BoardDAOImpl implements BoardDAO {
	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	// 생성자
	public BoardDAOImpl() {}
	// 1. 생성자를 통해서 의존성 주입(DI)
	public BoardDAOImpl( Connection conn ) {
		this.conn = conn;
	}
	// 2. setter를 통해서 의존성 주입(DI)
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public ArrayList<BoardDTO> select() throws SQLException {
		ArrayList<BoardDTO> list = null;
		BoardDTO dto = null;
		
		int seq;
		String writer;
		String email;
		String title;
		int readed;
		Date writedate;
		
		String sql = "SELECT seq, writer, email, title, readed, writedate "
					+ "FROM tbl_cstvsboard "
					+ "ORDER BY seq DESC";
		
		this.pstmt = this.conn.prepareStatement(sql);
		this.rs = this.pstmt.executeQuery();
		
		if (this.rs.next()) {
			list = new ArrayList<BoardDTO>();
			do {
				seq = this.rs.getInt("seq");
				writer = this.rs.getString("writer");
				email = this.rs.getString("email");
				title = this.rs.getString("title");
				readed = this.rs.getInt("readed");
				writedate = this.rs.getDate("writedate");
				
				dto = new BoardDTO(seq, writer, email, title, writedate, readed);
				
				list.add(dto);
			} while (this.rs.next());
		} // if
		
		this.rs.close();
		this.pstmt.close();
		
		return list;
	} // select

	@Override
	public int insert(BoardDTO dto) throws SQLException {
		int rowCount = 0;
		
		String sql = "INSERT INTO tbl_cstvsboard (seq, writer, pwd, email, title, tag, content) "
					+ "VALUES (seq_tbl_cstvsboard.nextval, ?, ?, ?, ?, ?, ?)";
		
		this.pstmt = this.conn.prepareStatement(sql);
		// ? 파라미터 설정.
		this.pstmt.setString(1, dto.getWriter());
		this.pstmt.setString(2, dto.getPwd());
		this.pstmt.setString(3, dto.getEmail());
		this.pstmt.setString(4, dto.getTitle());
		this.pstmt.setInt(5, dto.getTag());
		this.pstmt.setString(6, dto.getContent());
		
		rowCount = this.pstmt.executeUpdate(); // 자동 커밋 되어짐
		
		this.pstmt.close();
		
		return rowCount;
	} //insert
	
	@Override
	public void increaseReaded(int seq) throws SQLException {
		String sql = "UPDATE tbl_cstvsboard "
					+ "SET readed = readed + 1 "
					+ "WHERE seq = ?";
		
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setInt(1, seq);
		int rowCount = this.pstmt.executeUpdate();
		this.pstmt.close();
	} // increaseReaded
	
	@Override
	public BoardDTO view(int seq) throws SQLException {
		String sql = "SELECT seq, writer, email, title, readed, writedate, content "
					+ "FROM tbl_cstvsboard "
					+ "WHERE seq = ?";
		
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setInt(1, seq);
		this.rs = this.pstmt.executeQuery();
		BoardDTO dto = null;
		
		if (this.rs.next()) {
			dto = new BoardDTO();
			dto.setSeq(seq);
			dto.setWriter(this.rs.getString("writer"));
			dto.setEmail(this.rs.getString("email"));
			dto.setTitle(this.rs.getString("title"));
			dto.setReaded(this.rs.getInt("readed"));
			dto.setWritedate(this.rs.getDate("writedate"));
			dto.setContent(this.rs.getString("content"));
		} // if
		
		this.rs.close();
		this.pstmt.close();
		return dto;
	} // view
	
	@Override
	public int delete(int seq) throws SQLException {
		int rowCount;
		
		String sql = "DELETE tbl_cstvsboard "
					+ "WHERE seq = ?";

		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setInt(1, seq);
		rowCount = this.pstmt.executeUpdate();
		
		this.pstmt.close();

		return rowCount;
	} // delete
	
	@Override
	public int update(BoardDTO dto) throws SQLException {
		int rowCount = 0;
		
		String sql = "UPDATE tbl_cstvsboard "
					+ "SET email = ?, title = ?, content = ? "
					+ "WHERE seq = ?";

		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setString(1, dto.getEmail());
		this.pstmt.setString(2, dto.getTitle());
		this.pstmt.setString(3, dto.getContent());
		this.pstmt.setInt(4, dto.getSeq());
		
		rowCount = this.pstmt.executeUpdate(); 
		
		this.pstmt.close();
		
		return rowCount;
	} // update
	
	@Override
	public ArrayList<BoardDTO> search(int searchCondition, String searchWord) throws SQLException {
		ArrayList<BoardDTO> list = null;
		BoardDTO dto = null;
		
		int seq;
		String writer;
		String email;
		String title;
		int readed;
		Date writedate;
		
		String sql = "SELECT seq, writer, email, title, readed, writedate "
					+ "FROM tbl_cstvsboard ";
		
		switch (searchCondition) {
		case 1: // 제목
			sql += " WHERE REGEXP_LIKE(title, ?, 'i') ";
			break;
		case 2: // 내용
			sql += " WHERE REGEXP_LIKE(content, ?, 'i') ";
			break;
		case 3: // 작성자
			sql += " WHERE REGEXP_LIKE(writer, ?, 'i') ";
			break;
		case 4: // 제목 + 내용
			sql += " WHERE REGEXP_LIKE(title, ?, 'i') OR REGEXP_LIKE(content, ?, 'i') ";
			break;			
		} // switch
		
		sql += " ORDER BY seq DESC";
		
		this.pstmt = this.conn.prepareStatement(sql);
		this.pstmt.setString(1, searchWord); // 1,2,3,4 모두 무조건 1개의 파라미터 값을 주고
		if (searchCondition == 4) this.pstmt.setString(2, searchWord); // 4번 일때는 2개의 파라미터!
		
		this.rs = this.pstmt.executeQuery();
		
		if (this.rs.next()) {
			list = new ArrayList<BoardDTO>();
			do {
				seq = this.rs.getInt("seq");
				writer = this.rs.getString("writer");
				email = this.rs.getString("email");
				title = this.rs.getString("title");
				readed = this.rs.getInt("readed");
				writedate = this.rs.getDate("writedate");
				
				dto = new BoardDTO(seq, writer, email, title, writedate, readed);
				
				list.add(dto);
			} while (this.rs.next());
		} // if
		
		this.rs.close();
		this.pstmt.close();
		
		return list;
	} // search
	

} // class

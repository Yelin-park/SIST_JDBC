package days03;

/**
 * @author yelin
 * @date 2022-05-04 12:35:03
 * @subject 오라클 디벨로퍼에서 테이블 생성
 * @content
 */
public class Ex03 {

	public static void main(String[] args) {

		/*
		 * [MS SQL]
		 create table cstVSBoard (
			  seq int identity (1, 1) not null primary key clustered, -- 글번호
			  writer varchar (20) not null , -- 작성자
			  pwd varchar (20) not null , -- 비밀번호
			  email varchar (100) null , -- 이메일
			  title varchar (200) not null , -- 게시글제목
			  writedate smalldatetime not null default (getdate()), -- 작성일
			  readed int not null default (0), -- 조회수
			  mode tinyint not null , -- 모드(1 : html 적용 / 0 : html 적용X)
			  content text null -- 게시글내용
		)
		 */
		/*
		CREATE SEQUENCE seq_tbl_cstvsboard;

		CREATE TABLE tbl_cstVSBoard (
		    seq NUMBER NOT NULL PRIMARY KEY 
		     , writer VARCHAR2(20) NOT NULL
		     , pwd VARCHAR2(20) NOT NULL
		     , email VARCHAR2(100)
		     , title VARCHAR2(200) NOT NULL
		     , writedate DATE DEFAULT SYSDATE NOT NULL
		     , readed NUMBER DEFAULT 0
		     , tag NUMBER(1) DEFAULT 0
		     , content CLOB
		);
		*/
	} // class

} // main

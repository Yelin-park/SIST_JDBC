/*
1. 아래와 같이 출력되도록 코딩하세요.    
       ㄱ. 각 부서별 정렬 및 부서 소속 사원수 출력
       ㄴ. 각 부서별 소속된 사원 정보 출력
          ( 조건: sal 많이 받는 순으로 정렬해서 번호 붙여서 출력 )
*/


SELECT d.deptno, dname, COUNT(empno)
FROM emp e RIGHT JOIN dept d ON d.deptno = e.deptno
GROUP BY d.deptno, dname
ORDER BY d.deptno;


SELECT  d.deptno, empno, ename, sal
    , RANK() OVER(PARTITION BY d.deptno ORDER BY sal) "등수"
FROM emp e JOIN dept d ON d.deptno = e.deptno;

---------------------------------------------------------------------------------------

DESC tbl_cstvsboard;

-- 1) 모든 게시글 목록 반환하는 메서드에서 사용하는 쿼리
SELECT seq, writer, email, title, readed, writedate
FROM tbl_cstvsboard
ORDER BY seq DESC;

--2) 새로운 게시글을 추가하는 메서드에서 사용하는 쿼리
INSERT INTO tbl_cstvsboard (seq, writer, pwd, email, title, tag, content)
VALUES (seq_tbl_cstvsboard.nextval, ?, ?, ?, ?, ?, ?);

-- 시퀀스 확인
SELECT *
FROM user_sequences;

DROP SEQUENCE seq_tbl_cstvsboard;

-- 시퀀스 생성
CREATE SEQUENCE seq_tbl_cstvsboard;



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

DESC tbl_cstVSBoard;

--------------------------------------------------------------------------

DESC salgrade;

-- 등급 출력하는 쿼리
SELECT grade || '등급' AS "등급", '(' || losal || '~' || hisal || ')' AS "범위"
FROM salgrade s JOIN emp e ON sal BETWEEN losal AND hisal;

SELECT grade ,losal, hisal, COUNT(*) 명
FROM salgrade s JOIN emp e ON sal BETWEEN losal AND hisal
GROUP BY grade, losal, hisal
ORDER BY grade ASC;


--- dept와 emp조인
SELECT d.deptno, dname, empno, ename, sal
        , grade
FROM dept d FULL JOIN emp e ON d.deptno = e.deptno
            JOIN salgrade s ON sal BETWEEN losal AND hisal
WHERE grade = 5;





SELECT t.deptno, t.dname, t.empno, t.ename, t.hiredate, t.sal
FROM(
    SELECT d.deptno, dname, empno, ename, hiredate, sal, grade
    FROM dept d, emp e, salgrade s
    WHERE d.deptno = e.deptno AND e.sal BETWEEN s.losal AND s.hisal
    ORDER BY grade
)t
WHERE t.grade = 1;


SELECT d.deptno, dname, empno, ename, hiredate, job, sal, comm, sal + NVL(comm, 0) pay, grade
FROM dept d, emp e, salgrade s
WHERE d.deptno = e.deptno AND e.sal BETWEEN s.losal AND s.hisal
ORDER BY grade;
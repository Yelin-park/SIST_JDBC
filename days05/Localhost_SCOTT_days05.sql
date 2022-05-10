-- 이름 생성하는 함수)
CREATE OR REPLACE FUNCTION GET_KORNM
( V_FROM IN VARCHAR2, V_TO IN VARCHAR2 )
RETURN VARCHAR2
IS
    OUT_REAL_NM VARCHAR2(100);
    TYPE V_ARR IS TABLE OF VARCHAR2(10);
    V_FIRST V_ARR;
    V_LAST V_ARR;
    V_MID V_ARR;
BEGIN
    V_LAST := V_ARR('김' , '이' , '박' , '최' , '정' , '강' , '조' , '윤' , '장' , '임' , '오' , '한' , '신' , '서' , '권' , '황' , '안' , '송' , '유' , '홍' , '전' , '고' , '문' , '손' , '양' , '배' , '조' , '백' , '허' , '남');
    V_MID := V_ARR('민' , '현' , '동' , '인' , '지' , '현' , '재' , '우' , '건' , '준' , '승' , '영' , '성' , '진' , '준' , '정' , '수' , '광' , '영' , '호' , '중' , '훈' , '후' , '우' , '상' , '연' , '철' , '아' , '윤' , '은');
    V_FIRST := V_ARR('유' , '자' , '도' , '성' , '상' , '남' , '식' , '일' , '철' , '병' , '혜' , '영' , '미' , '환' , '식' , '숙' , '자' , '희' , '순' , '진' , '서' , '빈' , '정' , '지' , '하' , '연' , '성' , '공' , '안' , '원');
    
    SELECT SUBSTR(V_LAST(ROUND(DBMS_RANDOM.VALUE(1 , 30), 0)) ||
                    V_MID(ROUND(DBMS_RANDOM.VALUE(1 , 30), 0)) ||
                    V_FIRST(ROUND(DBMS_RANDOM.VALUE(1 , 30), 0)) ||
                    V_MID(ROUND(DBMS_RANDOM.VALUE(1 , 30), 0)) ||
                    V_FIRST(ROUND(DBMS_RANDOM.VALUE(1 , 30), 0)) ,V_FROM,V_TO)
        INTO OUT_REAL_NM
    FROM DUAL;
    
    RETURN OUT_REAL_NM;
END;


--------
-- 임의의 n개의 데이터 추가하는 익명프로시저)
DECLARE
    vi NUMBER := 1;
    vwriter tbl_cstvsboard.writer%TYPE;
    vemail tbl_cstvsboard.email%TYPE;
    vtitle tbl_cstvsboard.title%TYPE;
    vcontent tbl_cstvsboard.content%TYPE;
    vtag tbl_cstvsboard.tag%TYPE;
BEGIN
    FOR vi IN 1..347
    LOOP
        vwriter := GET_KORNM('1', '3');
        vemail := dbms_random.string('A', 5) || '@naver.com';
        vtitle := dbms_random.string('U', 10);
        vtag := TRUNC(dbms_random.value(0, 2));
        vcontent := dbms_random.string('A', 20);
        INSERT INTO tbl_cstvsboard (seq, writer, pwd, email, title, tag, content)
                    VALUES (seq_tbl_cstvsboard.nextval, vwriter, 1234, vemail, vtitle, vtag, vcontent);
    END LOOP;
-- EXCEPTION
END;


-------
-- 확인)
SELECT *
FROM tbl_cstvsboard
ORDER BY seq;

ROLLBACK;

COMMIT;

-- 삭제)
DELETE tbl_cstvsboard;

-- 구조확인)
DESC tbl_cstvsboard;

-- 시퀀스 삭제)
DROP SEQUENCE seq_tbl_cstvsboard;

-- 시퀀스 생성)
CREATE SEQUENCE seq_tbl_cstvsboard;


--------------------------------------
-- 강사님 풀이)
DECLARE
    vi NUMBER := 1;
    vwriter tbl_cstvsboard.writer%TYPE;
    vemail tbl_cstvsboard.email%TYPE;
    vtitle tbl_cstvsboard.title%TYPE;
    vcontent tbl_cstvsboard.content%TYPE;
    vfirstname VARCHAR2(6);
    vlastname VARCHAR2(3);
BEGIN
    WHILE vi <= 345
    LOOP
        vlastname := SUBSTR('김이박최차', TRUNC(DBMS_RANDOM.VALUE(1,6)), 1);
        vfirstname := SUBSTR('문자중지정된위치를숫자로리턴한다', TRUNC(DBMS_RANDOM.VALUE(1,LENGTH('문자중지정된위치를숫자로리턴한다'))), 2);
        vwriter := vlastname || vfirstname;
        INSERT INTO tbl_cstvsboard (seq, writer, pwd, email, title, tag, content, readed)
                    VALUES (seq_tbl_cstvsboard.nextval, vwriter, 1234, vemail, vtitle, 0, vcontent, vreaded);
                    
        vi := vi + 1;
    END LOOP;
-- EXCEPTION
END;

SELECT SUBSTR('김이박최차', TRUNC(DBMS_RANDOM.VALUE(1,6)), 1) lastname
        , SUBSTR('문자중지정된위치를숫자로리턴한다', TRUNC(DBMS_RANDOM.VALUE(1,LENGTH('문자중지정된위치를숫자로리턴한다'))), 2) firstname
FROM dual;


----------------------------------------
-- 총 게시글 수 : 347
-- 총 페이지 수 : 24 페이지 (23.1333333)
SELECT CEIL(COUNT(*) / 15 )
FROM tbl_cstvsboard;


----------------------------------------
-- 한 페이지에 출력할 게시글)
SELECT *
FROM (
    SELECT ROWNUM no, t.*
    FROM (
        SELECT seq, writer, email, title, readed, writedate
        FROM tbl_cstvsboard
        ORDER BY seq DESC
    ) t
) m
WHERE m.no BETWEEN ( (:currentPage - 1) * :numberPerPage + 1 ) AND (:currentPage - 1) * :numberPerPage + 1 + :numberPerPage - 1;

-- WHERE m.no BETWEEN 1 AND 15;


-------------------------------------
SELECT *
FROM tbl_cstvsboard
ORDER BY seq;

--
UPDATE tbl_cstvsboard
SET writer = '김검색'
WHERE seq BETWEEN 110 AND 148;

COMMIT;
--
SELECT *
FROM tbl_cstvsboard
WHERE writer = '김검색'
ORDER BY seq;


-------------------------------------
CREATE OR REPLACE PROCEDURE up_insertemp
(
    pEMPNO      NUMBER, 
    pENAME      VARCHAR2 := null, 
    pJOB        VARCHAR2 := null,  
    pMGR        NUMBER := null,     
    pHIREDATE   DATE := null,          
    pSAL        NUMBER := null,   
    pCOMM       NUMBER := null,   
    pDEPTNO     NUMBER := null  
)
IS
BEGIN
    INSERT INTO emp (empno, ename, job, mgr, hiredate, sal, comm, deptno)
    VALUES (pempno, pename, pjob, pmgr, phiredate, psal, pcomm, pdeptno);
    COMMIT;
-- EXCEPTION
END;

-- Procedure UP_INSERTEMP이(가) 컴파일되었습니다.
-- 반드시 저장 프로시저를 테스트 확인 후 Java에서 가져가서 사용
EXEC UP_INSERTEMP(pempno => 9999, pename => 'admin');

SELECT *
FROM emp;

ROLLBACK;

DELETE emp
WHERE empno = 9999;
COMMIT;


-------------------------------------
SELECT *
FROM dept;

INSERT INTO dept VALUES (90, 'QC', 'SEOUL');
COMMIT;

--
CREATE OR REPLACE PROCEDURE up_updateDept
(
    pdeptno dept.deptno%TYPE, 
    pdname dept.dname%TYPE := null, 
    ploc dept.loc%TYPE := null
)
IS
    vdname dept.dname%TYPE;
    vloc dept.loc%TYPE;
BEGIN
    SELECT dname, loc INTO vdname, vloc
    FROM dept
    WHERE deptno = pdeptno;
    
    UPDATE dept
    SET dname = NVL(pdname, vdname), loc = NVL(ploc, vloc)
    WHERE deptno = pdeptno;
    
    COMMIT;
-- EXCEPTION
END;

-- Procedure UP_UPDATEDEPT이(가) 컴파일되었습니다.


----------------------------------------------------
CREATE OR REPLACE PROCEDURE up_deleteDept
(
    pdeptno dept.deptno%TYPE
)
IS
BEGIN
    DELETE dept
    WHERE deptno = pdeptno;
    
    COMMIT;
-- EXCEPTION
END;

Procedure UP_DELETEDEPT이(가) 컴파일되었습니다.



-- �̸� �����ϴ� �Լ�)
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
    V_LAST := V_ARR('��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , 'Ȳ' , '��' , '��' , '��' , 'ȫ' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��');
    V_MID := V_ARR('��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , 'ȣ' , '��' , '��' , '��' , '��' , '��' , '��' , 'ö' , '��' , '��' , '��');
    V_FIRST := V_ARR('��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , 'ö' , '��' , '��' , '��' , '��' , 'ȯ' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��' , '��');
    
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
-- ������ n���� ������ �߰��ϴ� �͸����ν���)
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
-- Ȯ��)
SELECT *
FROM tbl_cstvsboard
ORDER BY seq;

ROLLBACK;

COMMIT;

-- ����)
DELETE tbl_cstvsboard;

-- ����Ȯ��)
DESC tbl_cstvsboard;

-- ������ ����)
DROP SEQUENCE seq_tbl_cstvsboard;

-- ������ ����)
CREATE SEQUENCE seq_tbl_cstvsboard;


--------------------------------------
-- ����� Ǯ��)
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
        vlastname := SUBSTR('���̹�����', TRUNC(DBMS_RANDOM.VALUE(1,6)), 1);
        vfirstname := SUBSTR('��������������ġ�����ڷθ����Ѵ�', TRUNC(DBMS_RANDOM.VALUE(1,LENGTH('��������������ġ�����ڷθ����Ѵ�'))), 2);
        vwriter := vlastname || vfirstname;
        INSERT INTO tbl_cstvsboard (seq, writer, pwd, email, title, tag, content, readed)
                    VALUES (seq_tbl_cstvsboard.nextval, vwriter, 1234, vemail, vtitle, 0, vcontent, vreaded);
                    
        vi := vi + 1;
    END LOOP;
-- EXCEPTION
END;

SELECT SUBSTR('���̹�����', TRUNC(DBMS_RANDOM.VALUE(1,6)), 1) lastname
        , SUBSTR('��������������ġ�����ڷθ����Ѵ�', TRUNC(DBMS_RANDOM.VALUE(1,LENGTH('��������������ġ�����ڷθ����Ѵ�'))), 2) firstname
FROM dual;


----------------------------------------
-- �� �Խñ� �� : 347
-- �� ������ �� : 24 ������ (23.1333333)
SELECT CEIL(COUNT(*) / 15 )
FROM tbl_cstvsboard;


----------------------------------------
-- �� �������� ����� �Խñ�)
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
SET writer = '��˻�'
WHERE seq BETWEEN 110 AND 148;

COMMIT;
--
SELECT *
FROM tbl_cstvsboard
WHERE writer = '��˻�'
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

-- Procedure UP_INSERTEMP��(��) �����ϵǾ����ϴ�.
-- �ݵ�� ���� ���ν����� �׽�Ʈ Ȯ�� �� Java���� �������� ���
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

-- Procedure UP_UPDATEDEPT��(��) �����ϵǾ����ϴ�.


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

Procedure UP_DELETEDEPT��(��) �����ϵǾ����ϴ�.



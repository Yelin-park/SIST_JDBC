CREATE OR REPLACE PROCEDURE up_deleteDept
(
    pdeptno dept.deptno%TYPE
)
IS
    vdname dept.dname%TYPE;
    vloc dept.loc%TYPE;
    vcnt NUMBER := 0;
    e_no_deptno EXCEPTION;
BEGIN
    SELECT COUNT(*) INTO vcnt
    FROM dept
    WHERE deptno = pdeptno;
    
    IF vcnt = 0 THEN
        RAISE e_no_deptno;
    END IF;
    
    DELETE dept
    WHERE deptno = pdeptno;
    COMMIT;
EXCEPTION
    WHEN e_no_deptno THEN
    RAISE_APPLICATION_ERROR(-20002, '삭제할 부서번호가 존재하지 않습니다');
    WHEN OTHERS THEN
    RAISE_APPLICATION_ERROR(-20004, 'Others Error');
END;

-------------------------------------------------------------------------------------
[ Ex01 관련 쿼리 ]
-- 동적 쿼리 사용
-- 응용프로그램에서 사용할 수 있도록 출력용 매개변수로 선언
-- 쿼리 안에서 커서 선언, 커서 OPEN, LOOP / FETCH, DBMS OUPUT 작업을 하지 않고 자바에서 처리!

CREATE OR REPLACE PROCEDURE up_selectEmp
(
    pdeptno dept.deptno%TYPE,
    pcursor OUT SYS_REFCURSOR -- 외부로 돌려주는 출력용 파라미터
)
IS
    --1) 커서 선언
    vsql VARCHAR2(2000);
    
BEGIN
    --2) 커서 OPEN
    vsql := ' SELECT * ';
    vsql := vsql || ' FROM emp ';
    vsql := vsql || ' WHERE deptno = :pdeptno ';
    
    -- OPEN FOR 문
    -- pdeptno 파라미터로 받아서 vsql 넘기고 그 결과 값을 pcursor 커서 파라미터에 담는다.
    OPEN pcursor FOR vsql USING pdeptno;

-- EXCEPTION
END;

-- Procedure UP_SELECTEMP이(가) 컴파일되었습니다.


-----------------------------------------------------------
create or replace PROCEDURE up_idCheck
(
    pempno IN emp.empno%TYPE -- ID를 받을 파라미터
    , pempnoCheck OUT NUMBER -- 사용가능여부(0, 1)를 돌려주는 파라미터
)
IS
BEGIN
    SELECT COUNT(*) INTO pempnoCheck
    FROM emp
    WHERE empno = pempno;
    -- ID가 있다면 갯수가 늘어나니까 바로 확인 가능.
-- EXCEPTION
END;

SELECT *
FROM emp;

------------------------------------------------------------
create or replace PROCEDURE up_logon
(
        pempno IN emp.empno%TYPE -- ID를 받을 파라미터(ID 대신 사용)
        , pename IN emp.ename%TYPE -- PW 받을 파라미터
        , plogonCheck OUT NUMBER -- 로그인 성공 0, ID가 존재X 1, PW가 일치하지않으면 -1
)
IS
        vename emp.ename%TYPE; -- 실제 비밀번호를 저장할 변수
BEGIN
        SELECT COUNT(*) INTO plogonCheck
        FROM emp
        WHERE empno = pempno;

        IF plogonCheck = 1 THEN -- ID가 존재한다면

            SELECT ename INTO vename
            FROM emp
            WHERE empno = pempno;

            IF vename = pename THEN -- ID가 존재하고 PW 일치하면
              plogonCheck := 0; -- 로그인 성공
            ELSE
              plogonCheck := -1; -- ID가 존재하지만 PW 일치하지 않으면 -1 반환
            END IF;

        ELSE
            plogonCheck := 1; -- ID가 존재하지 않는 경우
        END IF;
-- EXCEPTION
END;


------------------------------------------------------------
SELECT table_name
FROM tabs
ORDER BY table_name;
FROM user_tables















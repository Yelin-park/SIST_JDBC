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
    RAISE_APPLICATION_ERROR(-20002, '������ �μ���ȣ�� �������� �ʽ��ϴ�');
    WHEN OTHERS THEN
    RAISE_APPLICATION_ERROR(-20004, 'Others Error');
END;

-------------------------------------------------------------------------------------
[ Ex01 ���� ���� ]
-- ���� ���� ���
-- �������α׷����� ����� �� �ֵ��� ��¿� �Ű������� ����
-- ���� �ȿ��� Ŀ�� ����, Ŀ�� OPEN, LOOP / FETCH, DBMS OUPUT �۾��� ���� �ʰ� �ڹٿ��� ó��!

CREATE OR REPLACE PROCEDURE up_selectEmp
(
    pdeptno dept.deptno%TYPE,
    pcursor OUT SYS_REFCURSOR -- �ܺη� �����ִ� ��¿� �Ķ����
)
IS
    --1) Ŀ�� ����
    vsql VARCHAR2(2000);
    
BEGIN
    --2) Ŀ�� OPEN
    vsql := ' SELECT * ';
    vsql := vsql || ' FROM emp ';
    vsql := vsql || ' WHERE deptno = :pdeptno ';
    
    -- OPEN FOR ��
    -- pdeptno �Ķ���ͷ� �޾Ƽ� vsql �ѱ�� �� ��� ���� pcursor Ŀ�� �Ķ���Ϳ� ��´�.
    OPEN pcursor FOR vsql USING pdeptno;

-- EXCEPTION
END;

-- Procedure UP_SELECTEMP��(��) �����ϵǾ����ϴ�.


-----------------------------------------------------------
create or replace PROCEDURE up_idCheck
(
    pempno IN emp.empno%TYPE -- ID�� ���� �Ķ����
    , pempnoCheck OUT NUMBER -- ��밡�ɿ���(0, 1)�� �����ִ� �Ķ����
)
IS
BEGIN
    SELECT COUNT(*) INTO pempnoCheck
    FROM emp
    WHERE empno = pempno;
    -- ID�� �ִٸ� ������ �þ�ϱ� �ٷ� Ȯ�� ����.
-- EXCEPTION
END;

SELECT *
FROM emp;

------------------------------------------------------------
create or replace PROCEDURE up_logon
(
        pempno IN emp.empno%TYPE -- ID�� ���� �Ķ����(ID ��� ���)
        , pename IN emp.ename%TYPE -- PW ���� �Ķ����
        , plogonCheck OUT NUMBER -- �α��� ���� 0, ID�� ����X 1, PW�� ��ġ���������� -1
)
IS
        vename emp.ename%TYPE; -- ���� ��й�ȣ�� ������ ����
BEGIN
        SELECT COUNT(*) INTO plogonCheck
        FROM emp
        WHERE empno = pempno;

        IF plogonCheck = 1 THEN -- ID�� �����Ѵٸ�

            SELECT ename INTO vename
            FROM emp
            WHERE empno = pempno;

            IF vename = pename THEN -- ID�� �����ϰ� PW ��ġ�ϸ�
              plogonCheck := 0; -- �α��� ����
            ELSE
              plogonCheck := -1; -- ID�� ���������� PW ��ġ���� ������ -1 ��ȯ
            END IF;

        ELSE
            plogonCheck := 1; -- ID�� �������� �ʴ� ���
        END IF;
-- EXCEPTION
END;


------------------------------------------------------------
SELECT table_name
FROM tabs
ORDER BY table_name;
FROM user_tables















/*
1. �Ʒ��� ���� ��µǵ��� �ڵ��ϼ���.    
       ��. �� �μ��� ���� �� �μ� �Ҽ� ����� ���
       ��. �� �μ��� �Ҽӵ� ��� ���� ���
          ( ����: sal ���� �޴� ������ �����ؼ� ��ȣ �ٿ��� ��� )
*/


SELECT d.deptno, dname, COUNT(empno)
FROM emp e RIGHT JOIN dept d ON d.deptno = e.deptno
GROUP BY d.deptno, dname
ORDER BY d.deptno;


SELECT  d.deptno, empno, ename, sal
    , RANK() OVER(PARTITION BY d.deptno ORDER BY sal) "���"
FROM emp e JOIN dept d ON d.deptno = e.deptno;

---------------------------------------------------------------------------------------

DESC tbl_cstvsboard;

-- 1) ��� �Խñ� ��� ��ȯ�ϴ� �޼��忡�� ����ϴ� ����
SELECT seq, writer, email, title, readed, writedate
FROM tbl_cstvsboard
ORDER BY seq DESC;

--2) ���ο� �Խñ��� �߰��ϴ� �޼��忡�� ����ϴ� ����
INSERT INTO tbl_cstvsboard (seq, writer, pwd, email, title, tag, content)
VALUES (seq_tbl_cstvsboard.nextval, ?, ?, ?, ?, ?, ?);

-- ������ Ȯ��
SELECT *
FROM user_sequences;

DROP SEQUENCE seq_tbl_cstvsboard;

-- ������ ����
CREATE SEQUENCE seq_tbl_cstvsboard;



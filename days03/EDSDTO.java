package days03;

import java.util.Date;

public class EDSDTO {
	private int deptno;
	private String dname;
	private int empno;
	private String ename;
	private double sal;
	private int grade;
	
	public EDSDTO() {
		super();
	}

	public EDSDTO(int deptno, String dname, int empno, String ename, double sal, int grade) {
		super();
		this.deptno = deptno;
		this.dname = dname;
		this.empno = empno;
		this.ename = ename;
		this.sal = sal;
		this.grade = grade;
	}
	
	public EDSDTO(int deptno, String dname, int empno, String ename, double sal) {
		super();
		this.deptno = deptno;
		this.dname = dname;
		this.empno = empno;
		this.ename = ename;
		this.sal = sal;
	}

	public int getDeptno() {
		return deptno;
	}

	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public int getEmpno() {
		return empno;
	}

	public void setEmpno(int empno) {
		this.empno = empno;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public double getSal() {
		return sal;
	}

	public void setSal(double sal) {
		this.sal = sal;
	}

	public int getGrade() {
		return grade;
	}

	public void setGrade(int grade) {
		this.grade = grade;
	}

	@Override
	public String toString() {
		return String.format("%d\t%s\t%d\t%s\t%.2f", deptno, dname, empno, ename, sal);
	}
	
} // class

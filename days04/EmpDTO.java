package days04;

public class EmpDTO {
	private int 등수;
	private int empno;
	private String ename;
	private double sal;
	
	public EmpDTO() {
		super();
	}
	
	public EmpDTO(int 등수, int empno, String ename, double sal) {
		super();
		this.등수 = 등수;
		this.empno = empno;
		this.ename = ename;
		this.sal = sal;
	}

	public int get등수() {
		return 등수;
	}

	public void set등수(int 등수) {
		this.등수 = 등수;
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

	@Override
	public String toString() {
		return String.format("\t%d %d %s %.1f", 등수, empno, ename, sal);
	}

} // class

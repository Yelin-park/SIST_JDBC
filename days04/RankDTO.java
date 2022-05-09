package days04;

public class RankDTO {
	private int deptno;
	private String dname;
	private int cnt;
	
	public RankDTO() {
		super();
	}
	
	public RankDTO(int deptno, String dname, int cnt) {
		super();
		this.deptno = deptno;
		this.dname = dname;
		this.cnt = cnt;
	}

	public int getDeptno() {
		return deptno;
	}

	public void setDeptno(int deptno) {
		this.deptno = deptno;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

	public String getDname() {
		return dname;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	@Override
	public String toString() {
		return String.format("%d(%s) - %d명", deptno, dname, cnt);
	}
	
}

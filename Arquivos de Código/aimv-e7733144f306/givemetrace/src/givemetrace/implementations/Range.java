package givemetrace.implementations;

public class Range {
	private int start;
	private int end;
	
	public Range() {
		super();
	}
	
	public Range(int start, int end) {
		super();
		this.start = start;
		this.end = end;
	}
	
	public int getStart() {
		return start;
	}
	
	public void setStart(int start) {
		this.start = start;
	}
	
	public int getEnd() {
		return end;
	}
	
	public void setEnd(int end) {
		this.end = end;
	}
	public boolean inRange(int in) {
		if(start<=in && in<=end){
			return true;
		}else{
			return false;
			}
	}
	
}

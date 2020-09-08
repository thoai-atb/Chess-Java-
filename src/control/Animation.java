package control;

public class Animation {
	
	public float x, y;
	public float tx, ty;
	
	public void update() {
		x = lerp(x, tx, 0.2f);
		y = lerp(y, ty, 0.2f);
	}
	
	public void setLoc(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setTarget(int x, int y) {
		tx = x;
		ty = y;
	}
	
	public boolean isQuiet() {
		return Math.abs(x - tx) < 0.1 && Math.abs(y - ty) < 0.1;
	}
	
	private float lerp(float start, float end, float r) {
		return start + r * (end - start);
	}
}

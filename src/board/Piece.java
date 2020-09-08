package board;
import java.util.HashMap;
import java.util.Map;

public class Piece {
	
	public int x, y;
	public PieceType type;
	public Map<String, Boolean> properties = new HashMap<String, Boolean>();
	
	public Piece(int x, int y, PieceType type) {
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	public Piece clone() {
		Piece p = new Piece(x, y, type);
		for(String s : properties.keySet()) {
			p.properties.put(s, properties.get(s));
		}
		
		return p;
	}
	public void setCoord(int x, int y) {
		this.x = x;
		this.y = y;
	}
}

package board;



import java.util.ArrayList;
import java.util.List;

import board.setup.BlankSetup;
import board.setup.BoardSetup;

public class Board {
	public Piece[][] pieces;
	public final int width, height;
	public Move lastMove;
	public Rule rule = new Rule();
	public boolean whiteToMove = true;
	
	public Board(int width, int height, BoardSetup setup) {
		this.width = width;
		this.height = height;
		pieces = new Piece[width][height];
		
		setup.setup(this);
	}
	
	public Board clone() {
		Board b = new Board(width, height, new BlankSetup());
		for(Piece p : getPieceList()) {
			b.pieces[p.x][p.y] = p.clone(); 
			if(lastMove != null)
				b.lastMove = lastMove.clone();
			b.whiteToMove = whiteToMove;
		}
		return b;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean contains(int x, int y) {
		return x >= 0 && x < width && y >= 0 && y < height;
	}
	
	public boolean hasPiece(int x, int y) {
		return contains(x, y) && pieces[x][y] != null;
	}
	
	public void removePiece(int x, int y) {
		if(contains(x, y))
			pieces[x][y] = null;
	}
	
	public Piece getPiece(int x, int y) {
		return pieces[x][y];
	}
	
	public Piece lastMovePiece() {
		if(lastMove == null)
			return null;
		return getPiece(lastMove.x2, lastMove.y2);
	}
	
	public List<Piece> getPieceList() {
		ArrayList<Piece> list = new ArrayList<Piece>();
		for(int i = 0; i<width; i++)
			for(int j = 0; j<height; j++) {
				if(pieces[i][j] != null)
					list.add(pieces[i][j]);
			}
		return list;
	}
	
	public List<Piece> getPieceList(boolean isWhite){
		ArrayList<Piece> list = new ArrayList<Piece>();
		for(int i = 0; i<width; i++)
			for(int j = 0; j<height; j++) {
				if(pieces[i][j] != null && pieces[i][j].type.isWhite() == isWhite)
					list.add(pieces[i][j]);
			}
		return list;
	}
	
	public Piece getKing(boolean isWhite) {
		for(int i = 0; i<width; i++)
			for(int j = 0; j<height; j++) {
				if(pieces[i][j] != null) {
					if(isWhite && pieces[i][j].type == PieceType.WHITE_KING)
						return pieces[i][j];
					if(!isWhite && pieces[i][j].type == PieceType.BLACK_KING)
						return pieces[i][j];
				}
			}
		return null;
	}
	
	public void createPiece(int x, int y, PieceType type) {
		pieces[x][y] = new Piece(x, y, type);
	}
	
	public void createPiece(String s, PieceType type) {
		int[] loc = toIndex(s);
		int x = loc[0];
		int y = loc[1];
		pieces[x][y] = new Piece(x, y, type);
	}
	
	private int[] toIndex(String s) {
		int[] loc = new int[2];
		loc[0] = s.charAt(0) - 'a';
		loc[1] = 7 - (s.charAt(1) - '1');
		return loc;
	}
	
	public boolean isValidMove(int x1, int y1, int x2, int y2) {
		return rule.checkMove(this, false, true, x1, y1, x2, y2);
	}
	
	public boolean move(int x1, int y1, int x2, int y2, boolean kingSafe) {
		return rule.checkMove(this, true, kingSafe, x1, y1, x2, y2);
	}
	
}

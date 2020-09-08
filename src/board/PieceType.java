package board;



public enum PieceType {
	WHITE_KING, WHITE_QUEEN, WHITE_BISHOP, WHITE_KNIGHT, WHITE_ROOK, WHITE_PAWN,
	BLACK_KING, BLACK_QUEEN, BLACK_BISHOP, BLACK_KNIGHT, BLACK_ROOK, BLACK_PAWN;
	
	public boolean isWhite() {
		return this.ordinal() < 6;
	}
	
	public boolean isRook() {
		return this == WHITE_ROOK || this == BLACK_ROOK;
	}
	
	public boolean isKing() {
		return this == WHITE_KING || this == BLACK_KING;
	}
	
	public boolean isKnight() {
		return this == WHITE_KNIGHT || this == BLACK_KNIGHT;
	}
	
	public boolean isQueen() {
		return this == WHITE_QUEEN || this == BLACK_QUEEN;
	}
	
	public boolean isPawn() {
		return this == WHITE_PAWN || this == BLACK_PAWN;
	}
	
	public boolean isBishop() {
		return this == WHITE_BISHOP || this == BLACK_BISHOP;
	}
}

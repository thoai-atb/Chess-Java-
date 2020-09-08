package board;
public class ExplodingPawnRule extends Rule {
	@Override
	protected void updateMove(Board board, int enpassantX, int enpassantY, int x1, int y1, int x2, int y2) {		
		Piece p = board.pieces[x2][y2];
		
		board.pieces[x1][y1].setCoord(x2, y2);
		board.pieces[x1][y1].properties.put(MOVED, true);
		board.pieces[x2][y2] = board.pieces[x1][y1];
		board.pieces[x1][y1] = null;
		
		if(p != null && p.type.isPawn()) {
			for(int i = -1; i<=1; i++)
				for(int j = -1; j<=1; j++)
					board.removePiece(x2 + i, y2 + j);
		}
		
		if(enpassantX >= 0 && enpassantY >= 0)
			board.pieces[enpassantX][enpassantY] = null; 
		
		// Promotion
		if(y2 == 0 && board.pieces[x2][y2].type == PieceType.WHITE_PAWN)
			board.pieces[x2][y2].type = PieceType.WHITE_QUEEN;
		if(y2 == 7 && board.pieces[x2][y2].type == PieceType.BLACK_PAWN)
			board.pieces[x2][y2].type = PieceType.BLACK_QUEEN;
		
		// Castling
		if(board.pieces[x2][y2] != null && board.pieces[x2][y2].type.isKing()) {
			if(x2 == x1 - 2) {
				board.pieces[x2+1][y2] = board.pieces[0][y2];
				board.pieces[x2+1][y2].setCoord(x2+1, y2);
				board.pieces[0][y2] = null;
			} else if (x2 == x1 + 2) {
				board.pieces[x2-1][y2] = board.pieces[7][y2];
				board.pieces[x2-1][y2].setCoord(x2-1, y2);
				board.pieces[7][y2] = null;
			}
		}
		
		board.lastMove = new Move(x1, y1, x2, y2);
		board.whiteToMove = !board.whiteToMove;
	}
}

package board;
import java.util.ArrayList;
import java.util.List;

public class Rule {
	
	public static String MOVED = "moved";
	
	public synchronized boolean checkMove(Board board, boolean moveAfterChecking, boolean kingSafe, int x1, int y1, int x2, int y2) {
		// boundary
		if(x1 < 0 || y1 < 0 || x2 < 0 || y2 < 0 ||
		   x1 >= board.getWidth() || y1 >= board.getHeight() || x2 >= board.getWidth() || y2 >= board.getHeight())
			return false;
		// not moving
		if(x1 == x2 && y1 == y2)
			return false;
		// Piece exists
		Piece p = board.pieces[x1][y1];
		if(p == null)
			return false;
		// Correct Turn
		if(board.whiteToMove != p.type.isWhite())
			return false;
		// Same side capturing check
		Piece targetPiece = board.pieces[x2][y2];
		if(targetPiece != null && targetPiece.type.isWhite() == p.type.isWhite())
			return false;
		
		int enpassantX = -1;
		int enpassantY = -1;
		
		boolean valid = false;
		switch(p.type) {
			case BLACK_ROOK:
			case WHITE_ROOK:
			{
				valid = emptyStraight(board, x1, y1, x2, y2);
				break;
			}
			case BLACK_KNIGHT:
			case WHITE_KNIGHT:
			{
				int dx = Math.abs(x1 - x2);
				int dy = Math.abs(y1 - y2);
				valid = (dx * dy == 2) && (dx + dy == 3);
				break;
			}
			case BLACK_BISHOP:
			case WHITE_BISHOP:
			{
				valid = emptyDiagonal(board, x1, y1, x2, y2);
				break;
			}
			case BLACK_QUEEN:
			case WHITE_QUEEN:
			{
				valid = emptyStraight(board, x1, y1, x2, y2) ||
						emptyDiagonal(board, x1, y1, x2, y2);
				break;
			}
			case BLACK_KING:
			case WHITE_KING:
			{
				int dx = Math.abs(x1 - x2);
				int dy = Math.abs(y1 - y2);
				if(dx <= 1 && dy <= 1) {
					valid = true;
				} else {
					valid = castleValid(board, x1, y1, x2, y2);
				}
				
				break;
			}
			case BLACK_PAWN:
			case WHITE_PAWN:
			{
				if(targetPiece == null) {
					if(x1 != x2) {
						valid = false;
						if(board.lastMove == null)
							break;
						if(!(x1 == x2 + 1 || x1 == x2 - 1)) // near
							break;
						if(!(p.type.isWhite()? y2 == y1 - 1 : y2 == y1 + 1)) // ahead
							break;
						boolean en1 = board.lastMove.x2 == x2 && board.lastMove.y2 == y1;
						boolean en2 = board.lastMove.x1 == x2 && board.lastMove.y1 == (p.type.isWhite()? y1 - 2 : y1 + 2);
						Piece p1 = board.getPiece(board.lastMove.x2, board.lastMove.y2);
						if(en1 && en2 && p1 != null &&
								(p1.type == PieceType.BLACK_PAWN || p1.type  == PieceType.WHITE_PAWN)) {
							valid = true;
							enpassantX = board.lastMove.x2;
							enpassantY = board.lastMove.y2;
						}
						break;
					}
					if(p.type.isWhite()) {
						valid = (y2 == y1 - 1);
						if(y2 == y1 - 2 && y1 >= 6 && board.getPiece(x1, y1 - 1) == null)
							valid = true;
					}else {
						valid = (y2 == y1 + 1);
						if(y2 == y1 + 2 && y1 <= 1 && board.getPiece(x1, y1 + 1) == null)
							valid = true;
					}
				} else {
					boolean near = (x1 == x2 + 1 || x1 == x2 - 1);
					boolean ahead = p.type.isWhite()? y2 == y1 - 1 : y2 == y1 + 1;
					valid = near && ahead;
				}
				break;
			}
			default:
		}

		if(valid && kingSafe) {
			Board temp = board.clone();
			updateMove(temp, enpassantX, enpassantY, x1, y1, x2, y2);
			if(kingCapturable(temp))
				valid = false;
		}	
		
		if(valid && moveAfterChecking) {
			updateMove(board, enpassantX, enpassantY, x1, y1, x2, y2);
		}
		
		return valid;		
	}
	
	protected synchronized void updateMove(Board board, int enpassantX, int enpassantY, int x1, int y1, int x2, int y2) {	
		board.pieces[x1][y1].setCoord(x2, y2);
		board.pieces[x1][y1].properties.put(MOVED, true);
		board.pieces[x2][y2] = board.pieces[x1][y1];
		board.pieces[x1][y1] = null;
		
		if(enpassantX >= 0 && enpassantY >= 0)
			board.pieces[enpassantX][enpassantY] = null; 
		
		// Promotion
		if(y2 == 0 && board.pieces[x2][y2].type == PieceType.WHITE_PAWN)
			board.pieces[x2][y2].type = PieceType.WHITE_QUEEN;
		if(y2 == 7 && board.pieces[x2][y2].type == PieceType.BLACK_PAWN)
			board.pieces[x2][y2].type = PieceType.BLACK_QUEEN;
		
		// Castling
		if(board.pieces[x2][y2].type.isKing()) {
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
	
	public synchronized boolean kingCapturable(Board board) {
		Piece king = board.getKing(!board.whiteToMove);
		if(king == null)
			return false;
		List<Piece> opponentPieces = board.getPieceList(board.whiteToMove); 
		for(Piece p : opponentPieces) {
			if(checkMove(board, false, false, p.x, p.y, king.x, king.y))
				return true;
		}
		return false;
	}
	
	public synchronized int gameState(Board board) { // normal = -1, stalemate = 0, checkMate = 1
		if(!hasLegalMove(board)) {
			if(kingInCheck(board))
				return 1;
			return 0;
		} else {
			return -1;
		}
	}
	
	public synchronized boolean kingInCheck(Board board) {
		board.whiteToMove = !board.whiteToMove;
		boolean inCheck = kingCapturable(board);
		board.whiteToMove = !board.whiteToMove;
		return inCheck;
	}
	
	private synchronized boolean pieceCapturableIfMovedTo(Board board, int x1, int y1, int x2, int y2) {
		Board temp = board.clone();
		temp.pieces[x2][y2] = temp.pieces[x1][y1];
		temp.pieces[x1][y1] = null;
		temp.whiteToMove = !temp.whiteToMove;
		List<Piece> opponentPieces = temp.getPieceList(temp.whiteToMove); 
		for(Piece p : opponentPieces) {
			if(checkMove(temp, false, false, p.x, p.y, x2, y2))
				return true;
		}
		return false;
	}
	
	public synchronized boolean hasLegalMove(Board board) {
		List<Piece> myPieces = board.getPieceList(board.whiteToMove);
		for(Piece p : myPieces) {
			for(int i = 0; i<board.getWidth(); i++)
				for(int j = 0; j<board.getHeight(); j++) {
					if(checkMove(board, false, true, p.x, p.y, i, j))
						return true;
				}
		}
		return false;
	}
	
	public synchronized List<Move> possibleMoves(Board board, boolean kingSafe){
		List<Move> moves = new ArrayList<Move>();
		List<Piece> myPieces = board.getPieceList(board.whiteToMove);
		for(Piece p : myPieces) {
			List<Move> candidates = rangeOf(p);
			for(Move m : candidates)
				if(checkMove(board, false, kingSafe, m.x1, m.y1, m.x2, m.y2))
					moves.add(m.clone());
		}
		return moves;
	}
	
	public synchronized List<Move> possibleMoves(Board board, boolean kingSafe, Piece p){
		List<Move> moves = new ArrayList<Move>();
		List<Move> candidates = rangeOf(p);
		for(Move m : candidates)
			if(checkMove(board, false, kingSafe, m.x1, m.y1, m.x2, m.y2))
				moves.add(m.clone());
		return moves;
	}
	
	public synchronized List<Move> rangeOf(Piece p){
		List<Move> moves = new ArrayList<Move>();
		switch(p.type) {
			case WHITE_PAWN:
			case BLACK_PAWN:
				moves.add(new Move(p.x, p.y, p.x, p.y + 1));
				moves.add(new Move(p.x, p.y, p.x, p.y - 1));
				moves.add(new Move(p.x, p.y, p.x, p.y + 2));
				moves.add(new Move(p.x, p.y, p.x, p.y - 2));
				moves.add(new Move(p.x, p.y, p.x + 1, p.y + 1));
				moves.add(new Move(p.x, p.y, p.x - 1, p.y + 1));
				moves.add(new Move(p.x, p.y, p.x + 1, p.y - 1));
				moves.add(new Move(p.x, p.y, p.x - 1, p.y - 1));
				break;
			case WHITE_KING:
			case BLACK_KING:
				moves.add(new Move(p.x, p.y, p.x - 1, p.y + 1));
				moves.add(new Move(p.x, p.y, p.x - 1, p.y));
				moves.add(new Move(p.x, p.y, p.x - 1, p.y - 1));
				moves.add(new Move(p.x, p.y, p.x, p.y + 1));
				moves.add(new Move(p.x, p.y, p.x, p.y - 1));
				moves.add(new Move(p.x, p.y, p.x + 1, p.y + 1));
				moves.add(new Move(p.x, p.y, p.x + 1, p.y));
				moves.add(new Move(p.x, p.y, p.x + 1, p.y - 1));
				moves.add(new Move(p.x, p.y, p.x + 2, p.y));
				moves.add(new Move(p.x, p.y, p.x - 2, p.y));
				break;
			case WHITE_KNIGHT:
			case BLACK_KNIGHT:
				moves.add(new Move(p.x, p.y, p.x + 2, p.y + 1));
				moves.add(new Move(p.x, p.y, p.x + 2, p.y - 1));
				moves.add(new Move(p.x, p.y, p.x - 2, p.y + 1));
				moves.add(new Move(p.x, p.y, p.x - 2, p.y - 1));
				moves.add(new Move(p.x, p.y, p.x + 1, p.y + 2));
				moves.add(new Move(p.x, p.y, p.x + 1, p.y - 2));
				moves.add(new Move(p.x, p.y, p.x - 1, p.y + 2));
				moves.add(new Move(p.x, p.y, p.x - 1, p.y - 2));
				break;
			case WHITE_QUEEN:
			case BLACK_QUEEN:
			case WHITE_ROOK:
			case BLACK_ROOK:
				for(int i = 0; i<8; i++) {
					moves.add(new Move(p.x, p.y, p.x, i));
					moves.add(new Move(p.x, p.y, i, p.y));
				}
				if(p.type.isRook())
					break;
			case WHITE_BISHOP:
			case BLACK_BISHOP:
				for(int i = 0; i<8; i++) {
					moves.add(new Move(p.x, p.y, p.x + i, p.y + i));
					moves.add(new Move(p.x, p.y, p.x + i, p.y - i));
					moves.add(new Move(p.x, p.y, p.x - i, p.y + i));
					moves.add(new Move(p.x, p.y, p.x - i, p.y - i));
				}
				break;
		}
		return moves;
	}
	
	private synchronized boolean castleValid(Board board, int x1, int y1, int x2, int y2) {
		if(y1 != y2)
			return false;
		Piece king = board.getPiece(x1, y1);
		if(king.properties.get(MOVED) != null && king.properties.get(MOVED) == true)
			return false;
		
		if(x2 == x1 - 2) { // Queen side
			Piece rook = board.getPiece(0, y1);
			if(rook == null)
				return false;
			if(rook.properties.get(MOVED) != null && rook.properties.get(MOVED) == true)
				return false;
			if(!emptyStraight(board, x1, y1, 0, y1))
				return false;
			for(int x = x1; x != x2; x --){
				if(pieceCapturableIfMovedTo(board, x1, y1, x, y1))
					return false;
			}
			return true;
		} else if(x2 == x1 + 2) { // King side
			Piece rook = board.getPiece(7, y1);
			if(rook == null)
				return false;
			if(rook.properties.get(MOVED) != null && rook.properties.get(MOVED) == true)
				return false;
			if(!emptyStraight(board, x1, y1, 7, y1))
				return false;
			for(int x = x1; x != x2; x ++){
				if(pieceCapturableIfMovedTo(board, x1, y1, x, y1))
					return false;
			}
			return true;
		}
		return false;
	}
	
	
	private synchronized boolean emptyStraight(Board board, int x1, int y1, int x2, int y2) {
		int incX = 0, incY = 0;
		if(y1 == y2)
			incX = x1 < x2? 1 : -1;
		else if(x1 == x2)
			incY = y1 < y2? 1 : -1;
		else 
			return false;
		return emptyCheck(board, x1, y1, x2, y2, incX, incY);
	}
	
	private synchronized boolean emptyDiagonal(Board board, int x1, int y1, int x2, int y2) {
		// not diagonal
		int dx = x2 - x1;
		int dy = y2 - y1;
		if(dx != dy && dx != -dy)
			return false;
		
		int incX = 1, incY = 1;
		if(x2 < x1)
			incX = -1;
		if(y2 < y1)
			incY = -1;
		
		return emptyCheck(board, x1, y1, x2, y2, incX, incY);
	}
	
	private synchronized boolean emptyCheck(Board board, int x1, int y1, int x2, int y2, int incX, int incY) {
		int xw = x1 + incX, yw = y1 + incY;
		while(xw != x2 || yw != y2) {
			Piece t = board.getPiece(xw, yw);
			if(t != null)
				return false;
			xw += incX;
			yw += incY;
		}
		return true;
	}
}

package board.setup;

import board.Board;
import board.PieceType;

public class DefaultSetup implements BoardSetup {

	@Override
	public void setup(Board board) {
		board.createPiece(0, 0, PieceType.BLACK_ROOK);
		board.createPiece(7, 0, PieceType.BLACK_ROOK);
		board.createPiece(0, 7, PieceType.WHITE_ROOK);
		board.createPiece(7, 7, PieceType.WHITE_ROOK);
		board.createPiece(1, 0, PieceType.BLACK_KNIGHT);
		board.createPiece(6, 0, PieceType.BLACK_KNIGHT);
		board.createPiece(1, 7, PieceType.WHITE_KNIGHT);
		board.createPiece(6, 7, PieceType.WHITE_KNIGHT);
		board.createPiece(2, 0, PieceType.BLACK_BISHOP);
		board.createPiece(5, 0, PieceType.BLACK_BISHOP);
		board.createPiece(2, 7, PieceType.WHITE_BISHOP);
		board.createPiece(5, 7, PieceType.WHITE_BISHOP);
		board.createPiece(3, 0, PieceType.BLACK_QUEEN);
		board.createPiece(3, 7, PieceType.WHITE_QUEEN);
		board.createPiece(4, 0, PieceType.BLACK_KING);
		board.createPiece(4, 7, PieceType.WHITE_KING);
		for(int i = 0; i<8; i++) {
			board.createPiece(i, 1, PieceType.BLACK_PAWN);
			board.createPiece(i, 6, PieceType.WHITE_PAWN);
		}
	}

}

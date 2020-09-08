package board.setup;

import board.Board;
import board.PieceType;

public class KnightBishopSetup implements BoardSetup {

	@Override
	public void setup(Board board) {
		for(int i = 0; i<8; i++) {
			for(int j = 0; j<1; j++) {
				board.createPiece(i, j, PieceType.BLACK_BISHOP);
				board.createPiece(i, 7 - j, PieceType.WHITE_KNIGHT);
			}
		}
		
		for(int i = 0; i<8; i++) {
			board.createPiece(i, 1, PieceType.BLACK_PAWN);
			board.createPiece(i, 6, PieceType.WHITE_PAWN);
		}

		board.createPiece(4, 0, PieceType.BLACK_KING);
		board.createPiece(4, 7, PieceType.WHITE_KING);
	}

}

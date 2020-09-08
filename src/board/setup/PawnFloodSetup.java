package board.setup;

import board.Board;
import board.PieceType;

public class PawnFloodSetup implements BoardSetup {

	@Override
	public void setup(Board board) {
		for(int i = 0; i<8; i++) {
			for(int j = 0; j<4; j++) {
				board.createPiece(i, j, PieceType.BLACK_PAWN);
				board.createPiece(i, 7 - j, PieceType.WHITE_PAWN);
			}
		}

		board.createPiece(4, 0, PieceType.BLACK_KING);
		board.createPiece(4, 7, PieceType.WHITE_KING);
	}

}

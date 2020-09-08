package board.setup;

import java.util.Random;

import board.Board;
import board.PieceType;

public class ChaosSetup implements BoardSetup {

	@Override
	public void setup(Board board) {
		PieceType[] whites = {PieceType.WHITE_PAWN, PieceType.WHITE_KNIGHT, PieceType.WHITE_BISHOP, PieceType.WHITE_ROOK, PieceType.WHITE_QUEEN};
		PieceType[] blacks = {PieceType.BLACK_PAWN, PieceType.BLACK_KNIGHT, PieceType.BLACK_BISHOP, PieceType.BLACK_ROOK, PieceType.BLACK_QUEEN};
		Random r = new Random();
		for(int i = 0; i<8; i++) {
			for(int j = 0; j<4; j++) {
				board.createPiece(i, j, blacks[r.nextInt(whites.length)]);
				board.createPiece(i, 7 - j, whites[r.nextInt(blacks.length)]);
			}
		}
		
		board.createPiece(4, 0, PieceType.BLACK_KING);
		board.createPiece(4, 7, PieceType.WHITE_KING);
	}

}

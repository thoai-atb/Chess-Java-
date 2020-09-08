package control;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import board.Board;
import board.Move;
import board.Piece;
import board.PieceType;

public class AI {
	
	Random random = new Random();
	int search_depth = 2;
	
	public int getDifficulty() {
		return search_depth;
	}
	
	public void setDifficulty(int depth) {
		search_depth = depth;
	}
	
	public synchronized boolean move(Board board) {
		boolean side = board.whiteToMove;
		
		List<Move> moves = board.rule.possibleMoves(board, true);
		if (moves.size() == 0)
			return false;
		
		double maxEva = Double.NEGATIVE_INFINITY;
		ArrayList<Move> bestMoves = new ArrayList<Move>();
		for(Move m : moves) {
			Board temp = board.clone();
			temp.move(m.x1, m.y1, m.x2, m.y2, true);
			
			double eva = minimax(temp, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY, side, search_depth);
			if(maxEva < eva) {
				bestMoves.clear();
				bestMoves.add(m);
				maxEva = eva;
			} else if (maxEva == eva) {
				bestMoves.add(m);
			}
		}
		
		Move best = bestMoves.get(random.nextInt(bestMoves.size()));
		
		return board.move(best.x1, best.y1, best.x2, best.y2, true);
	}
	
	
	public double minimax(Board board, double alpha, double beta, boolean side, int depth) {
		int state = board.rule.gameState(board);
		if(state == 0)
			return 0;
		else if(state == 1)
			return 100000 * (side == board.whiteToMove? -1 : 1);
		
		if(depth > 0) {
			if(side == board.whiteToMove) {
				double max = Double.NEGATIVE_INFINITY;
				List<Move> moves = board.rule.possibleMoves(board, true);
				for(Move m : moves) {
					Board temp = board.clone();
					temp.move(m.x1, m.y1, m.x2, m.y2, true);
					double eva = minimax(temp, alpha, beta, side, depth - 1);
					if(eva > max) 
						max = eva;
					alpha = Math.max(alpha, eva);
					if(beta <= alpha)
						break;
				}
				return max;
			} else {
				double min = Double.POSITIVE_INFINITY;
				List<Move> moves = board.rule.possibleMoves(board, true);
				for(Move m : moves) {
					Board temp = board.clone();
					temp.move(m.x1, m.y1, m.x2, m.y2, true);
					double eva = minimax(temp, alpha, beta, side, depth - 1);
					if(eva < min) 
						min = eva;
					beta = Math.min(beta, eva);
					if(beta <= alpha)
						break;
				}
				return min;
			}
		}
		
		List<Piece> pieces = board.getPieceList();
		double evaluation = 0;
		for(Piece p : pieces) {
			int mult = p.type.isWhite() == side? 1 : -1;
			if(p.type.isQueen())
				evaluation += mult * 90;
			else if(p.type.isRook())
				evaluation += mult * 50;
			else if(p.type.isKnight() || p.type.isBishop())
				evaluation += mult * 30;
			else if(p.type.isPawn())
				evaluation += mult * 10;
			else if(p.type.isKing())
				evaluation += mult * 900;
			evaluation += mult * locationEvaluate(p.type, p.x, p.y);
		}
		return evaluation;
	}
	
	public double locationEvaluate(PieceType type, int x, int y) {
		if(type.isKnight()) {
			double[][] values = new double[][] {
              {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0},
              {-4.0, -2.0,  0.0,  0.0,  0.0,  0.0, -2.0, -4.0},
              {-3.0,  0.0,  1.0,  1.5,  1.5,  1.0,  0.0, -3.0},
              {-3.0,  0.5,  1.5,  2.0,  2.0,  1.5,  0.5, -3.0},
              {-3.0,  0.0,  1.5,  2.0,  2.0,  1.5,  0.0, -3.0},
              {-3.0,  0.5,  1.0,  1.5,  1.5,  1.0,  0.5, -3.0},
              {-4.0, -2.0,  0.0,  0.5,  0.5,  0.0, -2.0, -4.0},
              {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0}
			};
			return type.isWhite()? values[y][x] : values[7-y][x];
		}
		
		if(type.isBishop()) {
			double[][] values = new double[][] {
			    { -2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0},
			    { -1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0},
			    { -1.0,  0.0,  0.5,  1.0,  1.0,  0.5,  0.0, -1.0},
			    { -1.0,  0.5,  0.5,  1.0,  1.0,  0.5,  0.5, -1.0},
			    { -1.0,  0.0,  1.0,  1.0,  1.0,  1.0,  0.0, -1.0},
			    { -1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0, -1.0},
			    { -1.0,  0.5,  0.0,  0.0,  0.0,  0.0,  0.5, -1.0},
			    { -2.0, -1.0, -1.0, -1.0, -1.0, -1.0, -1.0, -2.0}
			};
			return type.isWhite()? values[y][x] : values[7-y][x];
		}
		
		if(type.isRook()) {
			double[][] values = new double[][] {
			    {  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0},
			    {  0.5,  1.0,  1.0,  1.0,  1.0,  1.0,  1.0,  0.5},
			    { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
			    { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
			    { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
			    { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
			    { -0.5,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -0.5},
			    {  0.0,   0.0, 0.0,  0.5,  0.5,  0.0,  0.0,  0.0}
			};
			return type.isWhite()? values[y][x] : values[7-y][x];
		}
		
		if(type.isKing()) {
			double[][] values = new double[][] {
		        {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0},
		        {-4.0, -2.0,  0.0,  0.0,  0.0,  0.0, -2.0, -4.0},
		        {-3.0,  0.0,  1.0,  1.5,  1.5,  1.0,  0.0, -3.0},
		        {-3.0,  0.5,  1.5,  2.0,  2.0,  1.5,  0.5, -3.0},
		        {-3.0,  0.0,  1.5,  2.0,  2.0,  1.5,  0.0, -3.0},
		        {-3.0,  0.5,  1.0,  1.5,  1.5,  1.0,  0.5, -3.0},
		        {-4.0, -2.0,  0.0,  0.5,  0.5,  0.0, -2.0, -4.0},
		        {-5.0, -4.0, -3.0, -3.0, -3.0, -3.0, -4.0, -5.0}
		    };
			return type.isWhite()? values[y][x] : values[7-y][x];
		}
		
		if(type.isQueen()) {
			double[][] values = new double[][] {
			    { -2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0},
			    { -1.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0, -1.0},
			    { -1.0,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -1.0},
			    { -0.5,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5},
			    {  0.0,  0.0,  0.5,  0.5,  0.5,  0.5,  0.0, -0.5},
			    { -1.0,  0.5,  0.5,  0.5,  0.5,  0.5,  0.0, -1.0},
			    { -1.0,  0.0,  0.5,  0.0,  0.0,  0.0,  0.0, -1.0},
			    { -2.0, -1.0, -1.0, -0.5, -0.5, -1.0, -1.0, -2.0}
			};
			return type.isWhite()? values[y][x] : values[7-y][x];
		}
		
		// type = pawn
		double[][] values = new double[][]{
	        {0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0},
	        {5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0,  5.0},
	        {1.0,  1.0,  2.0,  3.0,  3.0,  2.0,  1.0,  1.0},
	        {0.5,  0.5,  1.0,  2.5,  2.5,  1.0,  0.5,  0.5},
	        {0.0,  0.0,  0.0,  2.0,  2.0,  0.0,  0.0,  0.0},
	        {0.5, -0.5, -1.0,  0.0,  0.0, -1.0, -0.5,  0.5},
	        {0.5,  1.0, 1.0,  -2.0, -2.0,  1.0,  1.0,  0.5},
	        {0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0,  0.0}
	    };
		return type.isWhite()? values[y][x] : values[7-y][x];
	}
}

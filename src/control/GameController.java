package control;

import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;

import UI.game.Screen;
import board.Board;
import board.Move;
import board.Piece;
import board.setup.BoardSetup;

public class GameController {
	
	public interface EndGameListener {
		public void endGame();
	}

	public static final int WIN = 1, LOSE = -1, DRAW = 0; 
	
	List<Board> history = new LinkedList<Board>();
	private boolean playasWhite = true;
	List<Move> availableMoves;
	BoardSetup boardSetup;
	Screen screen;
	AI ai = new AI();
	EndGameListener listener;
	
	public GameController(Screen screen, BoardSetup setup) {
		this.screen = screen;
		this.boardSetup = setup;
		resetBoard();
	}
	
	public List<Move> getAvailableMoves(){
		return availableMoves;
	}
	
	public void resetBoard() {
		screen.board = new Board(8, 8, boardSetup);
		history.clear();
		history.add(screen.board.clone());
	}

	public void updateAvailableMoves(Piece selected) {
		availableMoves = screen.board.rule.possibleMoves(screen.board, true, selected);
	}

	public boolean isPlayasWhite() {
		return playasWhite;
	}

	public void update() {
		Board b = screen.board;
		if(!b.rule.hasLegalMove(b)) {
			if(b.rule.kingInCheck(b))
				endGame(isPlayasWhite() == b.whiteToMove? LOSE : WIN);
			else
				endGame(DRAW);
		} else {
			if(!isPlayasWhite() == b.whiteToMove) 
				aiMove();
		}
	}
	
	private synchronized void endGame(int type) {
		switch(type) {
			case WIN:
				JOptionPane.showMessageDialog(null, "You Won");
				break;
			case LOSE:
				JOptionPane.showMessageDialog(null, "You Lost");
				break;
			case DRAW:
				JOptionPane.showMessageDialog(null, "Draw");
				break;
		}
		listener.endGame();
	}
	
	public void aiMove() {
    	Board board = screen.board;
    	if(getAI().move(board)) {
    		Move m = board.lastMove;
    		screen.executeAnim(m.x1, m.y1, m.x2, m.y2);
			history.add(screen.board.clone());
			playChessSound();
		}
	}
	
	public void undoMove() {
		if(history.size() > 2) {
			history.remove(history.size() - 1);
			history.remove(history.size() - 1);
			screen.board = history.get(history.size() - 1).clone();
		}
	}

	public boolean playerMove(int x1, int y1, int x2, int y2) {
		if(screen.board.move(x1, y1, x2, y2, true)) {
			history.add(screen.board.clone());
			playChessSound();
			return true;
		}
		return false;
	}
	
	public void playChessSound() {
		Board a = history.get(history.size() - 1);
		Board b = history.get(history.size() - 2);
		if(b == null || a.getPieceList().size() == b.getPieceList().size())
			SoundPlayer.playSound(SoundPlayer.SoundEffect.NORMAL);
		else
			SoundPlayer.playSound(SoundPlayer.SoundEffect.CAPTURE);
	}

	public AI getAI() {
		return ai;
	}

	public void setPlayasWhite(boolean playasWhite) {
		this.playasWhite = playasWhite;
	}

	public void setListener(EndGameListener listener) {
		this.listener = listener;
	}
}

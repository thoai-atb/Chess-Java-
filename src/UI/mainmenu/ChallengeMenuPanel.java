package UI.mainmenu;

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import UI.MainMenuFrame;
import board.Board;
import board.PieceType;
import board.setup.BoardSetup;

@SuppressWarnings("serial")
public class ChallengeMenuPanel extends MenuPanel {
	
	GameLabel title, backB;
	List<EnterGameButton> challengeButtons = new ArrayList<EnterGameButton>();
	
	public ChallengeMenuPanel(MainMenuFrame frame) {
		super(frame);
		
		title = new GameLabel("Challenges", GameLabel.titleFont, Color.DARK_GRAY);
		title.setSelectable(false);
		this.add(title);
		
		backB = new GameLabel("Back");
		this.add(backB);
		backB.addMouseListener(this);
		
		challengeButtons.add(new EnterGameButton("2 Rooks Mate", frame) {
			@Override
			protected BoardSetup createBoardSetup() {
				return new BoardSetup() {
					@Override
					public void setup(Board board) {
						board.createPiece("e1", PieceType.WHITE_KING);
						board.createPiece("a1", PieceType.WHITE_ROOK);
						board.createPiece("h1", PieceType.WHITE_ROOK);
						board.createPiece("e8", PieceType.BLACK_KING);
					}
				};
			}
		});
		
		challengeButtons.add(new EnterGameButton("Queen Mate", frame) {
			@Override
			protected BoardSetup createBoardSetup() {
				return new BoardSetup() {
					@Override
					public void setup(Board board) {
						board.createPiece("e1", PieceType.WHITE_KING);
						board.createPiece("d1", PieceType.WHITE_QUEEN);
						board.createPiece("e8", PieceType.BLACK_KING);
					}
				};
			}
		});
		
		challengeButtons.add(new EnterGameButton("1 Rook Mate", frame) {
			@Override
			protected BoardSetup createBoardSetup() {
				return new BoardSetup() {
					@Override
					public void setup(Board board) {
						board.createPiece("e1", PieceType.WHITE_KING);
						board.createPiece("a1", PieceType.WHITE_ROOK);
						board.createPiece("e8", PieceType.BLACK_KING);
					}
				};
			}
		});
		
		challengeButtons.add(new EnterGameButton("2 Bishops Mate", frame) {
			@Override
			protected BoardSetup createBoardSetup() {
				return new BoardSetup() {
					@Override
					public void setup(Board board) {
						board.createPiece("e1", PieceType.WHITE_KING);
						board.createPiece("c1", PieceType.WHITE_BISHOP);
						board.createPiece("f1", PieceType.WHITE_BISHOP);
						board.createPiece("e8", PieceType.BLACK_KING);
					}
				};
			}
		});
		
		challengeButtons.add(new EnterGameButton("Bishop & Knight Mate", frame) {
			@Override
			protected BoardSetup createBoardSetup() {
				return new BoardSetup() {
					@Override
					public void setup(Board board) {
						board.createPiece("e1", PieceType.WHITE_KING);
						board.createPiece("c1", PieceType.WHITE_BISHOP);
						board.createPiece("g1", PieceType.WHITE_KNIGHT);
						board.createPiece("e8", PieceType.BLACK_KING);
					}
				};
			}
		});
		
		
		
		for(EnterGameButton b : challengeButtons) {
			this.add(b);
			b.addMouseListener(this);
		}
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		title.setCenter(this.getWidth() / 2, this.getHeight() / 10);   
		
		int y = this.getHeight() / 3;
		
		for(EnterGameButton b : challengeButtons) {
	        b.setCenter(this.getWidth() / 2, y);
	        y += 50;
		}
		
        backB.setCenter(this.getWidth() / 2, y);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		Object o = e.getSource();
		if(o == backB) {
			frame.toMainMenu();
		}
	}
}

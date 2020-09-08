package UI.mainmenu;

import java.awt.event.MouseEvent;

import UI.MainMenuFrame;
import UI.game.GameFrame;
import board.setup.BoardSetup;

@SuppressWarnings("serial")
public abstract class EnterGameButton extends GameLabel {
	
	MainMenuFrame frame;
	boolean exitOnFinish = true;

	public EnterGameButton(String name, MainMenuFrame frame) {
		super(name);
		this.frame = frame;
	}
	
	public EnterGameButton(String name, MainMenuFrame frame, boolean exitOnFinish) {
		super(name);
		this.frame = frame;
		this.exitOnFinish = exitOnFinish;
	}
	
	protected abstract BoardSetup createBoardSetup();

	@Override
	public void mousePressed(MouseEvent e) {
		frame.setVisible(false);
		new GameFrame(frame, createBoardSetup(), exitOnFinish);
	}
}

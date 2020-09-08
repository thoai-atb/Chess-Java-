package UI.mainmenu;

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;

import UI.MainMenuFrame;

@SuppressWarnings("serial")
public class MainMenuPanel extends MenuPanel {
	
	GameLabel newGameB, challengeB, quitB, gameTitle;
	
	
	public MainMenuPanel (MainMenuFrame frame) {
		super(frame);
		
		gameTitle = new GameLabel("Chess Game", GameLabel.titleFont, Color.DARK_GRAY);
		gameTitle.setSelectable(false);
		this.add(gameTitle);
		
		newGameB = new GameLabel("New Game");
		this.add(newGameB);
		newGameB.addMouseListener(this);
		
		challengeB = new GameLabel("Challenges");
		this.add(challengeB);
		challengeB.addMouseListener(this);
		
		quitB = new GameLabel("Quit");
		this.add(quitB);
		quitB.addMouseListener(this);
	}
	
	
	@Override
	public void componentResized(ComponentEvent e) {
		gameTitle.setCenter(this.getWidth() / 2, this.getHeight() / 10);
		
		int y = this.getHeight() / 3;
		newGameB.setCenter(this.getWidth() / 2, y);
		
		y += 60;
		challengeB.setCenter(this.getWidth() / 2, y);
		y += 60;
		quitB.setCenter(this.getWidth() / 2, y);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource() == newGameB) {
			frame.toGameSelectMenu();
		} else if (e.getSource() == challengeB) {
			frame.toChallengeMenu();
		} else if(e.getSource() == quitB) {
			frame.dispose();
			System.exit(0);
		}
	}
}

package UI.mainmenu;

import java.awt.Color;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import UI.MainMenuFrame;
import board.setup.BoardSetup;
import board.setup.ChaosSetup;
import board.setup.DefaultSetup;
import board.setup.KnightBishopSetup;
import board.setup.PawnFloodSetup;

@SuppressWarnings("serial")
public class GameSelectMenuPanel extends MenuPanel {
	
	GameLabel title, backB;
	List<EnterGameButton> buttons = new ArrayList<EnterGameButton>();
	
	public GameSelectMenuPanel(MainMenuFrame frame) {
		super(frame);
		
		title = new GameLabel("Board Setup", GameLabel.titleFont, Color.DARK_GRAY);
		title.setSelectable(false);
		this.add(title);
		
		backB = new GameLabel("Back");
		this.add(backB);
		backB.addMouseListener(this);
		
		buttons.add(new EnterGameButton("Default", frame, false) {
			@Override
			protected BoardSetup createBoardSetup() {
				return new DefaultSetup();
			}
		});
		
		buttons.add(new EnterGameButton("Pawn Flood", frame, false) {
			@Override
			protected BoardSetup createBoardSetup() {
				return new PawnFloodSetup();
			}
		});
		
		buttons.add(new EnterGameButton("Knights VS Bishops", frame, false) {
			@Override
			protected BoardSetup createBoardSetup() {
				return new KnightBishopSetup();
			}
		});
		
		buttons.add(new EnterGameButton("Chaos", frame, false) {
			@Override
			protected BoardSetup createBoardSetup() {
				return new ChaosSetup();
			}
		});
		
		for(EnterGameButton b : buttons) {
			this.add(b);
			b.addMouseListener(this);
		}
	}
	
	@Override
	public void componentResized(ComponentEvent e) {
		title.setCenter(this.getWidth() / 2, this.getHeight() / 10);   
		
		int y = this.getHeight() / 3;
		
		for(EnterGameButton b : buttons) {
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

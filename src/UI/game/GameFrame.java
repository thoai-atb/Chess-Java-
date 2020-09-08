package UI.game;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

import UI.MainMenuFrame;
import board.setup.BoardSetup;
import board.setup.DefaultSetup;
import control.MessageListener;

@SuppressWarnings("serial")
public class GameFrame extends JFrame implements MessageListener {
	
	public Screen s;
	public MainMenuFrame menuFrame;
	
	public GameFrame(MainMenuFrame menuFrame) {
		this(menuFrame, new DefaultSetup(), false);
	}
	
	public GameFrame(MainMenuFrame menuFrame, BoardSetup setup, boolean exitOnEnd) {
		this.menuFrame = menuFrame;
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Chess Game");

 		this.setLayout(new BorderLayout());
 		s = new Screen(setup, exitOnEnd);
 		s.setMessageListener(this);
 		this.add(s);
 		
 		GameMenu menu = new GameMenu(this);
 		this.setJMenuBar(menu);
 		
     	this.getContentPane().setPreferredSize(new Dimension(600, 600));
		this.pack();
 		this.setLocationRelativeTo(null);
 		this.setVisible(true);
		
 		this.setResizable(false);
	}

	@Override
	public void messageReceived(String message) {
		if(message == "exit") {
			this.dispose();
			this.menuFrame.setVisible(true);
		}
	}

}

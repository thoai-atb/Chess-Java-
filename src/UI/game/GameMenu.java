package UI.game;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

@SuppressWarnings("serial")
public class GameMenu extends JMenuBar {
	
	public GameMenu(GameFrame frame) {
		
		// ============================================= GAME
		
		JMenu gameMenu = new JMenu("Game");
		JMenuItem newGame = new JMenuItem("New");
		newGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.s.controller.resetBoard();
			}
		});
		gameMenu.add(newGame);
		
		JMenuItem exit = new JMenuItem("Back to Menu");
		exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.messageReceived("exit");
			}
		});
		gameMenu.add(exit);
		
		this.add(gameMenu);
		
		// ================================================== MOVE
		
		JMenu moveMenu = new JMenu("Move");
		JMenuItem undoMove = new JMenuItem("Back 1 step (Past)");
		undoMove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.s.controller.undoMove();
			}
		});
		moveMenu.add(undoMove);
		JMenuItem doMove = new JMenuItem("Foward 1 step (Future)");
		doMove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.s.controller.aiMove();
			}
		});
		moveMenu.add(doMove);
		this.add(moveMenu);
		
		// ========================================================== SETTINGS
		
		JMenu settingMenu = new JMenu("Settings");
		
		JMenuItem adjustDiff = new JMenuItem("Adjust Difficulty");
		adjustDiff.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				adjustDifficulty(frame);
			}
		});
		settingMenu.add(adjustDiff);
		
		JMenuItem playAs = new JMenuItem(frame.s.isPlayasWhite()? "Play as Black" : "Play as White");
		playAs.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.s.controller.setPlayasWhite(!frame.s.isPlayasWhite());
				playAs.setText(frame.s.isPlayasWhite()? "Play as Black" : "Play as White");
			}
			
		});
		settingMenu.add(playAs);
		this.add(settingMenu);
		
		// ================================================= OTHER
		
		JMenu otherMenu = new JMenu("Other");
		JMenuItem ttt = new JMenuItem("Tic tac toe");
		ttt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new TicTac();
			}
		});
		otherMenu.add(ttt);
		this.add(otherMenu);
		
	}
	
	private void adjustDifficulty(GameFrame frame){
		new DifficultyUI(frame);
	}
}

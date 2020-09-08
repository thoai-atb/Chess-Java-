package UI;

import javax.swing.JFrame;

import UI.mainmenu.ChallengeMenuPanel;
import UI.mainmenu.GameSelectMenuPanel;
import UI.mainmenu.MainMenuPanel;
import UI.mainmenu.MenuPanel;

@SuppressWarnings("serial")
public class MainMenuFrame extends JFrame {
	
	MenuPanel mainMenu, challengeMenu, gameSelectMenu;
	
	public static void main(String[] args) {
		new MainMenuFrame();
	}
	
	public MainMenuFrame() {
		super("Chess Game");
	
		mainMenu = new MainMenuPanel(this);
		this.getContentPane().add(mainMenu);
		
		this.setSize(500, 500);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void toChallengeMenu() {
		if(challengeMenu == null)
			challengeMenu = new ChallengeMenuPanel(this);
		this.setContentPane(challengeMenu);
		challengeMenu.refresh();
		this.revalidate();
	}
	
	public void toMainMenu() {
		this.setContentPane(mainMenu);
		mainMenu.refresh();
		this.revalidate();
	}


	public void toGameSelectMenu() {
		if(gameSelectMenu == null)
			gameSelectMenu = new GameSelectMenuPanel(this);
		this.setContentPane(gameSelectMenu);
		gameSelectMenu.refresh();
		this.revalidate();
	}
	
}

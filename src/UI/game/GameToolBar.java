package UI.game;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JToolBar;

@SuppressWarnings("serial")
public class GameToolBar extends JToolBar {
	public GameToolBar() {
		JButton but = new JButton("Juice");
		this.add(but);
		this.setBackground(Color.GRAY);
	}
}

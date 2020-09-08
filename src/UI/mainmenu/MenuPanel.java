package UI.mainmenu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import UI.MainMenuFrame;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel implements ComponentListener, MouseListener {

	private static BufferedImage background;
	
	protected MainMenuFrame frame;
	
	public MenuPanel(MainMenuFrame frame) {
		this.frame = frame;
		this.addComponentListener(this);
		this.addMouseListener(this);
		this.setLayout(null);
		
		if(background == null)
			 try {
			    	background = ImageIO.read(getClass().getResourceAsStream("/wood-background.jpg"));
			 } catch (IOException e) {}
	}
	
	public void refresh() {
		
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
		g.setColor(new Color(0, 0, 0, 30));
		g.fillRect(0, 0, this.getWidth(), this.getHeight() / 5); 
	}

	@Override
	public void componentResized(ComponentEvent e) {}
	@Override
	public void componentMoved(ComponentEvent e) {}
	@Override
	public void componentShown(ComponentEvent e) {}
	@Override
	public void componentHidden(ComponentEvent e) {}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseClicked(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
}
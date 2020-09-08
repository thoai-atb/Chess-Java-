package UI.mainmenu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class GameLabel extends JLabel implements MouseListener {
	
	public static Font titleFont = new Font(Font.SERIF, Font.BOLD, 50);
	public static Font optionFont = new Font(Font.MONOSPACED,  Font.BOLD, 25);
	public static Font optionFontSelect = new Font(Font.MONOSPACED,  Font.BOLD, 27);
	
	public Point center = new Point();
	private boolean selectable = true;
	
	public GameLabel(String name) {
		this(name, optionFont, Color.BLACK);
	}
	
	public GameLabel(String name, Font font, Color col) {
		super(name);
		this.setFont(font);
		this.setForeground(col);
		this.setVerticalAlignment(SwingConstants.CENTER);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.addMouseListener(this);
	}
	
	public void setSelectable(boolean b) {
		selectable = b;
	}
	
	public void setCenter(int x, int y) {
		center = new Point(x, y);
		Dimension s = this.getPreferredSize();
		this.setBounds(center.x - s.width/2, center.y - s.height/2, s.width, s.height);
	}
	
	private void setSelected(boolean selected) {
		this.setFont(selected? optionFontSelect : optionFont);
		setCenter(center.x, center.y);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		if(selectable)
			setSelected(false);
	}

	@Override
	public void mouseReleased(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(selectable)
			setSelected(true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(selectable)
			setSelected(false);
	}
}

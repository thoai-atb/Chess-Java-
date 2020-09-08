package UI.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TicTac extends JFrame {
	
	TicTacPanel panel;
	
	public TicTac() {
		super("Tic Cheat Toe");
		this.setSize(300, 300);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		this.setResizable(false);
		
		panel = new TicTacPanel();
		panel.setBackground(Color.white);
		panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		this.add(panel);
		panel.repaint();
	}
}

@SuppressWarnings("serial")
class TicTacPanel extends JPanel implements MouseListener{
	
	boolean done = false;
	
	public TicTacPanel() {
		this.addMouseListener(this);
	}
	
	int[][] data = new int[][] {
		{-1, -1, -1},
		{-1, -1, -1},
		{-1, -1, -1}
	};
	int[] line;

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = this.getWidth();
		int height = this.getHeight();
		Graphics2D g2d = (Graphics2D) g;
		g2d.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		int bound = 20;
		g2d.drawLine(bound,  height/3, width - bound, height/3);
		g2d.drawLine(bound,  2*height/3, width - bound, 2*height/3);
		g2d.drawLine(width/3, bound, width/3, height - bound);
		g2d.drawLine(2*width/3, bound, 2*width/3, height - bound);
		
		for(int i = 0; i<3; i++)
			for (int j = 0; j<3; j++) 
				switch(data[i][j]) {
					case -1:
						break;
					case 0:
						draw(g2d, "X", i, j);
						break;
					case 1:
						draw(g2d, "O", i, j);
				}
		int ux = getWidth() / 3, uy = getHeight() / 3;
		g2d.setStroke(new BasicStroke(6.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		if(done && line != null) {
			g2d.drawLine(line[0]*ux+ux/2 , line[1]*uy+uy/2, line[2]*ux+ux/2, line[3]*uy+uy/2);
		}
	}
	
	private void draw(Graphics2D g, String s, int i, int j) {
		int ux = getWidth() / 3, uy = getHeight() / 3;
		int x = i * ux + ux/2;
		int y = j * uy + uy/2;
		int l = 30;
		
		if(s == "X") {
			g.drawLine(x-30, y-30, x+30, y+30);
			g.drawLine(x-30, y+30, x+30, y-30);
		}else if(s == "O") {
			g.drawOval(x-l, y-l, l*2, l*2);
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(done) {
			data = new int[][] {
				{-1, -1, -1},
				{-1, -1, -1},
				{-1, -1, -1}
			};
			done = false;
			repaint();
			return;
		}
		
		int x = e.getX() * 3 / getWidth();
		int y = e.getY() * 3 / getHeight();
		if(data[x][y] == -1)
			data[x][y] = 0;
		else
			return;
		
		line = check(0);
		if(line != null) {
			data[x][y] = 1;
			line = check(1);
			if(line != null) {
				done = true;
				repaint();
				return;
			}
		}
		
		List<Point> points = new ArrayList<Point>();
		for(int i = 0; i<3; i++)
			for(int j = 0; j<3; j++)
				if(data[i][j] == -1) 
					points.add(new Point(i, j));
		
		if(points.size() > 0) {
			Point p = points.get(new Random().nextInt(points.size()));
			data[p.x][p.y] = 1;
			line = check(1);
			if(line != null)
				done = true;
		} else if (points.size() <= 1) {
			done = true;
		} 
		
		repaint();
	}
	
	public int[] check(int turn) {
		if(data[0][0] == turn && data[0][1] == turn && data[0][2] == turn)
			return new int[] {0, 0, 0, 2};
		if(data[1][0] == turn && data[1][1] == turn && data[1][2] == turn)
			return new int[] {1, 0, 1, 2};
		if(data[2][0] == turn && data[2][1] == turn && data[2][2] == turn)
			return new int[] {2, 0, 2, 2};
		if(data[0][0] == turn && data[1][0] == turn && data[2][0] == turn)
			return new int[] {0, 0, 2, 0};
		if(data[0][1] == turn && data[1][1] == turn && data[2][1] == turn)
			return new int[] {0, 1, 2, 1};
		if(data[0][2] == turn && data[1][2] == turn && data[2][2] == turn)
			return new int[] {0, 2, 2, 2};
		if(data[0][0] == turn && data[1][1] == turn && data[2][2] == turn)
			return new int[] {0, 0, 2, 2};
		if(data[0][2] == turn && data[1][1] == turn && data[2][0] == turn)
			return new int[] {0, 2, 2, 0};
		return null;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

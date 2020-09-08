package UI.game;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

import board.Board;
import board.Move;
import board.Piece;
import board.setup.BoardSetup;
import control.Animation;
import control.Converter;
import control.GameController;
import control.GameController.EndGameListener;
import control.MessageListener;

@SuppressWarnings("serial")
public class Screen extends JPanel implements MouseListener, MouseMotionListener, ActionListener, KeyListener, EndGameListener {
	
	public static final Color BRIGHT_COLOR = new Color(255, 255, 200);
	public static final Color DARK_COLOR = new Color(201, 144, 75);
	public static final Color HIGHLIGHT_COLOR = new Color(134, 179, 21, 100);
	public static final Color CHECK_COLOR = new Color(173, 40, 0, 200);
	
	Image[] piecesImg = new Image[12];

	public Board board;
	
	int columns = 8;
	int rows = 8;
	int mouseX, mouseY;
	
	
	Piece selectedPiece;
	Timer paintTimer = new Timer(5, this);
	
	Animation pieceAnim = new Animation();
	Converter converter = new Converter(this);
	
	GameController controller;
	boolean exitOnEnd;
	MessageListener messageListener;
	
	public Screen(BoardSetup setup, boolean exitOnEnd) {
		this.exitOnEnd = exitOnEnd;
		controller = new GameController(this, setup);
		controller.setListener(this);
		
		BufferedImage img = null;
	    try {
	        img = ImageIO.read(getClass().getResourceAsStream("/chess pieces 2.png"));
	    } catch (IOException e) {
	    	
	    }
	    
	    int w = img.getWidth()/6;
	    int h = img.getHeight()/2;
	    
	    for(int i = 0; i<6; i++){
	    	getPiecesImg()[i] = img.getSubimage(i*w, 0, w, h).getScaledInstance(200, 200, Image.SCALE_SMOOTH);
	    	getPiecesImg()[i+6] = img.getSubimage(i*w, h, w, h).getScaledInstance(200, 200, Image.SCALE_SMOOTH);
		}
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
		paintTimer.start();
	}
	
	public void setMessageListener(MessageListener listener) {
		messageListener = listener;
	}
	
	public int unit() {
		return this.getSize().width / columns;
	}
	
	public void paint(Graphics g) {
		int unit = unit();
		
		// Background
		Piece checkedKing = null;
		if(board.rule.kingInCheck(board))
			checkedKing = board.getKing(board.whiteToMove);
			
		for(int i = 0; i<columns; i++)
	      for (int j = 0; j<getRows(); j++) {
	    	g.setColor(BRIGHT_COLOR);
	        if ((i+j)%2 == 1) g.setColor(DARK_COLOR);
	        if (checkedKing != null && board.getPiece(i, j) == checkedKing) g.setColor(CHECK_COLOR);
	        g.fillRect(i*unit, j*unit, unit, unit);
	      }
		if(getBoard().lastMove != null) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setStroke(new BasicStroke(3.0f));
			g2d.setPaint(Color.BLACK);
			
			drawLastMove(g2d, getBoard().lastMove.x1, getBoard().lastMove.y1);
			drawLastMove(g2d, getBoard().lastMove.x2, getBoard().lastMove.y2);
		}
		
		// Pieces
		List<Piece> pieces = getBoard().getPieceList();
		Piece animPiece = getBoard().lastMovePiece();
		if(pieceAnim.isQuiet())
			animPiece = null;
		for(Piece p : pieces)
			if(p != selectedPiece && p != animPiece)
				drawPiece(g, p);
		drawAnimPiece(g, animPiece);
		drawAvailableMoves(g);
		drawSelectedPiece(g);	
	}
	
	public void drawAvailableMoves(Graphics g) {
		if(selectedPiece == null)
			return;
		List<Move> availableMoves = controller.getAvailableMoves();
		if(availableMoves == null)
			return;
		for(Move m : availableMoves) {
			Point p = converter.convertToPixel(m.x2, m.y2);
			int unit = unit();
			if(board.hasPiece(m.x2, m.y2)) {
				int r = (int) (unit * 0.55);
				Graphics2D g2 = (Graphics2D) g;
				g2.setColor(new Color(0, 0, 0, 50));
				g2.setStroke(new BasicStroke(5));
				int arcDeg = 30;
				g.drawArc(p.x - r , p.y - r, 2 * r, 2 * r, 45 - arcDeg/2, arcDeg);
				g.drawArc(p.x - r , p.y - r, 2 * r, 2 * r, 90 + 45 - arcDeg/2, arcDeg);
				g.drawArc(p.x - r , p.y - r, 2 * r, 2 * r, 180 + 45 - arcDeg/2, arcDeg);
				g.drawArc(p.x - r , p.y - r, 2 * r, 2 * r, 270 + 45 - arcDeg/2, arcDeg);
			}else {;
				int r = 15;
				g.setColor(new Color(0, 0, 0, 50));
				g.fillOval(p.x - r , p.y - r, 2 * r, 2 * r);
			}
		}
	}
	
	public void drawLastMove(Graphics2D g2d, int x, int y) {
		Point p = converter.convertToPixel(x, y);
		int unit = unit();
		g2d.setColor(HIGHLIGHT_COLOR);
		g2d.fillRect(p.x - unit/2, p.y - unit/2, unit, unit);
	}
	
	public void drawPieceAt(Graphics g, Piece piece, int pixelX, int pixelY) {
		Image img = piecesImg[piece.type.ordinal()];
		int unit = unit();
		g.drawImage(img, pixelX - unit/2, pixelY - unit/2, unit, unit, null);
	}
	
	public void drawPiece(Graphics g, Piece piece) {
		Point o = converter.convertToPixel(piece.x, piece.y);
		drawPieceAt(g, piece, o.x, o.y);
	}
	
	public void drawAnimPiece(Graphics g, Piece animPiece) {
		if(animPiece == null)
			return;
		drawPieceAt(g, animPiece, (int)pieceAnim.x, (int)pieceAnim.y);
	}
	
	public void drawSelectedPiece(Graphics g) {
		if(selectedPiece == null)
			return;
		drawPieceAt(g, selectedPiece, mouseX, mouseY);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1 && isPlayasWhite() == getBoard().whiteToMove) {
			Point p = converter.convertToCell(e.getX(), e.getY());
			selectedPiece = board.getPiece(p.x, p.y);
			if(selectedPiece != null)
				controller.updateAvailableMoves(selectedPiece);
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1 && selectedPiece != null) {
			Point p = converter.convertToCell(e.getX(), e.getY());
			boolean b = controller.playerMove(selectedPiece.x, selectedPiece.y, p.x, p.y);
			if(b) {
				pieceAnim.setLoc(e.getX(), e.getY());
				Point t = converter.convertToPixel(board.lastMove.x2, board.lastMove.y2);
				pieceAnim.setTarget(t.x, t.y);
			}
			selectedPiece = null;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		pieceAnim.update();
		repaint();
		if(pieceAnim.isQuiet()) {
			controller.update();
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_LEFT)
			controller.undoMove();
		if(e.getKeyCode() == KeyEvent.VK_RIGHT)
			controller.aiMove();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	public Image[] getPiecesImg() {
		return piecesImg;
	}

	public boolean isPlayasWhite() {
		return controller.isPlayasWhite();
	}

	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return columns;
	}

	public Board getBoard() {
		return board;
	}

	public void executeAnim(int x1, int y1, int x2, int y2) {
		Point l = converter.convertToPixel(board.lastMove.x1, board.lastMove.y1);
		Point t = converter.convertToPixel(board.lastMove.x2, board.lastMove.y2);
		pieceAnim.setLoc(l.x, l.y);
		pieceAnim.setTarget(t.x, t.y);
	}

	@Override
	public void endGame() {
		if(exitOnEnd) {
			this.paintTimer.stop();
			messageListener.messageReceived("exit");
		} else
			controller.resetBoard();
	}

}

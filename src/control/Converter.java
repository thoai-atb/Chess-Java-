package control;

import java.awt.Point;

import UI.game.Screen;

public class Converter {
	
	private Screen screen;
	
	public Converter(Screen screen) {
		this.screen = screen;
	}
	
	public Point convertToCell(int pixelX, int pixelY) {
		int x = pixelX / screen.unit();
		int y = pixelY / screen.unit();
		if(!screen.isPlayasWhite()) {
			y = screen.getRows() - 1 - y;
			x = screen.getColumns() - 1 - x;
		}
		return new Point(x, y);
	}
	
	public Point convertToPixel(int cellX, int cellY) {
		if(!screen.isPlayasWhite()) {
			cellX = screen.getColumns() - 1 - cellX;
			cellY = screen.getRows() - 1 - cellY;
		}
		int x = cellX * screen.unit() + screen.unit() / 2;
		int y = cellY * screen.unit() + screen.unit() / 2;
		return new Point(x, y);
	}

}

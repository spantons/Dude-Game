package com.spantons.stagesMenu;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.imgscalr.Scalr;

import com.spantons.Interfaces.IDrawable;
import com.spantons.Interfaces.IUpdateable;
import com.spantons.main.GamePanel;
import com.spantons.singleton.ImageCache;

public class Background implements IDrawable, IUpdateable{

	private BufferedImage image;
	private boolean repeat;
	private double movementScale;
	private Color bgColor;

	private double x;
	private double y;
	private double dx;
	private double dy;

	/****************************************************************************************/
	public Background(String _imageSource, double _movementScale, boolean _repeat, Color _bgColor) {
		try {
			repeat = _repeat;
			movementScale = _movementScale;
			bgColor = _bgColor;
			
			 image = ImageCache.getInstance().getImage(_imageSource);
			 			 
			if (!_repeat){
				
				if (image.getWidth() > GamePanel.RESOLUTION_WIDTH) 
					image = Scalr.resize(image, GamePanel.RESOLUTION_HEIGHT); 
				
				else if (image.getHeight() > GamePanel.RESOLUTION_HEIGHT) 
					image = Scalr.resize(image, GamePanel.RESOLUTION_WIDTH);

				x = (GamePanel.RESOLUTION_WIDTH - image.getWidth()) / 2;
				y = (GamePanel.RESOLUTION_HEIGHT - image.getHeight()) / 2;	    
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/****************************************************************************************/
	public void setPosition(double x, double y) {
		this.x = (x * movementScale) % GamePanel.RESOLUTION_WIDTH;
		this.y = (y * movementScale) % GamePanel.RESOLUTION_HEIGHT;
	}
	/****************************************************************************************/
	public void setVector(double dx, double dy) {
		this.dx = dx;
		this.dy = dy;
	}
	/****************************************************************************************/
	public void update() {
		if (repeat) {
			x += (dx * movementScale) % GamePanel.RESOLUTION_WIDTH;
			y += (dy * movementScale) % GamePanel.RESOLUTION_HEIGHT;
		}
	}
	/****************************************************************************************/
	public void draw(Graphics2D g) {

		if (repeat) {
			int iw = image.getWidth();
			int ih = image.getHeight();
			if (iw > 0 && ih > 0) {
				for (int x = 0; x < GamePanel.RESOLUTION_WIDTH; x += iw) {
					for (int y = 0; y < GamePanel.RESOLUTION_HEIGHT; y += ih) 
						g.drawImage(image, x, y, iw, ih, null);
				}
			}
			
		} else {
			g.setColor(bgColor);
			g.fillRect(0, 0, GamePanel.RESOLUTION_WIDTH,
				GamePanel.RESOLUTION_HEIGHT);
			g.drawImage(image, (int) x, (int) y, null);
		}
	}

}

package me.nimnon.nmengine.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import me.nimnon.nmengine.Game;

public class Camera {

	/**
	 * Screen X
	 */
	private double screenx = 0d;

	/**
	 * Screen Y
	 */
	private double screeny = 0d;

	/**
	 * World X
	 */
	public double x = 0d;

	/**
	 * World Y
	 */
	public double y = 0d;

	/**
	 * Camera screen-space width in pixels, width in worldspace is width/zoom
	 */
	private double width = 400d;

	/**
	 * Camera screen-space height in pixels, width in worldspace is height/zoom
	 */
	private double height = 300d;

	/**
	 * World zoom
	 */
	public int zoom = 3;

	public BufferedImage imageData;

	public Graphics2D imageGraphics;

	/**
	 * Sets up a plain camera with default properties
	 */
	public Camera() {
		updateCamera();
	}

	/**
	 * Sets up a camera
	 * 
	 * @param x
	 *            Screen-space y
	 * @param y
	 *            Screen-space y
	 * @param width
	 *            Screen-space width
	 * @param height
	 *            Screen-space height
	 * @param zoom
	 *            Zoom amount
	 */
	public Camera(double x, double y, double width, double height, int zoom) {
		this.screenx = x;
		this.screeny = y;
		this.width = width;
		this.height = height;
		this.zoom = zoom;
		updateCamera();
	}

	public void updateCamera() {
		imageData = new BufferedImage((int) (width / zoom), (int) (height / zoom), BufferedImage.TYPE_INT_ARGB);
		imageGraphics = imageData.createGraphics();
	}

	public void draw(Graphics2D g2d) {
		
		g2d.drawImage(imageData, (int) screenx, (int) screeny, (int) width, (int) height, null);
		
		imageGraphics.setColor(Color.white);
		imageGraphics.fillRect(0, 0, imageData.getWidth(), imageData.getHeight());
	}

	public boolean isOnScreen(GameObject object) {
		if (object.x + object.width - 1 >= x && object.x <= x + (width / zoom)) {
			if (object.y + object.height - 1 >= y && object.y <= y + (height / zoom)) {
				return true;
			}
		}
		return false;
	}

}
package def;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.nio.Buffer;
import java.util.Random;

public class Game {
	Random rand = new Random();
	private double posX = 128;
	private double posY = 256;
	private double gravity = 0.009;
	private double raggio = 50;
	private double velY = 0.5;
	private double pipeX = 700;
	private double pipeY = rand.nextInt(500) + 500;
	private double velX = 1 ;
	private Graphic graphic;

	public Game(Graphic g) {
		this.graphic = g;
	}

	public double getPosX() {
		return posX;
	}
	
	public double getVelY() {
		return velY;
	}

	public double getPosY() {
		return posY;
	}

	public double getGravity() {
		return gravity;
	}

	public double getRaggio() {
		return raggio;
	}
	
	public double getPipeX() {
		return pipeX;
	}

	public double getPipeY() {
		return rand.nextInt(500) + 400;
	}
	
	public double getVelX() {
		return velX;
	}

	public Graphic getGraphic() {
		return graphic;
	} 


	public void flap() {
		if(MyKeyListener.isSpacePressed()) {
			graphic.setPosY(-0.01);
			graphic.setVelY(0.5);
		}	
	}
 
}

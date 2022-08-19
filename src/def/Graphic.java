package def;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * 
 * @author Mattia Pezzotti
 */

public class Graphic extends JPanel implements ActionListener {

	// Costruttore
	public Graphic() {
		loadImage();
	}

	// Variabile per generare i numeri random
	Random rand = new Random();

	// Colors
	private Color bird = new Color(255, 255, 0);

	// Game physics
	private Game game = new Game(this);

	// Images
	private BufferedImage flappy;
	private BufferedImage bg;
	private BufferedImage botPipe;
	private BufferedImage topPipe;
	private Font arcade;
	private Font miniArcade;

	public void loadImage() {
		
		// Buffer del Font Custom
		try {
			arcade = Font.createFont(Font.TRUETYPE_FONT, new File("font/ARCADEPI.TTF")).deriveFont(50f);
			miniArcade = Font.createFont(Font.TRUETYPE_FONT, new File("font/ARCADEPI.TTF")).deriveFont(25f);
			GraphicsEnvironment gE = GraphicsEnvironment.getLocalGraphicsEnvironment();
			gE.registerFont(arcade);
		} catch (IOException | FontFormatException e) {
			System.err.print("An Error Occurred While Loading Font");
			System.exit(-1);
		}

		// Buffer delle immagini
		try {
			flappy = ImageIO.read(new File("sprites/FlappyBird.png"));
			bg = ImageIO.read(new File("sprites/Background.png"));
			botPipe = ImageIO.read(new File("sprites/BotPipe.png"));
			topPipe = ImageIO.read(new File("sprites/TopPipe.png"));
		} catch (IOException ex) {
			System.err.print("An Error Occurred While Loading Images");
			System.exit(-1);
		}
	}

	// Bird Position
	private int birdX = (int) game.getPosX();
	private int birdY = (int) game.getPosY();
	private double velY = game.getVelY();
	private double velX = game.getVelX();
	private int raggio = (int) game.getRaggio();
	private double gravity = game.getGravity();

	// Pipes Position
	private double pipeX = game.getPipeX();
	private double pipeX2 = game.getPipeX() * 1.75;
	private double pipeY = game.getPipeY();
	private double pipeY2 = game.getPipeY() * 0.75;
	private int pipeWidth = 127;
	private boolean gameover = false;

	// Score Things
	private String scoreField;
	private double score = 0;
	private int highScore = 0;

	//Timer
	private Timer timer = new Timer((int) game.getGravity(), this);

	
	public AffineTransformOp setRotation() {
		AffineTransformOp op = null;
		
		if (MyKeyListener.isSpacePressed()) {
			double rotationRequired = Math.toRadians(-45);
			double locationX = flappy.getWidth() / 2;
			double locationY = flappy.getHeight() / 2;
			AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		}

		if (velY >= 2) {
			double rotationRequired = Math.toRadians(45);
			double locationX = flappy.getWidth() / 2;
			double locationY = flappy.getHeight() / 2;
			AffineTransform tx = AffineTransform.getRotateInstance(rotationRequired, locationX, locationY);
			op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);
		}
		
		return op;
	}
	
	// Main Method
	public void paintComponent(Graphics graphics) {
		super.paintComponent(graphics);
		
		// Attributo per permettere la rotazione
		AffineTransformOp op = setRotation();
		
		// Draw BackGround
		graphics.drawImage(bg, 0, 0, this);

		// Draw TopPipes
		graphics.drawImage(topPipe, (int) pipeX, (int) pipeY - 1300, this);
		graphics.drawImage(topPipe, (int) pipeX2, (int) pipeY2 - 1300, this);

		// Draw BotPipes
		graphics.drawImage(botPipe, (int) pipeX, (int) pipeY, this);
		graphics.drawImage(botPipe, (int) pipeX2, (int) pipeY2, this);

		// Draw Bird
		if (!gameover && MyKeyListener.isSpacePressed() || velY >= 2)
			graphics.drawImage(op.filter(flappy, null), birdX, birdY, null);
		else
			graphics.drawImage(flappy, birdX, birdY, this);

		// Draw Strings
		graphics.setFont(arcade);
		graphics.setColor(Color.black);
		graphics.drawString(scoreField + " ", 275, 100 - 10);

		// What if Game Over
		if (gameover) {
			if (score > highScore)
				highScore = (int) score;
			// graphics.setFont(new Font("arcade", Font.BOLD, 100));
			graphics.setColor(Color.BLACK);
			graphics.fillRoundRect(50, 445, 500, 125, 35, 15);
			graphics.setColor(Color.red);
			graphics.drawString("Game Over", 135, 500);
			// graphics.setFont(new Font("arcade", Font.BOLD, 55));
			graphics.setFont(miniArcade);
			graphics.drawString("Press A to Restart", 135, 550);
			velX = 0;
			velY = 0;
			if (MyKeyListener.aPressed) {
				restart();
			}
			
			if(MyKeyListener.spacePressed) {
				MyKeyListener.spacePressed = false;
			}
		}

		timer.start();
		game.flap();
	}

	public void restart() {

		gameover = false;
		// Bird Position
		birdX = (int) game.getPosX();
		birdY = (int) game.getPosY();
		velY = game.getVelY();
		velX = game.getVelX();
		raggio = (int) game.getRaggio();
		gravity = game.getGravity();

		// Pipes Position
		pipeX = game.getPipeX();
		pipeX2 = game.getPipeX() * 1.75;
		pipeY = game.getPipeY() + rand.nextInt(10);
		pipeY2 = game.getPipeY() + rand.nextInt(10);
		pipeWidth = 127;
		gameover = false;

		// Other Things
		String scoreField;
		score = 0;

		Timer timer = new Timer((int) game.getGravity(), this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (birdX == (int) pipeX + pipeWidth || birdX == (int) pipeX2 + pipeWidth)
			score++;

		// System.out.println(birdX + " - " + (int) (pipeX + pipeWidth));

		scoreField = (int) score + "";

		if (birdY >= 975 - raggio)
			birdY = 975 - raggio - 5;

		if (birdX >= 600 || birdX < 0)
			velX *= -1;

		// Bottom Pipe HitBox
		if (birdY + raggio >= pipeY && birdX + raggio > pipeX && birdX + raggio < pipeX + pipeWidth)
			gameover = true;
		// while(!MyKeyListener.spacePressed)
		// window = new Window();

		if (birdY + raggio >= pipeY2 && birdX + raggio > pipeX2 && birdX + raggio < pipeX2 + pipeWidth)
			gameover = true;

		// System.out.println(birdX + " " + birdY + " - " + pipeX + " " + pipeY);

		// Top Pipe HitBox
		if (birdY <= pipeY - 110 && birdX + raggio > pipeX && birdX + raggio < pipeX + pipeWidth)
			gameover = true;

		if (birdY <= pipeY2 - 110 && birdX + raggio > pipeX2 && birdX + raggio < pipeX2 + pipeWidth)
			gameover = true;

		if (velY < 2)
			this.velY += gravity;

		this.birdY += velY;
		this.pipeX -= velX;
		this.pipeX2 -= velX;

		if (pipeX + pipeWidth < 0) {
			pipeX = game.getPipeX() * 1.40;
			pipeY = game.getPipeY();
		}

		if (pipeX2 + pipeWidth < 0) {
			pipeX2 = game.getPipeX() * 1.40;
			pipeY2 = game.getPipeY();
		}
		repaint();
	}

	public void setPosY(double pos) {
		birdY += pos;
	}

	public void setVelY(double pos) {
		velY = pos;
	}

	public int getBirdX() {
		return birdX;
	}

	public void setBirdX(int birdX) {
		this.birdX = birdX;
	}

	public int getBirdY() {
		return birdY;
	}

	public void setBirdY(int birdY) {
		this.birdY = birdY;
	}

}

package def;

import javax.swing.JFrame;

public class Window {
	private Graphic bird = new Graphic();
	private final JFrame window = new JFrame("Flappy Bird - Mattia Pezzotti");	
	
	public void draw() {
		// Main Window
		drawWindow();
		window.add(bird);
	}
	
	private void drawWindow() {
		window.setSize(600, 1000);
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
}

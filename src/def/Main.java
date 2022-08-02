package def;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

public class Main {
	
	public static void main(String args[]) {
		Window window = new Window();
		window.draw();
		
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
                synchronized (MyKeyListener.class) {
                    switch (ke.getID()) {
                    case KeyEvent.KEY_PRESSED:
                        if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                        	MyKeyListener.spacePressed = true;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_A) {
                        	MyKeyListener.aPressed = true;
                        }
                        break;

                    case KeyEvent.KEY_RELEASED:
                        if (ke.getKeyCode() == KeyEvent.VK_SPACE) {
                        	MyKeyListener.spacePressed = false;
                        }
                        if (ke.getKeyCode() == KeyEvent.VK_A) {
                        	MyKeyListener.aPressed = false;
                        }
                        break;
                    }
                    return false;
                }
            }
        });
	}
}

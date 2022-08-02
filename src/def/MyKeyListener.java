package def;

public class MyKeyListener{
	public static volatile boolean spacePressed = false;
	public static volatile boolean aPressed = false;
	
	public static boolean isSpacePressed() {
        synchronized (MyKeyListener.class) {
            return spacePressed;
        }
    }
	
	public static boolean isAPressed() {
        synchronized (MyKeyListener.class) {
            return aPressed;
        }
    }
	
}

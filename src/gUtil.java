
public class gUtil {//Class used for pixels and screen coordinates conversion
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 600;
	private static final int OFFSET = 200;
	private static final double SCALE = HEIGHT/100;

	static int XtoScreen(double X) {
		return (int) (X * SCALE);
	}
	
	static int YtoScreen(double Y) {
		return (int) (HEIGHT - Y * SCALE);
	}
	
	static int LtoScreen(double length) {
		return (int) (length * SCALE);
	}

}

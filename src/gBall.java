import java.awt.Color;
import acm.graphics.GOval;
public class gBall extends Thread{
	//Initialize the parameters of the gBall class
	public GOval myBall;
	private double Xi;
	private double Yi;
	public double bSize;
	private Color bColor;
	public double bLoss;
	public double bVel;
	public boolean isRunning; //We create a boolean variable to determine if the ball is still bouncing
	public static double sorted_x = 0; //We create a static double variable to set the x location of the sorted balls
	public static final double G=9.8;
	private static final double TICK = 0.1; 

	public gBall(double Xi, double Yi, double bSize, Color bColor, double bLoss, double bVel) {
		// Get simulation parameters
		this.Xi = Xi;
		this.Yi = Yi;
		this.bSize = bSize;
		this.bColor = bColor;
		this.bLoss = bLoss;
		this.bVel = bVel;
		this.isRunning = true;//When we initialize the object, it is running
		myBall = new GOval(gUtil.XtoScreen(Xi-bSize),gUtil.YtoScreen(Yi+bSize),gUtil.LtoScreen(2*bSize),gUtil.LtoScreen(2*bSize));
		myBall.setFilled(true);
		myBall.setFillColor(bColor);
	}

	public void run(){
		// Run the simulation code
		double time = 0;
		double total_time = 0;
		double vt = Math.sqrt(2*G*Yi);
		double height = Yi;
		int dir = 0;
		double last_top = Yi;
		double el = Math.sqrt(1.0-bLoss);
		while (isRunning) {//We use isRunning as a condition to be able to stop the balls
			if (dir == 0) {
				height = last_top - 0.5*G*time*time;
				if (height <= bSize) {
					dir=1;
					last_top = bSize;
					time=0;
					vt = vt*el;
				}
			}
			else {
				height =bSize + vt*time -0.5*G*time*time;
				if(height < last_top) {
					if(height <= bSize) break;
					dir = 0;
					time = 0;
				}
				last_top = height;
			}
			double Yt = Math.max(bSize, height);
			double Xt = Xi + bVel*total_time;
			myBall.setLocation(gUtil.XtoScreen(Xt-bSize),gUtil.YtoScreen(Yt+bSize));
			try {
				Thread.sleep((long)(TICK*500));
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
			time += TICK;
			total_time += TICK;
		}
		isRunning = false;//Once we are out of the while loop, the gBall object stops running
	}
	public void Sort(double xi,double yi) {//We set the location of the balls one after another on the x-axis
		myBall.setLocation(gUtil.XtoScreen(sorted_x),gUtil.YtoScreen(yi));//We use as the starting point the last final position (stored in sorted_x)
		sorted_x += xi;//We add xi (the diameter of the ball) to get the new final position
	}
}



import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import acm.graphics.GCanvas;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.gui.TableLayout;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

public class bSim extends GraphicsProgram implements ChangeListener,ActionListener{
	private static final int WIDTH = 1200; // WIDTH in pixels
	public static final int HEIGHT = 600; // HEIGHT in pixels
	private static final int OFFSET = 200; // OFFSET in pixels
	private static final int NUMBALLS = 25; // number of balls
	private static final double MINSIZE = 1; // Min ball size
	private static final double MAXSIZE = 25; // Max ball size
	private static final double XMIN = 1; // Min X starting location
	private static final double XMAX = 200; // Max X starting location
	private static final double YMIN = 1; // Min Y starting location
	private static final double YMAX = 100; // Max Y starting location
	private static final double EMIN = 0; // Minimum loss coefficient
	private static final double EMAX = 1.0; // Maximum loss coefficient
	private static final double VMIN = 0; // Minimum X velocity
	private static final double VMAX = 10; // Maximum Y velocity
	private static final double MinSingleSize = 1.0; //Minimum Size of a single ball on the slider
	private static final double MaxSingleSize = 8.0; //Maximum Size of a single ball on the slider
	private static final double SingleMinLoss = 0.1; //Minimum loss coefficient of a single ball on the slider
	private static final double SingleMaxLoss = 1.0; //Maximum loss coefficient of a single ball on the slider
	private static final double SingleVMin = 1.0; //Minimum velocity of a single ball on the slider
	private static final double SingleVMax = 5.0; //Maximum velocity of a single ball on the slider
	private bTree myTree;//We create the bTree, initially a null object
	private GObject gobj; /* The object being dragged */
	private double lastX; /* The last mouse X position */
	private double lastY; /* The last mouse Y position */
	private GOval clickedOval; /* will cast GObject to this variable*/
	private bNode clickedNode;//The node where the variable aBall contains the ball selected
	private int user_balls = NUMBALLS;//The value of number of balls selected by the user on the slider
	private double user_minsize = MINSIZE;//The minimum size of balls in the general simulation selected by the user
	private double user_maxsize = MAXSIZE;//The maximum size of balls in the general simulation selected by the user
	private double user_xmin = XMIN;//The minimum start position on the x-axis in the general simulation selected by the user
	private double user_xmax = XMAX;//The maximum start position on the x-axis in the general simulation selected by the user
	private double user_ymin = YMIN;//The minimum start position on the y-axis in the general simulation selected by the user
	private double user_ymax = YMAX;//The maximum start position on the y-axis in the general simulation selected by the user
	private double user_lossmin = EMIN;//The minimum loss coefficient in the general simulation selected by the user
	private double user_lossmax = EMAX;//The maximum loss coefficient in the general simulation selected by the user
	private double user_vmin = VMIN;//The minimum speed in the general simulation selected by the user
	private double user_vmax = VMAX;//The maximum speed in the general simulation selected by the user
	private Color ball_color = Color.MAGENTA;//The color of the ball selected by the user in the Single Ball Simulation Parameters
	private double user_single_size = 4.0;//The size of the ball selected by the user in the Single Ball Simulation Parameters
	private double user_single_loss = 0.4;//The loss coefficient selected by the user in the Single Ball Simulation Parameters
	private double user_single_v = 1.0;//The velocity in the x direction selected by the user in the Single Ball Simulation Parameters
	//We initialize the sliderBox objects used in the assignment as null objects
	private sliderBox BallsSlider;
	private sliderBox UserMinSize;
	private sliderBox UserMaxSize;
	private sliderBox UserXMin;
	private sliderBox UserXMax;
	private sliderBox UserYMin;
	private sliderBox UserYMax;
	private sliderBox UserLossMin;
	private sliderBox UserLossMax;
	private sliderBox UserVMin;
	private sliderBox UserVMax;
	private sliderBox BallColor;
	private sliderBox SingleBallSize;
	private sliderBox SingleLoss;
	private sliderBox SingleV;
	private JComboBox bSimC;//We initialize the JComboBox object used in the assignment as a null object
	private boolean Run = false;//The Run variable is used to execute code in the run method once the user
	//clicks on Run on the JComboBox, it is initially false and becomes true when Run is selected
	private boolean Delete = false;//Delete is used to execute code in the run method when the user
	//chooses to clear the simulation. It is initially false and becomes true when Clear is selected
	private boolean mouseReleased = false;//mouseReleased is used to execute code in the run method once
	//the mouse is released. It is initially false and becomes true when the mouse is released
	GLabel sorted_message;//the GLabel that displays the message "All sorted!"
	GRect gPlane;//The GRect object that contains the ground

	public void run() {
		this.resize(WIDTH,HEIGHT+OFFSET); // Resize the window
		//We create the sliderBox objects used in the assignment and add them to the GCanvas
		BallsSlider = new sliderBox("NUMBALLS",new Integer(1),new Integer(NUMBALLS),new Integer(NUMBALLS));
		UserMinSize = new sliderBox("MIN SIZE",MINSIZE,MINSIZE,MAXSIZE);
		UserMaxSize = new sliderBox("MAX SIZE",MINSIZE,MAXSIZE,MAXSIZE);
		UserXMin = new sliderBox("X MIN",XMIN,XMIN,XMAX);
		UserXMax = new sliderBox("X MAX",XMIN,XMAX,XMAX);
		UserYMin = new sliderBox("Y MIN",YMIN,YMIN,YMAX);
		UserYMax = new sliderBox("Y MAX",YMIN,YMAX,YMAX);
		UserLossMin = new sliderBox("LOSS MIN",EMIN,EMIN,EMAX);
		UserLossMax = new sliderBox("LOSS MAX",EMIN,EMAX,EMAX);
		UserVMin = new sliderBox("X VEL MIN",VMIN,VMIN,VMAX);
		UserVMax = new sliderBox("X VEL MAX",VMIN,VMAX,VMAX);
		//We first add the JLabel "General Simulation Parameters" before adding the 
		//General Simulation Parameters sliders
		JLabel gen_param = new JLabel("General Simulation Parameters");
		add(gen_param,EAST);
		add(BallsSlider.myPanel,EAST);
		add(UserMinSize.myPanel,EAST);
		add(UserMaxSize.myPanel,EAST);
		add(UserXMin.myPanel,EAST);
		add(UserXMax.myPanel,EAST);
		add(UserYMin.myPanel,EAST);
		add(UserYMax.myPanel,EAST);
		add(UserLossMin.myPanel,EAST);
		add(UserLossMax.myPanel,EAST);
		add(UserVMin.myPanel,EAST);
		add(UserVMax.myPanel,EAST);
		//We add the JLabel "Single Ball Simulation Parameters" before adding the
		//Single Ball Simulation Parameters sliders
		JLabel single_param = new JLabel("Single Ball Simulation Parameters");
		add(single_param,EAST);
		BallColor = new sliderBox(ball_color);
		SingleBallSize = new sliderBox("Ball Size",MinSingleSize,user_single_size,MaxSingleSize);
		SingleLoss = new sliderBox("E Loss",SingleMinLoss,user_single_loss,SingleMaxLoss);
		SingleV = new sliderBox("X Vel",SingleVMin,user_single_v,SingleVMax);
		add(BallColor.myPanel,EAST);
		add(SingleBallSize.myPanel,EAST);
		add(SingleLoss.myPanel,EAST);
		add(SingleV.myPanel,EAST);
		//We add change listeners to all the sliders
		BallsSlider.mySlider.addChangeListener((ChangeListener) this);
		UserMaxSize.mySlider.addChangeListener((ChangeListener) this);
		UserMinSize.mySlider.addChangeListener((ChangeListener) this);
		UserXMin.mySlider.addChangeListener((ChangeListener) this);
		UserXMax.mySlider.addChangeListener((ChangeListener) this);
		UserYMin.mySlider.addChangeListener((ChangeListener) this);
		UserYMax.mySlider.addChangeListener((ChangeListener) this);
		UserLossMin.mySlider.addChangeListener((ChangeListener) this);
		UserLossMax.mySlider.addChangeListener((ChangeListener) this);
		UserVMin.mySlider.addChangeListener((ChangeListener) this);
		UserVMax.mySlider.addChangeListener((ChangeListener) this);
		BallColor.mySlider.addChangeListener((ChangeListener) this);
		SingleBallSize.mySlider.addChangeListener((ChangeListener) this);
		SingleLoss.mySlider.addChangeListener((ChangeListener) this);
		SingleV.mySlider.addChangeListener((ChangeListener) this);
		setChoosers();//We set the choosers (options in the JComboBox)
		addJComboListeners();//We add action listeners to the JComboBox
		addMouseListeners();//We add mouse listeners to the canvas
		gPlane = new GRect(0,HEIGHT,WIDTH,3); //Create the floor
		gPlane.setColor(Color.BLACK); 
		gPlane.setFilled(true); 
		add(gPlane); //Display the floor
		while(true) {//We run indefinitely (until the user chooses to Quit) and perform certain actions depending on
			//the action performed by the user
			pause(200);
			if(Run) {//We add the floor and run a new simulation when the user clicks on Run in the JComboBox
				//We reset the variable Run to false
				Run = false;
				gPlane = new GRect(0,HEIGHT,WIDTH,3); //Create the floor
				gPlane.setColor(Color.BLACK); 
				gPlane.setFilled(true); 
				add(gPlane); //Display the floor
				RunSim();
			}
			else if(Delete) {//We re-display the ground after the removeAll() function called when the user
				//chooses to clear the simulation. We reset the variable Delete to false
				Delete = false;
				gPlane = new GRect(0,HEIGHT,WIDTH,3); //Create the floor
				gPlane.setColor(Color.BLACK); 
				gPlane.setFilled(true); 
				add(gPlane); //Display the floor
			}
			else if(mouseReleased) {//When the mouse is released, we display the ball that has been
				//changed, we start its simulation and we continue the simulation of all the balls in myTree
				//(done in RunSimulation()). We reset mouseReleased to false
				mouseReleased = false;
				add(clickedNode.aBall.myBall);
				clickedNode.aBall.start();
				RunSimulation();
			}
		}
	}

	public void mousePressed(MouseEvent e) {//when the mouse is pressed on a ball, we stop its simulation
		//and we temporarily stop the simulation function (Run or RunSimulation, this is achieved by myTree.quit = true)
		//the function (RunSimulation) will be called again once the mouse is released
		lastX = e.getX();
		lastY = e.getY();
		gobj = getElementAt(lastX,lastY);
		if(gobj != null) {
			clickedOval = (GOval)gobj;
			clickedNode = myTree.findNode(clickedOval);
			clickedNode.aBall.isRunning = false;
			myTree.quit = true;
		}
	}

	public void mouseDragged(MouseEvent e) {
		//When we drag a ball, we change its location
		if(gobj != null) {
			clickedOval.move((e.getX()-lastX), (e.getY()-lastY));
			lastX = e.getX();
			lastY = e.getY();
		}
	}

	public void mouseReleased(MouseEvent e) {
		//When a ball is released, it is removed and replaced with a ball containing the user's chosen parameters
		//(size, color, velocity, ELoss), we update the bTree to take in account the size of the new ball
		//and we set mouseReleased to true
		if(gobj != null) {
			if(myTree != null) {
				clickedNode.aBall.myBall.move((e.getX()-lastX), (e.getY()-lastY));
				double X = (clickedNode.aBall.myBall.getX())/6.0 +user_single_size;
				double Y = (100-(clickedNode.aBall.myBall.getY())/6.0 -user_single_size);
				remove(clickedNode.aBall.myBall);
				clickedNode.aBall = new gBall(X,Y,user_single_size,ball_color,user_single_loss,user_single_v);
				myTree.update();
				mouseReleased = true;
			}
		}
	}

	public void stateChanged(ChangeEvent e) {//We  account for changes in slider values
		//We take in account the new values and we make sure the minimum can never be bigger than the
		//maximum, where applicable
		JSlider source = (JSlider)e.getSource();
		if (source==BallsSlider.mySlider) {
			user_balls =BallsSlider.getSlider();
			BallsSlider.setSlider(new Integer(user_balls));
		}
		else if (source==UserMinSize.mySlider) {
			user_minsize=(double)UserMinSize.getSlider()/10;
			if(user_minsize > user_maxsize) {
				UserMaxSize.setSlider(user_minsize);
			}
			UserMinSize.setSlider(user_minsize);
		}
		else if (source==UserMaxSize.mySlider) {
			user_maxsize=(double)UserMaxSize.getSlider()/10;
			if(user_maxsize < user_minsize) {
				UserMinSize.setSlider(user_maxsize);
			}
			UserMaxSize.setSlider(user_maxsize);
		}
		else if (source==UserXMin.mySlider) {
			user_xmin=(double)UserXMin.getSlider()/10;
			if(user_xmin > user_xmax) {
				UserXMax.setSlider(user_xmin);
			}
			UserXMin.setSlider(user_xmin);
		}
		else if (source==UserXMax.mySlider) {
			user_xmax=(double)UserXMax.getSlider()/10;
			if(user_xmax < user_xmin) {
				UserXMin.setSlider(user_xmax);
			}
			UserXMax.setSlider(user_xmax);
		}
		else if (source==UserYMin.mySlider) {
			user_ymin=(double)UserYMin.getSlider()/10;
			if(user_ymin > user_ymax) {
				UserYMax.setSlider(user_ymin);
			}
			UserYMin.setSlider(user_ymin);
		}
		else if (source==UserYMax.mySlider) {
			user_ymax=(double)UserYMax.getSlider()/10;
			if(user_ymax < user_ymin) {
				UserYMin.setSlider(user_ymax);
			}
			UserYMax.setSlider(user_ymax);
		}
		else if (source==UserLossMin.mySlider) {
			user_lossmin=(double)UserLossMin.getSlider()/10;
			if(user_lossmin > user_lossmax) {
				UserLossMax.setSlider(user_lossmin);
			}
			UserLossMin.setSlider(user_lossmin);
		}
		else if (source==UserLossMax.mySlider) {
			user_lossmax=(double)UserLossMax.getSlider()/10;
			if(user_lossmax < user_lossmin) {
				UserLossMin.setSlider(user_lossmax);
			}
			UserLossMax.setSlider(user_lossmax);
		}
		else if (source==UserVMin.mySlider) {
			user_vmin=(double)UserVMin.getSlider()/10;
			if(user_vmin > user_vmax) {
				UserVMax.setSlider(user_vmin);
			}
			UserVMin.setSlider(user_vmin);
		}
		else if (source==UserVMax.mySlider) {
			user_vmax=(double)UserVMax.getSlider()/10;
			if(user_vmax < user_vmin) {
				UserVMin.setSlider(user_vmax);
			}
			UserVMax.setSlider(user_vmax);
		}
		else if (source == BallColor.mySlider) {
			ball_color = BallColor.getColor();
			BallColor.setColor(ball_color);
		}
		else if(source == SingleBallSize.mySlider) {
			user_single_size = (double)SingleBallSize.getSlider()/10;
			SingleBallSize.setSlider(user_single_size);
		}
		else if(source == SingleLoss.mySlider) {
			user_single_loss = (double)SingleLoss.getSlider()/10;
			SingleLoss.setSlider(user_single_loss);
		}
		else if(source == SingleV.mySlider) {
			user_single_v = (double)SingleV.getSlider()/10;
			SingleV.setSlider(user_single_v);
		}
	}

	void setChoosers() {//We create the JComboBox and display it with this method called by the
		//run method
		bSimC = new JComboBox<String>();
		bSimC.addItem("bSim");
		bSimC.addItem("Run");
		bSimC.addItem("Clear");
		bSimC.addItem("Stop");
		bSimC.addItem("Quit");
		add(bSimC,NORTH );
	}

	void addJComboListeners() {
		//We add action listeners to the JComboBox
		bSimC.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {//We  account for changes in the JComboBox
		JComboBox source = (JComboBox)e.getSource();
		if (source==bSimC) {
			if (bSimC.getSelectedIndex()==1) {//When the user runs a new simulation
				//we quit the current one (myTree.quit = true), remove everything and set the variable Run to true
				if(myTree != null) {
					myTree.quit = true;
				}
				removeAll();
				Run = true;
			}
			else if(bSimC.getSelectedIndex() == 2) {//When the user clears the simulation, we
				//quit the simulation, remove everything and set Delete to true
				if(myTree != null) {
					myTree.quit = true;
				}
				Delete = true;
				removeAll();
			}
			else if(bSimC.getSelectedIndex() == 3) {//When the user stops the simulation
				//We stop every ball in myTree
				if(myTree != null) {
					myTree.stopSim();
				}
			}
			else if(bSimC.getSelectedIndex() == 4) {//When the user chooses to quit
				//We close the applet
				System.exit(0);
			}
		}
	}

	public void RunSim() {
		if(sorted_message != null) {//When this method is called after sorting balls
			//we first remove the message "All sorted!"
			remove(sorted_message);
		}
		myTree = new bTree(); //Create the bTree to store the gBall objects
		RandomGenerator rgen = RandomGenerator.getInstance(); //Generate an instance of the RandomGenerator class
		for (int i=0; i<user_balls; i++) {
			double Xi = rgen.nextDouble(user_xmin,user_xmax);   // Current Xi
			double Yi = rgen.nextDouble(user_ymin,user_ymax);   // Current Yi
			double iSize = rgen.nextDouble(user_minsize,user_maxsize); // Current size
			Color iColor = rgen.nextColor();      // Current color
			double iLoss = rgen.nextDouble(user_lossmin,user_lossmax);   // Current loss coefficient
			double iVel = rgen.nextDouble(user_vmin,user_vmax);     // Current X velocity
			gBall iBall = new gBall(Xi,Yi,iSize,iColor,iLoss,iVel);  // Generate instance of gBall
			myTree.addNode(iBall); //Add the gBall to the bTree
			add(iBall.myBall);   // Add to display list
			iBall.start(); //Start running the gBall object
		}
		while(myTree.isRunning() && !(myTree.quit)) {
			//The println() is to solve a bug that appeared when the first run is called with 1 ball
			println();
		}//We do nothing when the balls are not stopped(isRunnig = true and quit = false)
		if(!myTree.quit) {//If we have not quit the method, we sort the balls
			String Ask_user = "Click to continue"; //We create the message to display when the balls stop bouncing 
			String Sorted = "All sorted!"; //Message to display when the balls are all sorted
			//Create the Label of the messages and set their location on the screen
			GLabel ask_user = new GLabel(Ask_user, this.getWidth() - 10*Ask_user.length(),HEIGHT);
			sorted_message = new GLabel(Sorted,this.getWidth() - 10*Sorted.length(),HEIGHT);
			ask_user.setFont("ComicSansMS-20");//We set the font and size of the first message
			ask_user.setColor(Color.BLUE);//We set color of the first message
			sorted_message.setFont("ComicSansMS-20");//We set the font and size of the first message
			sorted_message.setColor(Color.BLUE);////We set the color of the first message
			add(ask_user);//We display the first message when the balls stop bouncing
			waitForClick();//We wait for the user to click
			remove(ask_user);//We remove the first message
			myTree.moveSort();//We sort the balls
			add(sorted_message); //We display the second message
		}
	}
	public void RunSimulation() {//Function called to run the simulation after releasing a ball
		if(sorted_message != null) {//When this method is called after sorting balls
			//we first remove the message "All sorted!"
			remove(sorted_message);
		}
		myTree.quit = false;//We had set this value to true to be able to quit the simulation method
		//when the mouse was pressed on a ball, we reset it to false to be able to continue the simulation
		while(myTree.isRunning() && !(myTree.quit)) {println();}//We do nothing when the balls are not stopped
		//except print an empty line to avoid the bug when the first simulation uses 1 ball
		if(!myTree.quit) {
			String Ask_user = "Click to continue"; //We create the message to display when the balls stop bouncing 
			String Sorted = "All sorted!"; //Message to display when the balls are all sorted
			//Create the Label of the messages and set their location on the screen
			GLabel ask_user = new GLabel(Ask_user, this.getWidth() - 10*Ask_user.length(),HEIGHT);
			sorted_message = new GLabel(Sorted,this.getWidth() - 10*Sorted.length(),HEIGHT);
			ask_user.setFont("ComicSansMS-20");//We set the font and size of the first message
			ask_user.setColor(Color.BLUE);//We set color of the first message
			sorted_message.setFont("ComicSansMS-20");//We set the font and size of the first message
			sorted_message.setColor(Color.BLUE);////We set the color of the first message
			add(ask_user);//We display the first message when the balls stop bouncing
			waitForClick();//We wait for the user to click
			remove(ask_user);//We remove the first message
			myTree.moveSort();//We sort the balls
			add(sorted_message); //We display the second message
		}
	}
}
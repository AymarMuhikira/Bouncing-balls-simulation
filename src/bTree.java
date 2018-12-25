import acm.graphics.GOval;

public class bTree {

	bNode root=null;//Nothing is in the bTree when we initialize it
	int running_balls = 0;//We create a variable to determine the number of gBalls still running
	public boolean quit = false;//We create a quit variable that allows us to quit a Run or
	//RunSimulation method, and it is initially false
	bNode found = null;//We create a node that will be assigned to clickedNode and returned by the
	//findNode method
	public void addNode(gBall thisBall) {//function called in bSim to add a Node
		root=rNode(root,thisBall);//We update the Nodes in the tree by calling the rNode method			
	}

	private bNode rNode(bNode root, gBall thisBall) {

		if (root==null) {//If the root is empty, we put the node that contains the gBall object
			bNode node = new bNode();
			node.aBall = thisBall;
			node.left = null;
			node.right = null;
			root=node;
			return root;
		}

		else if (thisBall.bSize < root.aBall.bSize) {//if the size of the ball is less than that of the current node, we put it to its left
			root.left = rNode(root.left,thisBall);
		}

		else {//else, we put it to the right
			root.right = rNode(root.right,thisBall);
		}
		return root;
	}

	public boolean isRunning() {//The method that returns true if at least one ball is still running
		running_balls = 0; //We initialize the number of balls running to 0
		inorder(); //We traverse the bTree in order (left node - root - right node) and if the ball at a node is still running, we increment the number of balls running
		if(running_balls != 0) { //We check to see if there is no ball running
			return true; //If that is the case, the isRunning() returns false
		}
		else {//Otherwise, it return true
			return false;
		}
	}

	public void inorder() {	//Method to traverse the bTree in order to update the number of balls still running		
		traverse_inorder(root);//We call traverse_inorder on the root of the bTree
	}

	private void traverse_inorder(bNode root) {//We go through the bTree 
		if (root.left != null) traverse_inorder(root.left);
		if(root.aBall.isRunning) running_balls++;//We increment the number of balls running by 1 if the ball at the current root is still running
		if (root.right != null) traverse_inorder(root.right);
	}

	public void moveSort() {//Method called in bSim to set the location of the balls according to their sizes
		root.aBall.sorted_x = 0;//This value will not be zero if this is not the first time we sort balls,
		//So we have to explicitly set it to zero
		move(root);//Call the method move
	}

	private void move(bNode root) {//Method that goes through the bTree in order (left node-root-right node) and sets the location of the ball
		if(root.left != null) move(root.left);
		root.aBall.Sort(2*root.aBall.bSize,2*root.aBall.bSize); //Call the Sort method in the gBall object
		if(root.right != null) move(root.right);
	}

	public void stopSim() {//Method called to stop every ball
		stop_inorder(root);
	}

	private void stop_inorder(bNode root) {//We go through the bTree 
		if (root.left != null) stop_inorder(root.left);
		root.aBall.isRunning = false;//We set the variable isRunning of the gBall object to false to stop its run() method
		if (root.right != null) stop_inorder(root.right);
	}

	public bNode findNode(GOval clickedOval) {//We find and return the clickedNode given the GOval object that was clicked on
		foundNode(root, clickedOval);//We use this method to assign the clicked node to the bNode found
		return found;
	}

	private void foundNode(bNode root, GOval clickedOval) {//This method traverses the bTree in order
		//and assigns a node to found if the ball in the node corresponds to the clicked ball (clickedOval)
		if (root.left != null) foundNode(root.left,clickedOval);
		if(root.aBall.myBall == clickedOval) {
			found = root;
		}
		if(root.right != null) foundNode(root.right,clickedOval);
	}
	public void update() {//We create an intermediate node interm that contains the whole bTree
		//We set the root to null and we replace (by restartNodes) the balls in interm in the bTree according to the updated sizes
		bNode interm = root;
		root = null;
		restartNodes(interm);
	}
	private void restartNodes(bNode root) {//We add the balls in the bTree and they are placed according to their new sizes
		if(root.left != null) restartNodes(root.left);
		addNode(root.aBall);
		if(root.right != null) restartNodes(root.right);
	}
}


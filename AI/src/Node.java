
public class Node {
	private Node father;
	private Node[] children;
	private int value;
	private Directions dir;
	private BoardTails state;
	private int depth;
	private int numberOfNode;
	/**
	 * Constructor
	 * @param father of the node
	 * @param a witch direction to go.
	 * @param currentState fathers state.
	 */
	public Node(Node father, Directions a,BoardTails currentState, int num) {
			this.children = new Node[4];
			this.father = father;
			this.dir = a;
			this.state = new BoardTails(currentState, dir);
			if(!this.state.boardExist()) this.value = -1;
			this.depth = 0;
			this.numberOfNode = num;
	}
	/**
	 * Const' for first node.
	 * @param value
	 * @param a
	 * @param currentState
	 */
	public Node(Directions a,BoardTails currentState) {
			this.children = new Node[4];
			this.dir = a;
			this.state = new BoardTails(currentState, dir);
			this.father = null;
			if(!this.state.boardExist()) this.value = -1;
			this.depth = 0;
			//else setValue(this.state);
	}
	/**
	 * set children's nodes
	 * @param a
	 * @param b
	 * @param c
	 * @param d
	 */
	public void setChildens(int n) {
		BoardTails copy = new BoardTails(this.state, Directions.NULL);
		this.children[0] = new Node(this, Directions.UP, copy, n + 1);
		this.children[1] = new Node( this,Directions.DOWN, copy, n+2);
		this.children[2] = new Node( this, Directions.LEFT, copy,n+3);
		this.children[3] = new Node( this, Directions.RIGHT,copy,n+4);
		//set children's depth
		for(int i = 0; i < 4; i++) {
		 this.children[i].setDepth(this.depth + 1);
		}
	}
	/**
	 * set values for each child.
	 * @param solution
	 */
	public void setValuesForChildren(BoardTails solution) {
		for(int i = 0; i < 4; i++) {
			this.children[i].setValue(solution);
		}
	}
	/**
	 * @return the current board.
	 */
	public BoardTails getNodeSateBoard() { return this.state; }
	/**
	 * @return father of the node.
	 */
	public Node getFather() {
		return this.father;
	}
	/**
	 * @return the string of the move that we made to get to this node.
	 */
	public String getDir() {
		return Directions.getLetter(this.dir);
	}
	/**
	 * @return childrens of the node.
	 */
	public Node[] getChildrens() { return this.children; }
	/**
	 * @return false if no node.
	 */
	public boolean exsist() {
		if(!this.state.boardExist()) this.value = -1;
		if(value == -1) return false;
		return true;
	}
	/**
	 * @return node's athers move.
	 */
	public String getFathersDir() {
		if(this.father == null) return "";
		return this.father.getFathersDir() + Directions.getLetter(this.dir);
	}
	/**
	 * @param d-set depth of this node.
	 */
	public void setDepth(int d) {
		this.depth = d;
	}
	/**
	 * @return depth of node.
	 */
	public int getDepth() { return this.depth; }
	/**
	 * set the cost.
	 * @param sateGoal
	 */
	public void setValue(BoardTails sateGoal) {
		if(this.value != -1)
			this.value = this.state.calculateBoardCost(sateGoal) + this.depth;
	}
	/**
	 * @return cost of the node
	 */
	public int getCostOfNode() { return this.value; }
	
	public int getNumberOfNode() {
		return numberOfNode;
	}
}

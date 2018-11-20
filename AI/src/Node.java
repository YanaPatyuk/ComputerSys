
public class Node {
	private Node father;
	private Node[] children;
	private int value;
	private Directions dir;
	private BoardTails state;
	private int depth;
	/**
	 * regular
	 * @param value
	 * @param father
	 * @param a
	 * @param currentState
	 */
	public Node(Node father, Directions a,BoardTails currentState) {
			this.children = new Node[4];
			this.father = father;
			this.dir = a;
			this.state = new BoardTails(currentState, dir);
			//if(this.state.equals(null)) this.value = -1;
			if(!this.state.boardExist()) this.value = -1;
			this.depth = 0;
			//else setValue(this.state);
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
	public void setChildens() {
		BoardTails copy = new BoardTails(this.state, Directions.NULL);
		this.children[0] = new Node(this, Directions.UP, copy);
		this.children[1] = new Node( this,Directions.DOWN, copy);
		this.children[2] = new Node( this, Directions.LEFT, copy);
		this.children[3] = new Node( this, Directions.RIGHT,copy);
		for(int i = 0; i < 4; i++) {
		 this.children[i].setDepth(this.depth + 1);
		}
	}
	
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
	
	public void setValue(BoardTails sateGoal) {
		if(this.value != -1)
			this.value = this.state.calculateBoardCost(sateGoal) + this.depth;
	}
	
	public int getCostOfNode() { return this.value; }
}

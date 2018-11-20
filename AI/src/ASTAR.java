
public class ASTAR implements Algorithm {
	private BoardTails board;
	private BoardTails solution;
	private TreeLIFO tree;
	private int numOfNodes;
	private int depth;
	
	public ASTAR(BoardTails board) {
		this.board = board;
		this.tree = new TreeLIFO(board);
		//create solution board to compare with
		int numberOfBloks= (board.getSize()*board.getSize());
		String []sol = new String[numberOfBloks];
		int i;
		for(i = 1; i < numberOfBloks; i++) {
			sol[i-1] = "" + Integer.toString(i);
		}
		sol[i-1] =  "0";
		this.solution = new BoardTails(sol, this.board.getSize());
	}
	@Override
	public String getTrackOfWay() {
		// TODO Auto-generated method stub
		return this.tree.getLastNode().getFathersDir();
	}

	@Override
	public int cost() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getNumberOfNodes() {
		// TODO Auto-generated method stub
		return this.numOfNodes;
	}

	@Override
	public void solveTheGame() {
		int depth  = 0;
		int currentDepth = 0;
		boolean run = true;
		Node current = null;
		Node []chliderns;
		this.numOfNodes = 0;
			while(this.tree.isEmpty()) {
				current = this.tree.getBestNode();
				this.numOfNodes++;
				if(current.getNodeSateBoard().isEquel(solution)) {
					break;
				}
				current.setChildens();
				current.setValuesForChildren(solution);
				chliderns = current.getChildrens();
				if(chliderns[3].exsist()) this.tree.addNode(chliderns[3]);
				if(chliderns[2].exsist()) this.tree.addNode(chliderns[2]);
				if(chliderns[1].exsist()) this.tree.addNode(chliderns[1]);
				if(chliderns[0].exsist()) this.tree.addNode(chliderns[0]);
				}
		this.tree.setLastNode(current);
	}

	@Override
	public String getFullAnswer() {
		return this.tree.getLastNode().getFathersDir()+ " " +
				getNumberOfNodes() + " " + this.tree.getLastNode().getCostOfNode();
		
	}

}

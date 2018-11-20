import org.omg.CORBA.Current;

public class BFS implements Algorithm {
	private BoardTails board;
	private BoardTails solution;
	private Tree tree;
	private int numOfNodes;
	public BFS(BoardTails board) {
		this.board = board;
		this.tree = new Tree(board);
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
		return this.tree.getLastNode().getFathersDir();
	}

	@Override
	public int cost() {
		return 0;
	}

	@Override
	public int getNumberOfNodes() {
		 return this.numOfNodes;
	}

	@Override
	public void solveTheGame() {
		Node current = null;
		Node []chliderns;
		this.numOfNodes = 0;
		while(this.tree.isEmpty()) {
			current = this.tree.popNode();
			this.numOfNodes++;
			if(current.getNodeSateBoard().isEquel(solution)) {
				break;
			}
			current.setChildens();
			chliderns = current.getChildrens();
			if(chliderns[0].exsist()) this.tree.addNode(chliderns[0]);
			if(chliderns[1].exsist()) this.tree.addNode(chliderns[1]);
			if(chliderns[2].exsist()) this.tree.addNode(chliderns[2]);
			if(chliderns[3].exsist()) this.tree.addNode(chliderns[3]);
		}
		this.tree.setLastNode(current);
	}

	@Override
	public String getFullAnswer() {
		return this.tree.getLastNode().getFathersDir()+ " " + getNumberOfNodes() + " 0";
	}

}

import java.util.Stack;

public class IDS extends AbstractAlgo {
	private  Stack<Node> openList;
	private Node head;

	public IDS(BoardTails board) {
		super(board);
		this.head = new Node(Directions.FIRST,board);
		this.head.setDepth(0);
		this.openList = new Stack<Node>();
		this.openList.push(this.head);
	}

	@Override
	public void solveTheGame() {
		int depth  = 0;
		int currentDepth = 0;
		Node current = null;
		Node []chliderns;
		this.numOfNodes = 0;
		while(true) {//we assume that we will find an answer.
			while(!this.openList.isEmpty()) {
				//get next node
				current = this.openList.pop();
				currentDepth = current.getDepth();
				this.numOfNodes++;
				//if this is the answer-stop
				if(current.getNodeSateBoard().isEquel(solution)) {
					break;
				}
				//if this node in the end of the depth-continue to next
				if(currentDepth == depth) continue;
				//create node's children and add them.
				current.setChildens(0);
				chliderns = current.getChildrens();
				if(chliderns[3].exsist()) this.openList.push(chliderns[3]);
				if(chliderns[2].exsist()) this.openList.push(chliderns[2]);
				if(chliderns[1].exsist()) this.openList.push(chliderns[1]);
				if(chliderns[0].exsist()) this.openList.push(chliderns[0]);
				}
			//if this is the solution-stop/
			if(current.getNodeSateBoard().isEquel(solution)) break;
			//reset the tree and add 1 for depth
			this.openList.clear();
			this.openList.push(head);
			depth++;
			this.numOfNodes = 0;
		}
		this.lastNode = current;
	}

	@Override
	public String getFullAnswer() {
		return this.lastNode.getFathersDir()+ " " +
						getNumberOfNodes() + " " + this.lastNode.getDepth();
	}

}

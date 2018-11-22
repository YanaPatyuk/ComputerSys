import java.util.LinkedList;
import java.util.Queue;

public class BFS extends AbstructAlgo {
	private Queue<Node> openList;
	private Node head;
	
	public BFS(BoardTails board) {
		super(board);
		//this.tree = new Tree(board);
		this.openList = new LinkedList<Node>();
		this.head = new Node(Directions.FIRST,board);
		this.head.setDepth(0);
		this.openList.add(this.head);
	}

	@Override
	public void solveTheGame() {
		Node current = null;
		Node []chliderns;
		this.numOfNodes = 0;
		while(!this.openList.isEmpty()) {
			//get next node
			current = this.openList.poll();
			this.numOfNodes++;
			if(current.getNodeSateBoard().isEquel(solution)) {
				break;
			}
			current.setChildens(0);
			chliderns = current.getChildrens();
			if(chliderns[0].exsist()) this.openList.add(chliderns[0]);
			if(chliderns[1].exsist()) this.openList.add(chliderns[1]);
			if(chliderns[2].exsist()) this.openList.add(chliderns[2]);
			if(chliderns[3].exsist()) this.openList.add(chliderns[3]);
		}
		this.lastNode = current;
	}

	@Override
	public String getFullAnswer() {
		return this.lastNode.getFathersDir()+ " " + getNumberOfNodes() + " 0";
	}

}

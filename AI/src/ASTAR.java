import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class ASTAR extends AbstructAlgo {
	//private TreeLIFO tree;
	private PriorityQueue<Node> openList;
	private Node head;
	/**
	 * A* algorithm
	 * @param board
	 */
	public ASTAR(BoardTails board) {
		super(board);
		//this.tree = new TreeLIFO(board);
		this.openList = new PriorityQueue<Node>(new Distance());
		this.head = new Node(Directions.FIRST,board);
		this.head.setDepth(0);
		this.openList.add(this.head);
		
	}
	@Override
	public void solveTheGame() {
		Node current = null;
		Node []chliderns;
		this.numOfNodes = 0;
		int n = 0;
		while(!this.openList.isEmpty()) {
			//get next Best node.
			//current = getBestNode();
			current = this.openList.remove();
			this.numOfNodes++;//update number nodes we poped.
			//if this node is the solution-end.
			if(current.getNodeSateBoard().isEquel(solution)) {
				break;
			}
			//set children nodes and add them to list.
			current.setChildens(n);
			current.setValuesForChildren(solution);
			chliderns = current.getChildrens();
			if(chliderns[0].exsist()) this.openList.add(chliderns[0]);
			if(chliderns[1].exsist()) this.openList.add(chliderns[1]);
			if(chliderns[2].exsist()) this.openList.add(chliderns[2]);
			if(chliderns[3].exsist()) this.openList.add(chliderns[3]);
			n+=4;
			}
		//set last node
		this.lastNode = current;
	}

	@Override
	public String getFullAnswer() {
		return lastNode.getFathersDir()+ " " +
				getNumberOfNodes() + " " + lastNode.getCostOfNode();
	}
	/**
	 * this class used to sort the open list.
	 * @author Yana Patyuk
	 *
	 */
	public class Distance implements Comparator<Node> {

		@Override
		public int compare(Node arg0, Node arg1) {
			// TODO Auto-generated method stub
			if(arg0.getCostOfNode() - arg1.getCostOfNode() == 0)
				return arg0.getNumberOfNode()-arg1.getNumberOfNode();
			return arg0.getCostOfNode() - arg1.getCostOfNode();
		}
	}
}



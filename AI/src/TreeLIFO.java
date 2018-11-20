import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class TreeLIFO/* extends Tree */ {

	private  Stack<Node> tree;
	BoardTails board;
	Queue<Node> openList;
	Node head;
	Node last;
	/**
	 * const'
	 * @param board
	 */
	public TreeLIFO(BoardTails board) {
		//super(board);
		this.board = board;
		this.head = new Node(Directions.FIRST,board);
		this.head.setDepth(0);
		this.tree = new Stack<Node>();
		this.tree.push(this.head);
	}
	
	/**
	 * @return if the open list empty(means-no nodes to work on)
	 */
	public boolean isEmpty() {
		return !(this.tree.isEmpty());
	} 
	
	/**
	 * @param node to add to the tree
	 */
	public void addNode(Node node) {
		this.tree.push(node);
	}
	/**
	 * @return next Node to work on.
	 */
	public Node popNode() {
		return this.tree.pop();
	}
	
	/**
	 * clear the list to start with only the head.
	 */
	public void resetTree() {
		this.tree.clear();
		this.tree.push(head);
	}
	
	public Node getBestNode() {
		int size = this.tree.size();
		Node current = this.tree.get(0);
		for(int i = 1; i < size; i++) {
			if(current.getCostOfNode() > this.tree.get(i).getCostOfNode()) {
				current = this.tree.get(i);
			}
		}
		this.tree.remove(current);
		return current;
	}

	public Node getLastNode() {return this.last; }

	public void setLastNode(Node current) {
		// TODO Auto-generated method stub
		this.last = current;

		
	}

}

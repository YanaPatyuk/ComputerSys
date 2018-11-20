import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Tree {
	BoardTails board;
	Queue<Node> openList;
	Node head;
	Node last;
	public Tree(BoardTails board) {
		this.board = board;
		this.openList = new LinkedList<Node>();
		this.head = new Node(Directions.FIRST,board);
		this.head.setDepth(0);
		this.openList.add(this.head);
	}
	/**
	 * @return if the open list empty(means-no nodes to work on)
	 */
	public boolean isEmpty() {
		return !(openList.isEmpty());
	}
	/**
	 * @param node to add to the tree
	 */
	public void addNode(Node node) {
		this.openList.add(node);
	}
	/**
	 * @return next Node to work on.
	 */
	public Node popNode() {
		return this.openList.remove();
	}
	/**
	 * @param n node of solution state
	 */
	public void setLastNode(Node n) {
		this.last = n;
	}
	/**
	 * @return last node.
	 */
	public Node getLastNode() {return this.last; }
	/**
	 * clear the list to start with only the head.
	 */
	public void resetTree() {
		this.openList.clear();
		this.openList.add(head);
	}
	
}

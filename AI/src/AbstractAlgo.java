/**
 * This Class is an abstract class for all algorithms.
 * it basic function container.
 * @author Yana Patyuk
 *
 */
public class AbstractAlgo implements Algorithm {
	protected BoardTails solution;//board to compate with 
	protected BoardTails board;//start board state
	protected int numOfNodes;//number of nodes we poped
	protected Node lastNode;//last node-solution.

/**
 * constructor-create a new board
 * and solution board to compare with.
 * @param board of the game.
 */
	public AbstractAlgo(BoardTails board) {
		this.board = board;//set the board
		//create another board for the solution.
		int numberOfBloks= (board.getSize()*board.getSize());
		String []sol = new String[numberOfBloks];
		int i;
		//create array of 0-8 numbers for solution board
		for(i = 1; i < numberOfBloks; i++) {
			sol[i-1] = "" + Integer.toString(i);
		}
		sol[i-1] =  "0";
		//create the board
		this.solution = new BoardTails(sol, board.getSize());
	}
	@Override
	public String getTrackOfWay() {
		return lastNode.getFathersDir();
	}

	@Override
	public int cost() {return 0;}

	@Override
	public int getNumberOfNodes() {
		 return this.numOfNodes;
	}

	@Override
	public void solveTheGame() {}

	@Override
	public String getFullAnswer() {
		return null;
	}

}

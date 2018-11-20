
public interface Algorithm {
	/**
	 * @return string of the directions to solution.
	 */
	String getTrackOfWay();
	/**
	 * @return the cost of the algorithm.
	 */
	int cost();
	/**
	 * @return number of nodes we used(i.e open list)
	 */
	int getNumberOfNodes();
	/**
	 * solve the game.
	 * @param board of the game.
	 */
	void solveTheGame();
	/**
	 * @return string line that includes that includes path,cost, and number of nodes.
	 */
	String getFullAnswer();
}

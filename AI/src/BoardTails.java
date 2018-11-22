
public class BoardTails {
	private Integer [][] board;
	private int size; 
	private int cost;
	/**
	 * constuctor for board.
	 * @param board array from file
	 * @param size size of board
	 */
	public BoardTails(String [] board, int size) {
		this.size = size;
		this.board = createBoard(board);//get board
	}
	/**
	 * copy and change constructor
	 * @param b board to copy
	 * @param change what direction.
	 */
	public BoardTails(BoardTails b, Directions change) {
		this.size = b.size;
		this.board = new Integer[size][size];
		copy(this.board,b.board,this.size);
		if(change.equals(Directions.FIRST)||change.equals(Directions.NULL)) return;
		if(!moveTail(change)) this.board = null;
	}
	/**
	 * @return size of board
	 */
	public int getSize() {return this.size; }
	/**
	 * @return if board exsist.
	 */
	public boolean boardExist() {
		if(this.board == null) return false;
		return true;
	}
	/**
	 * move the tail and update board
	 * if the move not legal-return false. any other case true
	 * @param move direction
	 */
	public boolean moveTail(Directions move) {
		for(int i = 0; i < size; i++) {
			for(int j = 0;j<size;j++) {
				if(this.board[i][j] == 0) {
					//if the move id legal continue 
					if(!isLegal(move,i,j)) return false;
					if(move.equals(Directions.UP)) {
						this.board[i][j] = this.board[i+1][j];
						this.board[i+1][j] = 0;
					}else if(move.equals(Directions.DOWN)) {
						this.board[i][j] = this.board[i-1][j];
						this.board[i-1][j] = 0;
					} else if(move.equals(Directions.LEFT)) {
						this.board[i][j] = this.board[i][j+1];
						this.board[i][j+1] = 0;
					}else if(move.equals(Directions.RIGHT)) {
						this.board[i][j] = this.board[i][j-1];
						this.board[i][j-1] = 0;
					}
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * check if the move is correct
	 * @param move
	 * @param i of 0 x place
	 * @param j of 0 y place.
	 * @return true if you can move.
	 */
	public boolean isLegal(Directions move, int i, int j) {
		if(this.board[i][j] == 0) {
			if(move.equals(Directions.UP)) {
				if((i+1) >= this.size) return false;
			} else if(move.equals(Directions.DOWN)) {
				if((i-1) < 0) return false;
			} else if(move.equals(Directions.LEFT)) {
				if((j+1) >= this.size) return false;
			}else if(move.equals(Directions.RIGHT)) {
				if((j-1) < 0) return false;
			}
			return true;
			}
		return false;
	}
	/**
	 * copy board b to board a
	 * @param a
	 * @param b
	 * @param size of board
	 */
	public void copy(Integer[][] a, Integer[][]b, int size) {
		for(int i = 0; i < size; i++) {
			for(int j = 0;j<size;j++) {
				a[i][j] = b[i][j];
			}
		}
	}
	/**
	 * transfer array into board game
	 * @param boardArray in one line.
	 * @return ne board game array
	 */
	public Integer [][] createBoard(String[] boardArray) {
		//create new board array
		Integer [][] newBoard = new Integer[size][size];
		int m = 0;
		//copy the array into the board-double array.
		for(int j=0;j<size;j++) {
			for(int z = 0;z < size; z++) {
				newBoard[j][z] = Integer.parseInt(boardArray[m]);
				m++;
			}
		}
		return newBoard;
	}
	/**
	 * print currntBoard
	 */
	public void printBoard() {
		System.out.print("\n");
		for(int j=0;j<size;j++) {
			for(int z = 0;z < size; z++) {
				System.out.print(board[j][z]);
			}
			System.out.print("\n");
		}
	}
	/**
	 * compare between this board to other board
	 * @param other board
	 * @return true if they are the same. flase otherwise
	 */
	public boolean isEquel(BoardTails other) {
		for(int j=0;j<size;j++) {
			for(int z = 0;z < size; z++) {
				if(!this.board[j][z].equals(other.board[j][z])) return false;
			}
		}
		return true;
	}
	/**
	 * @return cost of board-how many steps to solove.
	 */
	public int getCost() { return this.cost; }
	/**
	 * F function to calculate how many steps are may be till end
	 * @param other solution board(end state)
	 */
	public int calculateBoardCost(BoardTails other) {
		this.cost = 0;
		for(int j=0;j<size;j++) {
			for(int z = 0;z < size; z++) {
					if(this.board[j][z] != 0)//no need to calculate zero
						this.cost += getDistance(j,z, this.board[j][z]);
				}
		}
		return this.cost;
	}
	/**
	 * calculate distance between current cell to its palce on other board.
	 * first-calculate the place that the cell should be.
	 * @param x val current place
	 * @param y val current place
	 * @param i value of cell
	 * @return
	 */
	private int getDistance(int x, int y, Integer i) {
		int xCurrect = (i-1)/size;
		int yCorrect = (i - 1)%size;
		return Math.abs(xCurrect - x) + Math.abs(y-yCorrect);
	}

}

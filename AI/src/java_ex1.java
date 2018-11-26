import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class java_ex1 {

	public static void main(String[] args) {
    String fileName = "input.txt";//input file to read from.
    LineNumberReader in = null;
    InputStreamReader sReader = null;
    Algorithems algoType = null; 
    String []boardNumbers;
    BoardTails board = null;
    Algorithm algo = null;
    int boardSize = 0;
    try {
    	//open file
    	sReader = new InputStreamReader(new FileInputStream(fileName));
	    in =  new LineNumberReader(sReader);
	    //read each line. we assume there are 3 lines. 
	    for(int i = 0; i < 3; i++) {
	    	if(i == 0) {//first line-algo type
	    		algoType = Algorithems.getAlgo(in.readLine().trim());
	    	}else if(i == 1) {//second line-the size of the board
	    		boardSize= Integer.parseInt(in.readLine());
	    	}else if(i == 2) {//third line- array represent the board
	    		boardNumbers = in.readLine().trim().split("-");
	    		board = new BoardTails(boardNumbers, boardSize);
	    	}
	    }
	} catch ( IOException e) {//if something went wrong
		e.printStackTrace();
	    System.out.print("something went wrong:\n");
	}
    //create the algorithm from data we read
	if(algoType.equals(Algorithems.IDS)) {
		algo = new IDS(board);
	}else if(algoType.equals(Algorithems.BFS)) {
		algo = new BFS(board);
	}else if(algoType.equals(Algorithems.ASTAR)) {
		algo = new ASTAR(board);
	}
    //solve the board.
    algo.solveTheGame();
   // print(algoType, board,algo); //used for debug only
    
    //write answer to file
    writeToFile("output.txt", algo);
	}
	/**
	 * write to a file
	 * @param fileName string
	 * @param algo to get the answer.
	 */
	public static void writeToFile(String fileName, Algorithm algo) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
			bw.write(algo.getFullAnswer());
			// no need to close it.
			//bw.close();
		} catch (IOException e) {
		    System.out.print("something went wrong: cant save to file\n");
			e.printStackTrace();
		}
	}
	/**
	 * print data.
	 * Programmer use only for debug.
	 * @param algoType
	 * @param board
	 * @param algo
	 */
	private static void print(Algorithems algoType, BoardTails board, Algorithm algo) {
		System.out.print("this is type:" + algoType.toString() + "\n");
	    System.out.print("this is board size:" + board.getSize() + "\n");
	    System.out.print("this is board:");
	    board.printBoard();
	    System.out.print(algo.getFullAnswer() + "\n");
	}
	
}

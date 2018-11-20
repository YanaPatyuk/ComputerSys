import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;

public class Main {

	public static void main(String[] args) {
    String fileName = "C:\\Users\\Yana Patyuk\\eclipse-workspace\\AIex1\\src\\input.txt";//args[0]; 
    File f = new File("output.txt");
    LineNumberReader in = null;
    InputStreamReader sReader = null;
    Algorithems algoType = null; 
    String []boardNumbers;
    BoardTails board = null;
    Algorithm algo = null;
    int boardSize = 0;
    try {
    	sReader = new InputStreamReader(new FileInputStream(fileName));
	    in =  new LineNumberReader(sReader);
	    for(int i = 0; i < 3; i++) {
	    	if(i == 0) {
	    		algoType = Algorithems.getAlgo(in.readLine().trim());
	    	}else if(i == 1) {
	    		boardSize= Integer.parseInt(in.readLine());
	    	}else if(i == 2) {
	    		boardNumbers = in.readLine().trim().split("-");
	    		board = new BoardTails(boardNumbers, boardSize);
	    	}
	    	
	    }
	} catch ( IOException e) {
		e.printStackTrace();
	    System.out.print("something went wrong:\n");
	}
	if(algoType.equals(Algorithems.IDS)) {
		algo = new IDS(board);
	}else if(algoType.equals(Algorithems.BFS)) {
		algo = new BFS(board);
	}else if(algoType.equals(Algorithems.ASTAR)) {
		algo = new ASTAR(board);
	}
    System.out.print("this is type:" + algoType.toString() + "\n");
    System.out.print("this is board size:" + boardSize + "\n");
    System.out.print("this is board:");
    board.printBoard();
    algo.solveTheGame();
    algo.getFullAnswer();
    System.out.print(algo.getFullAnswer() + "\n");
	}
	/**
	 * write to a file
	 * @param fileName
	 * @param algo
	 */
	public void writeToFile(String fileName, Algorithm algo) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
			bw.write(algo.getFullAnswer());
			// no need to close it.
			//bw.close();

		} catch (IOException e) {
		    System.out.print("something went wrong: cant save to file\n");
			e.printStackTrace();
		}
	}
	
}

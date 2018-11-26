/*
 * Enum fot algorithem Types
 */
public enum Algorithems {
	IDS, BFS, ASTAR;
	public static Algorithems getAlgo(String type) {
		switch(Integer.parseInt(type) - 1) {
	 	case 0:
			return IDS;
	    case 1:
	      return BFS;
	    case 2:
	      return ASTAR;
	      }
	    return null;
	    }
}
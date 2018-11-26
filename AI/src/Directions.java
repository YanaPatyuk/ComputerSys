/**
 * @author Yana Patyuk
 * Enum for moves
 */
public enum Directions {
	FIRST,UP,DOWN,LEFT,RIGHT, NULL;
	
	public static String getLetter(Directions type) {
		if(type.equals(FIRST)) return null;
		else if(type.equals(UP)) return "U";
		else if(type.equals(DOWN)) return "D";
		else if(type.equals(LEFT)) return "L";
		else if(type.equals(RIGHT)) return "R";
		else return null;
		}
}

// This is a bird
import java.awt.*;

public class Bird extends Critter {

   private int moveCounter = 0;
   private int moveSize = 3; // size of box
   private String setFace = "^";

	public boolean eat() {
		return false;
	}

	// method comment goes here
	public Attack fight(String opponent) {
		if(opponent == "%") {
         return Attack.ROAR;
      } else
      return Attack.POUNCE;
	}

	// method comment goes here
	public Color getColor() {
		return Color.BLUE;
	}

	public Direction getMove() {
      if(moveCounter < moveSize) {
         moveCounter++;
         setFace = "^";
         return Direction.NORTH;
      } else if(moveCounter < moveSize * 2) {
         moveCounter++;
         setFace = ">";
         return Direction.EAST;
      } else if(moveCounter < moveSize * 3) {
         moveCounter++;
         setFace = "V";
         return Direction.SOUTH;
      } else if(moveCounter < moveSize * 4) {
         moveCounter++;
         setFace = "<";
         return Direction.WEST;
      } else {
         moveCounter = 1;
         setFace = "^";
         return Direction.NORTH;
      }
      
	}
   
	// method comment goes here
	public String toString() {
		return setFace;
	}
}
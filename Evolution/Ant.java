// This ant takes a bool walkSOUTH to determine how it moves (like a bishop)

import java.awt.*;

public class Ant extends Critter {
	// method comment goes here
   
   private int moveCounter = 0;
   private boolean walkSouth;
   
	public boolean eat() {
		return true;
	}
   
   public Ant (boolean walkSouth) {
      this.walkSouth = walkSouth;
   }

	public Attack fight(String opponent) {
		return Attack.SCRATCH;
	}

	public Color getColor() {
		return Color.RED;
	}

	public Direction getMove() {
		if(walkSouth == true) {
         if(moveCounter % 2 == 0) {
            moveCounter++;
            return Direction.SOUTH;
         } else {
         moveCounter++;
         return Direction.EAST;
         }
      } else {
         if(moveCounter % 2 == 0) {
            moveCounter++;
            return Direction.NORTH;
         } else {
         moveCounter++;
         return Direction.EAST;
         }
      }
	}

	public String toString() {
		return "%";
	}
}

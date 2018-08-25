// This is a hippo
import java.awt.*;
import java.util.*;

public class Hippo extends Critter {

   private Random rand = new Random();
   private int moveDir = rand.nextInt(4);
   private int moveCounter = 0;
   private int moveSize = 5; // size of segment
   private int hunger = 5;
   private boolean isFull = false;
   private String setFace = "" + hunger;

   public Hippo(int hunger) {
      this.hunger = hunger;
   }

	public boolean eat() {
      if(hunger > 0) {
         hunger--;
         setFace = "" + hunger;
         return true;
      }
		return false;
	}

	// method comment goes here
	public Attack fight(String opponent) {
		if(isFull == false) {
         return Attack.SCRATCH;
      } else
      return Attack.POUNCE;
	}

	// method comment goes here
	public Color getColor() {
		if(isFull = false) {
         return Color.GRAY;
      } else
         return Color.WHITE;
	}

	public Direction getMove() {
      if(moveCounter < moveSize) {
         if(moveDir == 0) {
            moveCounter++;
            return Direction.NORTH;
         } else if(moveDir == 1) {
            moveCounter++;
            return Direction.EAST;
         } else if(moveDir == 2) {
            moveCounter++;
            return Direction.SOUTH;
         } else {
            moveCounter++;
            return Direction.WEST;
         }
      } else {
           moveDir = rand.nextInt(4);
           moveCounter = 0;
           if(moveDir == 0) {
               moveCounter++;
               return Direction.NORTH;
            } else if(moveDir == 1) {
               moveCounter++;
               return Direction.EAST;
            } else if(moveDir == 2) {
               moveCounter++;
               return Direction.SOUTH;
            } else {
               moveCounter++;
               return Direction.WEST;
         }
      }
	}
   
	public String toString() {
		return setFace;
	}
}
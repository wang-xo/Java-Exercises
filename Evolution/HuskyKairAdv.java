// Kairsten Fay
// 5/27/2018
// CSE142
// TA: Rachel Imhof
// Assignment #8
//
// Huskies are displayed as an @.
// They fight based on known weaknesses (and thus are easily fooled), and otherwise choose a random attack.
// They prioritize hunting, mating, and eating, and run away from or ignore unknown opponents.
//
import java.awt.*;
import java.util.*;

public class HuskyKairAdv extends Critter {

   private Random rand;
   private Direction[] movesMap;
   private Attack[] attacksMap;
   private boolean mated;
   private String[] knownFoes;
   private Direction[] oppositeMovesMap;
   private String[] foesToScratch;
   private String[] foesToPounce;
   private String[] harmless;
   private int numSections;
   private int section;
   private int xNoise;
   private int yNoise;
   private int fightOrFlight;
   
   public HuskyKairAdv() { 
      rand = new Random();
      mated = false;
      movesMap = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
      oppositeMovesMap = new Direction[]{Direction.SOUTH, Direction.WEST, Direction.NORTH, Direction.EAST};
      attacksMap = new Attack[]{Attack.ROAR, Attack.POUNCE, Attack.SCRATCH};
      foesToScratch = new String[]{"v", "^", ">", "<", "0"};
      foesToPounce = new String[]{"1", "2", "3", "4", "5", "6", "7", "8"};
      harmless = new String[]{"@", " ", "."};
      knownFoes = new String[]{"S", "%", "V", "^", "<", ">", "0", "1", "2", "3", "4", "5", "6",
                               "7", "8"};
      numSections = 4;
      section = rand.nextInt(8) + 1;
      xNoise = this.getWidth() / 10;
      yNoise = this.getHeight() / 10;
      fightOrFlight = rand.nextInt(2);
   }
   
   //
   public boolean checkForFoes() { 
      boolean noFoesAround = true;
      for (int i=0; i < movesMap.length; i++) { 
         if (!getNeighbor(movesMap[i]).equals(" ") && !getNeighbor(movesMap[i]).equals(".") && 
             !getNeighbor(movesMap[i]).equals("S")) {
            noFoesAround = false;
         }
      }
      return noFoesAround;
   }

   
   //
   public boolean eat() {  // Could be tricked!
      return checkForFoes();
   }
   
   // 
   public Direction getMove() { 

      for (int i=0; i < movesMap.length; i++) { 
         
         // priority 2: hunt known foes (hopefully no tricks)
         for (int j=0; j < movesMap.length; j++) { 
            if (Arrays.asList(knownFoes).contains(getNeighbor(movesMap[i]))) {
               return movesMap[i];
            } else if (!Arrays.asList(harmless).contains(getNeighbor(movesMap[i]))) { 
               if (fightOrFlight == 0) { // flight
                  return oppositeMovesMap[i];
               } 
            }
         }
         
         // priority 1: mating
         if (getNeighbor(movesMap[i]).equals("@") && !checkForFoes()) {
            if (!mated) { 
               return movesMap[i];
            } else { // this addresses bug where huskies form a line or cluster
               return movesMap[rand.nextInt(4)];
            }
         }
         
         // priority 3: eating
         if (getNeighbor(movesMap[i]).equals(".")) {
            return movesMap[i]; 
         }
         
      }
      // if not in priorities: 
      // get into your quadrant. Hard-coded for four quadrants
      if (section == 1) { 
         if (this.getX() >= this.getWidth() - xNoise) { // go East to get back in 
            return Direction.EAST;
         } else if (this.getX() >= (this.getWidth() / numSections * 2) + xNoise) { // go West to get back in
            return Direction.WEST;
         } else if (this.getY() >= this.getHeight() - yNoise) { // go South to get back in
            return Direction.SOUTH;
         } else if (this.getY() >= (this.getHeight() / numSections * 2) + yNoise) { // go North to get back in 
            return Direction.NORTH;
         }


      } else if (section == 2) { 
         if (this.getX() >= this.getWidth() - xNoise) { // go East to get back in 
            return Direction.EAST;
         } else if (this.getX() >= (this.getWidth() / numSections * 2) + xNoise) { // go West
            return Direction.WEST;
         } else if (this.getY() <= (this.getHeight() / numSections * 2) - yNoise) { 
            return Direction.SOUTH;
         } else if (this.getY() <= yNoise) { 
            return Direction.NORTH;
         }

      } else if (section == 3) { 
         if (this.getX() <= (this.getWidth() / numSections * 2) - xNoise) { 
            return Direction.EAST;
         } else if (this.getX() <= xNoise) { 
            return Direction.WEST;
         } else if (this.getY() <= (this.getHeight() / numSections * 2) - yNoise) { 
            return Direction.SOUTH;
         } else if (this.getY() <= yNoise) { 
            return Direction.NORTH;
         }
      
      } else if (section == 4) { 
         if (this.getX() <= xNoise) { 
            return Direction.WEST;
         } else if (this.getX() <= (this.getWidth() / numSections * 2) - xNoise) { 
            return Direction.EAST;
         } else if (this.getY() >= (this.getHeight() - yNoise)) { 
            return Direction.SOUTH;
         } else if (this.getY() >= (this.getHeight() / numSections * 2) + yNoise) { 
            return Direction.NORTH;
         }
      } else { // free-floaters
         return movesMap[rand.nextInt(4)];
      }
         
      return movesMap[rand.nextInt(4)];

   }
   
	// method comment goes here
	public Attack fight(String opponent) {
      if (this.toString().equals("%")) { 
            return Attack.POUNCE;
      } else { 
         if (opponent.equals("%")) { 
            return Attack.ROAR;
         } else if (Arrays.asList(foesToScratch).contains(opponent)) { 
            return Attack.SCRATCH;
         } else if (opponent.equals("S")) { 
            return Attack.POUNCE;
         } else if (Arrays.asList(foesToPounce).contains(opponent)) { 
            return Attack.ROAR;
         } else {
            return attacksMap[rand.nextInt(3)];
         }
      }
	}

	// method comment goes here
	public Color getColor() {
      if (fightOrFlight == 0) { 
         return Color.GRAY;
      } else {
         return Color.WHITE;
      }
	}

	// method comment goes here
	public String toString() {  // fool other animals!!
      boolean knownType = false;
      for (int i=0; i < movesMap.length; i++) { 
         if (Arrays.asList(knownFoes).contains(getNeighbor(movesMap[i])) || 
             Arrays.asList(harmless).contains(getNeighbor(movesMap[i]))) { 
            knownType = true;
         }
      }
      
      if (knownType) { 
            return "@";
         } else { 
            // disguise 
            return "%";
         }
	}
   
   public void mateEnd() { 
      mated = true;
   }
      
}

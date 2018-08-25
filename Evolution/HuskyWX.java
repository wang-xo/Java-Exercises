// xHusky can sense orthagonal neighbors
// xcreate two type of husky: hunters and vegans (vary the proportion)
// xvegans should sense food and eat it, then search for food
// WIP - hunters should sense prey and eat it, else move to nearby asleep or mating animals and patrol isAwake
// WIP - set some cost/reward to mating
// xall huskies should use known attack or else vary attack
// WIP - travel in packs
// WIP - ignore dead huskies isAlive()
// WIP - maybe go higher ratio hunters first then swap to vegans?
// WIP - defer mating until threat level is lower, then mate for many vegans

/*
Here's the whole plan:
A pack will have hunters and vegans (killers of meat and vegetables, respectively)

Hunter Behavior:
Hunters will prioritize attacking adjacent targets and ignore food.
After many adversaries have been destroyed, allow Hunters to eat food.

Vegan Behavior:
Vegans will prioritize attacking adjacent food.
Vegans will defend but not actively attack.

The starting ratio is important because Vegans go down for 20 rounds per 1 food consumed. 
If the vegan were a hunter, could it make more than one kill in 20 rounds?
One kill is better than 1 point since the dead adversary cannot consume food.

With an initial population of 25 huskies, I can expect a final population of ~30-35 huskies net of deaths and 200 points by move 600.

*/

import java.awt.*;
import java.util.*;

public class HuskyWX extends Critter {
	
   private double time = 100.0;
   private double veganRatio = 3;
   Random rand = new Random();
   private int veganRand = rand.nextInt(10);
   private String huskyType;
   private int huskyMoves = 0;
   private int hunterSwitch = 150;
   private int veganSwitch = 150;

   private int moveDir = rand.nextInt(4);
   private int moveCounter = 0;
   private int moveSize = 2; // size of segment

   private String opponent = "";
   
   public HuskyWX() {
      // husky type selector by random using veganRatio
      veganRand = rand.nextInt(10);
      if(veganRand <= veganRatio) {
         huskyType = "Vegan";
      } else {
         huskyType = "Hunter";
      }      
   }
   
	public boolean eat() {
      // assume threat level falls over time, allow Hunters to eat
      if(huskyMoves > hunterSwitch) {
         return true;
      }
      
      // in beginning, only Vegans eat
      if(huskyType.equals("Vegan")) {
         return true;
      } else {
   		return false;
      }
	}

	public Attack fight(String opponent) {
		this.opponent = getOpponent(opponent);
      if(this.opponent.equals("Ant")) {
         return Attack.ROAR;
      } else if (this.opponent.equals("Bird")) {
         return Attack.SCRATCH;
      } else if (this.opponent.equals("HippoH")) {    // hungry hippo
         return Attack.ROAR;
      } else if (this.opponent.equals("HippoF")) {    // full hippo
         return Attack.SCRATCH;
      } else if (this.opponent.equals("Unknown")) {   // all others, including blanks 
         return Attack.POUNCE;
      } else
         return Attack.ROAR;                          // probably unused
	}

	public Color getColor() {
      if(huskyType.equals("Vegan")) {
         return Color.GREEN;     
      } else {
         return Color.RED;
      }
	}

	public Direction getMove() {
      // getMove is called once per husky, use this to track time
      tickCounters();
      
      // change behavior after moves
      if(huskyType.equals("Hunter") && huskyMoves < hunterSwitch){
         return findPrey();
      } else if (huskyType.equals("Vegan") && huskyMoves > veganSwitch) {
         return findPrey();
      } else  
         return findFood();
	}
   
   public void tickCounters() {
      // track number of moves as proxy for time
      huskyMoves += 1;
      
      // steeply advance veganRatio to produce more Vegans
      veganRatio = (time * 0.03); 
      time += 50.0;
   }
   
   public Direction findFood() {
      // eat or move
		if(getNeighbor(Critter.Direction.EAST) == ".") {
         return Direction.EAST;
      } else if(getNeighbor(Critter.Direction.SOUTH) == ".") {
         return Direction.SOUTH;
		} else if(getNeighbor(Critter.Direction.WEST) == ".") {
         return Direction.WEST;
      } else if(getNeighbor(Critter.Direction.NORTH) == ".") {
         return Direction.NORTH;
      }
      return mosey(7); // if no target, then mosey
   }

   public Direction findPrey() {
      // fight or move
      String eastside = getOpponent(getNeighbor(Critter.Direction.EAST));
      String southside = getOpponent(getNeighbor(Critter.Direction.SOUTH));
      String westside = getOpponent(getNeighbor(Critter.Direction.WEST));
      String northside = getOpponent(getNeighbor(Critter.Direction.NORTH));
      
      /* debug text
      System.out.println("eastside: " + eastside);
      System.out.println("southside: " + southside);
      System.out.println("westside: " + westside);
      System.out.println("northside: " + northside);
      */
      
		if(eastside.equals("Ant") || eastside.equals("Bird") || eastside.equals("HippoH") || eastside.equals("HippoF")) {
         return Direction.EAST;
      } else if(southside.equals("Ant") || southside.equals("Bird") || southside.equals("HippoH") || southside.equals("HippoF")) {
         return Direction.SOUTH;
		} else if(westside.equals("Ant") || westside.equals("Bird") || westside.equals("HippoH") || westside.equals("HippoF")) {
         return Direction.WEST;
      } else if(northside.equals("Ant") || northside.equals("Bird") || northside.equals("HippoH") || northside.equals("HippoF")) {
         return Direction.NORTH;
      } else
         return mosey(5); // if no target, then mosey
         // minimum three to find new target zone
   }

   public Direction mosey(int moveSize) {
      // this is ugly
      // moveCounter increments in here
      // moveSize is how many steps before direction change
      
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
           moveDir = rand.nextInt(4);     // choose random direction but not stay still
           moveCounter = 0;               // this is the awkward reset to the move counter
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

	public Direction guard(int moveCounter, int moveSize) {
      // NOT USED
      // should find mating or sleep ally and patrol grid around the vulnerable member
      // center around mating couple, 2 steps aways, then 2 steps per side
      // need to reinitialize moveCounter
      if(moveCounter < moveSize) {
         moveCounter++;
         return Direction.NORTH;
      } else if(moveCounter < moveSize * 2) {
         moveCounter++;
         return Direction.EAST;
      } else if(moveCounter < moveSize * 3) {
         moveCounter++;
         return Direction.SOUTH;
      } else if(moveCounter < moveSize * 4) {
         moveCounter++;
         return Direction.WEST;
      } else {
         moveCounter = 1;
         return Direction.NORTH;
      }
      
	}
   
   public String getOpponent(String opponent) {
      // return the friendly name of the enemy
      // birds can be attacked as one class
      // hippos require different attack based on hunger state
 		if(opponent.equals("%")) {
         return "Ant";
      } else if (opponent.equals("^") || opponent.equals(">") || opponent.equals("<") || opponent.equals("V")) {
         return "Bird";
      } else if (opponent.charAt(0) + 0 >= 49 && opponent.charAt(0) + 0 <= 58) {
         // convert the first character of the opponent name to integer then test
         // assume hippo will be integer [1,9]
         return "HippoH";
      } else if (opponent.charAt(0) + 0 == 48) {
         // hippo is full when integer 0
         return "HippoF";
      } else
         return "Unknown";
   }
   
	public String toString() {
      /*
      NOT USED
      Attempt to emulate weaker animal and provoke the wrong attack
      
      if(opponent.length() > 0){
         this.opponent = getOpponent(opponent);
         if(this.opponent == "Unknown") {
            huskyType = "Hunter";
            return "~¢.¢£";
         }
      }
      */
      if(huskyType.equals("Vegan")) {
         return "~ÇOÇf";
      } else
         return "~¢.¢£";
	}
   
   public void win() {
      // System.out.println("You have won a fight!");
   }
}
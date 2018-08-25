// CSE 142 Critters
// Authors: Marty Stepp and Stuart Reges
//
// Stone objects are displayed as S and always stay put.
// They always pick ROAR in a fight.
//
import java.awt.*;

public class Human {
   private int height;
   private double strength;
   private boolean honor;
   private String name;
   
   public Human() {
      name = "";
      height = 0;
      strength = 0.0;
      honor = true;
   } 
   
   public int getHeight() { 
      return height;
   }
   
   public void setHeight(int height) { 
      this.height = height;
      
   }
}
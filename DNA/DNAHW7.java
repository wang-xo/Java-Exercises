import java.io.*;
import java.util.*;

public class DNAHW7 {
   
   public static final int MIN_CODON = 5;
   public static final int MIN_CG_MASS = 30;
   public static final int UNIQ_NUC = 4;
   public static final int NUC_PER_CODON = 3;
   
   public static void main(String[] args) throws FileNotFoundException {
      Scanner console = new Scanner(System.in);
      PrintStream sout = System.out;

      // intro - out of main
      sout.println("This program reports information about DNA");
      sout.println("nucleotide sequences that may encode proteins.");

      // get file names - out of main (you can do this in one method)
      // don't use main for reading lines or text inputs
      sout.print("Input file name? ");
      String inFile = console.nextLine();
      Scanner input = new Scanner(new File(inFile));
      sout.print("Output file name? ");
      String outFile = console.nextLine();
      PrintStream output = new PrintStream(new File(outFile));

      // check unused parameters and initializations
      String sequence;
      int[] nucCounts= new int[UNIQ_NUC];
      double[] massCalc = new double[UNIQ_NUC];
      double[] massRatio = new double[UNIQ_NUC];
      double massSum = 0;
      String[] codons;
      String[] tempCodons = {};
      String isProtein;
      
      while (input.hasNextLine()) {
         sequence = readProtein(output, input);
         nucCounts = readNucs(sout, sequence);
         massSum = readMass(sout, sequence, massRatio);
         codons = readCodons(sout, sequence, tempCodons);
         isProtein = testProtein(sout, codons, massRatio);
         
         // print to console
         // display(sout, sequence, nucCounts, massSum, massRatio, codons, isProtein);
         
         // write to file
         write(output, sequence, nucCounts, massSum, massRatio, codons, isProtein);

      }      
   }
   
   public static String readProtein(PrintStream output, Scanner input) {
      // readProtein
      // read two lines from input
      // write to file the name
      // pass in the console and input
      // later on will want to return an array for the second line
      // only one scanner is created, this preserves the index marker

      // you are alrady in the correct hasNextLine assertion, why do it again
      if (input.hasNextLine()) {
         String name = input.nextLine();
         output.println("Region Name: " + name);
         if (input.hasNextLine()) {
            String sequence = input.nextLine();
            sequence = sequence.toUpperCase();
            return sequence;
            }
         }
      return "";
   }

   public static int[] readNucs(PrintStream sout, String sequence) {
      // nucCounts
      // pass in the raw array and a blank array[3]
      // count for each one, write to blank array
      
      // array mapping, use an intial ACGT array
      // use else if to escape the whole loop on one success 
      
      int[] nucs = new int[UNIQ_NUC];
      for (int i = 0; i < sequence.length(); i++) {
        if (sequence.toUpperCase().charAt(i) == 'A'){
            nucs[0] += 1;
        }
        if (sequence.toUpperCase().charAt(i) == 'C'){
            nucs[1] += 1;
        }
        if (sequence.toUpperCase().charAt(i) == 'G'){
            nucs[2] += 1;
        }
        if (sequence.toUpperCase().charAt(i) == 'T'){
            nucs[3] += 1;
        }
      }
      return nucs;
   }
   
   public static double readMass(PrintStream sout, String sequence, double[] massRatio) {
      // massCalc
      // pass in the nucCounts results and a blank array[3]
      // calc for each and total, write to blank array
      double[] mass = new double[UNIQ_NUC];
      double[] masses = {135.128, 111.103, 151.128, 125.107, 100.0}; // make this a constant
      double massSum = 0;
      
      for (int i = 0; i < sequence.length(); i++) {
        if (sequence.charAt(i) == 'A'){
            mass[0] += masses[0];
            massSum += masses[0];
        } else if (sequence.charAt(i) == 'C'){
            mass[1] += masses[1];
            massSum += masses[1];
        } else if (sequence.charAt(i) == 'G'){
            mass[2] += masses[2];
            massSum += masses[2];
        } else if (sequence.charAt(i) == 'T'){
            mass[3] += masses[3];
            massSum += masses[3];
        } else {
            massSum += masses[4];
        }
      
      }
      for (int i = 0; i < UNIQ_NUC; i++) {
         massRatio[i] = Math.round((mass[i] / massSum) * 1000) / 10.0;
      }
      return massSum;
   }
   
   public static String[] readCodons(PrintStream sout, String sequence, String[] tempCodons) {
      // Codons
      // pass in the raw and a blank array[raw/3 - 1]
      // concat each triplet and write to blank array
      sequence = sequence.replace("-", "");            
      tempCodons = new String[sequence.length()/3];
      for (int i = 0; i < sequence.length() / 3; i++) {
            tempCodons[i] = sequence.substring(i * 3, (i*3)+3);
      }
      return tempCodons;
   }

   public static String testProtein(PrintStream sout, String[] codons, double[] massRatio) {
      // testProtein
      // string.equals(ATG) for first element
      // string.equals(TAA or TAG or TGA) for final element
      // return true or false
      
      // can test all conditions on one IF
      if (codons[0].equals("ATG") && codons.length > 4) {
         if(codons[codons.length-1].equals("TAG") || codons[codons.length-1].equals("TAA") || 
            codons[codons.length-1].equals("TGA")) {
            if( (massRatio[1] + massRatio[2]) > 30) {
               return "YES";
            }
         }
      }
      return "NO";
   }
   
   public static void display(PrintStream sout, String sequence, int[] nucCounts, double massSum, double[] massRatio, String[] codons, String isProtein) {
      // Display
      // pass in the nucCounts array, massCalc array, Codons array, testProtein binary
      // sout it first the figure out the printstream.
      // probably need to pass in the output file name
      sout.println("Nucleotides: " + sequence);
      sout.println("Nuc. Counts: " + Arrays.toString(nucCounts));
      sout.println("Total Mass%: " + Arrays.toString(massRatio) + " of " + (Math.round(massSum*10))/10.0);
      sout.println("Codons List: " + Arrays.toString(codons));
      sout.println("Is Protein?: " + isProtein);
      sout.println();
   }

   public static void write(PrintStream output, String sequence, int[] nucCounts, double massSum, 
                            double[] massRatio, String[] codons, String isProtein) {
      // Display
      // pass in the nucCounts array, massCalc array, Codons array, testProtein binary
      // sout it first the figure out the printstream.
      // probably need to pass in the output file name
      output.println("Nucleotides: " + sequence);
      output.println("Nuc. Counts: " + Arrays.toString(nucCounts));
      output.println("Total Mass%: " + Arrays.toString(massRatio) + " of " + (Math.round(massSum*10))/10.0);
      output.println("Codons List: " + Arrays.toString(codons));
      output.println("Is Protein?: " + isProtein);
      output.println();
   }
   
}
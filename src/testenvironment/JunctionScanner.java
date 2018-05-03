package testenvironment;

import datastructures.Junction;
import datastructures.JunctionType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

// Xmin = -20023.816080184883 Ymin = -14666.86116233825 Xmax = 20032.054293303245 Ymax = 12330.23285786473

public class JunctionScanner { //JunctionScanner is used to fetch the data from the junctions.csv sheet and retun it as an ArrayList
    public static ArrayList<Junction> init(){
        ArrayList<Junction> junctions = new ArrayList<>();
        try(Scanner p = new Scanner(new File(System.getProperty("user.dir") + "/data/junctions.csv"), "UTF-8")) {
            p.useDelimiter("[;\\n]");
            while(p.hasNext()){
                junctions.add(new Junction(p.next(),p.nextDouble(),p.nextDouble(),(p.next().equals("AIRPORT"))? JunctionType.AIRPORT:JunctionType.TRAINSTATION));
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("junctions.csv could not be found.");
            System.exit(1);
        }
        return junctions;
    }
}

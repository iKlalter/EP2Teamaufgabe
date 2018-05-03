package testenvironment;

import datastructures.*;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestCalls {
    enum DataType {COMPARE_DATA_STRUCTURES,PRIMITIVE_DATA_TYPE,POINT_QUAD_TREE,ANCHOR_DISTANCE,TWO_D_TREE}
    //@TODO Neuen Datentyp bitte im enum, im String und im switch (bzw. Array) unten hinzufügen, ansonsten sollte alles funktionieren.

    public static void start() {
        System.out.println("Please select a Data Structure to be tested:\n(0) Compare data structures\n(1) Primitive Data Type (ArrayList)" +
                "\n(2) Point-QuadTree \n(3) Anchor Point Distances\n(4) 2D-Grid");
        Scanner s = new Scanner(System.in);
        DataType[] d = DataType.values();
        DataType choice;
        if (s.hasNextInt()) {
            choice = d[s.nextInt()];
            if(choice != DataType.COMPARE_DATA_STRUCTURES) System.out.println("Running test for " + choice + "\n");
            else System.out.println("Comparing all data structures");
            switch (choice){
                case COMPARE_DATA_STRUCTURES:
                    compareDataStructures(new DataStructure[]{new PrimitiveDataType(),new PointQuadTree(),new AnchorDistance(),new TwoDGrid()});
                case PRIMITIVE_DATA_TYPE:
                    singleDataStructureTest(new PrimitiveDataType());
                    break;
                case POINT_QUAD_TREE:
                    singleDataStructureTest(new PointQuadTree());
                    break;
                case ANCHOR_DISTANCE:
                    singleDataStructureTest(new AnchorDistance());
                    break;
                case TWO_D_TREE:
                    singleDataStructureTest(new TwoDGrid());
                    break;
            }
        } else {
            s.next();
            System.out.println("Please enter a valid number!");
            start();
        }
    }

    private static void singleDataStructureTest(DataStructure dataStructure){
        Pattern junctionP = Pattern.compile("Junctions less than [0-9.]+ units from x=[0-9.]* y=[0-9.]+");
        Pattern aiportP = Pattern.compile("Airports with at least [0-9]+ Trainstations less than [0-9.]+ units away");
        Pattern quitP = Pattern.compile("quit");
        Pattern changeP = Pattern.compile("change");


        String name = dataStructure.getClass().getName();
        name = name.substring(15);
        long t = System.currentTimeMillis();
        DataStructure ds;
        ds = dataStructure.init();
        System.out.println("Initialization of " + name + " took " + (System.currentTimeMillis() - t) + " ms");
        Scanner s = new Scanner(System.in);
        String input;
        System.out.println("Please enter a request for the" + name + ":");
        System.out.println("Possible requests are for example: \n\"Junctions less than 100.0 units from x=1818.54657 y=5813.29982\" or \n\"Airports with at least 20 Trainstations less than 15.0 units away\"" +
                "\nThe application can be terminated using \"quit\" and the data structure can be changed using \"change\"");
        while(s.hasNextLine()){
            input = s.nextLine();
            Matcher junctionM = junctionP.matcher(input);
            Matcher airportM = aiportP.matcher(input);
            Matcher quitM = quitP.matcher(input);
            Matcher changeM = changeP.matcher(input);
            Pattern numberP = Pattern.compile("[0-9.]+");
            Matcher numberM = numberP.matcher(input);
            if(junctionM.matches()){
                double r,x,y;
                numberM.find();
                r = Double.parseDouble(numberM.group());
                numberM.find();
                x = Double.parseDouble(numberM.group());
                numberM.find();
                y = Double.parseDouble(numberM.group());
                findJunctions(ds,x,y,r);
            } else if (airportM.matches()){
                numberM.find();
                int numberOfStations = Integer.parseInt(numberM.group());
                numberM.find();
                double r = Double.parseDouble(numberM.group());
                findAirports(ds,numberOfStations,r);
            } else if (quitM.matches()) {
                break;
            } else if (changeM.matches()){
                System.out.println();
                start();
                break;
            } else {
                System.out.println("Please enter a valid request:");
            }
            System.out.println("Please enter another request for the" + name + " or type \"quit\" or \"change\":");
        }
    }

    private static void findJunctions(DataStructure ds, double x,double y,double r){
        String name = ds.getClass().getName();
        name = name.substring(15);
        long t = System.currentTimeMillis();
        int count[] = ds.findJunctions(x,y,r);
        t = System.currentTimeMillis() - t;
        System.out.println("  > Airports: " + count[0] + "\t\tTrainstations: " + count[1]);
        System.out.println("The request took " + t + "ms for the " + name + " to process");
    }

    private static void findAirports(DataStructure ds, int numberOfStations, double r){
        String name = ds.getClass().getName();
        name = name.substring(15);
        long t = System.currentTimeMillis();
        int airports = ds.findAirports(numberOfStations,r);
        t = System.currentTimeMillis() - t;
        System.out.println("  > " + airports);
        System.out.println("The request took " + t + "ms for the " + name + " to process");
    }

    private static void compareDataStructures(DataStructure[] dataStructures){
        Pattern junctionP = Pattern.compile("Junctions less than [0-9.]+ units from x=[0-9.]* y=[0-9.]+");
        Pattern aiportP = Pattern.compile("Airports with at least [0-9]+ Trainstations less than [0-9.]+ units away");
        Pattern quitP = Pattern.compile("quit");
        Pattern changeP = Pattern.compile("change");
        String[] name = new String[dataStructures.length];
        for(int i = 0; i< dataStructures.length; i++){
            name[i] = dataStructures[i].getClass().getName().substring(15);
        }
        long t = System.currentTimeMillis();

        for(int i = 0; i< dataStructures.length; i++){
            dataStructures[i] = dataStructures[i].init();
            System.out.println("Initialization of " + name[i] + " took " + (System.currentTimeMillis() - t) + " ms");
            t = System.currentTimeMillis();
        }

        Scanner s = new Scanner(System.in);
        String input;
        System.out.println("Please enter a request to compare the data structures:");
        System.out.println("Possible requests are for example: \n\"Junctions less than 100.0 units from x=1818.54657 y=5813.29982\" or \n\"Airports with at least 20 Trainstations less than 15.0 units away\"" +
                "\nThe application can be terminated using \"quit\" and the data structure can be changed using \"change\"");
        while(s.hasNextLine()){
            input = s.nextLine();
            Matcher junctionM = junctionP.matcher(input);
            Matcher airportM = aiportP.matcher(input);
            Matcher quitM = quitP.matcher(input);
            Matcher changeM = changeP.matcher(input);
            Pattern numberP = Pattern.compile("[0-9.]+");
            Matcher numberM = numberP.matcher(input);
            if(junctionM.matches()){
                double r,x,y;
                numberM.find();
                r = Double.parseDouble(numberM.group());
                numberM.find();
                x = Double.parseDouble(numberM.group());
                numberM.find();
                y = Double.parseDouble(numberM.group());
                for(int i = 0; i< dataStructures.length; i++){
                    System.out.println(name[i] + ":");
                    findJunctions (dataStructures[i],x,y,r);
                }
            } else if (airportM.matches()){
                numberM.find();
                int numberOfStations = Integer.parseInt(numberM.group());
                numberM.find();
                double r = Double.parseDouble(numberM.group());
                for(int i = 0; i< dataStructures.length; i++) {
                    System.out.println(name[i] + ":");
                    findAirports(dataStructures[i], numberOfStations, r);
                }
            } else if (quitM.matches()) {
                return;
            } else if (changeM.matches()){
                System.out.println();
                start();
                return;
            } else {
                System.out.println("Please enter a valid request:");
            }
            System.out.println("Please enter another request or type \"quit\" or \"change\":");
        }
    }
}



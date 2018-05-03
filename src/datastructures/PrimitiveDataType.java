package datastructures;

import testenvironment.JunctionScanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is a primitive implementation of depicting airports and train stations across the world. The main purpose of
 * this class is a comparison in runtime and efficiency to different data structures.
 */

public class PrimitiveDataType implements DataStructure{
    private ArrayList<Junction> junctions;

    /**
     * Initializes a new datastructures.PrimitiveDataType object with data from a given junctions.csv file, containing all airports
     * and train stations across the world, as well as their names and coordinates.
     * @return initialized datastructures.PrimitiveDataType object with ArrayList<Junctions> containing the data from the .csv-file.
     */
    public PrimitiveDataType init() {
        PrimitiveDataType prim = new PrimitiveDataType();
        prim.junctions = JunctionScanner.init();
        return prim;
    }

    public PrimitiveDataType(){
        junctions = new ArrayList<>();
    }

    /**
     * Returns an integer array of size 2 with a count of all train stations and airports in a given radius r of a point
     * in the euclidean plane.
     * @param x double  the x coordinate of the point
     * @param y double  the y coordinate of the point
     * @param r double  the radius to be searched in
     * @return  int[]   Integer array of size 2 containing counted number of junctions: [#AIPORTS,#TRAINSTATIONS]
     */

    public int[] findJunctions(double x, double y, double r){
        int[] count = new int[2];
        for(Junction j:junctions) {
            double xDist = Math.abs(x-j.getX());
            double yDist = Math.abs(y-j.getY());
            double dist = Math.sqrt(xDist*xDist + yDist*yDist);
            if(dist <= r){
                if (j.getType()==JunctionType.AIRPORT) {
                    count[0]++;
                } else {
                    count[1]++;
                }
            }
        }
        return count;
    }

    /**
     * Returns the number of airports of the given data set which have n or more train stations in a radius
     * r around them using the findJunctions() method.
     * @param   n       number of train stations to be searched for
     * @param   radius  radius of an airport to be searched in
     * @return  int     count of airports with n or more train stations in a radius r around them.
     */

    public int findAirports(int n, double radius){
        int count = 0;
        for(Junction j:junctions){
            if(j.getType()==JunctionType.AIRPORT){
                int[] temp = findJunctions(j.getX(),j.getY(),radius);
                if(temp[1]>=n){
                    count++;
                }
            }

        }

        return count;
    }
}

package datastructures;

import testenvironment.JunctionScanner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class allows to depict given data containing airports and train stations as well as their coordinates and names
 * as a datastructures.PointQuadTree. PointQuadTrees subdivide the space around them in four regions, while contrary to the
 * datastructures.RegionQuadTree using the coordinates of an added point as division points.
 *
 * Each datastructures.PointQuadTree consists of PointQuadTreeNodes, with datastructures.PointQuadTree objects only referencing to the root of the
 * tree. PointQuadTreeNodes have four references to PointQuadTreeNodes North-West(NW), North-East(NE), South-West(SW)
 * and South-East(SE) of them. the coordinates are used to determine where teh references are made to when a new node
 * is added to the datastructures.PointQuadTree.
 */

public class PointQuadTree implements DataStructure{
    private PointQuadTreeNode root;
    private ArrayList<Junction> junctions;

    /**
     * Initializes a new datastructures.PointQuadTree object with data from a given junctions.csv file, containing all airports
     * and train stations across the world, as well as their names and coordinates.
     * @return initialized datastructures.PointQuadTree object with ArrayList<Junctions> containing the data from the .csv-file.
     */
    public PointQuadTree init(){
        PointQuadTree quad = new PointQuadTree();

        quad.junctions = JunctionScanner.init();
        for (Junction j:quad.junctions) {
            quad.add(j);
        }

        return quad;
    }

    /**
     * Adds a datastructures.Junction to the QuadTree, calling the .add method of datastructures.PointQuadTreeNode
     * @param junction datastructures.Junction to be added to the datastructures.PointQuadTree
     */

    public void add(Junction junction){
        if(root == null) this.root = new PointQuadTreeNode(junction);
        else root.add(junction);
    }

    public PointQuadTree(){
        junctions = new ArrayList<>();
    }

    /**
     * Returns an integer array of size 2 with a count of all train stations and airports in a given radius r of a point
     * in the euclidean plane. In this case findJunctions is calling the findJunctions() method of datastructures.PointQuadTreeNode to
     * search the whole tree.
     * @param x double  the x coordinate of the point
     * @param y double  the y coordinate of the point
     * @param r double  the radius to be searched in
     * @return  int[]   Integer array of size 2 containing counted number of junctions: [#AIPORTS,#TRAINSTATIONS]
     */
    public int[] findJunctions(double x, double y, double r){
        int[] count = new int[2];
        if(root!=null) root.findJunctions(x,y,r,count);
        return count;
    }

    public int findAirports(int numberOfStations, double radius){
        int count = 0;
        for(Junction j:junctions){
            if((j.getType() == JunctionType.AIRPORT)){
                int dist[] = findJunctions(j.getX(),j.getY(),radius);
                if( dist[1] >= numberOfStations) count++;
            }
        }
        return count;
    }
}

class PointQuadTreeNode {
    private PointQuadTreeNode NW,NE,SW,SE;
    private Junction value;

    /**
     * Adds a given datastructures.Junction to the datastructures.PointQuadTree, by comparing the coordinates with the division point and thus
     * choosing which subdivision the point is to be added in.
     * @param junction datastructures.Junction to be added
     */

    public void add(Junction junction){
        if (this.value == null) {
            this.value = junction;
        } else {
            if( junction.getX() < value.getX()){
                if(junction.getY() < value.getY()) {
                    if(SW == null) SW = new PointQuadTreeNode(junction);
                    else SW.add(junction);
                } else {
                    if(NW == null) NW = new PointQuadTreeNode(junction);
                    else NW.add(junction);
                }
            } else {
                if(junction.getY() < value.getY()) {
                    if(SE == null) SE = new PointQuadTreeNode(junction);
                    else SE.add(junction);
                } else {
                    if(NE == null) NE = new PointQuadTreeNode(junction);
                    else NE.add(junction);
                }
            }
        }
    }

    PointQuadTreeNode(Junction junction){
        value = junction;
    }

    /**
     * Searches for junctions in the radius of a given point in the euclidean plane and writes them to an integer array
     *
     * Because of the nature of this data structure, the method checks if the bounding box with radius r around the
     * point intersects the subdivision lines, and thus checks the corresponding subdivisions if this is the case.
     * As such, the method eliminates unneeded recursive calls, and is distinctively faster than a primitive
     * implementation.
     *
     * @param x     x-coordinate of the given point
     * @param y     y-coordinate of the given point
     * @param r     radius around the given point
     * @param count integer array to be written to
     */
    public void findJunctions(double x, double y, double r, int[] count) {
        double xDist = Math.abs(value.getX() - x);
        double yDist = Math.abs(value.getY() - y);
        if (Math.sqrt(xDist * xDist + yDist * yDist) <= r) {
            if ((value.getType()==JunctionType.AIRPORT)) {
                count[0]++;
            } else {
                count[1]++;
            }
        }
        if (x < value.getX()) { //Radius intersection check
            if (y < value.getY()) { //Point is SW of value
                if (SW != null) SW.findJunctions(x, y, r, count);
                if (y + r > value.getY()) {
                    if (NW != null) NW.findJunctions(x, y, r, count);
                }
                if (x + r > value.getX()) {
                    if (SE != null) SE.findJunctions(x, y, r, count);
                }
                if (x + r > value.getX() && y + r > value.getY()) {
                    if (NE != null) NE.findJunctions(x, y, r, count);
                }
            } else { //Point is NW of value
                if (NW != null) NW.findJunctions(x, y, r, count);
                if (x + r > value.getX()) {
                    if (NE != null) NE.findJunctions(x, y, r, count);
                }
                if (y - r < value.getY()) {
                    if (SW != null) SW.findJunctions(x, y, r, count);
                }
                if (x + r > value.getX() && y - r < value.getY()) {
                    if (SE != null) SE.findJunctions(x, y, r, count);
                }
            }
        } else {
            if (y < value.getY()) { //Point is SE of value
                if (SE != null) SE.findJunctions(x, y, r, count);
                if (y + r > value.getY()) {
                    if (NE != null) NE.findJunctions(x, y, r, count);
                }
                if (x - r < value.getX()) {
                    if (SW != null) SW.findJunctions(x, y, r, count);
                }
                if (x - r < value.getX() && y + r > value.getY()) {
                    if (NW != null) NW.findJunctions(x, y, r, count);
                }
            } else { //Point is NE of value
                if (NE != null) NE.findJunctions(x, y, r, count);
                if (x - r < value.getX()) {
                    if (NW != null) NW.findJunctions(x, y, r, count);
                }
                if (y - r < value.getY()) {
                    if (SE != null) SE.findJunctions(x, y, r, count);
                }
                if (x - r < value.getX() && y - r < value.getY()) {
                    if (SW != null) SW.findJunctions(x, y, r, count);
                }
            }
        }

    }
}


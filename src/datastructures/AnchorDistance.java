package datastructures;

import java.util.ArrayList;

/**
 * AnchorDistance stores points in a binary tree with their key representing the distance to an anchor point
 * which is defined by AX and AY coordinates. When searching for points in a given radius only points between two
 * distances from the anchor point have to be considered and as such unnecessary checks can be avoided.
 */

public class AnchorDistance implements DataStructure{
    BinaryTree aDist;
    ArrayList<Junction> junctions;

    private final double AX = 20000;
    private final double AY = -10000;

    public void add(Junction j){
        aDist.add(Math.sqrt((j.getX()-AX)*(j.getX()-AX)+((j.getY()-AY)*(j.getY()-AY))),j);
        junctions.add(j);
    }

    public AnchorDistance init(){
        AnchorDistance range = new AnchorDistance();

        ArrayList<Junction> junctions = testenvironment.JunctionScanner.init();
        for (Junction j:junctions) {
            range.add(j);
        }

        return range;
    }

    public AnchorDistance(){
        aDist = new BinaryTree();
        junctions = new ArrayList<>();
    }

    public int[] findJunctions(double x, double y, double r){
        int[] count = new int[2];
        ArrayList<Junction> a;
        double disA = Math.sqrt((x-AX)*(x-AX)+(y-AY)*(y-AY));
        a = aDist.searchRange(disA-r,disA+r,x,y);
        for(Junction j:a){
            if((j.getType()==JunctionType.AIRPORT)) count[0]++;
            else count[1]++;
        }
        return count;
    }

    public int findAirports(int numberOfStations, double radius){
        int count = 0;
        for(Junction junc:junctions){
           if((junc.getType()==JunctionType.AIRPORT)){
               int dist[] = findJunctions(junc.getX(),junc.getY(),radius);
               if( dist[1] >=numberOfStations) count++;
           }
        }
        return count;
    }
}

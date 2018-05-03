package datastructures;

import testenvironment.JunctionScanner;

import java.util.ArrayList;
//Xmin = -20023.816080184883 Ymin = -14666.86116233825 Xmax = 20032.054293303245 Ymax = 12330.23285786473
public class TwoDGrid implements DataStructure { //not really functional (yet)
    JunctionList[][] grid;
    final int RESX = 4006;
    final int RESY = 2701;
    private ArrayList<Junction> junctions;

    @Override
    public DataStructure init() {
        TwoDGrid twoDGrid = new TwoDGrid();
        ArrayList<Junction> juncs = JunctionScanner.init();
        twoDGrid.grid = new JunctionList[RESX][RESY];
        for(Junction j:juncs){
            int x = (int) j.getX()/10 + 2002;
            int y = (int) j.getY()/10 + 1467;
            if(twoDGrid.grid[x][y] == null) twoDGrid.grid[x][y] = new JunctionList();
            twoDGrid.grid[x][y].add(j);
            twoDGrid.junctions.add(j);
        }
        return twoDGrid;
    }

    public TwoDGrid(){
        junctions = new ArrayList<>();
    }

    @Override
    public int[] findJunctions(double x, double y, double r) {
        int[] count = new int[2];
        for (int i = (int) (x-r)/10; i < (int) (x+r)/10; x++) {
            for (int j = (int) (y-r)/10; j < (int) (y+r)/10; j++) {
                if(grid[i][j]!=null){
                    for(Junction junction:grid[i][j]){
                        if((junction.getX()-x)*(junction.getX()-x)+(junction.getY()-y)*(junction.getY()-y)<=r*r){
                            if (junction.getType()==JunctionType.AIRPORT) count[0]++;
                            else count[1]++;
                        }
                    }
                }

            }
        }
        return count;
    }

    @Override
    public int findAirports(int numberOfStations, double radius) {
        return 0;
    }
}
//Subclass to allow generic array creation :-)
class JunctionList extends ArrayList<Junction>{ }

package datastructures;

/**
 * Simple Object storing Junctions with their coordinates, names and JunctionType which is realized as enumeration.
 * The toString-Method is meant to produce the same String as Junctions are described as in junctions.csv
 */

public class Junction {
    private double x,y;
    private String name;
    private JunctionType type; //if true => "AIRPORT" else => "TRAINSTATION"

    public Junction(String name, double x, double y, JunctionType type) {
        this.x = x;
        this.y = y;
        this.name = name;
        this.type = type;
    }

    public String toString(){
       return name+";"+x+";"+y+";"+type;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public String getName() {
        return name;
    }

    public JunctionType getType() {
        return type;
    }
}

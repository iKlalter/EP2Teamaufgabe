package datastructures;

public interface DataStructure {
    DataStructure init();
    int[] findJunctions(double x, double y, double r);
    int findAirports(int numberOfStations, double radius);
}

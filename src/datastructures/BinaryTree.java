package datastructures;

import java.util.ArrayList;

/**
 * BinaryTree is an ordinary Binary Tree with two child Nodes for every BinaryTreeNode, storing Objects of Type Junction
 * based on a double key. The node which is left in the lowest layer of the tree has the lowest key, while the most
 * right node contains the Junction with the highest key.
 */

public class BinaryTree {
    BinaryTreeNode root;

    /**
     * Adds a Junction to the Binary Tree
     * @param d key for the junction
     * @param j Junction to be added
     */

    public void add(double d, Junction j){
        if(root == null) root = new BinaryTreeNode(d,j);
        else root.add(d,j);
    }

    /**
     * Searches the BinaryTree for a range of Junctions, whose keys lie in between to given ones and whose coordinates
     * are in a given circle. Returned is an ArrayList of Junctions fulfilling the given keys and the circle.
     * The radius of the circle is implicitly passed with min and max, since the arithmetic mean of those two values
     * is equal to the radius.
     * @param min min key to be searched for
     * @param max max key to be searched for
     * @param x x-coordinate of the center point of the circle
     * @param y y-coordinate of the center point of the circle
     * @return ArrayList containing all Junctions fulfilling the given criteria
     */
    public ArrayList<Junction> searchRange(double min, double max, double x, double y){
        ArrayList<Junction> junctions = new ArrayList<>();
        root.searchRange(min,max,junctions,x,y);
        return junctions;
    }
}
class BinaryTreeNode{
    private BinaryTreeNode leftChild,rightChild;
    private double key;
    private Junction value;

    /**
     * Adds a Junction to the Binary Tree
     * @param d key for the junction
     * @param j Junction to be added
     */
    public void add(double d, Junction j){
        if(d<key){
            if(leftChild == null) leftChild = new BinaryTreeNode(d,j);
            else leftChild.add(d,j);
        } else {
            if(rightChild == null) rightChild = new BinaryTreeNode(d,j);
            else rightChild.add(d,j);
        }
    }

    public BinaryTreeNode(double d, Junction j){
        key = d;
        value = j;
    }

    /**
     * Adds Junctions fitting the given criteria to a given ArrayList
     * @param min minimum key of Junction
     * @param max maximum key of Junction
     * @param junctions ArrayList to be added to
     * @param x x-coordinate of the center point of the circle
     * @param y y-coordinate of the center point of the circle
     */
    void searchRange(double min, double max, ArrayList<Junction> junctions, double x, double y){
        double r = (max-min)/2;
        if(min > key) {
            if(rightChild != null) rightChild.searchRange(min,max,junctions,x,y);
        } else if(max < key) {
            if(leftChild != null) leftChild.searchRange(min,max,junctions,x,y);
        } else {
            if(leftChild != null) leftChild.searchRange(min,max,junctions,x,y);
            if(key >= min && key <= max){
                if((value.getX()-x)*(value.getX()-x)+(value.getY()-y)*(value.getY()-y)<=(r*r)) junctions.add(value);
            }

            if(rightChild != null) rightChild.searchRange(min,max,junctions,x,y);
        }
    }
}
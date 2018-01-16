/*
   @author Jianyuan Zhong
   At University of Rochester 2017 Fall
 */
import java.util.LinkedList;

public class Vertex {
    String id;
    Double Latitude;
    Double Longitude;
    Double dis;
    Vertex prev;
    Boolean visited;
    Boolean isPath;
    Boolean isStart;
    Boolean isEnd;
    LinkedList<Vertex> Adjacency;

    public Vertex(String id, double latitude, double longitude){
        this.id=id;
        this.Latitude=latitude;
        this.Longitude = longitude;
        this.visited=false;
        this.dis=Double.MAX_VALUE;
        this.Adjacency=new LinkedList<>();
        this.isPath=false;
        this.isEnd=false;
        this.isStart=false;
    }
}

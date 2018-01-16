/*
    @author Jianyuan Zhong
    At Univeristy of Rochester 2017 Fall
 */
public class Edge {
    public Vertex u,v;
    public String id;
    public double weight;

//    public Edge(Vertex u, Vertex v, double weight){
//        this.u=u;
//        this.v=v;
//        this.weight=weight;
//    }

    public Edge(String id, Vertex u, Vertex v){
        this.u=u;
        this.v=v;
        this.id=id;
        calWeight();
    }

    //implementation of Haversine Method to find the distance between to intersection and store it as the weight of the edge
    public void calWeight(){
        final int R= 6371;

        double latDis = Math.toRadians(v.Latitude-u.Latitude);
        double longDis= Math.toRadians(v.Longitude -u.Longitude);

        double a= Math.sin(latDis/2) * Math.sin(latDis/2) +
                Math.cos(Math.toRadians(u.Latitude)) * Math.cos(Math.toRadians(v.Latitude))
                * Math.sin(longDis/2)*Math.sin(longDis/2);
        double c = 2*Math.atan2(Math.sqrt(a),Math.sqrt(1-a));

        weight=R * c *0.621371;
//        double latDis = (v.Latitude-u.Latitude);
//        double longDis= (v.Longitude-u.Longitude);
//
//        weight=Math.sqrt(latDis*latDis+longDis*longDis);

    }


}

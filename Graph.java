/*
    @author Jianyuan Zhong
    At Univeristy of Rochester 2017 Fall
 */
import java.io.*;
import java.util.*;

public class Graph {
    public HashMap<String, Vertex> AdjacentList;
    public HashMap<Vertex, HashMap<Vertex,Edge>> EdgeSet=new HashMap<>();
    public ArrayList<Edge> Edges=new ArrayList<>();
    //scale
    public Double minLat=100.0;
    public Double minLong=0.0;
    public Double maxLat=-100.0;
    public Double maxLong=-100.0;


    private class VertexComparator implements Comparator<Vertex>{ //override comparator
        @Override
        public int compare(Vertex v1, Vertex v2) {
            return Double.compare(v1.dis,v2.dis);
        }
    }

    public Graph(String FileName){
        AdjacentList =new HashMap<>();
        try {
            BufferedReader rd = new BufferedReader(new FileReader(new File(FileName)));
            String str;
            while ((str = rd.readLine()) != null) {
                String[] line = str.split("\\t");

                if (line[0].equals("i")) {//if vertex
                    AdjacentList.put(line[1], new Vertex(line[1], Double.parseDouble(line[2]), Double.parseDouble(line[3])));
                    EdgeSet.put(AdjacentList.get(line[1]), new HashMap<Vertex,Edge>());

                    //keep track of the minLat, minLong, maxLat, laxLong
                    if(minLat> AdjacentList.get(line[1]).Latitude)
                        minLat= AdjacentList.get(line[1]).Latitude;

                    if(minLong> AdjacentList.get(line[1]).Longitude)
                        minLong= AdjacentList.get(line[1]).Longitude;

                    if(maxLat< AdjacentList.get(line[1]).Latitude)
                        maxLat= AdjacentList.get(line[1]).Latitude;

                    if(maxLong< AdjacentList.get(line[1]).Longitude)
                        maxLong= AdjacentList.get(line[1]).Longitude;
                }
                else if (line[0].equals("r")) {//if edge
                    addEdge(line[1], AdjacentList.get(line[2]), AdjacentList.get(line[3]));
                }
            }
            System.out.print("maxLat: "+maxLat+" minLat: "+minLat+" maxLong: "+maxLong+" minLong: "+minLong+"\n");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    private void addEdge(String id, Vertex u, Vertex v){
        //add vertex adjacency
        u.Adjacency.add(v);
        v.Adjacency.add(u);
        //add edge
        Edge e=new Edge(id,u,v);
        EdgeSet.get(u).put(v,e);
        EdgeSet.get(v).put(u,e);
        Edges.add(e);
    }

    private void dijkstra(Vertex s){
        for(Map.Entry<String, Vertex> m : AdjacentList.entrySet()){//set all vertex's values to default
            m.getValue().visited=false;
            m.getValue().isPath=false;
            m.getValue().isEnd=false;
            m.getValue().isStart=false;
            m.getValue().dis=Double.MAX_VALUE;
            m.getValue().prev=null;
        }

        VertexComparator comparator=new VertexComparator();
        PriorityQueue<Vertex> pq=new PriorityQueue<>(AdjacentList.size(), comparator);

        //add the starting vertex to pq, modify the corresponding values
        pq.offer(s);
        s.dis=0.0;
        s.isStart=true;

        while(!pq.isEmpty()){
            Vertex curr=pq.poll();
            if(!curr.visited){

                for(Vertex v : curr.Adjacency){//for all the vertices adjacent to curr
                    pq.offer(v);

                    if(curr.dis+EdgeSet.get(curr).get(v).weight<v.dis){//find the shortest path, assign the corresponding previous vertex
                        v.dis=curr.dis+EdgeSet.get(curr).get(v).weight;
                        v.prev=curr;
                    }
                }
                curr.visited=true;
            }
        }
    }

    public List<Vertex> directions(String id1, String id2){
        List<Vertex> path=new ArrayList<>();
        dijkstra(AdjacentList.get(id1));
        Vertex v=AdjacentList.get(id2);

        v.isEnd=true;
        while(v!=null){
            v.isPath=true;

            path.add(0,v);
            v=v.prev;
        }

        //when no such path, set the List to empty
        if (path.get(0)!=AdjacentList.get(id1)){
            path.clear();
        }

        return path;
    }
}

# StreetMap
CSC172
Project 4

Team Members:
Jianyuan Zhong jzhong9@u.rochester.edu 
Gao Fan gfan@u.rochester.edu

Running The Project:
Compilation: javac StreetMap.java
Execution: >>java StreetMap show ___.txt //for just showing the graphic
	   >>java StreetMap directions i(n) i(k) //for printing out the path information from i(n) to i(k)
	   >>java StreetMap show directions i(n) i(k) // for both showing the graphic with the path in highlighted color, and printing out the path information 
Major Obstacle:
The construction of the project works fairly well, Except for two difficulty:
Relatively long run time with the Arraylist Based Adjacency List.
Drawing resizable draw. 

About the code:
Constructing Graph: 
Reading Intersections from files and storing them a HashMap<String(id),Vertex> based Adjacency List for faster search time.
Reading Roads: reading two vertex responding to an Edge and storing them in the HashMap<Vertex,HashMap<Vertex,Edge>>(for searching weight using Dijkstra) and an ArrayList(for drawing the map, improved to O(|E|)).

Finding Path:
1.   Use Dijkstra algorithm the travers the map and search for the shortest path between the a start point the all the points in the graph.
2.   Finding Edge’s weight with HashMap, improves the search time to O(1);

Drawing Map:
Keep check of the Max and Minimum Value of the Latitude and Longitude of the Vertices, Use the (subtraction of the two number) / (Height or Width of the frame) to calculate the the Scaling Number, with with the graph will be well fit in every dimension. 

Extra Credit:
1.   Improving the run time. Huge improve when changing the Adjacency List to HashMap.
2.   Resizable windows. Graph will fit in every dimension, even if being scratched.
3.   Beautiful Map.
4.   Ability to draw the path with Red, ThickLine and mark the start and end point with yellow and blue circle.
5.   Interactive allow user to choose intersections, show intersections, and find path between the points they choose in the frame. 
  

Class Files:

Vertex.java: the class for the nodes
Graph.java: the class for constructing the map and finding the shortest distance
Edge.java: the class for the edge of two specific edges and calculating its weight using Haversine’s method
myCanvas.java: class for drawing and resizing the map.
StreetMap.java: the Main class to run the whole project.



Run Time Analytics: 

E= # of Edges, V= # of Vertices

Scanning through the text file and Creating Graph: O(|E+V|)
	1,It takes O(1), each time, to read a line, create a Vertex and add it to the 		adjacency list. There are V operations in total for this step.
	2,It takes O(1), each time, to read a line, map two Vertices with the edge that 	connects them and calculate the edge’s weight. There are E operations in total for 	this step.
Thus, the Run time for this step is O(|V|)+O(|E|)=O(|V+E|)

Finding path with Dijkstra Algorithm: O(|E|log(|V|))
	1,It takes O(|V|) to initialize all the fields in each Vertex.
	2,Starting from the Starting Vertex, we travers the entire graph according to the 	their adjacent Vertices, the edges, and uses the priority queue to compare and 		find shortest path to each pointer, which costs O(||E|log(|V|)|). (Note that we 	use HashMap as one of the adjacency List to sort Vertices and Edges in form of 		HashMap<Vertex, HashMap<Vertex, Edge(v1,v2)>>, the cost to search for the weigh of 	a specific Edges is O(1)! )
Thus, the Finding path with Dijkstra is O(|V|+|E|log|V|)=O(|E|log|V|).

Draw the graph:O(|E|)
	 When reading the file, we also store all the edges into an ArrayList. Therefore, 	we are Only Drawing Edges on the graph. Such strategy will reduce the run time to 	draw and redraw the map to O(|E|)

Total Run Time: O(|E+V|+|E|log(|V|)+|E|)=O(|E|log|V|). 

Work Load Distribution
Jianyuan Zhong mainly write out the Graph class including file reading, Dijkstra Algorithm , Path Finding and printing out the route.
Gao Fan is responsible to the graphic part and extra credits. 

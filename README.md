# Omnidirectional weighted graph
This is a project about Omnidirectional weighted graph 

## What's inside
1. container for the graph.
2. the algorithm of the graph.

## functions
the commands are:
****
* WGraph_DS() -makes new graph  
*  getNode(int key) -return the node with the corresponding key  
*  hasEdge(int node1, int node2) -check if there is an edge between node1 to node 2  
*  getEdge(int node1, int node2) -return the weight of the edge  
*  addNode(int key) -adds a new node to the graph  
*  connect(int node1, int node2, double w) -makes edge between two nodes with length w  
*  getV() -return collection of all the nodes in the graph  
*  getV(int key) -return collection of all the neighboring nodes of the node  
*  removeNode(int key) -remove a node and its connected edges  
*  removeEdge(int node1, int node2) -remove an edge  
*  nodeSize() -returns the number of nodes that the graph contain  
*  edgeSize() -returns the number of edges that the graph contain  
*  getMC() -return the number of modification that the graph undergo  
*  toKeyArray() -makes int array of the keys  
*  setInfo(int key, String info) -set the info of the node
*  equals(object o)- checks if equals  
****  
* WGraph_Algo() -makes a container for the graph for running algorithm  
*  init(weighted_graph g) -here you put the graph to operate on  
*  copy() -return a full copy of the graph with the original keys  
*  getGraph() -return the inside graph  
*  isConnected() -checks if the graph is connected and return true or false  
*  shortestPathDist(int src, int dest) -return the distance of the shortest path from the key src to the key dest  
*  shortestPath(int src, int dest) -return the list of nodes that was on the shortest path  
*  save(String file) -save the graph to file  
*  load(String file) -load graph from a file  

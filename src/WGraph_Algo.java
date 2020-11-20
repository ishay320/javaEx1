package ex1.src;

import java.util.*;
import java.io.*;
public class WGraph_Algo implements weighted_graph_algorithms {
    weighted_graph _graph;
    /**
     * Init the graph on which this set of algorithms operates on.
     *
     * @param g - graph to init
     */
    @Override
    public void init(weighted_graph g) {
        _graph = g;
    }

    /**
     * Return the underlying graph of which this class works.
     *
     * @return the graph inside
     */
    @Override
    public weighted_graph getGraph() {
        return _graph;
    }

    /**
     * Compute and returns a deep copy of this weighted graph.
     *
     * @return deep copy of the graph
     */
    @Override
    public weighted_graph copy() {
        return new WGraph_DS(_graph);
    }

    /**
     * Returns true if there is a valid path from every node to
     * other node.
     *
     * @return if all are connected
     */
    @Override
    public boolean isConnected() {

        if (_graph == null | _graph.getV().size() < 2) { //if the graph is less then 1 node
            return true;
        }
        if (_graph.getV().size() > _graph.edgeSize() + 1) { //if the graph have more v then e (optimization) O(1)
            return false;
        }

        boolean first = true;
        HashMap<Integer, Boolean> trueMap = new HashMap<Integer, Boolean>(); //truthMap
        LinkedList<node_info> meg = new LinkedList<node_info>();//meg of whats next
        for (node_info node : _graph.getV() //initiates the truth table of the neighbors
        ) {
            if (first) { //starting condition for the list
                trueMap.put(node.getKey(), true);
                first = false;
                Collection<node_info> neighborsOfNode = _graph.getV(node.getKey());
                if (neighborsOfNode.size() == 0) {
                    return false; //if its not got neighbors its disconnected
                }
                meg.addAll(neighborsOfNode); //adds all new neighbors to the meg
            } else
                trueMap.put(node.getKey(), false); //adds all the other nodes to the list
        }

        while (meg.size()!=0) {//while there is nodes its needs to continue
            if(trueMap.get(meg.peek().getKey())){//check if the node already passed on
                meg.removeFirst();
                continue;
            }
            node_info node = meg.pop();
            trueMap.put(node.getKey(),true);
            meg.addAll(_graph.getV(node.getKey()));
        }
        return !trueMap.containsValue(false);//check if there is node that don't have neighbor
}
    /**
     * returns the length of the shortest path between src to dest
     * Note: if no such path --> returns -1
     * note2: this is implementation of Dijkstra's algorithm
     *
     * @param src  - start node
     * @param dest - end (target) node
     * @return length of the shortest path
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        if (src==dest) return 0;

        int[] pointToKey = ((WGraph_DS)_graph).toKeyArray();// can improve by O(n)
        HashMap<Integer,Integer> keyToPoint = new HashMap<>();
        double[] distance = new double[pointToKey.length];
        boolean[] was = new boolean[pointToKey.length];
        int[] comeFromKey = new int[pointToKey.length];
        for (int i = 0; i < pointToKey.length; i++) { //initialisation
            keyToPoint.put(pointToKey[i],i);
            distance[i] = Double.MAX_VALUE;
            was[i]=false;
            comeFromKey[i]=-1;
        }
        for (node_info n : _graph.getV(dest) //for the dest
        ) {
            distance[keyToPoint.get(n.getKey())]=_graph.getEdge(dest,n.getKey());
            comeFromKey[keyToPoint.get(n.getKey())] = dest;
        }
        was[keyToPoint.get(dest)]=true;

        boolean thereIsChange = true;
        int nodePointer = keyToPoint.get(dest);

        while (thereIsChange) { //pass on all the graph
            thereIsChange=false;
            boolean first = true;
            for (int i = 0; i < pointToKey.length; i++) { //find the min distance and point to it
                if ((!was[i]&distance[i]<Integer.MAX_VALUE&first)|(!was[i]&distance[i]<distance[nodePointer]&!first)){
                    nodePointer=i;
                    first = false;
                    thereIsChange = true; //there is work to be done
                }
            }

            for (node_info n : _graph.getV(pointToKey[nodePointer]) //neighbors pass
            ) {
                if (distance[keyToPoint.get(n.getKey())] > distance[nodePointer] + _graph.getEdge(n.getKey(), pointToKey[nodePointer])&!was[keyToPoint.get(n.getKey())]) {
                    distance[keyToPoint.get(n.getKey())] = distance[nodePointer] + _graph.getEdge(n.getKey(), pointToKey[nodePointer]);
                    comeFromKey[keyToPoint.get(n.getKey())] = pointToKey[nodePointer];
                }
            }
            was[nodePointer] = true;
        }
        if(distance[keyToPoint.get(src)]==Double.MAX_VALUE) return -1;
        return distance[keyToPoint.get(src)];
    }

    /**
     * returns the the shortest path between src to dest - as an ordered List of nodes:
     * src--> n1-->n2-->...dest
     * if no such path --> returns null;
     * note: this is implementation of Dijkstra's algorithm
     * https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
     * @param src  - start node
     * @param dest - end (target) node
     * @return shortest path between src to dest - as an ordered List of nodes
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if(src==dest){
            List<node_info> r = new LinkedList<>();
            r.add(_graph.getNode(src));
            return r;
        }

       int[] pointToKey = ((WGraph_DS)_graph).toKeyArray();// can improve by O(n)
       HashMap<Integer,Integer> keyToPoint = new HashMap<>();
       double[] distance = new double[pointToKey.length];
        boolean[] was = new boolean[pointToKey.length];
        int[] comeFromKey = new int[pointToKey.length];
        for (int i = 0; i < pointToKey.length; i++) { //initialisation
            keyToPoint.put(pointToKey[i],i);
            distance[i] = Double.MAX_VALUE;
            was[i]=false;
            comeFromKey[i]=-1;
        }
        for (node_info n : _graph.getV(dest) //for the dest
             ) {
            distance[keyToPoint.get(n.getKey())]=_graph.getEdge(dest,n.getKey());
            comeFromKey[keyToPoint.get(n.getKey())] = dest;
        }
        was[keyToPoint.get(dest)]=true;

        boolean thereIsChange = true;
        int nodePointer = keyToPoint.get(dest);

        while (thereIsChange) { //pass on all the graph
           thereIsChange=false;
            boolean first = true;
            for (int i = 0; i < pointToKey.length; i++) { //find the min distance and point to it
                if ((!was[i]&distance[i]<Integer.MAX_VALUE&first)|(!was[i]&distance[i]<distance[nodePointer]&!first)){
                    nodePointer=i;
                    first = false;
                    thereIsChange = true; //there is work to be done
                }
            }

           for (node_info n : _graph.getV(pointToKey[nodePointer]) //neighbors pass
           ) {
               if (distance[keyToPoint.get(n.getKey())] > distance[nodePointer] + _graph.getEdge(n.getKey(), pointToKey[nodePointer])&!was[keyToPoint.get(n.getKey())]) {
                   distance[keyToPoint.get(n.getKey())] = distance[nodePointer] + _graph.getEdge(n.getKey(), pointToKey[nodePointer]);
                   comeFromKey[keyToPoint.get(n.getKey())] = pointToKey[nodePointer];
                   }
               }
            was[nodePointer] = true;
           }

        if (!was[keyToPoint.get(src)])return null;
        List<node_info> shortestPath = new LinkedList<>();
       shortestPath.add(_graph.getNode(src));
       int last = keyToPoint.get(comeFromKey[keyToPoint.get(src)]);
        if(last==keyToPoint.get(dest)) return shortestPath;
        while (true){
           shortestPath.add(_graph.getNode(pointToKey[last]));
           last = keyToPoint.get(comeFromKey[last]);
           if(pointToKey[last]==dest)break;
       }
        return shortestPath;
    }

    /**
     * Saves this weighted (undirected) graph to the given
     * file name
     *
     * @param file - the file name (may include a relative path).
     * @return true if the file was successfully saved
     */
    @Override
    public boolean save(String file) {
        try
        {
            //Saving of object in a file
            FileOutputStream f = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(f);
            // Method for serialization of object
            out.writeObject(_graph);
            out.close();
            f.close();

            System.out.println("Object has been serialized");

        }

        catch(IOException ex)
        {
            return false;
        }
        return true;

    }

    /**
     * This method load a graph to this graph algorithm.
     * if the file was successfully loaded - the underlying graph
     * of this class will be changed (to the loaded one), in case the
     * graph was not loaded the original graph should remain "as is".
     *
     * @param file - file name
     * @return true if the graph was successfully loaded.
     */
    @Override
    public boolean load(String file) {
        try
        {
            // Reading the object from a file
            FileInputStream f = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(f);
            // Method for deserialization of object
            init((WGraph_DS) in.readObject());
            in.close();
            f.close();
        }

        catch(IOException | ClassNotFoundException ex)
        {
            return false;
        }
        return true;
    }


}

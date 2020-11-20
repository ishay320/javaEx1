package ex1.src;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;


public class WGraph_DS implements weighted_graph {
    /**
     * class for making and getting the edges
     */
    private class EdgeInfo{
        private HashMap<Integer, Double> _edgeweight = new HashMap<>();

        /**
         * @param key of the node
         * @return the weight and if it doesn't have weight returns -1
         */
        private double getWeight(int key){
            return _edgeweight.get(key)==null? -1 :_edgeweight.get(key);
        }
    }
    /**
     * the node_info implementation inside the graph class
     */
    private class NodeInfo implements node_info {

        private int _key;
        private String _meta = null;
        private double _tag;

        public NodeInfo(int key){
            _key = key;
        }
        /**
         * Return the key (id) associated with this node.
         * Note: each node will have an unique key because it will reject doubles
         *
         * @return key
         */
        @Override
        public int getKey() {
            return _key;
        }

        /**
         * return the remark (meta data) associated with this node.
         * @return metadata
         */
        @Override
        public String getInfo() {
            return _meta;
        }

        /**
         * Allows changing the remark (meta data) associated with this node.
         * @param s - string to be inserted
         */
        @Override
        public void setInfo(String s) {
            _meta = s;
        }

        /**
         * Temporal data (aka distance, color, or state)
         * which can be used be algorithms
         * @return tag
         */
        @Override
        public double getTag() {
            return _tag;
        }

        /**
         * Allow setting the "tag" value for temporal marking an node
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            _tag = t;
        }

    }
    //the parameters of the graph
    private HashMap<Integer, EdgeInfo> _edgeset = new HashMap<Integer, EdgeInfo>();
    private HashMap<Integer, node_info> _nodeset =new HashMap<Integer, node_info>();
    private int _edges = 0;
    private int ModeCount = 1;

    public WGraph_DS(){
    }
    /**
     * copy constructor
     */
    public WGraph_DS(weighted_graph graph_ds) {
        for (node_info n: graph_ds.getV()
             ) {
            this.addNode(n.getKey());
            getNode(n.getKey()).setInfo(n.getInfo());
            getNode(n.getKey()).setTag(n.getTag());
        }
        for (node_info n: this.getV()
             ) {
            for (node_info neighbor :graph_ds.getV(n.getKey())
                 ) {
                this.connect(n.getKey(),neighbor.getKey(),graph_ds.getEdge(n.getKey(),neighbor.getKey()));
            }
        }
        ModeCount = graph_ds.getMC();
    }
    /**
     * return the node by the node id
     *
     * @param key - the node_id
     * @return the node by the node id, null if none
     */
    @Override
    public node_info getNode(int key) {
        return _nodeset.get(key);
    }

    /**
     * return true if and only if there is an edge between node1 and node2
     * Note: this method run in O(1) time
     *
     * @param node1
     * @param node2
     * @return true if there is an edge between node1 and node2
     */
    @Override
    public boolean hasEdge(int node1, int node2) {
        return _edgeset.get(node1)._edgeweight.containsKey(node2);
    }

    /**
     * return the weight of the edge between (node1, node1). In case
     * there is no such edge - returns -1
     * Note: this method run in O(1) time.
     *
     * @param node1
     * @param node2
     * @return the weight of the edge, -1 if none
     */
    @Override
    public double getEdge(int node1, int node2) {
        Double w = _edgeset.get(node1)._edgeweight.get(node2);
        return w==null? -1:w;
    }

    /**
     * adds a new node to the graph with a given key.
     * Note: this method run in O(1) time.
     * Note2: if there is already a node with such a key -> no action will be performed.
     *
     * @param key
     */
    @Override
    public void addNode(int key) {
        if(_nodeset.containsKey(key)) return;
        _nodeset.put(key,new NodeInfo(key));
        _edgeset.put(key,new EdgeInfo());
        ModeCount++;
    }

    /**
     * Connect an edge between node1 and node2, with an edge with weight >=0.
     * Note: this method run in O(1) time.
     * Note2: if the edge node1-node2 already exists - the method simply updates the weight of the edge.
     * Note3: if it exist and the same- will do nothing
     * @param node1
     * @param node2
     * @param w
     */
    @Override
    public void connect(int node1, int node2, double w) {
        if (node1==node2|| w<0)return;
        if (_nodeset.containsKey(node1)&_nodeset.containsKey(node2)){ //check if there is keys
            if(_edgeset.get(node1).getWeight(node2)==w) return;//check if the weight is the same
            _edgeset.get(node1)._edgeweight.put(node2,w);
            if (_edgeset.get(node2)._edgeweight.put(node1,w)==null)_edges++; //put the second and check if was there another one
            ModeCount++;
        }
    }

    /**
     * This method return a pointer (shallow copy) for a
     * Collection representing all the nodes in the graph.
     * Note: this method run in O(1) time
     *
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV() {
        return _nodeset.values();
    }

    /**
     * This method returns a Collection containing all the
     * nodes connected to the node
     * Note: this method run in O(k) time, k - being the degree of the given node.
     *
     * @param node_id
     * @return Collection<node_data>
     */
    @Override
    public Collection<node_info> getV(int node_id) {
        Collection<node_info> collection = new LinkedList<>();
        _edgeset.get(node_id)._edgeweight.forEach((k,v)->collection.add(_nodeset.get(k)));
        return collection;
    }

    /**
     * Delete the node (with the given ID) from the graph -
     * and removes all edges which starts or ends at this node.
     * This method run in O(n), |V|=n, as all the edges should be removed.
     *
     * @param key
     * @return the data of the removed node (null if none).
     */
    @Override
    public node_info removeNode(int key) {
        if (!_nodeset.containsKey(key)) return null;
        node_info node = _nodeset.get(key);
        Collection<node_info> temp = getV(key);
        _edges-=temp.size();
        for (node_info neigNode:temp){
            _edgeset.get(neigNode.getKey())._edgeweight.remove(key);
        }
        _edgeset.remove(key);
        _nodeset.remove(key);
        ModeCount++;
        return node;
    }

    /**
     * Delete the edge from the graph,
     * Note: this method run in O(1) time.
     *
     * @param node1
     * @param node2
     */
    @Override
    public void removeEdge(int node1, int node2) {
        _edgeset.get(node1)._edgeweight.remove(node2);
        if(_edgeset.get(node2)._edgeweight.remove(node1)!=null){
            _edges--;
            ModeCount++;
        }
    }

    /**
     * return the number of vertices (nodes) in the graph.
     * Note: this method run in O(1) time.
     *
     * @return the number of nodes in the graph
     */
    @Override
    public int nodeSize() {
        return _nodeset.size();
    }

    /**
     * return the number of edges (unidirectional graph).
     * Note: this method run in O(1) time.
     *
     * @return number of edges in the graph
     */
    @Override
    public int edgeSize() {
        return _edges;
    }

    /**
     * return the Mode Count - for testing changes in the graph.
     * Any change in the inner state of the graph should cause an increment in the ModeCount
     *
     * @return mode count
     */
    @Override
    public int getMC() {
        return ModeCount;
    }

    /**
     * for running in order and such
     * @return key array of the graph
     */
    public int[] toKeyArray(){
        int[] array = new int[getV().size()];
        int i=0;
        for (node_info n:getV()
             ) {
            array[i++]=n.getKey();
        }
        return array;
    }

    /** sets the info(metadata) of given node
     * @param key
     * @param info
     * @return if info has been set
     */
    public boolean setInfo(int key, String info) {
        if(!_nodeset.containsKey(key))return false;
        getNode(key).setInfo(info);
        return true;
    }

    /**
     * @param o
     * @return true if equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WGraph_DS)) return false;
        WGraph_DS wGraph_ds = (WGraph_DS) o;
        boolean t =_edges == wGraph_ds.edgeSize() &&
                ModeCount == wGraph_ds.getMC() &&
                nodeSize()==wGraph_ds.nodeSize();
        int[] keyArr = toKeyArray();
        for (int i = 0; i < keyArr.length-1; i++) {
            if (getEdge(keyArr[i],keyArr[i+1])!=wGraph_ds.getEdge(keyArr[i],keyArr[i+1])){
                t=false;
            }
        }
        return  t;
    }
}

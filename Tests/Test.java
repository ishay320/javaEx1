package ex1.Tests;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class Test {
    static int testNum = 0;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {

    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {

    }

    WGraph_DS graphMaker(int nodeSize, int edgeSize, int seed){
        WGraph_DS g = new WGraph_DS();
        Random r = new Random(seed);
        for (int i = 0; i < nodeSize; i++) {
            g.addNode(i);
        }
        while (g.edgeSize()<edgeSize){
            int a = r.nextInt(nodeSize);
            int b = r.nextInt(nodeSize);
            double w = r.nextDouble()*100;
            g.connect(a,b,w);
        }
        return g;
    }

    @org.junit.jupiter.api.Test
    void edgeConnection() {
        WGraph_DS g = new WGraph_DS();
        //check if zero nodes
        assertEquals(0,g.nodeSize());
        assertEquals(0,g.edgeSize());
        //check if only 1 node
        g.addNode(15);
        assertEquals(1,g.nodeSize());
        assertEquals(0,g.edgeSize());
        //check if 1 edge and 2 nodes
        g.addNode(16);
        g.connect(15,16,1);
        assertEquals(2,g.nodeSize());
        assertEquals(1,g.edgeSize());
        //check if removed edge
        g.removeEdge(16,15);
        assertEquals(2,g.nodeSize());
        assertEquals(0,g.edgeSize());
        //check if removed node
        g.connect(15,16,1);
        g.removeNode(15);
        assertEquals(1,g.nodeSize());
        assertEquals(0,g.edgeSize());
        //check if 10 edges
        g = new WGraph_DS();
        for (int i = 0; i < 11; i++) {
            g.addNode(i);
        }
        for (int i = 0; i < 10; i++) {
            g.connect(i,i+1,1);
        }
        assertEquals(11,g.nodeSize());
        assertEquals(10,g.edgeSize());
        //check if removed 5 edges
        for (int i = 0; i < 5; i++) {
            g.removeEdge(i,i+1);
        }
        assertEquals(11,g.nodeSize());
        assertEquals(5,g.edgeSize());

    }
    @org.junit.jupiter.api.Test
    void isConnected() {
        WGraph_DS g = new WGraph_DS();
        WGraph_Algo algo = new WGraph_Algo();
        algo.init(g);
        //no nothing
        assertEquals(true,algo.isConnected());
        //1 node
        g.addNode(1);
        assertEquals(true,algo.isConnected());
        //2 nodes
        g.addNode(2);
        assertEquals(false,algo.isConnected());
        //2 nodes connected
        g.connect(1,2,1);
        assertEquals(true,algo.isConnected());
        //2 nodes + 1 node
        g.addNode(3);
        assertEquals(false,algo.isConnected());
        //3 edges but disconnected
        g.addNode(4);
        g.connect(1,3,1);
        g.connect(2,3,1);
        assertEquals(false,algo.isConnected());
    }
    @org.junit.jupiter.api.Test
    void shortestPath() {
        WGraph_DS g = new WGraph_DS();
        WGraph_Algo algo = new WGraph_Algo();
        algo.init(g);
        g.addNode(1);
        //1 node
        assertNotNull(algo.shortestPath(1,1));
        assertEquals(0,algo.shortestPathDist(1,1));
        //2 nodes disconnected
        g.addNode(10);
        assertNull(algo.shortestPath(1,10));
        assertEquals(-1,algo.shortestPathDist(1,10));
        //2 nodes connected
        g.connect(1,10,4);
        assertNotNull(algo.shortestPath(1,10));
        assertEquals(4,algo.shortestPathDist(1,10));
        //3 nodes disconnected
        g.addNode(15);
        assertNull(algo.shortestPath(1,15));
        assertEquals(-1,algo.shortestPathDist(1,15));
        //5 nodes connected but leaps on edge
        g.addNode(16);
        g.addNode(17);
        g.connect(10,15,4);
        g.connect(15,16,4);
        g.connect(16,17,4);
        g.connect(15,17,4);
        assertNotNull(algo.shortestPath(1,16));
        assertEquals(4*3,algo.shortestPathDist(1,17));
    }

    @org.junit.jupiter.api.Test
    void readWrite() {
        //write and read check
        WGraph_Algo algo = new WGraph_Algo();
        algo.init(graphMaker(1000000,1000000,1));
        algo.save("test");
        algo.load("test");
    }
}
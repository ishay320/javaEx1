package ex1.Tests;
import ex1.src.WGraph_DS;

import java.util.Random;

public class benchmark {

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
    void make() {
        //time of 1,000,000 nodes and 1,000,000-1 edges in order
        WGraph_DS g = new WGraph_DS();
        for (int i = 0; i < 1000000; i++) {
            g.addNode(i);
        }
        for (int i = 0; i < 1000000-1; i++) {
            g.connect(i,i+1,1);
        }
    }
    @org.junit.jupiter.api.Test
    void makeMillion() {
        //time of 1,000,000 nodes and 10,000,000 edges in random
        graphMaker(1000000,10000000,1); //uncomment to kill your computer :)
    }


}

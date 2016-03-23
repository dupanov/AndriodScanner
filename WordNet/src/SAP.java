import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

/**
 * Created by Vadim on 19.03.2016.
 */
public class SAP {
    Digraph G, Gr;
    BreadthFirstDirectedPaths bfs, bfsReverse;
    final int graphTop = 38003;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G)
    {
        this.G = G;
        Gr = G.reverse();

    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w)
    {
        MySearch sap = new MySearch(G, v);

        int dist = sap.getDistance(w);
        if (dist <= G.E()) return dist;
        return -1;
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        MySearch sap = new MySearch(G, v);
        return sap.getAncestor(w);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w)
    {
        int shortest = G.E() + 100000000;
        for (int vertexV: v) {
            MySearch searchPath = new MySearch(G, vertexV);
            for (int vertexW: w) {
                int s = searchPath.getDistance(vertexW);
                if (s < shortest) shortest = s;
            }
        }
        if (shortest <= G.V()) return shortest;
        return -1;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
    {
        int ancestor = -1;
        for (int vertexV: v) {
            MySearch searchPath = new MySearch(G, vertexV);
            for (int vertexW: w) {
                int tempAncestor = searchPath.getAncestor(vertexW);
                MySearch searchPath2 = new MySearch(G, graphTop);
                if (ancestor != -1 && searchPath2.getDistance(tempAncestor) < searchPath2.getDistance(ancestor)) ancestor = tempAncestor;
            }
        }
        if (ancestor <= G.V()) return ancestor;
        return -1;
    }

    private class MySearch {
        Digraph G, Gr;
        BreadthFirstDirectedPaths bfs, bfsReverse;
        Iterable<Integer> pathToV;
        int ancestor, dist;
        MySearch(Digraph G, int v)
        {
            this.G = G;
            Gr = G.reverse();
            ancestor = 0;
            bfs = new BreadthFirstDirectedPaths(G, v);
            pathToV = bfs.pathTo(graphTop);
        }

       public BreadthFirstDirectedPaths pathV(int w) {
            for (int vertex : pathToV) {
                bfsReverse = new BreadthFirstDirectedPaths(Gr, vertex);
                if (bfsReverse.hasPathTo(w)) {
                    ancestor = vertex;
                    return bfsReverse;
                }
            }
           return null;
        }


        public int getAncestor(int w) {
            pathV(w);
            if (ancestor <= G.E()) return ancestor;
            return -1;
        }

        public int getDistance(int w) {
            pathV(w);
            int result = bfs.distTo(ancestor) + bfsReverse.distTo(w);
            if (result < G.E()) return result;
            return -1;
        }


    }

    // do unit testing of this class
    public static void main(String[] args)
    {

    }
}
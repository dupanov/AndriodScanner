import edu.princeton.cs.algs4.*;

/**
 * Created by admin on 24.03.2016.
 */
public class SAP {
    private static final int INFINITY = Integer.MAX_VALUE;
    private Digraph G;
    private MyBFS bfs;

    public SAP(Digraph G)
    {
        this.G = new Digraph(G);
        bfs = new MyBFS(G);
    }


    public int length(int v, int w) {

        if (v == w) return 0;
        MyBFS bfs = new MyBFS(G);
        int minPath = bfs.getPath(v, w);
        if (minPath <= G.V())return minPath;
        return -1;

    }

    public int ancestor(int v, int w) {
        if (v == w) return v;
        bfs = new MyBFS(G);
        return bfs.getAncestor(v, w);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w)
    {
        if (v == null || w == null ) throw new NullPointerException("Provide correct argument");
        for (Integer i: v) {
            for (Integer j: w) {
                if (i == j) return 0;
            }
        }

        bfs = new MyBFS(G);
        return bfs.getPath(v, w);
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w)
    {
        for (Integer i: v) {
            for (Integer j: w) {
                if (i == j) return i;
            }
        }
        if (v == null || w == null ) throw new NullPointerException("Provide correct argument");
        bfs = new MyBFS(G);
        return bfs.getAncestor(v, w);
    }

    private class MyBFS {
        private final Digraph Graph;
        private Stack<Integer> distToCash, markedCash;
        private boolean[] marked;  // marked[v] = is there an s->v path?
        private int[] edgeTo;      // edgeTo[v] = last edge on shortest s->v path
        private int[] distTo;      // distTo[v] = length of shortest s->v path
        private Queue<Integer> reversePost;
        private int ancestor;
        private int path;

        MyBFS(Digraph G){
            this.Graph = G;
            marked = new boolean[Graph.V()];
            distTo = new int[Graph.V()];
            for (int v = 0; v < Graph.V(); v++)
                distTo[v] = INFINITY;
            edgeTo = new int[Graph.V()];
            ancestor = INFINITY;
            path = INFINITY;
            distToCash = new Stack<>();
            markedCash  =new Stack<>();
        }

        public void bfs(int v, int w){
        if (v == w) {
            path = 0;
            return;
        }
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(Graph, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(Graph, w);

        reversePost = bfsaux(Graph, v);
        int minPath = INFINITY;
        for (int j: reversePost) {
            if (bfs2.hasPathTo(j)) {
                int path = bfs1.distTo(j) + bfs2.distTo(j);
                if (path < minPath) {
                    minPath = path;
                    ancestor = j;
                }
            }
        }

            reversePost = bfsaux(Graph, w);

            for (int j: reversePost) {
                if (bfs1.hasPathTo(j)) {
                    int path = bfs1.distTo(j) + bfs2.distTo(j);
                    if (path < minPath) {
                        minPath = path;
                        ancestor = j;
                    }
                }
            }

        if (minPath <= Graph.V()) path = minPath;
        else path = -1;
    }

        public void bfs(Iterable<Integer>v, Iterable<Integer> w) {
            BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(G, v);
            BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(G, w);
            int minPath = INFINITY;

            for (int i : v) {
                reversePost = bfsaux(Graph, i);

                for (int k : reversePost) {
                    if (bfs1.hasPathTo(k)) {
                        int path = bfs1.distTo(k) + bfs2.distTo(k);
                        if (path >= 0 && path < minPath) {
                            minPath = path;
                            ancestor = k;
                        }
                    }
                }
            }
                for (int j : w) {
                    reversePost = bfsaux(Graph, j);

                    for (int k : reversePost) {
                        if (bfs2.hasPathTo(k)) {
                            int path = bfs1.distTo(k) + bfs2.distTo(k);
                            if (path >= 0 && path < minPath) {
                                minPath = path;
                                ancestor = k;
                            }
                        }
                    }
            }
            if (minPath <= Graph.V()) path = minPath;
            else path = -1;
        }

        private Queue<Integer> bfsaux(Digraph Gr, int s) {
            while (!distToCash.isEmpty())
                distTo[distToCash.pop()] = INFINITY;
            while (!markedCash.isEmpty())
                marked[markedCash.pop()] = false;
            Queue<Integer> q = new Queue<Integer>();
            marked[s] = true;
            markedCash.push(s);
            distTo[s] = 0;
            distToCash.push(s);
            q.enqueue(s);
            while (!q.isEmpty()) {
                int v = q.dequeue();
                for (int w : Gr.adj(v)) {
                    if (!marked[w]) {
                        edgeTo[w] = v;
                        distTo[w] = distTo[v] + 1;
                        distToCash.push(w);
                        marked[w] = true;
                        markedCash.push(w);
                        q.enqueue(w);
                        reversePost.enqueue(w);
                    }
                }
            }
            return reversePost;
        }


        public int getAncestor(int v, int w) {
            ancestor = INFINITY;
            bfs(v, w);
            if (ancestor <= Graph.V()) return ancestor;
            else return -1;
        }

        public int getAncestor(Iterable<Integer> v, Iterable<Integer> w) {
            ancestor = INFINITY;
            bfs(v, w);
            if (ancestor <= Graph.V()) return ancestor;
            else return -1;
        }

        public int getPath(int v, int w) {
            ancestor = INFINITY;
            bfs(v, w);
            if (path <= Graph.E()) return path;
            else return -1;
        }

        public int getPath(Iterable<Integer> v, Iterable<Integer> w) {
            ancestor = INFINITY;
            bfs(v, w);
            if (path <= Graph.E()) return path;
            else return -1;
        }
    }

}

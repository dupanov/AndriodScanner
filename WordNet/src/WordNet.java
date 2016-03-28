import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;

import java.io.File;
import java.util.HashSet;

/**
 * Created by admin on 19.03.2016.
 */
public class WordNet {
    private ST<Integer, String[]> synsetMap;
    private String[] fields;
    private Digraph G;
    private String synsetsFilePath;
    private String hypernymsFilePath;
    private SAP sap;
    private ST<String, HashSet<Integer>> getSynset;
    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms)
    {
        synsetsFilePath = synsets;
        hypernymsFilePath = hypernyms;
        File synsetsFile = new File(synsetsFilePath);
        File hypernymsFile  = new File(hypernymsFilePath);
        In hypernymsStream = new In(hypernymsFile);
        In synsetsStream = new In(synsetsFile);
        synsetMap = new ST<>();
      //  vocab = new HashMap<>();
        getSynset = new ST<>();
        HashSet<Integer> stk;

        //parse synset file with num, str_str str2, description
        while(synsetsStream.hasNextLine()) {
            String synset = synsetsStream.readLine();
            //split string to String[] array per comma
            fields = synset.split(",");
            //already put fist field (vertex num) into digraph
            Integer vertexNum = Integer.parseInt(fields[0]);
            //get nouns from second field
            //and split it into String[] array per space symbol
            // "\\s"
            String[] nouns = fields[1].split("\\s");

            synsetMap.put(vertexNum, nouns);
            for (String str: nouns) {
                stk = new HashSet<>();
                if (getSynset.get(str) != null)
                    stk = getSynset.get(str);
                if (!stk.contains(vertexNum))
                    stk.add(vertexNum);
                if (getSynset.get(str) != stk)
                    getSynset.put(str, stk);
            }
            }

        G = new Digraph(synsetMap.size());

        int root = 0;
        while (hypernymsStream.hasNextLine()) {
            String[] parsedInt = hypernymsStream.readLine().split(",");
            if (parsedInt.length > 1) {
                for (int i = 1; i < parsedInt.length; i++)
                    G.addEdge(Integer.parseInt(parsedInt[0]), Integer.parseInt(parsedInt[i]));
            } else root++;
            if (root > 1) throw new IllegalArgumentException("wrong root");
        }
        checkRoot();
        sap = new SAP(G);

    }


    private void checkRoot (){
        int rootNums = 0;
        for (int i = 0; i < G.V(); i++) {
            if (G.outdegree(i) == 0 && G.indegree(i) != 0) rootNums++;
            if (G.outdegree(i) == 0 && G.indegree(i) == 0) throw new IllegalArgumentException("wrong root1");
            if (rootNums > 1) throw new IllegalArgumentException("wrong roots");
        }
        if (rootNums == 0) throw new IllegalArgumentException("wrong rooot");

    }


    // returns all WordNet nouns
    public Iterable<String> nouns()
    {
        return getSynset.keys();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word)
    {
        if (word == null) throw new NullPointerException();
        if (getSynset.contains(word)) return true;
        return false;
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB)
    {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        if (nounA == null || nounB == null) throw new NullPointerException();

        return sap.length(search(nounA), search(nounB));
    }

    private HashSet<Integer> search (String noun) {
        return getSynset.get(noun);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)

    public String sap(String nounA, String nounB)
    {
        if (!isNoun(nounA) || !isNoun(nounB)) throw new IllegalArgumentException();
        if (nounA == null || nounB == null) throw new NullPointerException();
        HashSet<Integer> nounAset = search(nounA);
        HashSet<Integer> nounBset = search(nounB);
        int ancestor;
        int nounAint = -1;
        int nounBint = -1;
        if (nounAset.size() == 1 && nounBset.size() == 1) {
            for (int i: nounAset) {
                nounAint = i;}
            for (int i: nounBset) {
                    nounBint = i;}

            ancestor = sap.ancestor(nounAint, nounBint);
        } else ancestor = sap.ancestor(search(nounA), search(nounB));
        return toString(synsetMap.get(ancestor));
    }

    private String toString(String[] str) {
        StringBuilder bld = new StringBuilder();
        for ( String temp: str) {
            bld.append(temp);
            if (str.length > 1) bld.append(" ");

        }
        return bld.toString();
    }

    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wn = new WordNet(args[0], args[1]);
        //System.out.println(wn.nouns());
        System.out.println(wn.sap("pickaxe", "climatology"));

    }
}
